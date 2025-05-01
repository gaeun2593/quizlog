package com.mtvs.quizlog.domain.folder.folderchapter.service;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.folder.folderbookmarks.dto.CreateFolderDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.dto.FolderChapterDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.folder.folderchapter.repository.FolderChapterRepository;
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
public class FolderChapterService {
    //로그정보
    private static final Logger logger = Logger.getLogger(FolderChapterService.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FolderChapterService.class);

    //Repository 인터페이스 가져옴
    private final FolderChapterRepository folderChapterRepository;
    private final ChapterRepository chapterRepository;

    // 생성자 주입
    @Autowired
    public FolderChapterService(FolderChapterRepository folderChapterRepository, ChapterRepository chapterRepository) {
        this.folderChapterRepository = folderChapterRepository;
        this.chapterRepository = chapterRepository;
    }

    //이 메서드 전체가 하나의 트랜잭션으로 실행된다는 뜻
    //중간에 예외가 나면 전체 롤백됨
    //@Transactional
    //  컨트롤러에서 받은 게시글 입력값이 담긴 DTO
    // 폴더 생성 (챕터를 담으면서 폴더 생성)

    public FolderChapterDTO createFolderChapter(FolderChapterDTO folderChapterDTO, User user, Long chapterId) {
        logger.info("폴더 추가하기 제목 : "+ folderChapterDTO.getTitle());

        // 로그인한 user의 폴더중에서만 폴더명 중복검사
        Optional<FolderChapter> findFolderChapter = folderChapterRepository.findByFolderChapterTitleAndUser(folderChapterDTO.getTitle(), user);

        //같은 제목이 있으면 예외처리
        if(findFolderChapter.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다. : " + folderChapterDTO.getTitle());
        }

        //같은 제목이 아니면 새 FolderChapter객체를 만들어 저장
        FolderChapter folderChapter = new FolderChapter(folderChapterDTO.getTitle());
        folderChapter.setUser(user); // 유저 연관관계 맵핑

        FolderChapter savedFolderChapter = folderChapterRepository.save(folderChapter);
        
        //챕터 id로 챕터 찾음
        Chapter chapter = chapterRepository.findChapterById(chapterId);

        chapter.setFolderChapter(savedFolderChapter);

        chapterRepository.save(chapter);

        logger.info("챕터의 폴더 등록 성공 : "+ savedFolderChapter.getFolderChapterId());

        return new FolderChapterDTO(savedFolderChapter.getFolderChapterId(), savedFolderChapter.getFolderChapterTitle());
    }


    public void save(CreateFolderDTO createFolderDTO){
        Chapter chapter = chapterRepository.findChapterById(createFolderDTO.getChapterId());
        Optional<FolderChapter> folder = folderChapterRepository.findById(createFolderDTO.getFolderChapterId());
        //folder.ifPresent(f -> f.getChapters().add(chapter));

       chapter.setFolderChapter(folder.get());


    }

    // 폴더 생성2 (빈 폴더 생성)
    public FolderChapterDTO createFolderChapter2(FolderChapterDTO folderChapterDTO, User user) {
        logger.info("폴더 추가하기 제목 : "+ folderChapterDTO.getTitle());

        // 로그인한 user의 폴더중에서만 폴더명 중복검사
        Optional<FolderChapter> findFolderChapter = folderChapterRepository.findByFolderChapterTitleAndUser(folderChapterDTO.getTitle(), user);

        //같은 제목이 있으면 예외처리
        if(findFolderChapter.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다. : " + folderChapterDTO.getTitle());
        }

        //같은 제목이 아니면 새 FolderChapter객체를 만들어 저장
        FolderChapter folderChapter = new FolderChapter(folderChapterDTO.getTitle());
        folderChapter.setUser(user); // 유저 연관관계 맵핑

        FolderChapter savedFolderChapter = folderChapterRepository.save(folderChapter);

        logger.info("챕터의 폴더 등록 성공 : "+ savedFolderChapter.getFolderChapterId());

        return new FolderChapterDTO(savedFolderChapter.getFolderChapterId(), savedFolderChapter.getFolderChapterTitle());
    }

    // 해당챕터 폴더에 추가
   // @Transactional
    public void addChapterToFolder(int folderChapterId,Long chapterId,User user) {

        FolderChapter folderChapter = folderChapterRepository.findByUserAndFolderChapterId(user, folderChapterId)
                .orElseThrow(() -> new RuntimeException("해당 유저의 폴더를 찾을 수 없습니다"));

        //챕터 id로 챕터 찾아서 폴더 저장
        Chapter chapter = chapterRepository.findChapterById(chapterId);
        chapter.setFolderChapter(folderChapter);
        chapterRepository.save(chapter);
    }


    // 폴더명 수정
    @Transactional
    public FolderChapterDTO updateFolderChapter(String folderUpdateTitle,String folderTitle,User user) {
        logger.info("update folder title: " + folderTitle);

        // 기존 객체 가져오기 (기존 제목으로 조회)
        FolderChapter folderChapter = folderChapterRepository.findByFolderChapterTitle(folderTitle)
                .orElseThrow(() -> new RuntimeException("해당 폴더가 없습니다."));

        // 현재 로그인한 유저의 폴더인지 검증
        if (!folderChapter.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("이 폴더를 수정할 권한이 없습니다.");
        }

        // 새 제목으로 중복 검사 (로그인한 유저의 폴더에서만, 자기 자신 제외)
        Optional<FolderChapter> duplicate = folderChapterRepository
                .findByFolderChapterTitleAndUserAndFolderChapterIdNot(folderUpdateTitle, user, folderChapter.getFolderChapterId());

        if (duplicate.isPresent()) {
            throw new IllegalArgumentException("중복되는 제목이 존재합니다.");
        }

        //로그인한 유저의 제목 업데이트
        folderChapter.setFolderChapterTitle(folderUpdateTitle);

        // 수정된 폴더를 DB에 저장
        FolderChapter savedFolderChapter = folderChapterRepository.save(folderChapter);
        logger.info("folder updated : " +folderChapter.getFolderChapterId());

        return new FolderChapterDTO(savedFolderChapter.getFolderChapterId(), savedFolderChapter.getFolderChapterTitle());
    }

    //전체 조회
    public List<FolderChapterDTO> getAllFolderChapters(User user) {
        //findAll()은 Repository에 디폴트로 생성되어 있음
        List<FolderChapter> folderChapters = folderChapterRepository.findByUser(user);

        // 조회한 FolderChapter들을 FolderChapterDTO로 바꿈
        List<FolderChapterDTO> folderChapterDTOList = new ArrayList<>();
        for (FolderChapter folderChapter : folderChapters) {
            FolderChapterDTO dto = new FolderChapterDTO(folderChapter.getFolderChapterId(), folderChapter.getFolderChapterTitle());
            folderChapterDTOList.add(dto);
        }
        return folderChapterDTOList;
    }


    @Transactional
    public void deleteFolderChapter(String folderTitle, User user) {
        logger.info("delete folder title : " + folderTitle);

        // folderTitle로 폴더를 찾고, 로그인한 유저의 폴더인지 확인
        FolderChapter folderChapter = folderChapterRepository.findByFolderChapterTitleAndUser(folderTitle, user)
                .orElseThrow(() -> new IllegalArgumentException("폴더가 존재하지 않습니다. " + folderTitle));

        // 로그인한 유저가 해당 폴더의 소유자인지 확인
        if (!folderChapter.getUser().getUserId().equals(user.getUserId())) {
            throw new SecurityException("이 폴더를 삭제할 권한이 없습니다.");
        }

        // 🔥 연결된 챕터들의 외래 키 끊기
        List<Chapter> chapters = chapterRepository.findByFolderChapter(folderChapter);
        for (Chapter chapter : chapters) {
            chapter.setFolderChapter(null); // 외래 키를 null로
        }

        chapterRepository.saveAll(chapters); // DB 반영

        // 폴더 삭제
        folderChapterRepository.delete(folderChapter);
        logger.info("folder deleted : " + folderTitle);
    }

}
