package hello.springmvc.basic;

import lombok.Data;


@Data   // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstrutor을 모두 적용해줌
public class HelloData {
    private String username;
    private int age;
}
