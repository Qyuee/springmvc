package hello.jpa.value_type_collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;

/**
 * JPA embeded test
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
            member.setAddress(new Address("seoul", "서울", "12345"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new Address("busan", "부산", "11111"));
            member.getAddressHistory().add(new Address("suwon", "수원", "22222"));

            // 값 타입 객체들이 모두 같이 영속화 되었다. 마치 Cascade된거 같다.
            // member와 라이프사이클을 모두 함께한다.
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("========================================");
            Member findMember = em.find(Member.class, member.getId());  // 지연로딩이다. == member에 대한 정보만 가져온다.
            // 이 시점에 별도로 Address 테이블에 조회쿼리가 별도로 생성된다.
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            // 값 타입 데이터 수정 => 인스턴스 전체를 교체해줘야 한다. (불변객체)
            findMember.setAddress(new Address("new city", "변경", "1234"));

            // 값 타입 컬렉션 수정
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 대상을 찾을 때, equals()를 사용한다. 그러므로 equals() 재정의를 명확하게 해야한다.
            findMember.getAddressHistory().remove(new Address("busan", "부산", "11111"));
            findMember.getAddressHistory().add(new Address("Incheon", "인천", "33333"));

            // member를 제거하면 address테이블, favoriteFoods 테이블에 데이터가 모두 함께 제거된다.
            //em.remove(member);

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
