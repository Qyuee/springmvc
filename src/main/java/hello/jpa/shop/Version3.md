**페치 전략 설정**
- 즉시로딩인 부분을 모두 지연로딩으로 변경한다.
- @ManyToOne, @OneToOne은 기본이 즉시로딩이다.
 - @OneToMany는 기본이 지연로딩

**지연로딩으로 변경하는 이유**
- 즉시로딩의 경우 연관관계가 있는 엔티티에 대해서 실제 사용하지 않아도 조인을 통해서 모든 데이터를 영속성 컨텍스트로 가져온다.
- JPQSL에서 N+1의 이슈를 발생시킨다.