package hub.babos.pkuweb.auth.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.auth.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    AuthInfo login(LoginRequestDto loginRequest);
    void validateExistTokenHeader(HttpServletRequest request);
}
