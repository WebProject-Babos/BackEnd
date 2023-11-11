package hub.babos.pkuweb.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    private final String email;
    private final String password;


    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
