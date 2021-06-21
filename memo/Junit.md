###### 테스트 함수
- isEqualTo: 단순히 값이 동일한지 검사
  ```
  assertThat(findItem).isEqualTo(savedItem);  
  assertThat(items.size()).isEqualTo(2);
  ```
- contains: 대상에 객체나 값이 포함되는지 검사
  ```
  assertThat(items).contains(savedItemA, savedItemB);
  ```
  
- assertThat(items).isSameAs(...)
  ```
  객체 인스턴스가 같은지 비교, ==과 동일
  ```



###### Junit 어노테이션
- @AfterEach: 각 단위테스트가 수행되고 실행되는 함수 (ex. DB초기화)
- @Test
- @BeforeEach: 각 단위테스트 전에 실행되는 함수 (ex. 의존성 주입)
- @DisplayName