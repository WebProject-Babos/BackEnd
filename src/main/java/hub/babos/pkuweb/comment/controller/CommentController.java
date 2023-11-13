package hub.babos.pkuweb.comment.controller;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.comment.dto.CommentsResponse;
import hub.babos.pkuweb.comment.dto.NewCommentRequest;
import hub.babos.pkuweb.comment.service.CommentService;
import hub.babos.pkuweb.support.token.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Long> addComment(@PathVariable(name = "postId") Long postId,
                                           @RequestBody NewCommentRequest newCommentRequest,
                                           @Login AuthInfo authInfo) {
        Long commentId = commentService.addComment(postId, newCommentRequest, authInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentId);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentsResponse> findComments(@PathVariable("postId") Long postId, @Login AuthInfo authInfo) {
        CommentsResponse commentsResponse = commentService.findComments(postId, authInfo);
        return ResponseEntity.ok(commentsResponse);
    }
}
