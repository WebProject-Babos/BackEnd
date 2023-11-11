package hub.babos.pkuweb.post.service;

import hub.babos.pkuweb.post.dto.PostsElementResponse;
import hub.babos.pkuweb.post.dto.PostsResponse;
import hub.babos.pkuweb.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    public PostServiceImpl(PostRepository postRepository) {
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
}
