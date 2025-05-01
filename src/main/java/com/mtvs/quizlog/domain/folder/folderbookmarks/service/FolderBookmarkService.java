package com.mtvs.quizlog.domain.folder.folderbookmarks.service;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.folder.folderbookmarks.dto.FolderBookmarkDTO;
import com.mtvs.quizlog.domain.folder.folderbookmarks.entity.FolderBookmark;
import com.mtvs.quizlog.domain.folder.folderbookmarks.repository.FolderBookmarkRepository;
import com.mtvs.quizlog.domain.folder.folderchapter.dto.FolderChapterDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.folder.folderchapter.repository.FolderChapterRepository;
import com.mtvs.quizlog.domain.folder.folderchapter.service.FolderChapterService;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.repository.QuizRepository;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class FolderBookmarkService {

    //로그정보
    private static final Logger logger = Logger.getLogger(FolderBookmarkService.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FolderBookmarkService.class);

    //Repository 인터페이스 가져옴
    private final FolderBookmarkRepository folderBookmarkRepository;
    private final QuizRepository quizRepository;

    // 생성자 주입
    @Autowired
    public FolderBookmarkService(FolderBookmarkRepository folderBookmarkRepository,QuizRepository quizRepository) {
        this.folderBookmarkRepository = folderBookmarkRepository;
        this.quizRepository = quizRepository;
    }

    // 폴더 생성
    public FolderBookmarkDTO createFolderBookmark(FolderBookmarkDTO folderBookmarkDTO, User user) {
        logger.info("폴더 추가하기 제목 : "+ folderBookmarkDTO.getTitle());

        // 로그인한 user의 폴더중에서만 폴더명 중복검사
        Optional<FolderBookmark> findFolderBookmark = folderBookmarkRepository.findByFolderBookmarkTitleAndUser(folderBookmarkDTO.getTitle(), user);

        //같은 제목이 있으면 예외처리
        if(findFolderBookmark.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다. : " + folderBookmarkDTO.getTitle());
        }

        //같은 제목이 아니면 새 FolderChapter객체를 만들어 저장
        FolderBookmark folderBookmark = new FolderBookmark(folderBookmarkDTO.getTitle());
        folderBookmark.setUser(user); // 유저 연관관계 맵핑

        FolderBookmark savedFolderBookmark = folderBookmarkRepository.save(folderBookmark);

        logger.info("챕터의 폴더 등록 성공 : "+ savedFolderBookmark.getFolderBookmarkId());

        return new FolderBookmarkDTO(savedFolderBookmark.getFolderBookmarkId(), savedFolderBookmark.getFolderBookmarkTitle());
    }

    // 폴더 생성2 ( 챕터 페이지에서 퀴즈를 담은 폴더 생성)
    public FolderBookmarkDTO createfolderBookmark2(FolderBookmarkDTO folderBookmarkDTO, User user, Long quizId) {
        logger.info("폴더 추가하기 제목 : "+ folderBookmarkDTO.getTitle());

        // 로그인한 user의 폴더중에서만 폴더명 중복검사
        Optional<FolderBookmark> findFolderBookmark = folderBookmarkRepository.findByFolderBookmarkTitleAndUser(folderBookmarkDTO.getTitle(), user);

        //같은 제목이 있으면 예외처리
        if(findFolderBookmark.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다. : " + folderBookmarkDTO.getTitle());
        }

        //같은 제목이 아니면 새 FolderChapter객체를 만들어 저장
        FolderBookmark folderBookmark = new FolderBookmark(folderBookmarkDTO.getTitle());
        folderBookmark.setUser(user); // 유저 연관관계 맵핑

        FolderBookmark savedFolderBookmark = folderBookmarkRepository.save(folderBookmark);

        //챕터 id로 챕터 찾음
        Quiz quiz = quizRepository.findQuizById(quizId);

        quiz.setFolderBookmark(savedFolderBookmark);

        quizRepository.save(quiz);

        logger.info("챕터의 폴더 등록 성공 : "+ savedFolderBookmark.getFolderBookmarkId());

        return new FolderBookmarkDTO(savedFolderBookmark.getFolderBookmarkId(), savedFolderBookmark.getFolderBookmarkTitle());
    }

    // 해당퀴즈 폴더에 추가
   // @Transactional
    public void addQuizToFolder(int folderBookmarkId,Long quizId,User user) {

        FolderBookmark folderBookmark = folderBookmarkRepository.findByUserAndFolderBookmarkId(user, folderBookmarkId)
                .orElseThrow(() -> new RuntimeException("해당 유저의 폴더를 찾을 수 없습니다"));

        //퀴즈 id로 퀴즈 찾아서 폴더 저장
        Quiz quiz = quizRepository.findQuizById(quizId);
        quiz.setFolderBookmark(folderBookmark);
        quizRepository.save(quiz);
    }

    // 폴더명 수정
   // @Transactional
    public FolderBookmarkDTO updateFolderBookmark(String folderUpdateTitle,String folderTitle,User user) {
        logger.info("update folder title: " + folderTitle);

        // 기존 객체 가져오기 (기존 제목으로 조회)
        FolderBookmark folderBookmark = folderBookmarkRepository.findByFolderBookmarkTitle(folderTitle)
                .orElseThrow(() -> new RuntimeException("해당 폴더가 없습니다."));

        // 현재 로그인한 유저의 폴더인지 검증
        if (!folderBookmark.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("이 폴더를 수정할 권한이 없습니다.");
        }

        // 새 제목으로 중복 검사 (로그인한 유저의 폴더에서만, 자기 자신 제외)
        Optional<FolderBookmark> duplicate = folderBookmarkRepository
                .findByFolderBookmarkTitleAndUserAndFolderBookmarkIdNot(folderUpdateTitle, user, folderBookmark.getFolderBookmarkId());

        if (duplicate.isPresent()) {
            throw new IllegalArgumentException("중복되는 제목이 존재합니다.");
        }

        //로그인한 유저의 제목 업데이트
        folderBookmark.setFolderBookmarkTitle(folderUpdateTitle);

        // 수정된 폴더를 DB에 저장
        FolderBookmark savedFolderBookmark = folderBookmarkRepository.save(folderBookmark);
        logger.info("folder updated : " +folderBookmark.getFolderBookmarkId());

        return new FolderBookmarkDTO(savedFolderBookmark.getFolderBookmarkId(), savedFolderBookmark.getFolderBookmarkTitle());
    }

 //   @Transactional
    //전체 조회
    public List<FolderBookmarkDTO> getAllfolderBookmarks(User user) {
        //findAll()은 Repository에 디폴트로 생성되어 있음
        List<FolderBookmark> folderBookmarks = folderBookmarkRepository.findByUser(user);

        // 조회한 FolderChapter들을 FolderChapterDTO로 바꿈
        List<FolderBookmarkDTO> folderBookmarkDTOList = new ArrayList<>();
        for (FolderBookmark folderBookmark : folderBookmarks) {
            FolderBookmarkDTO dto = new FolderBookmarkDTO(folderBookmark.getFolderBookmarkId(), folderBookmark.getFolderBookmarkTitle());
            folderBookmarkDTOList.add(dto);
        }
        return folderBookmarkDTOList;
    }

  //  @Transactional
    public void deleteFolderBookmark(String folderTitle, User user) {
        logger.info("delete folder title : " + folderTitle);

        // folderTitle로 폴더를 찾고, 로그인한 유저의 폴더인지 확인
        FolderBookmark folderBookmark = folderBookmarkRepository.findByFolderBookmarkTitleAndUser(folderTitle, user)
                .orElseThrow(() -> new IllegalArgumentException("폴더가 존재하지 않습니다. " + folderTitle));

        // 로그인한 유저가 해당 폴더의 소유자인지 확인
        if (!folderBookmark.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("이 폴더를 삭제할 권한이 없습니다.");
        }

        // 폴더 삭제
        folderBookmarkRepository.delete(folderBookmark);
        logger.info("folder deleted : " + folderTitle);
    }


}
