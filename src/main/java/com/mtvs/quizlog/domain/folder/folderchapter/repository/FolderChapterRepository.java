package com.mtvs.quizlog.domain.folder.folderchapter.repository;

import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FolderChapterRepository extends JpaRepository<FolderChapter, Integer> {
    Optional<FolderChapter> findByFolderChapterTitle(String folderChapterTitle);
    Optional<FolderChapter> findByFolderChapterTitleAndFolderChapterIdNot(String folderChapterTitle, int folderChapterId);
    boolean  existsByfolderChapterTitle(String folderTitle);
    void deleteByfolderChapterTitle(String folderTitle);
}
