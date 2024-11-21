package kr.seula.javatest.domain.comment.dto;


public record Comment(
        String username,
        String content,
        String createAt
) {
}
