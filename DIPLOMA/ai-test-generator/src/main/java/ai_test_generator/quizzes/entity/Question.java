package ai_test_generator.quizzes.entity;

import ai_test_generator.quizzes.dto.Difficulty;
import ai_test_generator.quizzes.dto.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class Question {

    private String text;
    private QuestionType type;
    private Difficulty difficulty;
    private List<String> options;
    private Integer correctOptionIndex;
}
