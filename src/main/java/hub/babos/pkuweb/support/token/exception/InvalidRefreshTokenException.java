package hub.babos.pkuweb.support.token.exception;

import hub.babos.pkuweb.exception.UnauthorizedException;

public class InvalidRefreshTokenException extends UnauthorizedException {

    private static final String MESSAGE = "유효하지 않은 리프레시 토큰입니다.";

    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}