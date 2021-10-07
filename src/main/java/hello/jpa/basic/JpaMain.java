package hello.jpa.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // 엔티티매니저 팩토리 -> DB당 하나만 생성된다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 이슈: Could not find any META-INF/persistence.xml file in the classpath
        // springboot에서는 별도로 persistence.xml을 구성 할 필요는 없다. (별도로 구성은 가능하다.)
        // application.properties에 설정을 따로 구성 할 순 있다.

        // 엔티티 매니저는 쓰레드간 공유되어선 안된다. DB Connection처럼 사용하고 버리는 존재이다.
        EntityManager em = emf.createEntityManager();

        // JPA의 모든 쿼리를 트랜잭션내에서 실행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /*
            회원 등록
            Member member = new Member();
            member.setId(2L);
            member.setName("helloB");
            */

            // 회원 조회 (가장 단순)
            Member findMember = em.find(Member.class, 1L);
            System.out.println(findMember.getId()+", "+findMember.getName());

            // 회원 수정
            findMember.setName("Hello JPA");    // 조회한 회원 객체의 name을 변경한다.
            // 수정한 객체를 다시 persist하지 않아도 update가 되었음을 알 수 있다. (update 쿼리가 실행됨)
            // em이 조회한 객체의 정보를 트랜잭션이 commit되기 전까지 정보가 변경되었는지 체크한다.
            // commit하는 시점에 데이터가 변경되었을 경우, update쿼리를 수행한다. 그 후 commit한다.

            // 이슈: Unknown entity: hello.jpa.Member
            /*
                해결: persistence.xml에 Member class를 인식 할 수 있도록 추가해줘야 한다.
                <class>hello.jpa.Member</class>
                스프링 환경에서는 자동으로 엔티티를 스캔하기에 고려 할 필요는 없다.
             */
            // em.persist(member);
            // 쿼리가 실패하거나 이슈상황에서 rollback 할 수도 있다.

            // JPQL
            // member 테이블에 대해서 쿼리를 실행하는게 아니다. Member(엔티티) 객체에 대해서 쿼리를 실행하는 것이다.
            // 왜? 어플리케이션에서 사용하는 JPQL 쿼리가 특정DB에 종속(의존)되지 않게하기 위해서이다. (다른 이유도 있겠지만)
            // 예를 들어서 dialect(방언)을 다른 JPA가 지원하는 DB로 변경하면 그에 맞는 쿼리가 자동으로 생성된다.
            List<Member> findMembers = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();

            for (Member member : findMembers) {
                System.out.println(member.getName());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
