package com.mtvs.quizlog.domain.folder.folderbookmarks.repository;

import com.mtvs.quizlog.domain.folder.folderbookmarks.entity.FolderBookmark;
import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderBookmarkRepository extends JpaRepository<FolderBookmark, Integer> {
    List<FolderBookmark> findByUser(User user);
    Optional<FolderBookmark> findByFolderBookmarkTitleAndUser(String folderTitle, User user);
    Optional<FolderBookmark> findByFolderBookmarkTitleAndUserAndFolderBookmarkIdNot(String title, User user, int id);
    Optional<FolderBookmark> findByFolderBookmarkTitle(String folderBookmarkTitle);
    Optional<FolderBookmark> findByUserAndFolderBookmarkId(User user, int folderBookmarkId);
}
