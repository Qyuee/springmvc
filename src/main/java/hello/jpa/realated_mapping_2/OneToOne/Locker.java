package hello.jpa.realated_mapping_2.OneToOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "OneToOne_LCK")
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOCKER_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    // 일대일 양방향으로? -> 읽기전용
    @OneToOne(mappedBy = "locker")  // Member 엔티티에 있는 locker를 의미
    private Member member;
}
