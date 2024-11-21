package kr.seula.javatest.domain.diary;

import kr.seula.javatest.domain.diary.dto.DiaryForm;
import kr.seula.javatest.domain.member.MemberEntity;
import kr.seula.javatest.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final MemberService userService;


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
}
