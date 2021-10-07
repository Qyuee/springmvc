package hello.jpa.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest001 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Member member = new Member();
            member.setId(3L);
            member.setName("1차캐시 테스트");

            // member 객체를 영속화한다. -> 영속성 컨테스트 내부의 1차 캐시 영역에 member 객체가 저장된다.
            em.persist(member);

            // 3L에 해당하는 member 데이터를 1차 캐시로부터 가져온다.
            // ??? -> select 쿼리가 나가지 않았다.
            Member member1 = em.find(Member.class, 3L);
            System.out.println("member1 = " + member1);

            // 다시 동일한 member를 조회한다. -> 현재 같은 트랜잭션이므로 1차 캐시에 있는 앞서 동일한 member1과 객체가 동일 할 것이다.
            Member member2 = em.find(Member.class, 3L);
            System.out.println("member2 = " + member2);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
