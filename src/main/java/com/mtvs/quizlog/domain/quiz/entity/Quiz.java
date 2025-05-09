package com.mtvs.quizlog.domain.quiz.entity;


import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.folder.folderbookmarks.entity.FolderBookmark;
import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

import static com.mtvs.quizlog.domain.chapter.entity.Chapter.createChapter;
import static jakarta.persistence.FetchType.*;

@Entity
@Table(name="quizzes")
@Getter
@Setter
public class Quiz {
//f
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "answer" )
    private String answer;

    @Column(name = "hint")
    private String hint;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 폴더 북마크랑 다대일 맵핑
    @ManyToOne
    @JoinColumn(name = "folder_bookmark_id", nullable = true)
    private FolderBookmark folderBookmark;

   // @ManyToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "bookmark_id")
   // private Bookmark bookmark;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="chapter_id")
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    public static Quiz createQuiz(User user ,Chapter chapter ,String title, String answer, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setTitle(title);
        quiz.setChapter(chapter);
        quiz.setAnswer(answer);
        quiz.setCreatedAt(createdAt);
        quiz.setUpdatedAt(updatedAt);
        return quiz  ;
    }
}
