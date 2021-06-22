package hello.springcore.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // Thread A : 사용자 A가 10000워 주문
        statefulService1.order("ItemA", 10000);

        // Thread B : 사용자 B가 10000워 주문
        statefulService2.order("ItemB", 20000);

        // 사용자 A가 주문 금액 조회
        int price1 = statefulService1.getPrice();
        System.out.println("price1 = " + price1);

        // 사용자 B가 주문 금액 조회
        int price2 = statefulService2.getPrice();
        System.out.println("price2 = " + price2);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}