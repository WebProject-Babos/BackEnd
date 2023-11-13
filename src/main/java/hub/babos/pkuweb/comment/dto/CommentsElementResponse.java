package hub.babos.pkuweb.comment.dto;

import hub.babos.pkuweb.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentsElementResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;

    @Builder
    public CommentsElementResponse(Long id, String nickname, String content, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static CommentsElementResponse of(Comment comment) {
        return CommentsElementResponse.builder()
                .id(comment.getId())
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
