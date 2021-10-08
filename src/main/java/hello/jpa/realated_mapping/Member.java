package hello.jpa.realated_mapping;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "EX_MBR")
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 테이블에 맞추어서 설계된 경우
    /*@Column(name = "TEAM_ID")
    private Long teamId;*/

    // member 입장에서는 N, Team 입장에서는 1 이다.
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")   // 조인해야하는 컬럼명
    private Team team;

    @Column(name = "USERNAME")
    private String name;

    /**
     * team이라는 연관관계에 대해서 주인과 주인이 아닌쪽 모두 값을 세팅하도록 해주는 편의 메소드
     * @param team
     */
    public void updateTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
