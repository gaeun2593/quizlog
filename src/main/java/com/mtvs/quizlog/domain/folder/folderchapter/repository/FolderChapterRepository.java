package com.mtvs.quizlog.domain.folder.folderchapter.repository;

import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderChapterRepository extends JpaRepository<FolderChapter, Integer> {
    Optional<FolderChapter> findByFolderChapterTitle(String folderChapterTitle);
    // user 기준으로 폴더챕터 리스트 조회
    List<FolderChapter> findByUser(User user);
    Optional<FolderChapter> findByFolderChapterTitleAndUserAndFolderChapterIdNot(String title, User user, int id);
    Optional<FolderChapter> findByFolderChapterTitleAndUser(String folderTitle, User user);
    Optional<FolderChapter> findByUserAndFolderChapterId(User user, int folderChapterId);
}
