package hub.babos.pkuweb.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLikeResonse {

    private int likeCount;
    private boolean isLiked;
}
