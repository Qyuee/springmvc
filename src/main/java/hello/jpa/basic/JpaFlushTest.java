package hello.jpa.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaFlushTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Member member = new Member();
            member.setId(100L);
            member.setName("flush 테스트");

            // 1차 캐시저장, 쓰기 지연 SQL 저장소 저장
            em.persist(member);

            // flush()를 호출하는 경우 -> 쓰기 지연 SQL 저장소에 있는 쿼리가 DB에 전달된다. (commit되는건 아니다.)
            // 1차 캐시도 유지된다.
            System.out.println(" ===> flush 호출 시점");
            em.flush();

            // 이 사이에서 쿼리가 전달된다.

            // 원래는 commit()되었을 때, 쿼리가 DB에 전달된다.
            System.out.println(" ===> commit 호출 시점");
            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
