package k.kirov.famousquotequiz.main;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import k.kirov.famousquotequiz.R;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private IRecycleViewQuestions mListener;
    private List<QuizData> questionList;

    class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        TextView title, description;

        public void setItemPosition(int position) {
            this.position = position;
        }

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_description);

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
                        mListener.onItemSelected(position);
                    }
                    return true;
                }
            });
        }
    }

    public QuestionsAdapter(List<QuizData> questionList, IRecycleViewQuestions listener) {
        this.questionList = questionList;
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_questions, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder != null) {
            holder.title.setText(questionList.get(position).title);
            holder.description.setText(questionList.get(position).description);

            holder.setItemPosition(position);
        }
    }
}
