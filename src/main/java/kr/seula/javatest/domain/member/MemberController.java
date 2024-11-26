package kr.seula.javatest.domain.member;

import kr.seula.javatest.domain.member.dto.request.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public String createUser(MemberRegisterRequest dto) {
        memberService.createUser(dto);
        return "redirect:/login"; // 기본 Spring Security 로그인 페이지로 리다이렉트
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
