package com.mtvs.quizlog.domain.folder.folderbookmarks.entity;


import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="folder_bookmarks")
public class FolderBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_bookmark_id")
    private int folderBookmarkId;

    @Column(name = "folder_bookmark-title")
    private String folderBookmarkTitle;

    public FolderBookmark(String folderBookmarkTitle) {
        this.folderBookmarkTitle = folderBookmarkTitle;
    }

    //user랑 다대일
    @ManyToOne
    @JoinColumn(name = "user_id") // 외래키 이름
    private User user;

    @OneToMany(mappedBy = "folderBookmark")
    private List<Quiz> quizzes = new ArrayList<>();

    public FolderBookmark() {
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFolderBookmarkTitle() {
        return folderBookmarkTitle;
    }

    public void setFolderBookmarkTitle(String folderBookmarkTitle) {
        this.folderBookmarkTitle = folderBookmarkTitle;
    }

    public int getFolderBookmarkId() {
        return folderBookmarkId;
    }

    public void setFolderBookmarkId(int folderBookmarkId) {
        this.folderBookmarkId = folderBookmarkId;
    }

    @Override
    public String toString() {
        return "FolderBookmark{" +
                "folderBookmarkId=" + folderBookmarkId +
                ", folderBookmarkTitle='" + folderBookmarkTitle + '\'' +
                ", user=" + user +
                '}';
    }
}
