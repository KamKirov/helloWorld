package k.kirov.famousquotequiz.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import k.kirov.famousquotequiz.R;
import k.kirov.famousquotequiz.account.AccountActivity;

public class Splash extends Activity {
    long userId;

    SharedPreferences.Editor editorLogin;
    SharedPreferences prefLogin;
    RelativeLayout splash_logo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        splash_logo = (RelativeLayout) findViewById(R.id.splashscreen);

        Animation zoom_effect = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_zoom_effect);
        zoom_effect.setStartOffset(1200);
        splash_logo.startAnimation(zoom_effect);


        prefLogin = getSharedPreferences("fragment_login", MODE_PRIVATE);
        editorLogin = prefLogin.edit();

        userId = prefLogin.getLong("userId", -1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splash_logo.setVisibility(View.GONE);

                if (userId == -1) {
                    Intent mainIntent = new Intent(Splash.this, AccountActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, 2600);
    }

    @Override
    public void onBackPressed(){
        // Override onBackPressed() to prevent closing the Splash Screen
    }
}