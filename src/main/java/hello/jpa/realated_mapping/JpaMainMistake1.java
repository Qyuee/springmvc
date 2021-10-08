package hello.jpa.realated_mapping;

import javax.persistence.*;
import java.util.List;

/**
 * Member와 Team이 양방향 관계에 있을 때,
 * 관계의 주인이 아닌 Team의 members만 수정한 경우
 */
public class JpaMainMistake1 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            // 팀 입력
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 회원 입력
            Member member = new Member();
            member.setName("member1");
            member.updateTeam(team);
            //member.setTeam(team);
            //team.getMembers().add(member);    // 양방향 관계라면 진짜매핑과 가짜매핑 모두 값을 입력해주자.

            em.persist(member);

            // member1의 FK에 teamA의 FK가 정상적으로 입력되었을까?
            // DB에 TEAM_ID는 null이다.
            // 이유: 연관관계의 주인에 값을 입력하지 않았기 때문.
            // 즉, member 엔티티의 Team객체에 값을 입력했어야 했다.

            Team findTeam = em.find(Team.class, team.getId());  // 1차캐시
            List<Member> findMembers = findTeam.getMembers();

            // 1차캐시된 findTeam.members에는 현재 member 값이 없다.
            System.out.println("======================");
            for (Member m : findMembers) {
                System.out.println("m = " + m.getName());
            }
            System.out.println("======================");

            // findTeam객체의 members에도 member를 추가해주는게 객체지향적으로 생각했을 때, 값을 넣어주는게 좋다.
            // 매번 team.getMembers().add(member); 을 할순 없으니, member, team 엔티티에 모두 값이 세팅 될 수 있는
            // 연관관계 편의 메소드를 생성하자. --> Member::updateTeam()

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
