package hub.babos.pkuweb.comment.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.comment.domain.Comment;
import hub.babos.pkuweb.comment.dto.CommentsElementResponse;
import hub.babos.pkuweb.comment.dto.CommentsResponse;
import hub.babos.pkuweb.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentsResponse findComments(Long postId, AuthInfo authInfo) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        List<CommentsElementResponse> responses = comments.stream()
                .map(CommentsElementResponse::of)
                .toList();
        return new CommentsResponse(responses);
    }
}
