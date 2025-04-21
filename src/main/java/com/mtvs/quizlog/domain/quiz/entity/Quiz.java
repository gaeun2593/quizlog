package com.mtvs.quizlog.domain.quiz.entity;


import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="quizzes")
public class Quiz {
    public Quiz(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id",nullable = false,columnDefinition = "bigInt")
    private Long quizId;

    @Column(name = "title",nullable = false,columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(name = "answer" , nullable = false ,columnDefinition = "TEXT")
    private String answer;

    @Column(name = "hint" , nullable = false , columnDefinition = "TEXT")
    private String hint;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


   // @ManyToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "bookmark_id")
   // private Bookmark bookmark;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="chapter_id", referencedColumnName = "chapter_id")
    Chapter chapter;

    protected Quiz() {

    }

    public String getAnswer() {
        return answer;
    }

    public String getTitle() {
        return title;
    }
}
