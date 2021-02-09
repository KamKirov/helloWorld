package k.kirov.famousquotequiz.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import k.kirov.famousquotequiz.R;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {

    private List<ScoreData> scoresList;

    class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        TextView name, scores;

        public void setItemPosition(int position) {
            this.position = position;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.list_user);
            scores = (TextView) itemView.findViewById(R.id.list_scores);
        }
    }

    public ScoresAdapter(List<ScoreData> scoresList) {
        this.scoresList = scoresList;
    }

    @Override
    public int getItemCount() {
        return scoresList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_scores, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder != null) {
            String result = String.valueOf(scoresList.get(position).scores) + " / " + String.valueOf(scoresList.get(position).answers);

            holder.name.setText(scoresList.get(position).name);
            holder.scores.setText(result);

            holder.setItemPosition(position);
        }
    }
}
