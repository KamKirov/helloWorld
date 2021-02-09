package k.kirov.famousquotequiz.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import k.kirov.famousquotequiz.R;
import k.kirov.famousquotequiz.models.Answer;
import k.kirov.famousquotequiz.models.Question;
import k.kirov.famousquotequiz.models.Quiz;

public class QuizEdit extends AppCompatActivity implements View.OnClickListener, IRecycleViewQuestions {

    ArrayList<QuizData> quiz = new ArrayList<>();
    private int mPosition = -1;
    private boolean bAddNew = false;
    private String mMode;
    private LinearLayout mEditLayout;
    private RelativeLayout mAnswerLayout, mAnswerLayout2, mAnswerLayout3;
    private QuizData mQuizDataUpdate;
    private AlertDialog mAlertDialog;
    private EditText mEditTitle, mEditDescription, mAnswer, mAnswer2, mAnswer3;
    private Switch mCorrect, mCorrect2, mCorrect3;
    private static final String mBinaryMode = "Binary mode (Yes/No)";
    private static final String mMultiMode = "Multiple choice mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_edit);

        Button quizSelectBtn = (Button) findViewById(R.id.q_select_quiz_btn);
        Button selectEditBtn = (Button) findViewById(R.id.q_select_btn);
        Button saveBtn = (Button) findViewById(R.id.q_save_btn);
        Button addBtn = (Button) findViewById(R.id.q_add_quiz_btn);

        mEditLayout = (LinearLayout) findViewById(R.id.edit_layout);
        mEditTitle = (EditText) findViewById(R.id.title_edit);
        mEditDescription = (EditText) findViewById(R.id.question_edit);

        mAnswerLayout = (RelativeLayout) findViewById(R.id.answer_layout);
        mAnswerLayout2 = (RelativeLayout) findViewById(R.id.answer_layout2);
        mAnswerLayout3 = (RelativeLayout) findViewById(R.id.answer_layout3);

        mAnswer = (EditText) findViewById(R.id.answer_edit);
        mAnswer2 = (EditText) findViewById(R.id.answer_edit2);
        mAnswer3 = (EditText) findViewById(R.id.answer_edit3);

        mCorrect = (Switch) findViewById(R.id.switch_correct);
        mCorrect2 = (Switch) findViewById(R.id.switch_correct2);
        mCorrect3 = (Switch) findViewById(R.id.switch_correct3);

        quizSelectBtn.setOnClickListener(this);
        selectEditBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

        clearDefaults();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.q_select_quiz_btn:
                bAddNew = false;
                selectQuiz();

                break;
            case R.id.q_select_btn:
                if (quiz.isEmpty()) {
                    Toast.makeText(this, R.string.quiz_first, Toast.LENGTH_LONG).show();
                    return;
                }

                bAddNew = false;
                selectQuestion();

                break;
            case R.id.q_save_btn:
                if (quiz.isEmpty()) {
                    Toast.makeText(this, R.string.quiz_first, Toast.LENGTH_LONG).show();
                    return;
                }

                if (bAddNew) {
                    createQuestion();
                } else {
                    saveQuestion();
                }
                break;
            case R.id.q_add_quiz_btn:
                if (quiz.isEmpty()) {
                    Toast.makeText(this, R.string.quiz_first, Toast.LENGTH_LONG).show();
                    return;
                }

                bAddNew = true;
                clearDefaults();
                restoreDefaults();

                break;
        }
    }

    private void selectQuiz() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.quiz_select_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button binaryModeBtn = (Button) dialogView.findViewById(R.id.binary_quiz_btn);
        final Button multiModeBtn = (Button) dialogView.findViewById(R.id.multi_quiz_btn);

        final AlertDialog alertDialog = dialogBuilder.create();

        binaryModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMode = mBinaryMode;
                new loadQuestions().execute(mMode);
                alertDialog.dismiss();
            }
        });

        multiModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMode = mMultiMode;
                new loadQuestions().execute(mMode);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void selectQuestion() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.question_select_dialog, null);
        dialogBuilder.setView(dialogView);

        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.recyclerView);
        QuestionsAdapter adapter;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new QuestionsAdapter(quiz, this);
        recyclerView.setAdapter(adapter);

        mAlertDialog = dialogBuilder.create();

        mAlertDialog.show();
    }

    @Override
    public void onItemSelected(int position) {
        if (mAlertDialog == null) {
            return;
        }
        mAlertDialog.dismiss();

        mPosition = position;

        clearDefaults();
        restoreDefaults();

        // Clear old data
        mQuizDataUpdate = new QuizData();

        mEditTitle.setText(quiz.get(position).title);
        mEditDescription.setText(quiz.get(position).description);

        for (int i = 0; i < quiz.get(position).answers.size(); i++) {
            AnswerData answerData = quiz.get(position).answers.get(i);

            switch (i) {
                case 0:
                    mAnswer.setText(answerData.answer);
                    mCorrect.setChecked(answerData.correct);

                    break;
                case 1:
                    mAnswer2.setText(answerData.answer);
                    mCorrect2.setChecked(answerData.correct);

                    break;
                case 2:
                    mAnswer3.setText(answerData.answer);
                    mCorrect3.setChecked(answerData.correct);

                    break;
            }
        }
    }

    private void createQuestion() {
        String strTitle = mEditTitle.getText().toString();
        String strDescription = mEditDescription.getText().toString();

        String strAnswer = mAnswer.getText().toString();
        String strAnswer2 = mAnswer2.getText().toString();
        String strAnswer3 = mAnswer3.getText().toString();

        Boolean correct = mCorrect.isChecked();
        Boolean correct2 = mCorrect2.isChecked();
        Boolean correct3 = mCorrect3.isChecked();

        switch (mMode) {
            case mBinaryMode:
                Quiz quizBinary = new Quiz(mBinaryMode);
                quizBinary.save();

                Question q1 = new Question(quizBinary, strTitle, strDescription);
                q1.save();

                setAnswer(q1, strAnswer, correct);

                break;
            case mMultiMode:
                Quiz quizMulti = new Quiz(mMultiMode);
                quizMulti.save();

                Question q2 = new Question(quizMulti, strTitle, strDescription);
                q2.save();

                setAnswer(q2, strAnswer, correct);
                setAnswer(q2, strAnswer2, correct2);
                setAnswer(q2, strAnswer3, correct3);

                break;
        }

        new loadQuestions().execute(mMode);
        clearDefaults();
    }

    private void setAnswer(Question question, String answer, Boolean correct) {
        SugarRecord.save(new Answer(question, answer, correct));
    }

    private void saveQuestion() {
        ArrayList<AnswerData> mAnswerDataUpdate = new ArrayList<>();

        mQuizDataUpdate.id = quiz.get(mPosition).id;
        mQuizDataUpdate.title = mEditTitle.getText().toString();
        mQuizDataUpdate.description = mEditDescription.getText().toString();

        for (int i = 0; i < quiz.get(mPosition).answers.size(); i++) {
            AnswerData answerData = new AnswerData();
            answerData.id = quiz.get(mPosition).answers.get(i).id;
            switch (i) {
                case 0:
                    if (mAnswer.getText().length() > 0) {
                        answerData.answer = mAnswer.getText().toString();
                        answerData.correct = mCorrect.isChecked();
                        mAnswerDataUpdate.add(answerData);
                    }

                    break;
                case 1:
                    if (mAnswer2.getText().length() > 0) {
                        answerData.answer = mAnswer2.getText().toString();
                        answerData.correct = mCorrect2.isChecked();
                        mAnswerDataUpdate.add(answerData);
                    }

                    break;
                case 2:
                    if (mAnswer3.getText().length() > 0) {
                        answerData.answer = mAnswer3.getText().toString();
                        answerData.correct = mCorrect3.isChecked();
                        mAnswerDataUpdate.add(answerData);
                    }

                    break;
            }
        }

        mQuizDataUpdate.answers = mAnswerDataUpdate;
        saveQuery();
    }

    private void clearDefaults() {
        mEditTitle.setText(null);
        mEditDescription.setText(null);

        mAnswer.setText(null);
        mAnswer2.setText(null);
        mAnswer3.setText(null);

        mCorrect.setChecked(false);
        mCorrect2.setChecked(false);
        mCorrect3.setChecked(false);

        mEditLayout.setVisibility(View.GONE);
        mAnswerLayout.setVisibility(View.GONE);
        mAnswerLayout2.setVisibility(View.GONE);
        mAnswerLayout3.setVisibility(View.GONE);
    }

    private void restoreDefaults() {
        mEditLayout.setVisibility(View.VISIBLE);
        mAnswerLayout.setVisibility(View.VISIBLE);

        if (mMode.equals(mMultiMode)) {
            mAnswerLayout2.setVisibility(View.VISIBLE);
            mAnswerLayout3.setVisibility(View.VISIBLE);
        }
    }

    private void saveQuery() {
        Question question = Question.findById(Question.class, mQuizDataUpdate.id);
        question.setTitle(mQuizDataUpdate.title);
        question.setDescription(mQuizDataUpdate.description);
        question.save();

        for (int i = 0; i < mQuizDataUpdate.answers.size(); i++) {
            Answer answer = Answer.findById(Answer.class, mQuizDataUpdate.answers.get(i).id);
            answer.setAnswer(mQuizDataUpdate.answers.get(i).answer);
            answer.setCorrect(mQuizDataUpdate.answers.get(i).correct);
            answer.save();
        }

        // Preload the questions from DB
        new loadQuestions().execute(mMode);

        Toast.makeText(this, R.string.success_changed, Toast.LENGTH_LONG).show();
        clearDefaults();
    }

    private class loadQuestions extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            quiz.clear();
        }

        @Override
        protected Void doInBackground(String... args) {
            String mode = args[0];

            List<Quiz> quizzes = Quiz.find(Quiz.class, "title =?", mode);

            for (int i = 0; i < quizzes.size(); i++) {
                List<Question> questions = quizzes.get(i).getQuestions();

                for (int j = 0; j < questions.size(); j++) {
                    QuizData quizData = new QuizData();

                    quizData.id = questions.get(j).getId();
                    quizData.title = questions.get(j).getTitle();
                    quizData.description = questions.get(j).getDescription();

                    List<Answer> answers = questions.get(j).getAnswers();
                    ArrayList<AnswerData> getAnswers = new ArrayList<>();

                    for (int k = 0; k < answers.size(); k++) {
                        AnswerData answerData = new AnswerData();

                        answerData.id = answers.get(k).getId();
                        answerData.answer = answers.get(k).getAnswer();
                        answerData.correct = answers.get(k).getCorrect();

                        getAnswers.add(answerData);
                    }

                    quizData.answers = getAnswers;

                    quiz.add(quizData);
                }
            }

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            clearDefaults();
        }
    }
}
