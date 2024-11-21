package kr.seula.javatest.domain.comment;


import kr.seula.javatest.domain.diary.Diary;
import kr.seula.javatest.domain.diary.DiaryRepository;
import kr.seula.javatest.domain.member.MemberEntity;
import kr.seula.javatest.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final MemberService memberService;

    public void createComment(Long diaryId, String content) {
        MemberEntity currentUser = memberService.getCurrentUser(); // 현재 로그인한 사용자
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setDiary(diary);
        comment.setMember(currentUser);
        commentRepository.save(comment);
    }


    // 댓글 수정
    public void updateComment(Long commentId, String content, MemberEntity currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 댓글 작성자가 아니면 수정할 수 없음
        if (!comment.getMember().equals(currentUser)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        comment.setContent(content);
        commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, MemberEntity currentUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        // 댓글 작성자가 아니면 삭제할 수 없음
        if (!comment.getMember().equals(currentUser)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
