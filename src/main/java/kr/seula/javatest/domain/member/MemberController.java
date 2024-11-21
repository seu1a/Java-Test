package kr.seula.javatest.domain.member;

import kr.seula.javatest.domain.member.dto.request.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(MemberRegisterRequest dto) {
        memberService.createUser(dto);
        return ResponseEntity.ok("회원가입 완료");
    }

}
