package hub.babos.pkuweb.support.token;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthorizationExtractor {

    public static final String BEARER_TYPE = "Bearer";

    private AuthorizationExtractor() {
    }

    public static String extractAccessToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        return extract(headers);
    }

    public static String extractRefreshToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Refresh-Token");
        return extract(headers);
    }

    private static String extract(Enumeration<String> headers) {
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (notStartWithBearerString(value))
                continue;
            return extractAuthHeaderValue(value);
        }
        return null;
    }

    private static boolean notStartWithBearerString(String string) {
        return string.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }

    private static String extractAuthHeaderValue(String string) {
        String authHeaderValue = string.substring(BEARER_TYPE.length()).trim();

        int commaIndex = authHeaderValue.indexOf(',');
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }
}
