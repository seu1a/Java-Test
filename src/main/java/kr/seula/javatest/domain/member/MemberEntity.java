package kr.seula.javatest.domain.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberType role;

    private String nickname;

    public static MemberEntity of(Long id, String username, String password, MemberType role, String nickname) {
        return new MemberEntity(id, username, password, role, nickname);
    }

}