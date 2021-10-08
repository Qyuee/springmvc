package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    // OrderItem 입장에서는 orderItem은 N, Item은 1 이다.
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int price;
    private int count;
}
