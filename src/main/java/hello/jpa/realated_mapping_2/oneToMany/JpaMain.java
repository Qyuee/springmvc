package hello.jpa.realated_mapping_2.oneToMany;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setName("memberA");
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");

            // 단점
            // 1. member 테이블에 update 쿼리가 발생한다.
            // 2. team 객체를 조작했는데 member와 관련된 쿼리가 발생한다. -> 혼란
            team.getMembers().add(member);
            em.persist(team);



            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
