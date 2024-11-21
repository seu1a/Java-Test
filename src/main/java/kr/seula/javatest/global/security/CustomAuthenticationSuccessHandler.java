package kr.seula.javatest.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.seula.javatest.domain.member.MemberType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String url = getTargetUrl(user, session);
        session.setMaxInactiveInterval(60);
        response.sendRedirect(url);
    }

    private String getTargetUrl(UserDetails user, HttpSession session) {
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", MemberType.USER.getName());
        return "/";
    }

}
