package hello.jpa.named_query;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "NAMED_TEAM")
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @BatchSize(size = 100)  // LAZY 로딩에 대한 쿼리를 취합하여 한번에 전달한다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
