package com.mtvs.quizlog.solvedQuiz.service;

import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.solvedQuiz.entity.UserCheckedQuiz;
import com.mtvs.quizlog.solvedQuiz.repository.CheckedQuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CheckedQuizService {

    private final CheckedQuizRepository checkedQuizRepository;

    public void saveCheckedQuiz(QuizForm quizForm , long userId) {
        User user = checkedQuizRepository.findUser(userId);
        Quiz quiz = checkedQuizRepository.findQuiz(quizForm.getId());
        UserCheckedQuiz userCheckedQuiz = new UserCheckedQuiz(user, quiz);
        checkedQuizRepository.save(userCheckedQuiz);

    }
}
