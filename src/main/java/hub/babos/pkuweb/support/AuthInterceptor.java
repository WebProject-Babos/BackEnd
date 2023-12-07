package hub.babos.pkuweb.support;

import hub.babos.pkuweb.support.token.AuthorizationExtractor;
import hub.babos.pkuweb.support.token.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;


    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS 리퀘스트일시 토큰체크 안함
        if (Objects.equals(request.getMethod(), "OPTIONS")) {
            return true;
        }

        if (notExistHeader(request)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        String token = AuthorizationExtractor.extractAccessToken(request);
        if (isInvalidToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }

    private boolean notExistHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return Objects.isNull(authorizationHeader);
    }

    private boolean isInvalidToken(String token) {
        return !tokenManager.isValid(token);
    }
}
