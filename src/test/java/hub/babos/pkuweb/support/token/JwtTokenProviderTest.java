package hub.babos.pkuweb.support.token;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.member.domain.RoleType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private TokenManager tokenManager;

    private final AuthInfo authInfo = AuthInfo.builder()
                .id(3L)
                .nickname("Mike")
                .role(RoleType.USER.getName())
                .build();


    String createAccessToken() {
        return tokenManager.createAccessToken(authInfo);
    }

    String createRefreshToken() {
        return tokenManager.createRefreshToken();
    }

    @Test
    @DisplayName("accessToken과 refreshToken을 생성해요.")
    void generateTokens() {
        String accessToken = createAccessToken();
        String refreshToken = createRefreshToken();

        Assertions.assertThat(tokenManager.getParsedClaims(accessToken).getId()).isEqualTo(authInfo.getId());
        Assertions.assertThat(tokenManager.getParsedClaims(refreshToken).getId()).isNull();
    }

}
