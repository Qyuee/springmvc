package hello.jpa.JPQL;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

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

    @BatchSize(size = 100)  // LAZY 로딩에 대한 쿼리를 취합하여 한번에 전달한다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
