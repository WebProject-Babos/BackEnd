package hub.babos.pkuweb.auth.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.auth.dto.LoginRequestDto;
import hub.babos.pkuweb.auth.exception.LoginFailedException;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.repository.MemberRepository;
import hub.babos.pkuweb.support.token.exception.TokenNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordManager passwordManager;


    public AuthServiceImpl(MemberRepository memberRepository, PasswordManager passwordManager) {
        this.memberRepository = memberRepository;
        this.passwordManager = passwordManager;
    }

    public AuthInfo login(LoginRequestDto loginRequest) {
        String password = passwordManager.encode(loginRequest.getPassword());
        Member member = memberRepository.findByEmailAndPassword(loginRequest.getEmail(), password)
                .orElseThrow(LoginFailedException::new);
        return new AuthInfo(member.getId(), member.getRoleType().getName(), member.getNickname());
    }

    @Override
    public void validateExistTokenHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshTokenHeader = request.getHeader("Refresh-Token");
        if (Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
            throw new TokenNotFoundException();
        }
    }

}
