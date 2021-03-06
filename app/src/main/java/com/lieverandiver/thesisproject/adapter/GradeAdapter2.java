package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Grade;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class GradeAdapter2 extends RecyclerView.Adapter<GradeAdapter2.GradeViewHolder> {

    private Context context;
    private List<Grade> gradeList;
    private LayoutInflater layoutInflater;

    public GradeAdapter2(Context context, List<Grade> gradeList, long termId) {
        this.gradeList = gradeList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_total_grade_cardview, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        holder.setView(gradeList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    class GradeViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrdScore;
        private TextView txName;
        private TextView txDate;

        public GradeViewHolder(View itemView) {
            super(itemView);
            txtGrdScore = (TextView) itemView.findViewById(R.id.grade_score_part);
            txName = (TextView) itemView.findViewById(R.id.grade_studentname);
            txDate = (TextView) itemView.findViewById(R.id.grade_update_date);
        }

        public void setView(final Grade grade, final int position) {
            String name = String.format("%s, %s %s", grade.getStudent().getLastName(),
                    grade.getStudent().getFirstName(),
                    grade.getStudent().getMiddleName().substring(0, 1)) + ".";
            DecimalFormat format = new DecimalFormat(".##");
            format.setRoundingMode(RoundingMode.UP);
            String score = String.format(Locale.ENGLISH, "%s", format.format(grade.getTotalScore()));
            txName.setText(name);
            txtGrdScore.setText(score);
        }

        public void setScore(double score) {
            txtGrdScore.setText(score + "");
        }

    }
}
