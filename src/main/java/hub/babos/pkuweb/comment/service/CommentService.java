package hub.babos.pkuweb.comment.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.comment.dto.CommentsResponse;

public interface CommentService {
    CommentsResponse findComments(Long postId, AuthInfo authInfo);
}
