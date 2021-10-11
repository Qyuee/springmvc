package hello.jpa.proxy_lazyLoding.cascade;

import hello.jpa.proxy_lazyLoding.Member;
import hello.jpa.proxy_lazyLoding.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * JPA proxy 테스트
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Child child1 = new Child();
            child1.setName("child1");

            Child child2 = new Child();
            child2.setName("child2");

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            // 각 객체들을 영속화하기 위해서는 각각 em.persist()를 해야한다.
            em.persist(parent);
            /*em.persist(child1);
            em.persist(child2);*/

            // parent가 persist될 때, child도 함께 persist되기 바란다면. Cascade를 사용해야한다.

            /* child 테이블에 update 쿼리가 발생하는 경우 */
            /*System.out.println("=====================");
            System.out.println("==> before parent.id:"+parent.getId());

            em.persist(child1);
            em.persist(child2);
            em.persist(parent);

            System.out.println("=====================");
            System.out.println("==> after parent.id:"+parent.getId());*/

            // child1, child2에 대한 쿼리가 parent_id를 모르는 상태로 `SQL 임시 저장소`에 저장된다.
            // 이후에 parent를 영속화하면서 알게된 시퀀스 번호로 다시 child에 대한 update 쿼리가 별도로 생성된다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
