package hub.babos.pkuweb.like.controller;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.like.dto.PostLikeResponse;
import hub.babos.pkuweb.like.service.LikeService;
import hub.babos.pkuweb.support.token.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private final LikeService likeService;


    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PutMapping("/posts/{postId}/like")
    public ResponseEntity<PostLikeResponse> likePost(@PathVariable("postId") Long postId, @Login AuthInfo authInfo) {
        PostLikeResponse response = likeService.likePost(postId, authInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/{postId}/isLiked")
    public ResponseEntity<PostLikeResponse> getLiked(@PathVariable("postId") Long postId, @Login AuthInfo authInfo) {
        PostLikeResponse response = likeService.getLiked(postId, authInfo);
        return ResponseEntity.ok(response);
    }
}
