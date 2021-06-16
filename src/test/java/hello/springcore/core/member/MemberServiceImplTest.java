package hello.springcore.core.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceImplTest {
    MemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberServiceImpl();

    @AfterEach
    void clear() {
        memberRepository.clear();
    }

    @Test
    void save_test() {
        // given
        Member member1 = new Member(1L, "memberA", Grade.BASIC);

        // when
        memberService.join(member1);
        Member savedMember = memberService.findMember(1L);

        // then
        assertThat(member1).isEqualTo(savedMember);
    }
}