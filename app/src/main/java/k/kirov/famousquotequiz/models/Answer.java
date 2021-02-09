package k.kirov.famousquotequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * @author Kamen Kirov (kamen_kirov@mail.bg).
 */

public class Answer extends SugarRecord implements Parcelable {

    private Question question;
    private String answer;
    private Boolean correct;

    public Answer() {

    }

    public Answer(Question question, String answer, Boolean correct) {
        this.setQuestion(question);
        this.setAnswer(answer);
        this.setCorrect(correct);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    protected Answer(Parcel in) {
        question = (Question) in.readValue(Question.class.getClassLoader());
        answer = in.readString();
        byte correctVal = in.readByte();
        correct = correctVal == 0x02 ? null : correctVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(question);
        dest.writeString(answer);
        if (correct == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (correct ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
