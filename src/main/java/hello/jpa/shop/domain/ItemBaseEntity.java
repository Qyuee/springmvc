package hello.jpa.shop.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class ItemBaseEntity {
    private String createBy;
    private LocalDateTime createdDate;
}
