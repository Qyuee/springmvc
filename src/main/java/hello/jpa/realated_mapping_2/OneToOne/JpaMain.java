package hello.jpa.realated_mapping_2.OneToOne;

import hello.jpa.realated_mapping_2.oneToMany.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            Locker locker = new Locker();
            locker.setName("1번 사물함");
            em.persist(locker);

            Member member = new Member();
            member.setName("MemberA");
            member.addLocker(locker);   // 주 엔티티와 가짜 엔티티에 모두 locker를 저장
            em.persist(member);

            em.flush();
            em.clear();

            // member까지 join해서 가져옴
            // 지연로딩을 하면 조인을 하지 않을까? -> 실제 참조하는 순간에 쿼리를 날리지 않을까?
            Locker findLocker = em.find(Locker.class, locker.getId());
            System.out.println("locker name:"+findLocker.getName());
            System.out.println("member name:"+findLocker.getMember().getName());

            System.out.println("==================");

            Member findMember = em.find(Member.class, 2L);
            System.out.println("findMember userLocker name: "+findMember.getLocker().getName());

            System.out.println("==================");   // 실제 insert는 commit 이후에 처리됨

            tx.commit();
        } catch (Exception e) {
            tx.rollback();

        } finally {
            em.close();
            emf.close();
        }
    }
}
