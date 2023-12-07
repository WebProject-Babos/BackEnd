package hub.babos.pkuweb.post.controller;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.post.dto.NewPostRequest;
import hub.babos.pkuweb.post.dto.PostsResponse;
import hub.babos.pkuweb.post.service.PostService;
import hub.babos.pkuweb.support.token.Login;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/all")
    public ResponseEntity<PostsResponse> findAll(@Login AuthInfo authInfo) {
        PostsResponse postsResponse;
        if (authInfo.getId() != null)
            postsResponse = postService.findAll(authInfo);
        else
            postsResponse = postService.findAll();
        return ResponseEntity.ok(postsResponse);
    }

    @PostMapping
    public ResponseEntity<Long> addPost(@Valid @RequestBody NewPostRequest newPostRequest, @Login AuthInfo authInfo) {
        Long postId = postService.addPost(newPostRequest, authInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    @GetMapping("/me")
    public ResponseEntity<PostsResponse> findMyPosts(@Login AuthInfo authInfo) {
        PostsResponse postsResponse = postService.findMyPosts(authInfo);
        return ResponseEntity.ok(postsResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostsResponse> findAllbyId(@PathVariable("postId") Long postId) {
        PostsResponse postsResponse = postService.findAllById(postId);
        return ResponseEntity.ok(postsResponse);
    }
}
