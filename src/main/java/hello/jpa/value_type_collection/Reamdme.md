### 값 타입 컬렉션
- 값 타입을 하나 이상 저장 할 때 사용한다.
- 데이터베이스의 경우 컬렉션을 따로 저장 할 수 있는 구조가 아니다.
    - 그렇기에 별도의 테이블로써 분리를 해주어야 한다.
- @ElementCollection: JPA에게 컬렉션 객체임을 명시해준다.
- @CollectionTable: 값 타입 컬렉션이 별도의 테이블로 구성될 때, 해당 테이블의 이름 및 조인키 설정

### 특징
- 컬렉션들은 소속된 엔티티와 생명주기를 함께한다. (마치 Cascade + 고아객체 삭제 옵션이 있는 것 처럼)
- 값 타입 컬렉션들은 모두 `지연로딩`이 기본이다.
- 임베디드 값 타입은 지연/즉시로딩 개념이 없음 (굳이..? 할 필요가 없으니)


### 값 타입 컬렉션 수정
- 컬렉션내의 데이터만 수정하여도 JPA가 이를 인지하여 DB에 쿼리를 자동으로 생성해준다.

### 음..?
- Set 컬렉션으로 된 데이터를 수정 했을 때는 delete/insert 쿼리가 정상적으로 생성되었다.
```java
// 값 타입 컬렉션 수정
findMember.getFavoriteFoods().remove("치킨");
```
```sql
delete
from
    FAVORITE_FOODS
where
    MEMBER_ID=?
    and FOOD_NAME=?
```

```java
findMember.getFavoriteFoods().add("한식");
```
```sql
insert
into
    FAVORITE_FOODS
    (MEMBER_ID, FOOD_NAME)
values
    (?, ?)
```

- List 컬렉션 값 타입의 데이터를 수정하는 경우에는 다음과 같다.
```java
findMember.getAddressHistory().remove(new Address("busan", "부산", "11111"));
```
```sql
// 잉..? ADDRESS내에 MEMBER_ID에 해당하는 데이터를 모두 제거했다.
delete 
from
    ADDRESS 
where
    MEMBER_ID=?
```
```java
findMember.getAddressHistory().add(new Address("Incheon", "인천", "33333"));
```
```sql
??? insert 쿼리가 두번 발생하였다.
insert 
into
    ADDRESS
    (MEMBER_ID, city, street, zipcode) 
values
    (?, ?, ?, ?)

insert
into
  ADDRESS
  (MEMBER_ID, city, street, zipcode)
values
  (?, ?, ?, ?)
```

이유: 값 타입 컬렉션에 변경사항이 발생하면, 주인 엔티티와 연관된 데이터를 모두 삭제하고,
값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
(만약 1000건이 있으면 1000번의 insert가 발생하겠지.. 와우..)

결론: 값 타입 컬렉션을 사용하지 않는게 좋다. 다른 대안을 찾자.  
다르게 풀어야 한다.

### 값 타입 컬렉션의 한계
- 값 타입은 엔티티와는 다르게 식별자 개념이 없다.
- 값은 변경하면 추적이 어렵다.
- 값 타입 컬렉션에 변경사항이 발생하면, 주인 엔티티와 연관된 데이터를 모두 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
- 값 타입 컬렉션을 매핑하는 테이블은 모두 컬럼을 묶어서 기본키를 구성해야한다.
  - null불가, 중복저장 불가
  

### 값 타입 컬렉션의 대안
- 일대다 연관 관계를 고려한다. (일대다 단방향)
  - 기존 컬렉션에 사용한 값 타입을 일대다를 위해 생성한 엔티티에 포함시켜서 사용한다.
- 영속성 전이와 고아객체 삭제 옵션을 통해서 생명주기를 맞춰준다.
