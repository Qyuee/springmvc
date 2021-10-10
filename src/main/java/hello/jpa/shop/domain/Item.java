package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
// item만 단독으로 저장할 일이 있는가 없는가에 따라서 abstract 추가 여부가 결정된다.
// 현재는 상속 관계 매핑 전략이 단일테이블 이므로 item 테이블은 생성될 것이다.
public abstract class Item extends ItemBaseEntity {
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}