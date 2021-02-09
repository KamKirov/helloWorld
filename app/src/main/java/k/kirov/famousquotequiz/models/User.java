package k.kirov.famousquotequiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

/**
 * @author Kamen Kirov (kamen_kirov@mail.bg).
 */

public class User extends SugarRecord implements Parcelable {

    private String username;
    private String password;
    private String email;

    public User() {

    }

    public User(String username, String password, String email) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
    }

    public List<Results> getResults() {

        return Results.find(Results.class, "user =?", String.valueOf(getId()));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
    }

    @SuppressWarnings("unused")
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}