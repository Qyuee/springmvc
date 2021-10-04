````
JPA에서 중요한 2가지
````

EntityManager Factory
- 엔티티매니저를 생성하는 역활을 담당한다.
- 고객의 요청(ex. 회원가입/수정/삭제 등 DB와 관련된 작업)이 있는 경우 엔티티 매니저를 생성한다.
- 그 후, 엔티티 매니저는 Conenction Pool을 통해서 DB connection을 획득하고 실제 DB쿼리 실행을 수행한다.


영속성 컨테스트 (논리적인 개념)
- 엔티티를 영구적으로 저장하는 환경
- 엔티티 매니저를 통해서 영속성 컨텍스트에 접근한다. 
- em.persist(entity); -> 엔티티를 DB에 저장한다는 의미가 아니다.  
  엔티티를 영속성 컨테스트에 저장한다는 뜻이다.
  
엔티티의 생명주기
- 비영속 (new/transient)
  - 겍체가 생성된 상태. 아직 컨텍스트에 저장되지 않음.
  - JPA와 아무런 관계가 없다.  
- 영속 (managed)
  - 컨텍스트에 객체를 저장한 상태. (persist한 것)
    ```java
        Member member = new Member(); -> 비영속 상태
        member.setId(1L);
        member.setName("test");
        
        em.persist(member); -> 영속성 컨테스트에 저장함. 영속상태 진입
        -> 아직 DB에 저장된게 아니다.
        
        tx.commit(); -> 트랜잭션이 commit되면 이때서야 쿼리가 실행된다.         
    ```
- 준영속 (detached)
- 삭제 (removed)