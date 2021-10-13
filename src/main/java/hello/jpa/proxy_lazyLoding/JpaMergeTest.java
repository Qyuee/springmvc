package hello.jpa.proxy_lazyLoding;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * JPA proxy 테스트
 */
public class JpaMergeTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            /*Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("memberA");
            member.addTeam(team);
            em.persist(member);

            em.flush();
            em.clear();*/

            /* em.merge() 테스트 */
            Team teamB = new Team();
            teamB.setName("TeamBB");
            em.merge(teamB);        // DB에 데이터가 들어와 있음. 영속화된 이력이 없는 객체에 대해서는 persist와 동일하게 동작하는 것으로 보인다.
            //em.persist(teamB);

            System.out.println("merge한 엔티티가 영속성 컨텍스트에 있을까? "+em.contains(teamB));   // false (당연히 원래 컨텍스트에 없던 객체이므로)
            Team findTeamB = em.find(Team.class, 3L);     // 데이터가 DB에 존재함
            //System.out.println(findTeamB.getName());                // TeamBB

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
