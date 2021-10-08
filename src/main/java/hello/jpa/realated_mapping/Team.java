package hello.jpa.realated_mapping;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "TEAM")
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    // team의 입장에서 하나의 팀은 여려명의 회원을 포함 할 수 있으니 OneToMany이다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
