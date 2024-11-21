package kr.seula.javatest.domain.diary;

import jakarta.persistence.*;
import kr.seula.javatest.domain.comment.Comment;
import kr.seula.javatest.domain.member.MemberEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private MemberEntity author;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>(); // 댓글 목록 추가

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
