package kr.seula.javatest.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {

    USER("사용자"), ADMIN("관리자");

    private final String name;

}
