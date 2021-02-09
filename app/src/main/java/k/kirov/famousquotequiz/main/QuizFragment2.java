package k.kirov.famousquotequiz.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import k.kirov.famousquotequiz.R;
import k.kirov.famousquotequiz.models.Answer;
import k.kirov.famousquotequiz.models.Question;
import k.kirov.famousquotequiz.models.Quiz;
import k.kirov.famousquotequiz.models.Results;
import k.kirov.famousquotequiz.models.User;

public class QuizFragment2 extends BaseQuizContainerFragment implements View.OnClickListener {

    long userId;
    int number = 0, scores = 0, answers = 0;
    ArrayList<QuizData> quiz = new ArrayList<>();
    ArrayList<QuizData> results = new ArrayList<>();
    SharedPreferences.Editor editorLogin;
    SharedPreferences prefLogin;
    private TextView mTitle, mDescription;
    private Button mAnswer1, mAnswer2, mAnswer3;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz2, container, false);

        mTitle = (TextView) view.findViewById(R.id.q2_title);
        mDescription = (TextView) view.findViewById(R.id.q2_description);
        mAnswer1 = (Button) view.findViewById(R.id.q_answer1);
        mAnswer2 = (Button) view.findViewById(R.id.q_answer2);
        mAnswer3 = (Button) view.findViewById(R.id.q_answer3);

        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);

        prefLogin = getActivity().getSharedPreferences("fragment_login", Activity.MODE_PRIVATE);
        editorLogin = prefLogin.edit();

        userId = prefLogin.getLong("userId", -1);

        new loadQuestions().execute();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        String correctAnswer = "";
        for (int i = 0; i < quiz.get(number).answers.size(); i++) {
            if (quiz.get(number).answers.get(i).correct) {
                correctAnswer = quiz.get(number).answers.get(i).answer;
            }
        }

        QuizData question = new QuizData();
        question.description = quiz.get(number).description;
        question.result = Boolean.parseBoolean(v.getTag().toString());

        results.add(question);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        if (Boolean.parseBoolean(v.getTag().toString())) {
            alertDialogBuilder.setMessage(getString(R.string.correct_answer) + correctAnswer);

            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            // Hide after some seconds
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    number++;
                    displayQuiz(number);
                    alertDialog.dismiss();
                }
            };

            handler.postDelayed(runnable, 2000);
        } else {
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder
                    .setMessage(getString(R.string.not_correct) + correctAnswer)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            number++;
                            displayQuiz(number);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private class loadQuestions extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... args) {
            List<Quiz> quizzes = Quiz.find(Quiz.class, "title =?", "Multiple choice mode");

            for (int i = 0; i < quizzes.size(); i++) {
                List<Question> questions = quizzes.get(i).getQuestions();

                answers = questions.size();
                for (int j = 0; j < questions.size(); j++) {
                    QuizData quizData = new QuizData();

                    quizData.title = questions.get(j).getTitle();
                    quizData.description = questions.get(j).getDescription();

                    List<Answer> answers = questions.get(j).getAnswers();
                    ArrayList<AnswerData> getAnswers = new ArrayList<>();

                    for (int k = 0; k < answers.size(); k++) {
                        AnswerData answerData = new AnswerData();
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

            displayQuiz(number);
        }
    }

    public void displayQuiz(int pos) {
        if (pos < quiz.size()) {
            QuizData data = quiz.get(pos);

            mTitle.setText(data.title);
            mDescription.setText(data.description);

            for (int i = 0; i < data.answers.size(); i++) {
                switch (i) {
                    case 0:
                        mAnswer1.setText(data.answers.get(i).answer);
                        mAnswer1.setTag(data.answers.get(i).correct);

                        break;
                    case 1:
                        mAnswer2.setText(data.answers.get(i).answer);
                        mAnswer2.setTag(data.answers.get(i).correct);

                        break;
                    case 2:
                        mAnswer3.setText(data.answers.get(i).answer);
                        mAnswer3.setTag(data.answers.get(i).correct);

                        break;
                }
            }
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.result_dialog, null);
            dialogBuilder.setView(dialogView);

            TextView resultsText = (TextView) dialogView.findViewById(R.id.results_text);
            final Button startAgain = (Button) dialogView.findViewById(R.id.start_again_btn);
            final Button addResult = (Button) dialogView.findViewById(R.id.add_result_btn);

            scores = 0;
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).result) {
                    scores++;
                }
                resultsText.append(String.valueOf(i + 1) + ": " + results.get(i).result.toString() + "\n");
            }

            final AlertDialog alertDialog = dialogBuilder.create();

            startAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startAgain();
                    alertDialog.dismiss();
                }
            });

            addResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = User.findById(User.class, userId);

                    Results results = new Results(user, scores, answers);
                    results.save();
                    alertDialog.dismiss();
                }
            });


            alertDialog.show();
        }
    }

    /**
     *  Start the Quiz again
     */
    public void startAgain() {
        results.clear();
        number = 0;
        displayQuiz(number);
    }
}
