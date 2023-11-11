package hub.babos.pkuweb.message.dto;

import hub.babos.pkuweb.message.domain.Message;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessagesElementResponse {

    private final Long id;
    private final String senderNickname;
    private final String receiverNickname;
    private final String content;
    private final LocalDateTime date;


    @Builder
    public MessagesElementResponse(Long id, String senderNickname, String receiverNickname, String content, LocalDateTime date) {
        this.id = id;
        this.senderNickname = senderNickname;
        this.receiverNickname = receiverNickname;
        this.content = content;
        this.date = date;
    }

    public static MessagesElementResponse from(Message message) {
        return MessagesElementResponse.builder()
                .id(message.getId())
                .senderNickname(message.getSender().getNickname())
                .receiverNickname(message.getReceiver().getNickname())
                .content(message.getContent())
                .date(message.getCreatedAt())
                .build();
    }
}
