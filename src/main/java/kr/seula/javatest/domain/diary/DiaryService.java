package kr.seula.javatest.domain.diary;

import kr.seula.javatest.domain.member.MemberEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
