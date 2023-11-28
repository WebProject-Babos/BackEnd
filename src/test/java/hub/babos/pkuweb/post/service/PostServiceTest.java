package hub.babos.pkuweb.post.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.domain.RoleType;
import hub.babos.pkuweb.post.domain.Post;
import hub.babos.pkuweb.post.dto.NewPostRequest;
import hub.babos.pkuweb.post.dto.PostsResponse;
import hub.babos.pkuweb.post.repository.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@Transactional
class PostServiceTest {

    private final PostRepository postRepository;

    private final PostService postService;

    @PersistenceContext
    EntityManager entityManager;

    private final AuthInfo AUTH_INFO = new AuthInfo(66L, RoleType.USER.getName(), "testUser66");

    private Member testMember;
    private List<Post> testPosts = new ArrayList<>();

    @Autowired
    public PostServiceTest(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @BeforeEach
    void setupData() {
        testMember = Member.builder()
                .id(66L)
                .email("test66@email.com")
                .nickname("testUser66")
                .password("6297d64078fc9abcfe37d0e2c910d4798bb4c04502d7dd1207f558860c2b382e")
                .build();
        for (int i = 0; i < 3; i++) {
            testPosts.add(Post.builder()
                    .title("post title" + i)
                    .content("post content" + i)
                    .author(testMember)
                    .postLikes(new ArrayList<>())
                    .comments(new ArrayList<>())
                    .build());
        }
    }

    @BeforeEach
    void insertData() {
        entityManager.createNativeQuery("ALTER TABLE member AUTO_INCREMENT=5;")
                .executeUpdate();
        entityManager.createNativeQuery(
                        "insert into member (id, email, nickname, password, role_type) values (66, 'test66@email.com', 'testUser66', '6297d64078fc9abcfe37d0e2c910d4798bb4c04502d7dd1207f558860c2b382e', 'ADMIN');")
                .executeUpdate();

    }

    @Test
    @DisplayName("새로운 게시글 작성")
    void addPost() {
        NewPostRequest newPostRequest = NewPostRequest.builder()
                .title("title")
                .content("content")
                .build();

        Long postId = postService.addPost(newPostRequest, AUTH_INFO);
        Post saved = postRepository.findById(postId).orElseThrow();

        Assertions.assertThat(saved.getTitle()).isEqualTo(newPostRequest.getTitle());
        Assertions.assertThat(saved.getContent()).isEqualTo(newPostRequest.getContent());
    }

    @Test
    @DisplayName("본인이 작성한 게시글 조회")
    void findMyPosts() {
        List<Post> savedPosts = new ArrayList<>();
        testPosts.forEach(post -> {
            savedPosts.add(postRepository.save(post));
        });

        PostsResponse postsResponse = postService.findMyPosts(AUTH_INFO);

        Assertions.assertThat(postsResponse.getPosts().size()).isEqualTo(3);
        Assertions.assertThat(postsResponse.getPosts().get(1).getTitle()).isEqualTo(testPosts.get(1).getTitle());
    }
}