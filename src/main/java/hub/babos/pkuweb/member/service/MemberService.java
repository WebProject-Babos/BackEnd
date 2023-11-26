package hub.babos.pkuweb.member.service;

import hub.babos.pkuweb.member.dto.SignupRequestDto;

public interface MemberService {

    Long signup(SignupRequestDto signupRequestDto);
    boolean validateUniqueNickname(String nickname);
    boolean validateUniqueEmail(String email);
}
