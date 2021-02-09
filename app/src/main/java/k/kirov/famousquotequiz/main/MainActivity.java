package k.kirov.famousquotequiz.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import k.kirov.famousquotequiz.R;
import k.kirov.famousquotequiz.account.AccountActivity;
import k.kirov.famousquotequiz.models.Results;

public class MainActivity extends AppCompatActivity implements ProfileFragment.IProfile, SettingsFragment.ISettings {

    private int[] imageResId = {
            R.mipmap.ic_format_list_bulleted,
            R.mipmap.ic_settings,
            R.mipmap.ic_account
    };

    SharedPreferences.Editor editorMode;
    SharedPreferences prefMode;
    ViewPager mViewPager;
    PagerAdapter adapter;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefMode = getSharedPreferences("quiz_mode", Context.MODE_PRIVATE);
        editorMode = prefMode.edit();

        fragmentManager = getSupportFragmentManager();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        // Add fragments
        adapter = new PagerAdapter(getSupportFragmentManager());
        if (prefMode.getBoolean("binary", false)) {
            adapter.addFragment(new QuizFragment2());
        } else {
            adapter.addFragment(new QuizFragment());
        }
        adapter.addFragment(new SettingsFragment());
        adapter.addFragment(new ProfileFragment());
        mViewPager.setOffscreenPageLimit(adapter.getCount());

        // Setting adapter
        mViewPager.setAdapter(adapter);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(imageResId[i]);
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
                Intent editIntent = new Intent(this, QuizEdit.class);
                startActivity(editIntent);

                return true;
            case R.id.scores:
                Intent scoresIntent = new Intent(this, ScoresActivity.class);
                startActivity(scoresIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onLogout() {
        Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(accountIntent);
        finish();
    }

    @Override
    public void onSettings(boolean action) {
        if (!action) {
            QuizFragment quizFragment = new QuizFragment();
            ((BaseQuizContainerFragment) fragmentManager.getFragments().get(0)).replaceFragment(quizFragment, false);
            mViewPager.invalidate();
        } else {
            QuizFragment2 quizFragment2 = new QuizFragment2();
            ((BaseQuizContainerFragment) fragmentManager.getFragments().get(0)).replaceFragment(quizFragment2, false);
            mViewPager.invalidate();
        }
    }
}
