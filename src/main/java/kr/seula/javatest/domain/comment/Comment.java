package kr.seula.javatest.domain.comment;

import kr.seula.javatest.domain.diary.Diary;
import kr.seula.javatest.domain.member.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private String content;
    private LocalDateTime createAt;

    // 생성자, Getter, Setter 생략

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
