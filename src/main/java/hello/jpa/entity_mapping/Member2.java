package hello.jpa.entity_mapping;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MBR2")       // 유니크 제약조건은 테이블에서 걸면 이름을 지정 할 수 있다. uniqueConstraints
public class Member2 {
    @Id                                 // pk 매핑
    private String id;

    // insertable: 등록가능 여부
    // updatable: 수정가능 여부
    // nullable: null 허용여부 DDL 생성시
    @Column(name = "name", insertable = true, updatable = false, nullable = false)              // 테이블의 컬럼명을 변경
    private String username;

    // unique: 유니크 제약조건, 유니크 키가 랜덤하게 생성된다.
    // columnDefinition: 컬럼정보를 직접 입력 할 수 있다.
    @Column(unique = true, columnDefinition = "int default 10")
    private Integer age;

    // ORDINAL: enum의 순서를 DB에 저장
    // STRING: enum의 이름을 DB에 저장
    @Enumerated(EnumType.STRING)        // enum 타입 매핑
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)   // 날짜 타입 매핑
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob                                // BLOB, CLOB 매핑
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
