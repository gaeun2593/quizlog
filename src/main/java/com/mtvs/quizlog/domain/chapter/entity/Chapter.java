package com.mtvs.quizlog.domain.chapter.entity;


import com.mtvs.quizlog.domain.lesson.entity.Lesson;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="chapters")
@Getter
@Builder(toBuilder = true)
@SQLRestriction("status <> 'DELETED'")
public class Chapter {
    public Chapter(Long id, String title, String description, int criteria, Status status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, List<Quiz> quizzes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.criteria = criteria;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.quizzes = quizzes;
    }

    //    PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id",nullable = false,columnDefinition = "BIGINT")
    private Long id;


    @Column(name ="title" ,columnDefinition = "VARCHAR(255)")
    private String title;


    @Column(name ="description" ,columnDefinition = "TEXT")
    private String description;

    @Column(name ="criteria" ,columnDefinition = "INT")
    private int criteria;


    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime deletedAt;

// quiz 외래키 매핑.
    @OneToMany(mappedBy = "chapter",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    public Chapter(String title) {
        this.title = title;
    }

    public Chapter(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Chapter() {

    }


//    User
/*    @ManyToOne()
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;


    @ManyToOne()
    @JoinColumn(name="lesson_id", referencedColumnName = "user_id")
    private Lesson lesson;*/

/*

    @ManyToOne()
    @JoinColumn(name="folder_quizset_id",referencedColumnName = "folder_quizset_id")
    private FolderChapter folderChapter;

*/

}
