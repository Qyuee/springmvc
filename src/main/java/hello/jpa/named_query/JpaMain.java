package hello.jpa.named_query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Named Query 테스트
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("회원1");
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("회원2");
            em.persist(member1);

            List<Member> results = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Member m : results) {
                System.out.println("m = " + m);
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
