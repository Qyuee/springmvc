package hello.jpa.value_type_collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashSet;

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

            // member를 제거하면 address테이블, favoriteFoods 테이블에 데이터가 모두 함께 제거된다.
            em.remove(member);

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
