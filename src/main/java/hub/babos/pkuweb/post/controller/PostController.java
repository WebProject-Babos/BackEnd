package hub.babos.pkuweb.post.controller;

import hub.babos.pkuweb.post.dto.PostsResponse;
import hub.babos.pkuweb.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
