package k.kirov.famousquotequiz;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.multidex.MultiDex;

import com.orm.SugarContext;
import com.orm.SugarDb;

import java.io.File;
import java.util.List;

import k.kirov.famousquotequiz.helpers.InitDB;
import k.kirov.famousquotequiz.models.Quiz;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Delete DataBase
//        if (doesDBExist(this, "quiz.db")) {
//            SugarDb sugarDb = new SugarDb(getApplicationContext());
//            new File(sugarDb.getDB().getPath()).delete();
//        }

        SugarContext.init(getApplicationContext());
        mInstance = this;

        initDBWithQuestions();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initDBWithQuestions() {
        List<Quiz> quizzes = Quiz.listAll(Quiz.class);

        // Init DB with Quizzes
        if (quizzes.size() == 0) {
            InitDB initDB = new InitDB();
            initDB.initDBQuiz1();
            initDB.initDBQuiz2();
        }
    }

    /**
     *  Check if DB exist
     *
     * @param context Context
     * @param dbName String
     * @return boolean
     */
    private boolean doesDBExist(ContextWrapper context, String dbName) {

        File dbFile = context.getDatabasePath(dbName);

        return dbFile.exists();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

}
