package hub.babos.pkuweb.auth.controller;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.auth.dto.LoginRequestDto;
import hub.babos.pkuweb.auth.service.AuthService;
import hub.babos.pkuweb.auth.service.RefreshTokenService;
import hub.babos.pkuweb.support.token.AuthorizationExtractor;
import hub.babos.pkuweb.support.token.Login;
import hub.babos.pkuweb.support.token.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    private final AuthService authService;
    private final TokenManager tokenManager;
    private final RefreshTokenService refreshTokenService;


    public AuthController(AuthService authService, TokenManager tokenManager, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.tokenManager = tokenManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto loginRequestDto) {
        AuthInfo authInfo = authService.login(loginRequestDto);
        String accessToken = tokenManager.createAccessToken(authInfo);
        String refreshToken = tokenManager.createRefreshToken();
        refreshTokenService.saveToken(refreshToken, authInfo.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("Refresh-Token", "Bearer " + refreshToken)
                .build();
    }

    @GetMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request, @Login AuthInfo authInfo) {
        authService.validateExistTokenHeader(request);

        String refreshToken = AuthorizationExtractor.extractRefreshToken(request);
        Long memberId = authInfo.getId();

        refreshTokenService.matches(refreshToken, memberId);

        String accessToken = tokenManager.createAccessToken(authInfo);

        return ResponseEntity.noContent()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@Login AuthInfo authInfo) {
        refreshTokenService.deleteToken(authInfo.getId());
        return ResponseEntity.noContent().build();
    }

}
