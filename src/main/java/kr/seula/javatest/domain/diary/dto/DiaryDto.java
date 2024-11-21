package kr.seula.javatest.domain.diary.dto;

import kr.seula.javatest.domain.comment.dto.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record DiaryDto(
        Long id,
        String title,
        String content,
        String username,
        LocalDateTime createdAt,
        List<Comment> commentList
) {}