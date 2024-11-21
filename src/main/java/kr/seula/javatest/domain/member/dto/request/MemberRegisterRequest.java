package kr.seula.javatest.domain.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegisterRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}
