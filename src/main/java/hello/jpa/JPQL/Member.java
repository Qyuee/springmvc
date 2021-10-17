package hello.jpa.JPQL;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "JPQL_MBR")
@Getter @Setter
public class Member extends BasicEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;
    private int age;

    // team에 대한 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team2;
}
