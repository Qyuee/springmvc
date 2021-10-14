package hello.jpa.value_type;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            /* 임베디드 값 타입 테스트 */
            /*Member member = new Member();
            member.setUsername("hello");
            member.setHomeAddress(new Address("seoul", "서울시", "12345"));
            em.persist(member);*/

            /* 값 타입 공유 이슈 */
            Address address = new Address("Seoul", "서울", "12345");
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setHomeAddress(address);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setHomeAddress(address);
            em.persist(member2);

            Member findMember = em.find(Member.class, member1.getId());
            // address의 city를 변경하고 싶다. -> 객체를 새롭게 생성해서 넣어준다.
            findMember.setHomeAddress(new Address("Seoul", "부산", "45678"));

            /* 값 타입 비교 */
            Address address1 = new Address("Seoul", "서울", "123456");
            Address address2 = new Address("Seoul", "서울", "123456");
            System.out.println(address1 == address2);
            System.out.println(address1.equals(address2));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
