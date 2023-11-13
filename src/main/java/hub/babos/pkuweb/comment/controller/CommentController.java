package hub.babos.pkuweb.comment.controller;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.comment.dto.CommentsResponse;
import hub.babos.pkuweb.comment.service.CommentService;
import hub.babos.pkuweb.support.token.Login;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<CommentsResponse> findComments(@PathVariable("postId") Long postId, @Login AuthInfo authInfo) {
        CommentsResponse commentsResponse = commentService.findComments(postId, authInfo);
        return ResponseEntity.ok(commentsResponse);
    }
}
