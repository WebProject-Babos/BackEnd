package hub.babos.pkuweb.comment.service;

import hub.babos.pkuweb.comment.repository.CommentRepository;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 id를 통해 댓글을 반환")
    void findComments() {

    }

}