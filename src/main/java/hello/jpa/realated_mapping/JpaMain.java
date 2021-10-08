package hello.jpa.realated_mapping;

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
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            /*
            // 회원을 팀에 등록 (외래키 식별자를 직접 다루는 상황이다. 객체지향적이지 않다)
            Member member = new Member();
            member.setName("member1");
            member.setTeamId(team.getId());
            em.persist(member);

            // 회원의 팀 정보를 조회 -> 굉장히 식별자를 지속적으로 호출하여 사용하고 복잡하고 객체지향적이지 않다.
            // Member 엔티티와 Team 엔티티간의 협력관계가 전혀 없다.
            Member findMember = em.find(Member.class, member.getId());
            Long findTeamId = findMember.getTeamId();
            Team findTeam = em.find(Team.class, findTeamId);
            */

            // 연관관계 매핑이 적용된 경우
            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            /*em.flush();
            em.clear();*/

            // 연관관계를 통해서 바로 사용이 가능하다.
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

            List<Member> members = findTeam.getMembers();
            for (Member member1 : members) {
                System.out.println("TEST!!:"+member1.getName());
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
