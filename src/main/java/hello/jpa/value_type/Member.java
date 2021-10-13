package hello.jpa.value_type;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter @Setter
@Table(name = "EMBED_MBR")
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 기간 Period
    @Embedded
    private Period period;

    // 주소 Address
    @Embedded
    private Address homeAddress;

    // 만약 직장주소를 추가하고자 한다면?
    // Address 객체를 재사용 할 수 있다. 근데 속성명이 겹친다.
    // @AttributeOverrides을 통해서 속성을 재정의해준다.
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city",
                    column=@Column(name="work_city")),
            @AttributeOverride(name="street",
                    column=@Column(name="work_steert")),
            @AttributeOverride(name="zipcode",
                    column=@Column(name="work_zipcode"))
    })
    private Address workAddress;

}
