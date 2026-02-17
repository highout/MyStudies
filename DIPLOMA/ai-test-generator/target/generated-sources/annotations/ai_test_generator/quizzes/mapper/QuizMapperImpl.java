package ai_test_generator.quizzes.mapper;

import ai_test_generator.quizzes.dto.QuestionResponse;
import ai_test_generator.quizzes.dto.QuizResponse;
import ai_test_generator.quizzes.entity.Question;
import ai_test_generator.quizzes.entity.Quiz;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-17T10:55:04+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class QuizMapperImpl implements QuizMapper {

    @Override
    public QuizResponse toResponse(Quiz quiz) {
        if ( quiz == null ) {
            return null;
        }

        QuizResponse quizResponse = new QuizResponse();

        quizResponse.setId( quiz.getId() );
        quizResponse.setTopic( quiz.getTopic() );
        quizResponse.setAmount( quiz.getAmount() );
        quizResponse.setType( quiz.getType() );
        quizResponse.setDifficulty( quiz.getDifficulty() );
        quizResponse.setCreatedAt( quiz.getCreatedAt() );
        quizResponse.setQuestions( questionListToQuestionResponseList( quiz.getQuestions() ) );

        return quizResponse;
    }

    @Override
    public List<QuizResponse> toResponseList(List<Quiz> quizzes) {
        if ( quizzes == null ) {
            return null;
        }

        List<QuizResponse> list = new ArrayList<QuizResponse>( quizzes.size() );
        for ( Quiz quiz : quizzes ) {
            list.add( toResponse( quiz ) );
        }

        return list;
    }

    protected QuestionResponse questionToQuestionResponse(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionResponse questionResponse = new QuestionResponse();

        questionResponse.setText( question.getText() );
        questionResponse.setType( question.getType() );
        questionResponse.setDifficulty( question.getDifficulty() );
        List<String> list = question.getOptions();
        if ( list != null ) {
            questionResponse.setOptions( new ArrayList<String>( list ) );
        }
        questionResponse.setCorrectOptionIndex( question.getCorrectOptionIndex() );

        return questionResponse;
    }

    protected List<QuestionResponse> questionListToQuestionResponseList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionResponse> list1 = new ArrayList<QuestionResponse>( list.size() );
        for ( Question question : list ) {
            list1.add( questionToQuestionResponse( question ) );
        }

        return list1;
    }
}
