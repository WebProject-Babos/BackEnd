package hub.babos.pkuweb.support.token;

import hub.babos.pkuweb.auth.AuthInfo;

public interface TokenManager {

    String createAccessToken(AuthInfo authInfo);

    String createRefreshToken();

    String getPayload(String token);

    AuthInfo getParsedClaims(String token);

    boolean isValid(String token);

    String createNewTokenWithNewNickname(String newNickname, AuthInfo authInfo);
}
