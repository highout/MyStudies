package ai_test_generator.quizzes.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionResponse {

    private String text;
    private QuestionType type;
    private Difficulty difficulty;

    private List<String> options;
    private Integer correctOptionIndex;
}

