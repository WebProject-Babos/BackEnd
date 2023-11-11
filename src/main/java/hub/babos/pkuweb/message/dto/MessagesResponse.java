package hub.babos.pkuweb.message.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MessagesResponse {

    private final List<MessagesElementResponse> messages;

    public MessagesResponse(List<MessagesElementResponse> messages) {
        this.messages = messages;
    }
}
