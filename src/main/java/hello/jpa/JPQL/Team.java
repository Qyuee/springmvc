package hello.jpa.JPQL;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "JPQL_TEAM")
public class Team extends BasicEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team2")
    private List<Member> members = new ArrayList<>();
}
