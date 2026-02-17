package ai_test_generator.quizzes.repository;

import ai_test_generator.quizzes.entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {
}
