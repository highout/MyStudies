package ai_test_generator.quizzes;

import ai_test_generator.quizzes.entity.Quiz;
import ai_test_generator.quizzes.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> findById(String id) {
        return quizRepository.findById(id);
    }

    public boolean existsById(String id) {
        return quizRepository.existsById(id);
    }

    public void deleteById(String id) {
        quizRepository.deleteById(id);
    }
}
