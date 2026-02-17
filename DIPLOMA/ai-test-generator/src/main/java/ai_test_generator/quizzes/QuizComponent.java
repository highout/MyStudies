package ai_test_generator.quizzes;

import ai_test_generator.quizzes.dto.QuizResponse;
import ai_test_generator.quizzes.mapper.QuizMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class QuizComponent {

    private final QuizService quizService;
    private final QuizMapper quizMapper;
    
    public List<QuizResponse> getAllQuizzes() {
        return quizService.findAll().stream()
                .map(quizMapper::toResponse)
                .toList();
    }

    public Optional<QuizResponse> findQuizById(String id){
        return quizService.findById(id)
                .map(quizMapper::toResponse);
    }

//    public Quiz generateAndSave(GenerateQuizRequest request) {
//        Quiz quiz = new Quiz();
//        quiz.setTopic(request.getTopic());
//        quiz.setAmount(request.getAmount());
//        quiz.setType(request.getType());
//        quiz.setDifficulty(request.getDifficulty());
//        quiz.setCreatedAt(LocalDateTime.now());
//
//        List<Question> questions = new ArrayList<>();
//
//        for (int i = 1; i <= request.getAmount(); i++) {
//            Question q = new Question();
//            q.setType(request.getType());
//            q.setDifficulty(request.getDifficulty());
//            q.setText("Q" + i + ". Питання по темі: " + request.getTopic());
//
//            if (request.getType() == QuestionType.TEST) {
//                q.setOptions(List.of(
//                        "Варіант A",
//                        "Варіант B",
//                        "Варіант C",
//                        "Варіант D"
//                ));
//                q.setCorrectOptionIndex(1); // умовно правильний B
//            }
//
//            questions.add(q);
//        }
//
//        quiz.setQuestions(questions);
//        return quizService.save(quiz);
//    }

    public void deleteById(String id) {
        if (!quizService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        quizService.deleteById(id);
    }
}

