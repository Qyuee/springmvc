package hello.springcore.core.member;

import hello.springcore.core.AppConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceImplTest {
    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    void init() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        memberRepository = appConfig.memberRepository();
    }

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