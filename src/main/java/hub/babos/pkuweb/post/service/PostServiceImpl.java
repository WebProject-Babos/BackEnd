package hub.babos.pkuweb.post.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.exception.MemberNotFoundException;
import hub.babos.pkuweb.like.repository.PostLikeRepository;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.post.domain.Post;
import hub.babos.pkuweb.post.dto.NewPostRequest;
import hub.babos.pkuweb.post.dto.PostsElementResponse;
import hub.babos.pkuweb.post.dto.PostsResponse;
import hub.babos.pkuweb.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    public PostServiceImpl(PostLikeRepository postLikeRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.postLikeRepository = postLikeRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Override
    public PostsResponse findAll() {
        List<PostsElementResponse> postsElementResponses = postRepository.findAll()
                .stream()
                .map(PostsElementResponse::from)
                .toList();

        return new PostsResponse(postsElementResponses);
    }

    @Override
    public PostsResponse findAll(AuthInfo authInfo) {
        Member member = memberRepository.findById(authInfo.getId())
                .orElseThrow(MemberNotFoundException::new);

        List<PostsElementResponse> postsElementResponses = postRepository.findAll()
                .stream()
                .map(post -> {
                    boolean liked = postLikeRepository.existsByPostAndMember(post, member);
                    return PostsElementResponse.from(post, liked);
                })
                .toList();

        return new PostsResponse(postsElementResponses);
    }

    @Override
    @Transactional
    public Long addPost(NewPostRequest newPostRequest, AuthInfo authInfo) {
        Member author = memberRepository.findById(authInfo.getId())
                .orElseThrow(MemberNotFoundException::new);

        Post post = createPost(newPostRequest, author);
        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    private Post createPost(NewPostRequest newPostRequest, Member author) {
        return Post.builder()
                .title(newPostRequest.getTitle())
                .content(newPostRequest.getContent())
                .author(author)
                .build();
    }

    @Override
    public PostsResponse findMyPosts(AuthInfo authInfo) {
        Member author = memberRepository.findById(authInfo.getId())
                .orElseThrow(MemberNotFoundException::new);

        List<PostsElementResponse> responses = postRepository.findByAuthor(author)
                .stream()
                .map(post -> {
                    boolean liked = postLikeRepository.existsByPostAndMember(post, author);
                    return PostsElementResponse.from(post, liked);
                })
                .toList();

        return new PostsResponse(responses);
    }

    @Override
    public PostsResponse findAllById(Long postId) {
        List<PostsElementResponse> postsElementResponses = postRepository.findAllById(postId)
                .stream()
                .map(PostsElementResponse::from)
                .toList();

        return new PostsResponse(postsElementResponses);
    }
}
