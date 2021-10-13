package hello.jpa.value_type;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable @Getter @Setter
public class Period {
    @Column(name = "STARTDATE2")
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 기본 생성자 필수
    public Period() {

    }
}
