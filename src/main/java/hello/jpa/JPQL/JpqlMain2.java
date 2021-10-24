package hello.jpa.JPQL;

import hello.jpa.JPQL.DTO.MemberDTO;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class JpqlMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("A팀");
            em.persist(team);

            Team team1 = new Team();
            team1.setName("B팀");
            em.persist(team1);

            Member member = new Member();
            member.setUsername("1번 회원");
            member.setAge(20);
            member.setTeam(team);
            em.persist(member);

            Member member1 = new Member();
            member1.setUsername("2번 회원");
            member1.setAge(30);
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            // 상태 필드: 경로탐색의 끝이다.
            String query = "select m.username From Member m";
            List<String> result = em.createQuery(query, String.class).getResultList();
            for (String s : result) {
                System.out.println("s:"+s);
            }

            em.flush();
            em.clear();

            // 단일 값 연관 경로: 묵시적으로 내부 조인(inner join)이 발생한다. 탐색 가능
            // Member에 연관된 team 객체의 name을 탐색한다.
            String query2 = "select m.team From Member m";  // 묵시적으로 join이 발생한다. 명시적으로 표현해주자.
            // String query2 = "select m.team.name From Member m";
            List<Team> result2 = em.createQuery(query2, Team.class).getResultList();
            for (Team t : result2) {
                System.out.println("t:" + t.getName());
            }

            em.flush();
            em.clear();

            /* 컬렉션 값 연관 경로 */
            // 컬렉션 값 연관 경로: 묵시적인 조인이 발생한다. 하지만 탐색은 불가하다. size는 사용가능
            String query3 = "select t.members From Team t"; // 탐색을 하고 싶으면 명시적인 조인을 해야한다.
            Collection result3 = em.createQuery(query3, Collection.class).getResultList();
            for (Object m : result3) {
                System.out.println("m = " + m);
            }

            em.flush();
            em.clear();

            String query4 = "select m.username From Team t join t.members m"; // 탐색을 하고 싶으면 명시적인 조인을 해야한다.
            List<String> result4 = em.createQuery(query4, String.class).getResultList();
            for (String s : result4) {
                System.out.println("s = " + s);
            }

            // 실무에서 권장하는 방식은 묵시적인 join을 사용하지 않는 것이다.
            // 묵시적인 join은 튜닝에 있어서 방해가 되고 혼란을 불러일으킨다.

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
