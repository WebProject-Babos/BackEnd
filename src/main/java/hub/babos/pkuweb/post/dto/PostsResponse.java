package hub.babos.pkuweb.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostsResponse {

    private final List<PostsElementResponse> posts;

    public PostsResponse(List<PostsElementResponse> posts) {
        this.posts = posts;
    }
}
