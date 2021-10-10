package hello.jpa.advanced_mapping;

import hello.jpa.realated_mapping_2.OneToOne.Locker;
import hello.jpa.realated_mapping_2.OneToOne.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * 일대일 관계 매핑 테스트
 create table OneToOne_MBR (
 MEMBER_ID bigint not null,
 USERNAME varchar(255),
 LOCKER_ID bigint,
 primary key (MEMBER_ID)
 )

 // 외래키 제약조건 추가
 alter table OneToOne_MBR
 add constraint FK7xhgei4aagw6glxotgcg0ks5d
 foreign key (LOCKER_ID)
 references OneToOne_LCK

 create table OneToOne_LCK (
 LOCKER_ID bigint not null,
 NAME varchar(255),
 primary key (LOCKER_ID)
 )
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Movie movie = new Movie();
            movie.setDirector("유명한 감독1");
            movie.setActor("유명한 배우1");
            movie.setName("해리포터");
            movie.setPrice(10000);
            movie.setCreatedBy("Lee");
            movie.setCreatedDate(LocalDateTime.now());

            // item insert -> movie insert
            em.persist(movie);

            em.flush();
            em.clear();

            // movie와 item을 조인하여 조회한다.
            Movie findMovie = em.find(Movie.class, 1L);
            System.out.println("findMovie = " + findMovie.getName());

            em.flush();
            em.clear();

            // item을 가져올 때는 관련된 자식테이블을 모두 조인한다.
            /*
            from
                ITEM item0_
            left outer join
                Album item0_1_
                    on item0_.id=item0_1_.id
            left outer join
                Movie item0_2_
                    on item0_.id=item0_2_.id
            left outer join
                Book item0_3_
                    on item0_.id=item0_3_.id
             */
            Item findItem = em.find(Item.class, 1L);
            System.out.println("findItem = " + findItem.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
