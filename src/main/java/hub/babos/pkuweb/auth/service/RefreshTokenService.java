package hub.babos.pkuweb.auth.service;

public interface RefreshTokenService {

    void saveToken(String token, Long memberId);

    void matches(String refreshToken, Long memberId);

    void deleteToken(Long memberId);
}
