package ai_test_generator.quizzes.mapper;

import ai_test_generator.quizzes.dto.QuizResponse;
import ai_test_generator.quizzes.entity.Quiz;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizMapper {

    QuizResponse toResponse(Quiz quiz);

    List<QuizResponse> toResponseList(List<Quiz> quizzes);
}