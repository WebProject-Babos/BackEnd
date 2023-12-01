package hub.babos.pkuweb.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hub.babos.pkuweb.post.dto.NewPostRequest;
import hub.babos.pkuweb.post.dto.PostsElementResponse;
import hub.babos.pkuweb.post.dto.PostsResponse;
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

import java.util.ArrayList;
import java.util.List;

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

    private final PostsElementResponse POSTS_ELEMENT_RESPONSE1 = PostsElementResponse.builder()
            .id(1L)
            .title("title1")
            .content("content1")
            .likeCount(0)
            .commentCount(0)
            .build();
    private final PostsElementResponse POSTS_ELEMENT_RESPONSE2 = PostsElementResponse.builder()
            .id(2L)
            .title("title2")
            .content("content2")
            .likeCount(0)
            .commentCount(0)
            .build();

    @BeforeEach
    public void setup() throws Exception {
        Mockito.when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @Test
    @DisplayName("권한 없이 모든 게시글 요청 시 게시글 목록 반환")
    void findAll() throws Exception {
        PostsElementResponse[] postsElementResponses = {POSTS_ELEMENT_RESPONSE1, POSTS_ELEMENT_RESPONSE2};
        PostsResponse postsResponse = new PostsResponse(List.of(postsElementResponses));
        Mockito.when(postService.findAll()).thenReturn(postsResponse);
        Mockito.when(postService.findAll(any())).thenReturn(postsResponse);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/posts/all"))
                .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        JsonArray posts = JsonParser.parseString(content).getAsJsonObject().get("posts").getAsJsonArray();

        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(posts.size()).isEqualTo(2);
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

    @Test
    @DisplayName("내가 쓴 글 조회 시, 200을 반환")
    void findMyPosts() throws Exception {
        PostsElementResponse[] postsElementResponses = {POSTS_ELEMENT_RESPONSE1, POSTS_ELEMENT_RESPONSE2};
        PostsResponse postsResponse = new PostsResponse(List.of(postsElementResponses));
        Mockito.when(postService.findMyPosts(any())).thenReturn(postsResponse);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/posts/me")
                .header("Authorization", "any"))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    private String convertToJSONString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}