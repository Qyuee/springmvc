package hello.jpa.JPQL;

import hello.jpa.JPQL.DTO.MemberDTO;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpqlMain {
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

            System.out.println("==============");
            Member findMember = em.find(Member.class, member.getId());
            findMember.setTeam(team1);  // dirty checking, team1 엔티티가 1차 캐시에 있기에

            Team findTeam = findMember.getTeam();   // 별도의 쿼리를 전달하지 않음
            System.out.println("==============");
            System.out.println(findTeam.getName());

            /* JPQL 사용해보기 */
            List<Member> findMemberList = em.createQuery(
                    "select m from Member m where m.username like '%회원%'",
                    Member.class
            ).getResultList();

            for (Member mbr : findMemberList) {
                System.out.println(mbr.getUsername());
            }

            /* Criteria 사용해보기 */
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            // Root 클래스 지정
            Root<Member> m = query.from(Member.class);
            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "1번 회원"));
            List<Member> resultList = em.createQuery(cq).getResultList();
            for (Member mbr : resultList) {
                System.out.println("mbr = " + mbr.getUsername());
            }

            /* QueryDSL 사용하기 */
            // 일단은 JPQL의 문법부터 익히는게 중요

            /* 네이티브 쿼리 */
            /* em.createNativeQuery()가 실행되기전에 flush()가 호출된다. */
            System.out.println("===네이티브 query ======");
            List<Member> resultList1 = em.createNativeQuery("select id, username from JPQL_MBR", Member.class)
                    .getResultList();
            for (Member mbr : resultList1) {
                System.out.println(mbr.getUsername());
            }

            em.flush();
            em.clear();

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setAge(10);
            em.persist(member2);

            // 타입이 명확한 경우: TypedQuery
            TypedQuery<Member> mQuery = em.createQuery("select m from Member m where m.username=:username", Member.class);

            // 타입이 명확하지 않은 경우: Query
            Query mQuery2 = em.createQuery("select m.username, m.age from Member m");

            //List<Member> findMembers = mQuery.getResultList();
            // mQuery.getSingleResult(); -> 주의. 진짜 딱 1개여야함
                // 결과가 없으면: NoResultException 발생
                // 두개 이상이여도 NonUniqueResultException 발생

            // 파라미터 바인딩
            mQuery.setParameter("username", "1번 회원");

            List<Member> findMembers2 = mQuery.getResultList();
            for (Member mbr : findMembers2) {
                System.out.println("mbr.getName :"+mbr.getUsername());
            }

            em.flush();
            em.clear();

            /* 프로젝션 테스트 */
            List<Member> findMembers3 = em.createQuery("select m from Member m", Member.class).getResultList();
            Member member3 = findMembers3.get(0);
            member3.setAge(40); // DB에 변경이 되면 영속성 컨텍스트에서 관리가 된다는 것 -> update 쿼리가 발생한다. 영속성 컨텍스트에서 관리한다.

            List<Team> findTeams = em.createQuery("select m.team2 from Member m", Team.class).getResultList();
            Team findTeam2 = findTeams.get(0);
            System.out.println(findTeam2.getId());
            findTeam2.setName("1번팀");

            /* 임베디드 프로젝션 */
            Product product = new Product();
            product.setName("첫번째 상품");
            product.setPrice(1000);
            em.persist(product);

            Address address = new Address("Seoul", "서울", "12345");

            Order order = new Order();
            order.setOrderAmount(1);
            order.setProduct(product);
            order.setAddress(address);
            em.persist(order);

            // 임베디드 프로젝션은 조인이 발생하지 않는다. 딱 그 값에 대한 쿼리만 실행함
            List<Address> findAddress = em.createQuery("select o.address from Order o", Address.class).getResultList();
            for (Address address1 : findAddress) {
                System.out.println(address1);
            }

            /* 스칼라 타입 프로젝션 */
            //List queryType = em.createQuery("select m.username, m.age from Member m").getResultList();
            // Object[] 배열로 조회

            // DTO로 조회
            // 패키지명을 포함한 전체 클래스명 입력 필요
            // 순서와 타입이 일치하는 생성자 필요
            List<MemberDTO> findMemberDto = em.createQuery("select new hello.jpa.JPQL.DTO.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
            for (MemberDTO memberDTO : findMemberDto) {
                System.out.println(memberDTO);
            }

            em.flush();
            em.clear();

            /* 페이징 */
            List<Member> memberPaging = em.createQuery("select m from Member m ORDER BY m.age DESC", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for (Member pagingMbr : memberPaging) {
                System.out.println("pagingMbr = " + pagingMbr);
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
