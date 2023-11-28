package hub.babos.pkuweb.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NewPostRequest {

    private final String title;
    private final String content;

    @Builder
    public NewPostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
