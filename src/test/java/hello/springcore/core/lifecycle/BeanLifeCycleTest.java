package hello.springcore.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 스프링 빈은 객체를 생성하고 의존관계를 주입한다.
 * -> 그러면 언제 의존관계가 주입되는가?
 *  -> 콜백!
 *  -> 의존관계가 모두 주입되었으니 너가 할려는 초기화를 하렴
 *
 * 라이프 사이클
 * 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료 (싱글톤의 경우)
 *
 * 빈 생명주기 콜백
 * -> 인터페이스를 통해서 지원 (거의 사용하지 않는 방식)
 *  단점
 *  - 스프링 전용 인터페이스이다.
 *  - 메소드 이름을 변겨 할 수 없다.
 *  - 외부 라이브러리에 적용 할 수 없다.
 *
 * -> 설정 정보에 초기화 메소드
 *
 * -> 어노테이션으로 설정
 */
public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
