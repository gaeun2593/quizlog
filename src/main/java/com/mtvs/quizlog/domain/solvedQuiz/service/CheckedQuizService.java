package com.mtvs.quizlog.domain.solvedQuiz.service;
import java.util.List;
import com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.solvedQuiz.dto.UserCheckedChapterDTO;
import com.mtvs.quizlog.domain.solvedQuiz.dto.UserCheckedQuizDTO;
import com.mtvs.quizlog.domain.solvedQuiz.entity.UserCheckedQuiz;
import com.mtvs.quizlog.domain.solvedQuiz.repository.CheckedQuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.mtvs.quizlog.domain.solvedQuiz.entity.Status.COMPLETED;
import static com.mtvs.quizlog.domain.solvedQuiz.entity.Status.INCOMPLETE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CheckedQuizService {

    private final CheckedQuizRepository checkedQuizRepository;

    public void saveCheckedQuiz(QuizForm quizForm , long userId  , long chapterId) {
        User user = checkedQuizRepository.findUser(userId);
        Quiz quiz = checkedQuizRepository.findQuiz(quizForm.getId());
        Chapter chapter = checkedQuizRepository.findChapter(chapterId);

        if(quizForm.getStatus().equals("correct")) {
            UserCheckedQuiz userCheckedQuiz = new UserCheckedQuiz(user, quiz , chapter , COMPLETED);
            checkedQuizRepository.save(userCheckedQuiz);
        }
        else {
            UserCheckedQuiz userCheckedQuiz = new UserCheckedQuiz(user, quiz , chapter ,INCOMPLETE);
            checkedQuizRepository.save(userCheckedQuiz);
        }

//
    }

    public UserCheckedQuizDTO findCheckdQuiz(long chapterId, long userId) {
       return checkedQuizRepository.findCheckdQuiz(chapterId , userId);
    }

    public List<QuizForm> findCheckdQuizs(long chapterId, long userId) {
        return checkedQuizRepository.findCheckdQuizs(chapterId , userId);
    }

    public List<UserCheckedChapterDTO> findCheckedChapters(long userId) {
        return checkedQuizRepository.findCheckedChapters(userId);
    }

    public List<UserCheckedChapterDTO> findChekedFolder(long folderChapterId) {
        return checkedQuizRepository.findChekedFolder(folderChapterId);
    }

    public void removeCheckdQuiz(long chapterId, Long userId) {
        checkedQuizRepository.removeCheckdQuiz(chapterId, userId);
    }
}
