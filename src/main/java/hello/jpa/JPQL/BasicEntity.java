package hello.jpa.JPQL;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 각 엔티티에 공통적으로 사용되는 매핑을 분리
 */
@MappedSuperclass
public abstract class BasicEntity {
    private String createBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
