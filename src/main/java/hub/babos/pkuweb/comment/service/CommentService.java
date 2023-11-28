package hub.babos.pkuweb.comment.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.comment.dto.CommentsResponse;
import hub.babos.pkuweb.comment.dto.NewCommentRequest;

public interface CommentService {
    CommentsResponse findComments(Long postId);

    Long addComment(Long postId, NewCommentRequest newCommentRequest, AuthInfo authInfo);
}
