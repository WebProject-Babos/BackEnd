package hub.babos.pkuweb.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.dto.SignupRequestDto;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("새로운 멤버 회원가입")
    public void signup() throws Exception {
        SignupRequestDto requestDto = SignupRequestDto.builder()
                .email("test@test.com")
                .password("pass")
                .nickname("nick")
                .build();

        Member member = requestDto.toEntity();

        Mockito.when(memberRepository.save(any())).thenReturn(member);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/members/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJSONString(requestDto)))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
    }

    private String convertToJSONString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    @DisplayName("중복되지 않은 이메일은 true를 반환")
    public void validateUniqueEmail() throws Exception {
        Mockito.when(memberRepository.existsMemberByEmail(any())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/members/signup/exists")
                .param("email", "valid@email.com"))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(Boolean.parseBoolean(content)).isEqualTo(true);
    }

    @Test
    @DisplayName("중복되는 이메일은 false를 반환")
    public void validateUniqueEmailWithDuplicate() throws Exception {
        Mockito.when(memberRepository.existsMemberByEmail(any())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/members/signup/exists")
                        .param("email", "test1@test.com"))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(Boolean.parseBoolean(content)).isEqualTo(false);
    }

    @Test
    @DisplayName("중복되지 않은 닉네임은 true를 반환")
    public void validateUniqueNickname() throws Exception {
        Mockito.when(memberRepository.existsMemberByNickname(any())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/members/signup/exists")
                        .param("nickname", "nickname"))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertThat(Boolean.parseBoolean(content)).isEqualTo(true);
    }

}