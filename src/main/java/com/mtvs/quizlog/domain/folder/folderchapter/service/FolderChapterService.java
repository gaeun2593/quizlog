package com.mtvs.quizlog.domain.folder.folderchapter.service;

import com.mtvs.quizlog.domain.folder.folderchapter.dto.FolderChapterDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.folder.folderchapter.repository.FolderChapterRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FolderChapterService {
    //로그정보
    private static final Logger logger = Logger.getLogger(FolderChapterService.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FolderChapterService.class);

    //Repository 인터페이스 가져옴
    private final FolderChapterRepository folderChapterRepository;

    // 생성자 주입
    @Autowired
    public FolderChapterService(FolderChapterRepository folderChapterRepository) {
        this.folderChapterRepository = folderChapterRepository;
    }

    //이 메서드 전체가 하나의 트랜잭션으로 실행된다는 뜻
    //중간에 예외가 나면 전체 롤백됨
    @Transactional
    //  컨트롤러에서 받은 게시글 입력값이 담긴 DTO
    // 폴더 생성
    public FolderChapterDTO createFolderChapter(FolderChapterDTO folderChapterDTO) {
        logger.info("보드 추가하기 제목 : "+ folderChapterDTO.getTitle());

        // title로 DB에서 이미 같은 제목의 게시글이 있는지 확인
        Optional<FolderChapter> findFolderChapter = folderChapterRepository.findByFolderChapterTitle(folderChapterDTO.getTitle());

        //같은 제목이 있으면 예외처리
        if(findFolderChapter.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 제목입니다. : " + folderChapterDTO.getTitle());
        }

        //같은 제목이 아니면 새 FolderChapter객체를 만들어 저장
        FolderChapter folderChapter = new FolderChapter(folderChapterDTO.getTitle());
        FolderChapter savedFolderChapter = folderChapterRepository.save(folderChapter);

        logger.info("챕터의 폴더 등록 성공 : "+ savedFolderChapter.getFolderChapterId());

        return new FolderChapterDTO(savedFolderChapter.getFolderChapterId(), savedFolderChapter.getFolderChapterTitle());
    }

    
    // 폴더명 수정
    @Transactional
    public FolderChapterDTO updateFolderChapter(String folderUpdateTitle,String folderTitle) {
        logger.info("update folder title: " + folderTitle);

        // 1. 기존 객체 가져오기 (기존 제목으로 조회)
        FolderChapter folderChapter = folderChapterRepository.findByFolderChapterTitle(folderTitle)
                .orElseThrow(() -> new RuntimeException("해당 폴더 챕터가 없습니다."));

        // 새 제목으로 중복 검사 (자기 자신 제외)
        Optional<FolderChapter> duplicate = folderChapterRepository
                .findByFolderChapterTitleAndFolderChapterIdNot(folderUpdateTitle, folderChapter.getFolderChapterId());
        if(duplicate.isPresent()) {
            throw new IllegalArgumentException("중복되는 제목이 존재 합니다.");
        }

        //제목 업데이트
        folderChapter.setFolderChapterTitle(folderUpdateTitle);
        FolderChapter savedFolderChapter = folderChapterRepository.save(folderChapter);
        logger.info("folder updated : " +folderChapter.getFolderChapterId());

        return new FolderChapterDTO(savedFolderChapter.getFolderChapterId(), savedFolderChapter.getFolderChapterTitle());
    }

    //전체 조회
    public List<FolderChapterDTO> getAllFolderChapters() {
        //findAll()은 Repository에 디폴트로 생성되어 있음
        List<FolderChapter> folderChapters = folderChapterRepository.findAll();

        // 조회한 FolderChapter들을 FolderChapterDTO로 바꿈
        List<FolderChapterDTO> folderChapterDTOList = new ArrayList<>();
        for (FolderChapter folderChapter : folderChapters) {
            FolderChapterDTO dto = new FolderChapterDTO(folderChapter.getFolderChapterId(), folderChapter.getFolderChapterTitle());
            folderChapterDTOList.add(dto);
        }
        return folderChapterDTOList;
    }


    @Transactional
    public void deleteFolderChapter(String folderTitle) {
        logger.info("delete folder title : " + folderTitle);
        // JPA에서 FolderTitle이 존재하는지 확인하는 메서드
        boolean result = folderChapterRepository.existsByfolderChapterTitle(folderTitle);

        if(!result) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다. " + folderTitle);
        }

        folderChapterRepository.deleteByfolderChapterTitle(folderTitle);
        logger.info("folder deleted : " + folderTitle);
    }
}
