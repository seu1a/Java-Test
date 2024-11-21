package kr.seula.javatest.global.security;

import kr.seula.javatest.domain.member.MemberEntity;
import kr.seula.javatest.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(username + " : 멤버가 존재하지 않습니다."));

        return new User(
                memberEntity.getUsername(),
                memberEntity.getPassword(),
                List.of(new SimpleGrantedAuthority(memberEntity.getRole().getName()))
        );
    }

}
