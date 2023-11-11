package hub.babos.pkuweb.post.controller;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.post.dto.NewPostRequest;
import hub.babos.pkuweb.post.dto.PostsResponse;
import hub.babos.pkuweb.post.service.PostService;
import hub.babos.pkuweb.support.token.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<PostsResponse> findAll() {
        PostsResponse postsResponse = postService.findAll();
        return ResponseEntity.ok(postsResponse);
    }

    @PostMapping
    public ResponseEntity<Long> addPost(@RequestBody NewPostRequest newPostRequest, @Login AuthInfo authInfo) {
        Long postId = postService.addPost(newPostRequest, authInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }
}
