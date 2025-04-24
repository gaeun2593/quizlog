package com.mtvs.quizlog.domain.folder.folderchapter.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "folder_chapters")
public class FolderChapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_chapter_id")
    private int folderChapterId;

    @Column(name = "folder_chapter_title")
    private String folderChapterTitle;

    //users랑 다대일관계 나중에 추가!


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

    @Override
    public String toString() {
        return "FolderChapter{" +
                "folderChapterId=" + folderChapterId +
                ", folderChapterTitle='" + folderChapterTitle + '\'' +
                '}';
    }
}
