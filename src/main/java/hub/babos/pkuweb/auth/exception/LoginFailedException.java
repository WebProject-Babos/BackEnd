package hub.babos.pkuweb.auth.exception;

import hub.babos.pkuweb.exception.UnauthorizedException;

public class LoginFailedException extends UnauthorizedException {

    private static final String MESSAGE = "아이디나 비밀번호가 잘못되었습니다";

    public LoginFailedException() {
        super(MESSAGE);
    }
}