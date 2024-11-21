package kr.seula.javatest.domain.comment.dto;


import java.time.LocalDateTime;

public record Comment(
        Long id,
        String username,
        String content,
        LocalDateTime createdAt
) {
}
