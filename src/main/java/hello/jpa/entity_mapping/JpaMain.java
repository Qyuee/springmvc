package hello.jpa.entity_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // 엔티티매니저 팩토리 -> DB당 하나만 생성된다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 엔티티 매니저는 쓰레드간 공유되어선 안된다. DB Connection처럼 사용하고 버리는 존재이다.
        EntityManager em = emf.createEntityManager();

        // JPA의 모든 쿼리를 트랜잭션내에서 실행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member2 member2 = new Member2();
            member2.setId("ID_A");
            member2.setAge(10);
            member2.setUsername("dslee03");
            member2.setRoleType(RoleType.ADMIN);

            em.persist(member2);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
