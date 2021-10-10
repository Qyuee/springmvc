package hello.jpa.realated_mapping_2.OneToOne;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "OneToOne_MBR")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    // 편의함수 추가
    public void addLocker(Locker locker) {
        locker.setMember(this); // 가짜매핑쪽에 member를 입력
        this.locker = locker;
    }
}
