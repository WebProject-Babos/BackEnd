package hub.babos.pkuweb.post.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.post.dto.NewPostRequest;
import hub.babos.pkuweb.post.dto.PostsResponse;

public interface PostService {

    PostsResponse findAll();

    Long addPost(NewPostRequest newPostRequest, AuthInfo authInfo);
}
