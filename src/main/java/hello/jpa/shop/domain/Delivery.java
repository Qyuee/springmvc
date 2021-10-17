package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "DELIVERY")
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    // 값 타임 임베디드 추가
    @Embedded
    private Address address;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    // 일대일 양방향 (읽기전용)
    // Order의 delivery에 매핑되었다
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;
}
