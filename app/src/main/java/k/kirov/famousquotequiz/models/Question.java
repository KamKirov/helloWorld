package k.kirov.famousquotequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

/**
 * @author Kamen Kirov (kamen_kirov@mail.bg).
 */

public class Question extends SugarRecord implements Parcelable {

    private Quiz quiz;
    private String title;
    private String description;

    public Question() {

    }

    public Question(Quiz quiz, String title, String description) {
        this.setQuiz(quiz);
        this.setTitle(title);
        this.setDescription(description);
    }

    public List<Answer> getAnswers() {
        return Answer.find(Answer.class, "question =?", String.valueOf(getId()));
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected Question(Parcel in) {
        quiz = (Quiz) in.readValue(Quiz.class.getClassLoader());
        title = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quiz);
        dest.writeString(title);
        dest.writeString(description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
