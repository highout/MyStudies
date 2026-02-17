package ai_test_generator.quizzes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenerateQuizRequest {

    @NotBlank(message = "Topic is required")
    @Size(min = 2, max = 120, message = "Topic must be between 2 and 120 characters")
    private String topic;

    @Min(value = 1, message = "Amount must be at least 1")
    @Max(value = 50, message = "Amount must be at most 50")
    private int amount;

    @NotNull(message = "Question type is required")
    private QuestionType type;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;
}