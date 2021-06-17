package hello.springcore.core;

import hello.springcore.core.discount.DiscountPolicy;
import hello.springcore.core.discount.RateDiscountPolicy;
import hello.springcore.core.member.MemberRepository;
import hello.springcore.core.member.MemberService;
import hello.springcore.core.member.MemberServiceImpl;
import hello.springcore.core.member.MemoryMemberRepository;
import hello.springcore.core.order.OrderService;
import hello.springcore.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 정책의 변경에 따라 AppConfig 영역만 변경하면 구성영역의 코드변경 없이 적용이 가능하다.
 */
@Configuration
public class AppConfig {
    /**
     * MemberServiceImpl 객체를 생성하면서 생성자를 통해서 MemoryMemberRepository 주입(설정)된다.
     */
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
