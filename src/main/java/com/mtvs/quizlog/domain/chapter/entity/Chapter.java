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
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE lessons SET status = DELETED WHERE id = ?")
@SQLRestriction("status <> 'DELETED'")
public class Chapter {

//    PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id",nullable = false,columnDefinition = "BIGINT")
    private Long chapterId;

    @NotNull
    @Column(name ="title" ,columnDefinition = "VARCHAR(255)")
    private String title;

    @NotNull
    @Column(name ="description" ,columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name ="criteria" ,columnDefinition = "INT")
    private int criteria;


    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    private LocalDateTime createdAt ;

    private LocalDateTime updatedAt ;

    private LocalDateTime deletedAt ;

// quiz 외래키 매핑.
    @OneToMany(mappedBy = "chapter_id",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    public Chapter(String title) {
        this.title = title;
    }

    public Chapter(String title, String description) {
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
