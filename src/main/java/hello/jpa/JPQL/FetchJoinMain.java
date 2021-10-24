package hello.jpa.JPQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class FetchJoinMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("팀A");
            em.persist(team);

            Team team1 = new Team();
            team1.setName("팀B");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("팀C");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("회원1");
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(team);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(team1);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("회원4");
            em.persist(member4);

            em.flush();
            em.clear();

            /*
            fetch join을 사용하지 않았을 경우
            Member 엔티티내의 Team이 Lazy 로딩이기에 JPQL을 통해서 얻어온 객체를 통해 team을 조회하기 전까지는 team에 대한 쿼리가 추가적으로 발생하지 않는다.
            하지만, 결국 team에 대한 객체 탐색이 발생하는 순간 team에 대한 쿼리가 별도로 발생한다.
            Lazy 로딩을 통해서 결국 추가적인 N+1 쿼리의 발생시점을 늦추는 것 일 뿐, 근본적인 해결책이 되지 못한다.

            이를 fetch join을 통해서 극복해본다.
             */
            String query = "select m from Member m";
            List<Member> result = em.createQuery(query, Member.class).getResultList();
            for (Member m : result) {
                if (m.getTeam() != null) {
                    System.out.println("회원명:"+m.getUsername()+", team's name = " + m.getTeam().getName());
                } else {
                    System.out.println("회원명:"+m.getUsername()+", team's name: 팀 없음");
                }
            }

            em.flush();
            em.clear();

            /* 일반적인 inner join을 사용했다면? (기본적으로는 지연로딩인 상태) */
            /*
            m 객체내의 team을 사용하기 전, 쿼리
            select
                member0_.id as id1_0_,
                member0_.createBy as createby2_0_,
                member0_.createdDate as createdd3_0_,
                member0_.updatedBy as updatedb4_0_,
                member0_.updatedDate as updatedd5_0_,
                member0_.age as age6_0_,
                member0_.TEAM_ID as team_id8_0_,
                member0_.USERNAME as username7_0_
            from
                JPQL_MBR member0_
            inner join
                JPQL_TEAM team1_
                    on member0_.TEAM_ID=team1_.id

             m 객체내의 team을 사용하는 순간?
             기본적으로 회원의 정보를 구하는 쿼리가 발생한다. (1)
             select
                member0_.id as id1_0_,
                member0_.createBy as createby2_0_,
                member0_.createdDate as createdd3_0_,
                member0_.updatedBy as updatedb4_0_,
                member0_.updatedDate as updatedd5_0_,
                member0_.age as age6_0_,
                member0_.TEAM_ID as team_id8_0_,
                member0_.USERNAME as username7_0_
            from
                JPQL_MBR member0_
            inner join
                JPQL_TEAM team1_
                    on member0_.TEAM_ID=team1_.id

            // 팀의 정보를 구하는 쿼리가 팀의 개수만큼 N번 발생한다. (N)
            select
                team0_.id as id1_3_0_,
                team0_.createBy as createby2_3_0_,
                team0_.createdDate as createdd3_3_0_,
                team0_.updatedBy as updatedb4_3_0_,
                team0_.updatedDate as updatedd5_3_0_,
                team0_.name as name6_3_0_
            from
                JPQL_TEAM team0_
            where
                team0_.id=?

             */
            /*String innerJoinQuery = "select m from Member m inner join m.team";
            List<Member> resultMembers = em.createQuery(innerJoinQuery, Member.class).getResultList();
            for (Member m : resultMembers) {
                System.out.println("m = " + m.getTeam());
            }*/

            em.flush();
            em.clear();

            // fetch join -> JPQL이 엔티티 연관관계에 대해서 생성하지 못하던 쿼리를 구성해준다.
            /*
            select
                member0_.id as id1_0_0_,
                team1_.id as id1_3_1_,
                member0_.createBy as createby2_0_0_,
                member0_.createdDate as createdd3_0_0_,
                member0_.updatedBy as updatedb4_0_0_,
                member0_.updatedDate as updatedd5_0_0_,
                member0_.age as age6_0_0_,
                member0_.TEAM_ID as team_id8_0_0_,
                member0_.USERNAME as username7_0_0_,
                team1_.createBy as createby2_3_1_,
                team1_.createdDate as createdd3_3_1_,
                team1_.updatedBy as updatedb4_3_1_,
                team1_.updatedDate as updatedd5_3_1_,
                team1_.name as name6_3_1_
            from
                JPQL_MBR member0_
            inner join
                JPQL_TEAM team1_
                    on member0_.TEAM_ID=team1_.id
             */
            /*String fetchJoinQuery = "select m from Member m join fetch m.team";
            List<Member> result2 = em.createQuery(fetchJoinQuery, Member.class).getResultList();
            for (Member m : result2) {
                System.out.println("username = " + m.getUsername()+" ,team name = "+m.getTeam().getName());
            }*/


            /* 컬렉션 패치 조인 */
            String collectionFetchJoin = "select distinct t from Team t join fetch t.members m";
            List<Team> resultTeams = em.createQuery(collectionFetchJoin, Team.class).getResultList();
            for (Team t : resultTeams) {
                System.out.println("team name = " + t.getName()+", members size = "+t.getMembers().size());
            }

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
