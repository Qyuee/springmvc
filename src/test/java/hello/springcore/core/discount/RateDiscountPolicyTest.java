package hello.springcore.core.discount;

import hello.springcore.core.member.Grade;
import hello.springcore.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyTest {
    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_o() {
        Member member = new Member(1L, "memberA", Grade.VIP);
        int discountPrice = discountPolicy.discount(member, 10000);

        assertThat(discountPrice).isEqualTo(10000 * 10/100);
    }

    @Test
    @DisplayName("VIP가 아니면 10% 할인이 적용되지 않아야 한다.")
    void vip_X() {
        Member member = new Member(1L, "memberA", Grade.BASIC);
        int discountPrice = discountPolicy.discount(member, 10000);

        assertThat(discountPrice).isEqualTo(0);
    }
}