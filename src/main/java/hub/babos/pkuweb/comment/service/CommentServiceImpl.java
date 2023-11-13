package hub.babos.pkuweb.comment.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.comment.domain.Comment;
import hub.babos.pkuweb.comment.dto.CommentsElementResponse;
import hub.babos.pkuweb.comment.dto.CommentsResponse;
import hub.babos.pkuweb.comment.dto.NewCommentRequest;
import hub.babos.pkuweb.comment.repository.CommentRepository;
import hub.babos.pkuweb.exception.MemberNotFoundException;
import hub.babos.pkuweb.exception.PostNotFoundException;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.post.domain.Post;
import hub.babos.pkuweb.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentsResponse findComments(Long postId, AuthInfo authInfo) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        List<CommentsElementResponse> responses = comments.stream()
                .map(CommentsElementResponse::from)
                .toList();
        return new CommentsResponse(responses);
    }

    @Override
    @Transactional
    public Long addComment(Long postId, NewCommentRequest newCommentRequest, AuthInfo authInfo) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        Member member = memberRepository.findById(authInfo.getId())
                .orElseThrow(MemberNotFoundException::new);

        Comment comment = Comment.builder()
                        .member(member)
                        .post(post)
                        .content(newCommentRequest.getContent())
                        .build();
        commentRepository.save(comment);

        return comment.getId();
    }
}
