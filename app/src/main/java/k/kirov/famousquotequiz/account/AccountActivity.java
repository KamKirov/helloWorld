package k.kirov.famousquotequiz.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import k.kirov.famousquotequiz.R;
import k.kirov.famousquotequiz.main.MainActivity;
import k.kirov.famousquotequiz.main.QuizEdit;
import k.kirov.famousquotequiz.main.ScoresActivity;

public class AccountActivity extends AppCompatActivity implements LoginFragment.ILogin, RegisterFragment.IRegister {

    long userId;
    Editor editorLogin;
    SharedPreferences prefLogin;
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        fragmentManager = getSupportFragmentManager();

        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();

        prefLogin = getSharedPreferences("fragment_login", MODE_PRIVATE);
        editorLogin = prefLogin.edit();

        userId = prefLogin.getLong("userId", -1);

        if (userId == -1) {
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, loginFragment).commit();
        } else {
            // Open Quiz Activity
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(this, R.string.login_need, Toast.LENGTH_LONG).show();

                return true;
            case R.id.scores:
                Intent scoresIntent = new Intent(this, ScoresActivity.class);
                startActivity(scoresIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeFragment(int action) {
        switch (action) {
            case R.string.log_in:
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, loginFragment).commit();

                break;
            case R.string.register:
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, registerFragment).commit();

                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLogin(int action) {
        if (action == R.string.login_success) {
            Intent mainIntent = new Intent(AccountActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        } else {
            changeFragment(action);
        }
    }

    @Override
    public void onRegister(int action) {
        changeFragment(action);
    }

    @Override
    public void onBackPressed() {
        if (loginFragment.isVisible()) {
            super.onBackPressed();
        } else {
            changeFragment(R.string.log_in);
        }
    }
}
