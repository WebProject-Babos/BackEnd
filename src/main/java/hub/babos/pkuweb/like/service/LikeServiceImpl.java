package hub.babos.pkuweb.like.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.exception.MemberNotFoundException;
import hub.babos.pkuweb.exception.PostNotFoundException;
import hub.babos.pkuweb.like.domain.PostLike;
import hub.babos.pkuweb.like.dto.PostLikeResonse;
import hub.babos.pkuweb.like.repository.PostLikeRepository;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.post.domain.Post;
import hub.babos.pkuweb.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {

    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(PostLikeRepository postLikeRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public PostLikeResonse likePost(Long postId, AuthInfo authInfo) {
        Member member = memberRepository.findById(authInfo.getId())
                .orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        int likeCount = findPostLike(post, member);
        boolean liked = postLikeRepository.existsByPostAndMember(post, member);

        return PostLikeResonse.builder()
                .likeCount(likeCount)
                .isLiked(liked)
                .build();
    }

    private int findPostLike(Post post, Member member) {
        Optional<PostLike> postLike = postLikeRepository.findByPostAndMember(post, member);
        if (postLike.isPresent()) {
            post.deletePostLike(postLike.get());
            postRepository.decreaseLikeCount(post.getId());
            return post.getLikeCount() - 1;
        }

        addNewPostLike(post, member);
        postRepository.increaseLikeCount(post.getId());
        return post.getLikeCount() + 1;
    }

    private void addNewPostLike(Post post, Member member) {
        PostLike postLike = PostLike.builder()
                .member(member)
                .post(post)
                .build();

        postLikeRepository.save(postLike);
    }
}
