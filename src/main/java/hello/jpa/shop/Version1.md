**엔티티를 DB에 맞추어서 설계한 경우**
- 테이블의 외래키를 그대로 가져온다.
    - 객체 그래프 탐색이 불가능하다.
    
```java
Order order = em.find(Order.class, "주문번호");
int memberId = order.getMemberId(); // 주문을 한 회원의 ID
Member member = em.find(Member.class, memberId);
member -> 주문한 회원의 객체

--- 위에 상태가 과연 객체지향과 어울리는 형태일까?
만약, 객체를 그래프 탐색하여 외래키가 아닌 객체를 바로 얻을 수 있다면?

Order order = em.find(Order.class, "주문번호");
Member member = order.getMember();
```

==> 연관 관계 매핑이 필요하다!