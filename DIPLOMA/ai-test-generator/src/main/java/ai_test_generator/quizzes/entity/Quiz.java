package ai_test_generator.quizzes.entity;

import ai_test_generator.quizzes.dto.Difficulty;
import ai_test_generator.quizzes.dto.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "quizzes")
public class Quiz {

    @Id
    private String id;

    private String topic;
    private int amount;
    private QuestionType type;
    private Difficulty difficulty;
    private LocalDateTime createdAt;

    private List<Question> questions;
}
