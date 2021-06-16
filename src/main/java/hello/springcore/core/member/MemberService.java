package hello.springcore.core.member;

public interface MemberService {
    void join(Member member);

    Member findMember(Long memberId);
}
