### JPQL
- JPA를 사용하면 엔티티 객체를 중심으로 개발하게 된다.
- 검색 시, 테이블이 아닌 엔티티 객체를 대상으로 검색한다.
- 모든 DB의 데이터를 객체로 변환하여 검색하는 것은 불가능하니, 결국 조건문이 포함된 SQL을 구성 할 수 있어야 한다.
    - JPA는 SQL을 추상화한 JPQL이라는 `객체 지향 쿼리 언어`를 제공한다.
- SQL문법과 유사하다.
  
- JPQL은 엔티티 객체를 대상으로 쿼리를 실행하는 것이다.
- SQL은 DB 테이블을 대상으로 쿼리를 실행하는 것이다.
```java
List<Member> findMemberList = em.createQuery(
        "select m from Member m where m.name like '%회원%'",
        Member.class
).getResultList();
```

### Criteria (표준스펙에 있지만 잘 사용하지 않음, 유지보수 힘듬)
- JPQL을 사용한 모습을 보면 결국 쿼리는 String이다. 동적 쿼리를 생성하기가 참 까다로울 수 있다.
- 자바코드를 사용해서 JPQL을 생성 할 수 있도록 해준다.
    - 장점
        - 쿼리를 자바코드로 구성하므로 컴파일시 에러 감지 가능
        - 동적 쿼리를 생성하기 비교적 편함
    - 단점
        - 그냥 쿼리보다는 알아보기 힘듬(SQL스럽지 않다)

### QueryDSL (사용 권장)
- 문자가 아닌 자바코드로 JPQL을 작성 할 수 있다.
- JPQL 빌더 역할
- 컴파일 시점에 문법 오류를 찾을 수 있고, 동적쿼리 작성이 용이하다.
- 실무에서 사용을 권장한다.


### 네이티브 SQL
- JPA가 제공하는 SQL을 직접 작성하여 실행
- JPA를 통해서 해결 할 수 없는 데이터베이스에 의존적인 기능


### JPQL 기본 문법
- 엔티티와 속성은 대소문자를 구분한다. (Member, age..)
- JPQL 키워드는 대소문자 구분 X (ex. select)

#### typeQuery
- 반환타입이 명확할 때 사용한다.

---

### 프로젝션
- SELECT 절에 조회 할 대상을 지정하는 것.

#### 종류
- 엔티티 프로젝션: select m from Member m
- 엔티티 프로젝션: select m.team from Member m
  - 암묵적으로 join 쿼리가 발생한다. 왠만하면 JPQL에도 join 쿼리를 명시해서 혼동을 방지하는게 좋다.
- 임베디드 프로젝션: select m.address FROM Member m
- 스칼라 타입 프로젝션: select m.username, m.age FROM Member m

---

### 페이징
- JPA는 페이징을 두 개의 API로 추상화 해놓았다.
1. setFirstResult()
2. setMaxResults()