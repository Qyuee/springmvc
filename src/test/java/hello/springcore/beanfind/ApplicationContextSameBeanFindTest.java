package hello.springcore.beanfind;

import hello.springcore.core.AppConfig;
import hello.springcore.core.discount.DiscountPolicy;
import hello.springcore.core.member.MemberRepository;
import hello.springcore.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시, 같은 타입이 2개 이상 있으면 중복오류 발생")
    void findByTypeDuplicate() {
        //MemberRepository bean = ac.getBean(MemberRepository.class);
        //System.out.println("bean = " + bean);
        assertThrows(NoUniqueBeanDefinitionException.class, () ->
                ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회 시, 같은 타입이 2개 이상 있으면 빈의 이름을 지정해서 조회하면 된다.")
    void findBeanByName() {
        Object memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        Object memberRepository2 = ac.getBean("memberRepository2", MemberRepository.class);

        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
        assertThat(memberRepository2).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정타입을 모두 조회하기")
    void findBeanByName2() {
        /*String[] beanNamesForType = ac.getBeanNamesForType(MemberRepository.class);
        for (String name : beanNamesForType) {
            Object bean = ac.getBean(name);
            System.out.println("bean = " + bean + " name = " + name);
        }*/
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }

        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
