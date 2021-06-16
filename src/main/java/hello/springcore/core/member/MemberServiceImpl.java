package hello.springcore.core.member;

import org.springframework.stereotype.Service;

//@Service
public class MemberServiceImpl implements MemberService{
    /**
     * DIP 원칙 위배
     * 이유: memberRepository에 대한 인터페이스만 의존되어야 하는데, MemoryMemberRepository라는 구현체까지 의존하고 있다.
     * - MemberServiceImpl은 memberRepository가 어떤 구현체인지 구체적으로 알 필요가 없다.
     * - MemberServiceImpl 입장에서는 회원 정보를 Memory에 저장하던, DB에 저장하던 저장만 되면 된다.
     *
     * ┌─────────────────────────────────────┐
     * │추상화에 의존해야지 구현체에 의존하면 안된다.│
     * └─────────────────────────────────────┘
     */
    private static MemberRepository memberRepository = new MemoryMemberRepository();

    /*@Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
