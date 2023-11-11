package hub.babos.pkuweb.auth.service;

import hub.babos.pkuweb.auth.domain.RefreshToken;
import hub.babos.pkuweb.auth.repository.RefreshTokenRepository;
import hub.babos.pkuweb.support.token.TokenManager;
import hub.babos.pkuweb.support.token.exception.InvalidRefreshTokenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenManager tokenManager;


    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, TokenManager tokenManager) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenManager = tokenManager;
    }

    @Transactional
    public void saveToken(String token, Long memberId) {
        deleteToken(memberId);
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(memberId)
                .token(token)
                .build();
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void matches(String refreshToken, Long memberId) {
        RefreshToken savedToken = refreshTokenRepository.findByMemberId(memberId)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (!tokenManager.isValid(savedToken.getToken())) {
            refreshTokenRepository.delete(savedToken);
            throw new InvalidRefreshTokenException();
        }
        savedToken.validateSameToken(refreshToken);
    }

    @Transactional
    public void deleteToken(Long memberId) {
        refreshTokenRepository.deleteAllByMemberId(memberId);
    }
}
