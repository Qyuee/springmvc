package hello.jpa.JPQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JoinMain {
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

            for (int i = 0; i<10; i++) {
                Member member = new Member();
                member.setUsername("회원"+i);
                member.setAge(i);

                if (i%2 == 0) {
                    member.addTeam(team);
                } else {
                    member.addTeam(team1);
                }

                em.persist(member);
            }

            Member member = new Member();
            member.setAge(30);
            member.setUsername("팀이 없음");
            em.persist(member);

            em.flush();
            em.clear();

            // 조인
            List<Member> findMembers = em.createQuery("select m from Member m inner join m.team t", Member.class).getResultList();
            // Member에 대한 join쿼리가 실행되고, 이어서 team에 대한 쿼리가 별도로 실행된다.
            // JPQL이 그대로 SQL로 번역된다. 프로젝션에 m만 있기에 Member 객체의 team필드에 대해서 별도로 N회 질의 한다.

            // LAZY 로딩이 이 N+1의 문제를 해결해준다? --> 아니다.
            /*

            LAZY로딩으로 설정 했을 때,
            - Team 객체를 사용하지 않는 이상, 일단은 N회 쿼리가 발생하지 않는다.
            - 하지만 team객체를 사용하는 시점에 N+1의 질의가 발생한다.
             */
            List<Member> teamMembers = findMembers.get(0).getTeam().getMembers();   // team에 대한 쿼리가 발생한다.
            List<Member> teamMembers2 = findMembers.get(1).getTeam().getMembers();  // team에 대한 쿼리가 발생한다.
            List<Member> teamMembers3 = findMembers.get(2).getTeam().getMembers();  // 앞서 질의한 team객체가 영속성 컨텍스트에 있으니 더 이상 추가 질의는 없음

            // 결국 현재 member들은 2개의 team에 나누어서 저장된 형태인데,
            // 전체 Member를 조회 할 때, 각 team에 대한 정보를 얻기위해 team의 개수많큼 질의를 더 하고 있다.
            // 1: Member에 대한 질의
            // N: Team에 대한 질의 (여기서는 2회)
            // 총 3회의 질의가 발생한다.

            // N+1질의가 발생하는 이유: JPQL은 연관관계 데이터를 무시하고 해당 엔티티를 기준으로 쿼리를 실행하기 때문

            // 외부 조인
            List<Member> findMembers2 = em.createQuery("select m from Member m left join m.team t", Member.class).getResultList();
            for (Member m : findMembers2) {
                System.out.println("이름:"+m.getUsername()+", 팀명:"+m.getTeam());
            }

            em.flush();
            em.clear();

            // 조인 필터링
            // ex) 회원과 팀을 조인하면서 회원의 이름이 A인 것만 조회한다.
            List<Member> findMembers3 = em.createQuery("select m from Member m join m.team t on t.name = '팀A'", Member.class).getResultList();
            for (Member m : findMembers3) {
                System.out.println("이름:"+m.getUsername()+", 팀명:"+m.getTeam().getName());
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
