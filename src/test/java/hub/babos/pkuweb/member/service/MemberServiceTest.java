package hub.babos.pkuweb.member.service;

import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.dto.SignupRequestDto;
import hub.babos.pkuweb.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceTest(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("새로운 사용자 1명 가입")
    void signup() {
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .email("test@test.com")
                .password("pass")
                .nickname("nick")
                .build();

        memberService.signup(requestDto);

        Member savedMember = memberRepository.findByNickname(requestDto.getNickname());

        Assertions.assertThat(savedMember.getEmail())
                .isEqualTo(requestDto.getEmail());
    }

}