package hello.jpa.advanced_mapping;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn    // Dtype 컬럼을 만들어준다.
@Table(name = "ITEM")
public abstract class Item extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
