package hub.babos.pkuweb.like.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.like.dto.PostLikeResponse;

public interface LikeService {

    PostLikeResponse likePost(Long postId, AuthInfo authInfo);
    PostLikeResponse getLiked(Long postId, AuthInfo authInfo);
}
