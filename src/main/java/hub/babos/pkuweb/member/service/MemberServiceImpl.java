package hub.babos.pkuweb.member.service;

import hub.babos.pkuweb.auth.service.PasswordManager;
import hub.babos.pkuweb.member.domain.Member;
import hub.babos.pkuweb.member.dto.SignupRequestDto;
import hub.babos.pkuweb.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordManager passwordManager;


    public MemberServiceImpl(MemberRepository memberRepository, PasswordManager passwordManager) {
        this.memberRepository = memberRepository;
        this.passwordManager = passwordManager;
    }

    @Override
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        Member member = signupRequestDto.toEntity();
        member.encodePassword(passwordManager);

        memberRepository.save(member);
    }

}
