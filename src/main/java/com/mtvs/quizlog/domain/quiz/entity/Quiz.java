package com.mtvs.quizlog.domain.quiz.entity;


import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name="quizzes")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE lessons SET status = DELETED WHERE id = ?")
@SQLRestriction("status <> 'DELETED'")
public class Quiz {
    public Quiz(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id",nullable = false,columnDefinition = "bigInt")
    private Long id;

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

    @Column(name = "deleted_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime deletedAt;


   // @ManyToOne(fetch = FetchType.LAZY)
  //  @JoinColumn(name = "bookmark_id")
   // private Bookmark bookmark;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="chapter_id", referencedColumnName = "chapter_id")
    Chapter chapter;

}
