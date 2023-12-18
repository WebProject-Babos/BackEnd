package hub.babos.pkuweb.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLikeResponse {

    private int likeCount;
    private boolean isLiked;
}
