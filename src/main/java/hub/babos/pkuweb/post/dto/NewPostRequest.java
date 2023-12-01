package hub.babos.pkuweb.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NewPostRequest {

    @NotBlank
    private final String title;
    private final String content;

    @Builder
    public NewPostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
