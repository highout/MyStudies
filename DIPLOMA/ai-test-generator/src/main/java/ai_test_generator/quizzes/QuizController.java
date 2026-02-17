package ai_test_generator.quizzes;

import ai_test_generator.quizzes.dto.QuizResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@AllArgsConstructor
public class QuizController {

    private final QuizComponent quizComponent;

//    @PostMapping("/generate")
//    public QuizResponse generate(@Valid @RequestBody GenerateQuizRequest request) {
//        Quiz quiz = quizComponent.generateAndSave(request);
//        return quizMapper.toResponse(quiz);
//    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuizzes() {
        return ResponseEntity.ok(quizComponent.getAllQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable String id){
        return quizComponent.findQuizById(id)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        quizComponent.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}