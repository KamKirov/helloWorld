package k.kirov.famousquotequiz.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import k.kirov.famousquotequiz.R;
import k.kirov.famousquotequiz.models.Results;

public class ScoresActivity extends AppCompatActivity {

    ArrayList<ScoreData> scores = new ArrayList<>();
    RecyclerView recyclerView;
    ScoresAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewScores);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        loadScores();
    }

    private void loadScores() {
        Results results = new Results();
        List<Results> scoresResult = results.getResults();
        for (int i = 0; i < scoresResult.size(); i++) {
            ScoreData scoreData = new ScoreData();

            scoreData.name = scoresResult.get(i).getUserId();
            scoreData.scores = scoresResult.get(i).getScores();
            scoreData.answers = scoresResult.get(i).getAnswers();

            scores.add(scoreData);
        }

        adapter = new ScoresAdapter(scores);
        recyclerView.setAdapter(adapter);
    }
}
