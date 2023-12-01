package hub.babos.pkuweb.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hub.babos.pkuweb.post.dto.NewPostRequest;
import hub.babos.pkuweb.post.service.PostService;
import hub.babos.pkuweb.support.AuthInterceptor;
import hub.babos.pkuweb.support.token.AuthenticationPrincipalArgumentResolver;
import hub.babos.pkuweb.support.token.TokenManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    protected TokenManager tokenManager;

    @MockBean
    protected AuthInterceptor authInterceptor;

    @MockBean
    protected AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
        Mockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @Test
    @DisplayName("글 작성 요청 시, 새로운 개시글 등록")
    void addPost() throws Exception {
        NewPostRequest newPostRequest = NewPostRequest.builder()
                .title("post title")
                .content("post content")
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .header("Authorization", "any")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJSONString(newPostRequest)))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(201);
    }

    @Test
    @DisplayName("게시글 제목만 없을 경우, 400을 반환")
    void addPost_exception_noTitle() throws Exception {
        NewPostRequest newPostRequest = NewPostRequest.builder()
                .title(" ")
                .content("post content")
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .header("Authorization", "any")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJSONString(newPostRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("게시글 본문만 없을 경우, 201을 반환")
    void addPost_exception_noContent() throws Exception {
        NewPostRequest newPostRequest = NewPostRequest.builder()
                .title("title")
                .content(null)
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .header("Authorization", "any")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJSONString(newPostRequest)))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    private String convertToJSONString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}