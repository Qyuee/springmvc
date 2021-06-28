package hello.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    @Test
    void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        // Bean 등록 시, 초기화/소멸 메소드를 미리 지정한다.
        // destroyMethod는 기본값이 inferred(추론)이다.
        // 즉, 메서드의 이름이 close 혹은 shutdown이라는 이름을 가지고 있으면 자동으로 호출해준다.
        // destroyMethod가 없어도 close 메소드가 호출된다.
        @Bean/*(initMethod = "init", destroyMethod = "close")*/
        public NetworkClient networkClient() {
            // 객체를 생성한 다음에 url에 대한 값이 세팅되었음 -> 객체의 생성과 초기화가 함께 있는 경우
            // 분리해야한다.
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("https://hell-spring.com");
            return networkClient;
        }
    }
}
