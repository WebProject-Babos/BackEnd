package hub.babos.pkuweb.message.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NewMessageRequest {

    private final Long receiverId;
    private final String content;

    @Builder
    public NewMessageRequest(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
    }
}
