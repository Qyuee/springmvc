package hello.jpa.advanced_mapping;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 각 엔티티에 공통적으로 포함되는 속성들 정의
 */
@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity {
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModified;
    private LocalDateTime lastModifiedDate;
}
