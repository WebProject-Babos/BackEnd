package hub.babos.pkuweb.post.dto;

import hub.babos.pkuweb.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsElementResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String nickname;
    private final int commentCount;
    private final int likeCount;
    private final boolean liked;


    @Builder
    public PostsElementResponse(Long id, String title, String content, String nickname, int commentCount, int likeCount, boolean liked) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.liked = liked;
    }

    public static PostsElementResponse from(Post post) {
        return PostsElementResponse.from(post, false);
    }

    public static PostsElementResponse from(Post post, boolean liked) {
        return PostsElementResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getAuthor().getNickname())
                .commentCount(post.getCommentCount())
                .likeCount(post.getLikeCount())
                .liked(liked)
                .build();
    }
}
