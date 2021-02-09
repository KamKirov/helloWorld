package k.kirov.famousquotequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

/**
 * @author Kamen Kirov (kamen_kirov@mail.bg).
 */

public class Quiz extends SugarRecord implements Parcelable {

    private String title;

    public Quiz() {

    }

    public Quiz(String title) {
        this.setTitle(title);
    }

    public List<Question> getQuestions() {
        return Question.find(Question.class, "quiz =?", String.valueOf(getId()));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected Quiz(Parcel in) {
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Quiz> CREATOR = new Parcelable.Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}
