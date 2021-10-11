package hello.jpa.proxy_lazyLoding;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * JPA proxy 테스트
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("memberA");
            member.addTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 조인쿼리가 기본적으로 발생한다.
            // --> Team은 필요없는 경우라면 어떻게 해야할까
            //Member findMember = em.find(Member.class, member.getId());
            //System.out.println(findMember.getClass());

            // 이미 영속성 컨텍스트에 엔티티 객체가 있다면
            // 그 뒤의 getReference()를 호출하여도 실제 엔티티를 반환한다.

            //Member refMember = em.getReference(Member.class, member.getId());
            //System.out.println(refMember.getClass());
            // class hello.jpa.proxy_lazyLoding.Member$HibernateProxy$x7UKM9TH --> 객체를 사용하기 전에는 쿼리가 발생하지 않는다.

            /* 지연로딩 */
            Member m1 = em.find(Member.class, member.getId()); // member테이블에만 쿼리 전달
            System.out.println(m1.getClass()); // 진짜 객체

            // Team 객체는 프록시 객체이다.
            System.out.println(m1.getTeam().getClass());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
