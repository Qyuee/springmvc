package hello.jpa.proxy_lazyLoding;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "PROXY_TEAM")
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    // 다대일 양방향
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
