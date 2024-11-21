package kr.seula.javatest.domain.diary;

import kr.seula.javatest.domain.comment.dto.Comment;
import kr.seula.javatest.domain.diary.dto.DiaryDto;
import kr.seula.javatest.domain.member.MemberEntity;
import kr.seula.javatest.domain.member.MemberType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public void createDiary(String title, String content, MemberEntity author) {
        Diary diary = new Diary();
        diary.setTitle(title);
        diary.setContent(content);
        diary.setAuthor(author);

        diaryRepository.save(diary);
    }

    public Page<DiaryDto> getDiaryList(Pageable pageable) {
        return diaryRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(diary -> new DiaryDto(
                        diary.getId(),
                        diary.getTitle(),
                        diary.getContent(),
                        diary.getAuthor().getUsername(),
                        diary.getCreatedAt(),
                        null
                ));
    }

    public DiaryDto getDiaryById(Long id) {
        // 일기 찾기
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        // 일기 정보와 댓글 목록을 DTO로 변환
        List<Comment> commentList = diary.getCommentList().stream()
                .map(comment -> new Comment(comment.getId(), comment.getMember().getUsername(), comment.getContent(), comment.getCreateAt()))
                .toList();


        // 일기와 댓글 목록을 반환
        return new DiaryDto(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getAuthor().getUsername(),
                diary.getCreatedAt(),
                commentList
//                comments
        );
    }

    public void updateDiary(Long id, String title, String content, MemberEntity currentUser) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        // 작성자가 아니면 수정할 수 없음
        if (!diary.getAuthor().equals(currentUser)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        diary.setTitle(title);
        diary.setContent(content);
        diaryRepository.save(diary);
    }

    // 일기 삭제
    public void deleteDiary(Long id, MemberEntity currentUser) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일기를 찾을 수 없습니다."));

        if (currentUser.getRole() == MemberType.ADMIN) {
            diaryRepository.delete(diary);
            return;
        }

        // 작성자가 아니면 삭제할 수 없음
        if (!diary.getAuthor().equals(currentUser)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        diaryRepository.delete(diary);
    }

}