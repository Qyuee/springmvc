package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity @Getter @Setter
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // Order의 입장에서 주문은 N 회원은 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")    // 테이블의 컬럼명을 적어준다.
    private Member member;

    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Delivery와의 1:1 연관 관계
    // DELIVERY_ID(외래키)에 조인
    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)    // order를 persist하면 delivery도 함께 영속화
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    // OrderItem과의 양방향 매핑 추가
    /*
    orderItem의 경우에는 소유자가 order와 item이 있다.
    근데 왜? cascade를 사용한 것일까? -> 소유자가 하나일때만 사용해야한다고 했는데 말이다..
    여기서 소유함이란 외부에서 orderItem을 참조한 것을 의미한다.
    즉, Order에서는 orderItems를 참조하고 있다.
    하지만, item에서는 orderItems를 참조하고 있지않다.

    풀어서 설명하면 OrderItem이 제거되면 Order에는 영향이 간다.
    하지만, item에는 영향이 가지 않는다.
     */
    @OneToMany(mappedBy = "order", cascade = ALL)       // order를 persist하면 orderItem도 함께 영속화
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
