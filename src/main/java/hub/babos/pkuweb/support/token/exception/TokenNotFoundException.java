package hub.babos.pkuweb.support.token.exception;

import hub.babos.pkuweb.exception.UnauthorizedException;

public class TokenNotFoundException extends UnauthorizedException {

    private static final String MESSAGE = "토큰이 존재하지 않습니다.";

    public TokenNotFoundException() {
        super(MESSAGE);
    }
}