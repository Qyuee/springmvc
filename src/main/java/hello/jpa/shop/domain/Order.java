package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // Order의 입장에서 주문은 N 회원은 1
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")    // 테이블의 컬럼명을 적어준다.
    private Member member;

    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // OrderItem과의 양방향 매핑 추가
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 양방향 관계가 설정되었으니 member데이터를 주인과 비주인에 모두 등록해주기 위한 편의 메소드를 생성하자.
    public void changeMember(Member member) {
        this.setMember(member);
        member.getOrders().add(this);
    }

    // OrderItem와의 양방향 매핑 편의 메소드
    public void changeOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
