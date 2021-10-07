package hello.jpa.entity_mapping;

import javax.persistence.*;

@Entity
@Table(name = "MBR3")
public class Member3 {

    /**
     * IDENTITY 전략은 기본키의 생성을 DB에게 위임하는 것이다.
     * -> 근데.. JPS에서 em.persist()를 하면 트랜잭션 커밋직전까지는 영속성 컨텍스트에 1차캐시로서 값을 가지고 있을 것이다.
     * -> 응? 기본키는 DB가 정해주는데? 그러면 영속성 컨텍스트에는 아직 기본키가 없는 엔티티가 있는거 아닌가?
     * -> 그래서 IDENTITY 전략에서는 영속화되는 즉시 바로 DB에 쿼리가 전달된다.
     * -> 실제 테스트를해보면 commit 이전에 insert 쿼리가 전달된다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
