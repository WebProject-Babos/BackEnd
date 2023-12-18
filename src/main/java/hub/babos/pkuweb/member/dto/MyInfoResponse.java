package hub.babos.pkuweb.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyInfoResponse {
    private final Long id;
    private final String nickname;

    @Builder
    public MyInfoResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
