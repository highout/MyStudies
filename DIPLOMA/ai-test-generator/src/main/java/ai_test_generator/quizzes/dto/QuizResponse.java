package ai_test_generator.quizzes.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuizResponse {

    private String id;
    private String topic;
    private int amount;
    private QuestionType type;
    private Difficulty difficulty;
    private LocalDateTime createdAt;

    private List<QuestionResponse> questions;
}
