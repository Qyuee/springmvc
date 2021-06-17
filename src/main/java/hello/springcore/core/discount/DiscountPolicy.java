package hello.springcore.core.discount;

import hello.springcore.core.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
