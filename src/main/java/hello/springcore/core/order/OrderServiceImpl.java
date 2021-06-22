package hello.springcore.core.order;

import hello.springcore.core.discount.DiscountPolicy;
import hello.springcore.core.discount.FixDiscountPolicy;
import hello.springcore.core.member.Member;
import hello.springcore.core.member.MemberRepository;
import hello.springcore.core.member.MemoryMemberRepository;

/**
 * 할인 정책을 변경하려고 한다 -> 주문 서비스 클라이언트의 코드를 고쳐야한다.
 */
public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository;
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // 추상화에만 의존하도록 변경 -> 근데 객체가 생성되지 않았기에 NPE 발생
    private final DiscountPolicy discountPolicy; // <- 여기에 누군가가 FixDiscount정책이던 RateDiscount정책을 주입해줘야 한다.

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
