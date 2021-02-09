package k.kirov.famousquotequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

/**
 * @author Kamen Kirov (kamen_kirov@mail.bg).
 */

public class Results extends SugarRecord implements Parcelable {

    private User user;
    private int scores;
    private int answers;

    public Results() {

    }

    public Results(User user, int scores, int answers) {
        this.user = user;
        this.setScores(scores);
        this.setAnswers(answers);
    }

    public List<Results> getResults() {
        return Results.findWithQuery(Results.class, "SELECT * FROM RESULTS");
    }

    public String getUserId() {
        if (user == null)
            return "Unknown";

        return user.getEmail();
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    protected Results(Parcel in) {
        user = (User) in.readValue(User.class.getClassLoader());
        scores = in.readInt();
        answers = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
        dest.writeInt(scores);
        dest.writeInt(answers);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };
}