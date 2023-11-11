package hub.babos.pkuweb.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthInfo {

    private final Long id;
    private final String role;
    private final String nickname;

    @Builder
    public AuthInfo(Long id, String role, String nickname) {
        this.id = id;
        this.role = role;
        this.nickname = nickname;
    }
}