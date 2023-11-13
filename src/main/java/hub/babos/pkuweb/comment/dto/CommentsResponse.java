package hub.babos.pkuweb.comment.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentsResponse {

    private final List<CommentsElementResponse> comments;

    public CommentsResponse(List<CommentsElementResponse> comments) {
        this.comments = comments;
    }
}
