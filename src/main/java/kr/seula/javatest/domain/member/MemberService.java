package kr.seula.javatest.domain.member;

import kr.seula.javatest.domain.member.dto.request.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public void createUser(MemberRegisterRequest dto) {
        if (memberRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException(dto.getUsername() + " : 이미 존재하는 유저입니다.");
        }

        System.out.println(bCryptPasswordEncoder.encode(dto.getPassword()));

        MemberEntity memberEntity = MemberEntity.of(
                null,
                dto.getUsername(),
                bCryptPasswordEncoder.encode(dto.getPassword()),
                dto.getIsAdmin() ? MemberType.ADMIN : MemberType.USER
        );

        memberRepository.save(memberEntity);
    }

    public MemberEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }

}
