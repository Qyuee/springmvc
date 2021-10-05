package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaDetachedTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setId(200L);
            member.setName("준영속 테스트");

            em.persist(member); // 1차 캐시 및 쓰기 지연 SQL 저장소 저장

            Member member1 = em.find(Member.class, 200L); // 조회, 이미 1차 캐시에 있으니 SQL 생성X
            System.out.println("member1 = " + member1);
            System.out.println(member == member1); // true, 1차 캐시를 통해 엔티티 동일성이 보장됨

            //em.flush(); // detach() 이전에 flush()하면 준영속상태가 된 이후에도 em.find()를 통해서 데이터를 가져올 수 있을 것으로 예상됨. 이미 SQL이 반영되었으니
            em.detach(member); // member 엔티티를 준영속 상태로 변경, 1차 캐시에서 제거됨 + 쓰기지연 SQL에서도 제거됨
            //em.flush();   // 쓰기지연 SQL에서도 제거되었기에 detach() 이후에는 의미 없음

            // 다시, 200L에 해당하는 데이터를 조회 -> null
            // 사유: 200L에 대한 1차 캐시가 제거되었고, 실제 DB에 쿼리를 날렸을 것이다. 하지만 아직 commit()이 발생하지 않았기에 DB에는 200L에 대한 데이터는 없다.
            Member member2 = em.find(Member.class, 200L);
            System.out.println("member2 = " + member2);
            System.out.println(member1 == member2); // false, member2 엔티티는 find()에 의해서 새롭게 영속화된 엔티티 이므로 앞선 엔티티와는 다름

            // em.flush()가 없으면 SQL은 생성되지 않는다. 즉, DB에 데이터가 없을 것이다.
            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
