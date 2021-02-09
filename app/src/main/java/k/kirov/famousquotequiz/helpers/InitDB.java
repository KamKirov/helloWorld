package k.kirov.famousquotequiz.helpers;

import com.orm.SugarRecord;

import k.kirov.famousquotequiz.models.Answer;
import k.kirov.famousquotequiz.models.Question;
import k.kirov.famousquotequiz.models.Quiz;

public class InitDB {

    public void initDBQuiz1() {
        Quiz quiz = new Quiz("Binary mode (Yes/No)");
        quiz.save();

        Question q1 = new Question(quiz, "Film / TV", "How many oscars did the Titanic movie got?");
        q1.save();

        setAnswer(q1, "Nine?", false);

        Question q2 = new Question(quiz, "Film / TV", "What is the house number of the Simpsons?");
        q2.save();

        setAnswer(q2, "Number 321?", false);

        Question q3 = new Question(quiz, "Sport", "How matches did Mohammed Ali lose in his career?");
        q3.save();

        setAnswer(q3, "Only one?", true);

        Question q4 = new Question(quiz, "Sport", "How long is an Olympic swimming pool?");
        q4.save();

        setAnswer(q4, "40 meters?", false);

        Question q5 = new Question(quiz, "Food and drink", "Which country is the origin of the Stella beer?");
        q5.save();

        setAnswer(q5, "Belgium?", true);

        Question q6 = new Question(quiz, "Geography", "What is the largest state of the United States?");
        q6.save();

        setAnswer(q6, "Texas?", false);

        Question q7 = new Question(quiz, "General", "How many stars in the American flag?");
        q7.save();

        setAnswer(q7, "50 stars?", true);

        Question q8 = new Question(quiz, "Art / Culture", "Who did the Mona Lisa paint?");
        q8.save();

        setAnswer(q8, "Pablo Picasso?", false);

        Question q9 = new Question(quiz, "Film / TV", "What is the profession of Popeye?");
        q9.save();

        setAnswer(q9, "Seaman?", true);

        Question q10 = new Question(quiz, "Science", "Which planet is nearest the sun?");
        q10.save();

        setAnswer(q10, "Mercury?", false);
    }

    public void initDBQuiz2() {
        Quiz quiz = new Quiz("Multiple choice mode");
        quiz.save();

        Question q1 = new Question(quiz, "Film / TV", "How many oscars did the Titanic movie got?");
        q1.save();

        setAnswer(q1, "One", false);
        setAnswer(q1, "Six", false);
        setAnswer(q1, "Eleven", true);

        Question q2 = new Question(quiz, "Film / TV", "What is the house number of the Simpsons?");
        q2.save();

        setAnswer(q2, "Number 742", true);
        setAnswer(q2, "Number 652", false);
        setAnswer(q2, "Number 555", false);

        Question q3 = new Question(quiz, "Sport", "How matches did Mohammed Ali lose in his career?");
        q3.save();

        setAnswer(q3, "Two", false);
        setAnswer(q3, "Five", false);
        setAnswer(q3, "Only one", true);

        Question q4 = new Question(quiz, "Sport", "How long is an Olympic swimming pool?");
        q4.save();

        setAnswer(q4, "50 meters", true);
        setAnswer(q4, "60 meters", false);
        setAnswer(q4, "65 meters", false);

        Question q5 = new Question(quiz, "Food and drink", "Which country is the origin of the Stella beer?");
        q5.save();

        setAnswer(q5, "Germany", false);
        setAnswer(q5, "Hungary", false);
        setAnswer(q5, "Belgium", true);

        Question q6 = new Question(quiz, "Geography", "What is the largest state of the United States?");
        q6.save();

        setAnswer(q6, "Texas", false);
        setAnswer(q6, "Florida", false);
        setAnswer(q6, "Alaska", true);

        Question q7 = new Question(quiz, "General", "How many stars in the American flag?");
        q7.save();

        setAnswer(q7, "50 stars", true);
        setAnswer(q7, "40 stars", false);
        setAnswer(q7, "25 stars", false);

        Question q8 = new Question(quiz, "Art / Culture", "Who did the Mona Lisa paint?");
        q8.save();

        setAnswer(q8, "Rembrandt", false);
        setAnswer(q8, "Leonardo Da Vinci", true);
        setAnswer(q8, "Pablo Picasso", false);

        Question q9 = new Question(quiz, "Film / TV", "What is the profession of Popeye?");
        q9.save();

        setAnswer(q9, "Police officer", false);
        setAnswer(q9, "Lawyer", false);
        setAnswer(q9, "Seaman", true);

        Question q10 = new Question(quiz, "Science", "Which planet is nearest the sun?");
        q10.save();

        setAnswer(q10, "Mercury", false);
        setAnswer(q10, "Mars", true);
        setAnswer(q10, "Earth", false);
    }

    private void setAnswer(Question question, String answer, Boolean correct) {
        SugarRecord.save(new Answer(question, answer, correct));
    }
}
