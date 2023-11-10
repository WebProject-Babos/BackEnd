package hub.babos.pkuweb.member.service;

import hub.babos.pkuweb.member.dto.SignupRequestDto;

public interface MemberService {

    void signup(SignupRequestDto signupRequestDto);
}
