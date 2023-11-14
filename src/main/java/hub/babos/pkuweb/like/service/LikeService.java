package hub.babos.pkuweb.like.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.like.dto.PostLikeResonse;

public interface LikeService {

    PostLikeResonse likePost(Long postId, AuthInfo authInfo);
}
