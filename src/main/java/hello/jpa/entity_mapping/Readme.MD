````
객체와 테이블 매핑
````

@Entity
- @Entity가 붙은 클래스는 JPA가 관리하는 엔티티이다.
- DB의 테이블과 매칭하려면 필수이다.
- 기본생성자 필수 -> lombok으로 대체 가능
- 저장할 필드에 final 사용불가

@Table
- 엔티티와 매핑할 테이블을 지정한다.

@Column
- 컬럼에 대한 제약사항 추가 가능
- name = "컬러명", unique=true, length 등
  (DDL 생성 기능은 DDL 생성에 대해서만 영향을주고 JPA의 실행에는 영향을 주지 않는다.)
  
IDENTITY 전략
