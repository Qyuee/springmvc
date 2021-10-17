package hello.jpa.value_type_collection;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity @Getter @Setter
@Table(name = "MBR")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 임베디드 타입 설정
    @Embedded
    private Address address;

    // 값 타입 컬렉션
    @ElementCollection/*(fetch = FetchType.EAGER)*/
    @CollectionTable(name = "FAVORITE_FOODS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME") // 예외적인 상황: 해당 값은 class가 아니기에 예외적으로 여기서 PK를 설정 할 수 있다.
    private Set<String> favoriteFoods = new HashSet<>();

    /**
     *  Member 테이블과 ADDRESS 테이블은 1:N 관계 이다.
     */
    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();
}
