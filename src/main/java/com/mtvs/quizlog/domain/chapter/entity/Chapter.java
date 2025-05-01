package com.mtvs.quizlog.domain.chapter.entity;


import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.lesson.entity.Lesson;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.solvedQuiz.entity.UserCheckedQuiz;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="chapters")
@Getter
@Setter
public class Chapter {

    //    PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long id;


    @Column(name ="title" ,columnDefinition = "VARCHAR(255)")
    private String title;


    @Column(name ="description" ,columnDefinition = "TEXT")
    private String description;

    @Column(name ="criteria" ,columnDefinition = "INT")
    private int criteria;


    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

// quiz 외래키 매핑.
    @OneToMany(mappedBy = "chapter")
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "chapter")
    private List<UserCheckedQuiz> userCheckedQuizs = new ArrayList<>();

    //    User
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
    
    // chapter folder와 다대일 맵핑
    @ManyToOne
    @JoinColumn(name = "folder_chapter_id", nullable = true)
    private FolderChapter folderChapter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;



    public static Chapter createChapter(User user , String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Chapter chapter = new Chapter();
        chapter.setUser(user);
        chapter.setTitle(title);
        chapter.setDescription(description);
        chapter.setCreatedAt(createdAt);
        chapter.setUpdatedAt(updatedAt);
        return chapter;

    }

    /*
    @ManyToOne()
    @JoinColumn(name="folder_quizset_id",referencedColumnName = "folder_quizset_id")
    private FolderChapter folderChapter;

*/

/*
    @ManyToOne()
    @JoinColumn(name="lesson_id", referencedColumnName = "user_id")
    private Lesson lesson;

*/



}
