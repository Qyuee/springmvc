package hello.jpa.named_query;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m From Member m where m.username = :username"
)
@Table(name = "NAMED_MBR")
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String username;
    private int age;

    // team에 대한 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public void addTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
