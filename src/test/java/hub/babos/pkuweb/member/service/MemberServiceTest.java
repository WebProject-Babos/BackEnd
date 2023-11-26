package hub.babos.pkuweb.member.service;

import hub.babos.pkuweb.auth.service.PasswordManager;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.dto.SignupRequestDto;
import hub.babos.pkuweb.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private PasswordManager passwordManager;

    @Test
    @DisplayName("새로운 사용자 1명 가입")
    void signup() {
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .email("test@test.com")
                .password("pass")
                .nickname("nick")
                .build();

        Member member = requestDto.toEntity();

        Mockito.when(memberRepository.save(any()))
                .thenReturn(member);

        Assertions.assertThat(memberService.signup(requestDto)).isEqualTo(member.getId());
    }

}