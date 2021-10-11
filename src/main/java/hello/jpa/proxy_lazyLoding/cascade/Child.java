package hello.jpa.proxy_lazyLoding.cascade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "CHILD")
public class Child {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
