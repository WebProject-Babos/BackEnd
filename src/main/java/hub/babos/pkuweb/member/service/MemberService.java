package hub.babos.pkuweb.member.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.member.dto.MyInfoResponse;
import hub.babos.pkuweb.member.dto.SignupRequestDto;

public interface MemberService {

    Long signup(SignupRequestDto signupRequestDto);
    boolean validateUniqueNickname(String nickname);
    boolean validateUniqueEmail(String email);
    MyInfoResponse getMyInfo(AuthInfo authInfo);
}
