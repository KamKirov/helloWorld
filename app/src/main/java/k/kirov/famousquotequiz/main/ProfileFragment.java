package k.kirov.famousquotequiz.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import k.kirov.famousquotequiz.R;
import k.kirov.famousquotequiz.models.User;

/**
 * @author Kamen Kirov (kamen_kirov@abv.bg).
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    long userId;
    User user;
    Editor editorLogin;
    SharedPreferences prefLogin;

    public interface IProfile {
        void onLogout();
    }

    IProfile callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (IProfile) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        EditText mEmail = (EditText) view.findViewById(R.id.email_profile);
        EditText mUserName = (EditText) view.findViewById(R.id.username_profile);
        Button mLogoutBtn = (Button) view.findViewById(R.id.logout_profile);

        mLogoutBtn.setOnClickListener(this);

        prefLogin = getActivity().getSharedPreferences("fragment_login", Context.MODE_PRIVATE);
        editorLogin = prefLogin.edit();

        userId = prefLogin.getLong("userId", -1);

        // Get user data if exist
        user = User.findById(User.class, userId);
        if (user != null) {
            mEmail.setText(user.getEmail());
            mUserName.setText(user.getUsername());
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout_profile) {
            editorLogin.putLong("userId", -1);
            editorLogin.commit();

            if (callback != null)
                callback.onLogout();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
