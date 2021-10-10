package hello.jpa.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SHOP_MBR")
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;
    private String city;
    private String street;
    private String zipcode;

    // 양방향에 대한 요구사항이 발생한다.
    // 회원이 주문한 주문목록에 대한 데이터가 필요하다. -> 실제로는 별로 안좋은 설계 (주문목록이 필요하면 Order테이블을 조회하면된다.)
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
