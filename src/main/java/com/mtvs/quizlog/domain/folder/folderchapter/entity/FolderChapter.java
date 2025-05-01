package com.mtvs.quizlog.domain.folder.folderchapter.entity;


import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "folder_chapters")
public class FolderChapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_chapter_id")
    private int folderChapterId;

    @Column(name = "folder_chapter_title")
    private String folderChapterTitle;

    //users랑 다대일관계
    @ManyToOne
    @JoinColumn(name = "user_id") // 외래키 이름
    private User user;

    @OneToMany(mappedBy = "folderChapter" , cascade = CascadeType.ALL)
    private List<Chapter> chapters = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    protected FolderChapter(){}

    public FolderChapter(String folderChapterTitle) {
        this.folderChapterTitle = folderChapterTitle;
    }

    public int getFolderChapterId() {
        return folderChapterId;
    }

    public void setFolderChapterId(int folderChapterId) {
        this.folderChapterId = folderChapterId;
    }

    public String getFolderChapterTitle() {
        return folderChapterTitle;
    }

    public void setFolderChapterTitle(String folderChapterTitle) {
        this.folderChapterTitle = folderChapterTitle;
    }



}
