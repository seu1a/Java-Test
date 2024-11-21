package kr.seula.javatest.domain.diary;

import kr.seula.javatest.domain.comment.CommentService;
import kr.seula.javatest.domain.diary.dto.DiaryDto;
import kr.seula.javatest.domain.diary.dto.DiaryForm;
import kr.seula.javatest.domain.member.MemberEntity;
import kr.seula.javatest.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final MemberService userService;
    private final CommentService commentService;


    @GetMapping("/diary/new")
    public String showDiaryForm(Model model) {
        model.addAttribute("diaryForm", new DiaryForm());
        return "diary/new";
    }

    @PostMapping("/diary/new")
    public String createDiary(@Valid DiaryForm diaryForm, BindingResult result) {
        if (result.hasErrors()) {
            return "diary/new";
        }

        MemberEntity currentUser = userService.getCurrentUser(); // 현재 로그인한 사용자 가져오기
        diaryService.createDiary(diaryForm.getTitle(), diaryForm.getContent(), currentUser);
        return "redirect:/"; // 홈으로 리다이렉트
    }

    @GetMapping("/")
    public String listDiaries(
            @RequestParam(defaultValue = "0") int page, // 기본 페이지 0
            @RequestParam(defaultValue = "10") int size, // 기본 페이지 크기 10
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DiaryDto> diaryPage = diaryService.getDiaryList(pageable);

        model.addAttribute("diaryPage", diaryPage);
        return "diary/list";
    }

    // 일기 상세 페이지
    @GetMapping("/diary/{id}")
    public String showDiaryDetail(@PathVariable Long id, Model model) {
        DiaryDto diary = diaryService.getDiaryById(id);
        model.addAttribute("diary", diary);
        model.addAttribute("currentUser", userService.getCurrentUser());
        return "diary/detail";
    }


    // 댓글 작성
    @PostMapping("/diary/{id}/comment/create")
    public String createComment(@PathVariable Long id, @RequestParam String content) {
        commentService.createComment(id, content);
        return "redirect:/diary/" + id;
    }

    @GetMapping("/diary/{id}/edit")
    public String editDiary(@PathVariable Long id, Model model) {
        DiaryDto diary = diaryService.getDiaryById(id);
        DiaryForm diaryForm = new DiaryForm();
        diaryForm.setTitle(diary.title());
        diaryForm.setContent(diary.content());
        model.addAttribute("diaryForm", diaryForm); // 기존 내용으로 폼 채우기
        model.addAttribute("id", id);
        return "diary/edit";
    }

    // 일기 수정 처리
    @PostMapping("/diary/{id}/edit")
    public String updateDiary(@PathVariable Long id, DiaryForm diaryForm, Model model) {
        MemberEntity currentUser = userService.getCurrentUser();
        diaryService.updateDiary(id, diaryForm.getTitle(), diaryForm.getContent(), currentUser);
        return "redirect:/diary/" + id; // 수정 후 상세 보기 페이지로 리다이렉트
    }

    // 일기 삭제
    @GetMapping("/diary/{id}/delete")
    public String deleteDiary(@PathVariable Long id, Model model) {
        MemberEntity currentUser = userService.getCurrentUser();
        diaryService.deleteDiary(id, currentUser);
        return "redirect:/"; // 홈으로 리다이렉트
    }

    // 댓글 수정 처리
    @PostMapping("/diary/{diaryId}/comment/{commentId}/edit")
    public String updateComment(@PathVariable Long diaryId, @PathVariable Long commentId,
                                @RequestParam String content, Model model) {
        MemberEntity currentUser = userService.getCurrentUser();
        commentService.updateComment(commentId, content, currentUser);
        return "redirect:/diary/" + diaryId; // 수정 후 상세 보기 페이지로 리다이렉트
    }

    // 댓글 삭제 처리
    @GetMapping("/diary/{diaryId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long diaryId, @PathVariable Long commentId, Model model) {
        MemberEntity currentUser = userService.getCurrentUser();
        commentService.deleteComment(commentId, currentUser);
        return "redirect:/diary/" + diaryId; // 삭제 후 상세 보기 페이지로 리다이렉트
    }

}
