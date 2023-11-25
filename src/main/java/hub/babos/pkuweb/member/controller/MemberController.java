package hub.babos.pkuweb.member.controller;

import hub.babos.pkuweb.member.dto.SignupRequestDto;
import hub.babos.pkuweb.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;


    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/signup/exists", params = "email")
    public ResponseEntity<Boolean> validateUniqueEmail(@RequestParam String email) {
        Boolean unique = memberService.validateUniqueEmail(email);
        return ResponseEntity.ok(unique);
    }

    @GetMapping(value = "/signup/exists", params = "nickname")
    public ResponseEntity<Boolean> validateUniqueNickname(@RequestParam String nickname) {
        Boolean unique = memberService.validateUniqueNickname(nickname);
        return ResponseEntity.ok(unique);
    }
}