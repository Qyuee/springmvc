package hello.springmvc.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 파라미터 구분을 위한 어노테이션 제작
 * @Target -> 어노테이션을 붙일 수 있는 대상의 정의한다.
 * @Target(ElementType.PARAMETER) -> 메소드 파라미터에 어노테이션을 붙일 수 있음
 * @Target({ElementType.PARAMETER, ElementType.FIELD}) -> 파라미터, 필드에 붙일 수 있음
 *
 * @Retention -> 해당 어노테이션의 메모리를 어디까지 유지 할 것인지를 정의 (어디까지 살아남을지)
 * RetentionPolicy에 따라서 결정된다.
 * - RUNTIME: 런타임에도 어노테이션 정보를 뽑아서 사용 할 수 있음
 *  ex) 스프링이 실행되는 시점에서 @Controller, @Service, @Autowired 등이 필요하므로 해당됨
 *
 * - CLASS: 클래스파일까지만 어노테이션이 유지됨
 *  ex) @Nonnull의 경우
 *
 * - SOURCE: 사실상 주석처럼 사용하는 것으로 컴파일러가 컴파일 시점에 어노테이션의 메모리를 버린다.
 *  ex) 롬복의 @Getter, @Setter의 경우가 RetentionPolicy가 SOURCE인 경우이다.
 *      롬복이 필드들에 대한 getter/setter 바이트코드를 클래스파일에 넣어준다.
 *      즉, class 파일에 @Getter/@Setter에 대한 코드가 남을 필요가 없다.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserInfo {
}
