package hub.babos.pkuweb.member.service;

import hub.babos.pkuweb.auth.dto.AuthInfo;
import hub.babos.pkuweb.auth.service.PasswordManager;
import hub.babos.pkuweb.exception.DuplicateEmailException;
import hub.babos.pkuweb.exception.DuplicateNicknameException;
import hub.babos.pkuweb.exception.MemberNotFoundException;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.dto.MyInfoResponse;
import hub.babos.pkuweb.member.dto.SignupRequestDto;
import hub.babos.pkuweb.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordManager passwordManager;


    public MemberServiceImpl(MemberRepository memberRepository, PasswordManager passwordManager) {
        this.memberRepository = memberRepository;
        this.passwordManager = passwordManager;
    }

    @Override
    @Transactional
    public Long signup(SignupRequestDto signupRequestDto) {
        validate(signupRequestDto);

        Member member = signupRequestDto.toEntity();
        member.encodePassword(passwordManager);

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Override
    public boolean validateUniqueNickname(String nickname) {
        return !memberRepository.existsMemberByNickname(nickname);
    }

    @Override
    public boolean validateUniqueEmail(String email) {
        return !memberRepository.existsMemberByEmail(email);
    }

    public void validate(SignupRequestDto signupRequestDto) {
        if (!validateUniqueEmail(signupRequestDto.getEmail()))
            throw new DuplicateEmailException();
        if (!validateUniqueNickname(signupRequestDto.getNickname()))
            throw new DuplicateNicknameException();
    }

    @Override
    public MyInfoResponse getMyInfo(AuthInfo authInfo) {
        Member member = memberRepository.findById(authInfo.getId())
                .orElseThrow(MemberNotFoundException::new);

        return MyInfoResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
