package kr.seula.javatest.domain.diary.dto;

import kr.seula.javatest.domain.comment.dto.Comment;
import java.util.List;

public record DiaryDto(
        Long id,
        String title,
        String content,
        String username,
        String createAt,
        List<Comment> commentList
) {}
