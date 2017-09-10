package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.lieverandiver.thesisproject.ActivityInputExam;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Student;

import java.util.List;
import java.util.Locale;

@Deprecated
public class ExamStudentInputAdapter extends RecyclerView.Adapter<ExamStudentInputAdapter.ExamStudentInputAdapterViewHolder>
    implements ActivityInputExam.InputListener {

    private List<Student> studentList;
    private Context context;
    private LayoutInflater layoutInflater;
    private int score[];
    private boolean isNoError = true;
    private int totalScore;
    private boolean doValidate;

    public ExamStudentInputAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        score = new int[studentList.size()];
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ExamStudentInputAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_input_cardview, parent, false);
        return new ExamStudentInputAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamStudentInputAdapterViewHolder holder, int position) {

        Student student = studentList.get(position);
        holder.setView(student, position);

        if(doValidate) {
               if(holder.getScore() > totalScore || holder.getScore() < 0) {
                   holder.setStatus(false);
                   isNoError = false;
               }else {

                   holder.setStatus(true);
               }
                score[position] = holder.getScore();
        }if(position >= studentList.size()) {
            doValidate = false;
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public void onValidate(int totalScore, boolean doValidate) {
        this.totalScore = totalScore;
        this.doValidate = doValidate;
        notifyDataSetChanged();
    }

    @Override
    public boolean isNoError() {
        return false;
    }

    @Override
    public int getScore(int index) {
        return score[index];
    }

    public class ExamStudentInputAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView studentImage;
        private TextView studentDetail;
        private EditText editText;
        private Student student;
        private Spinner spinner;
        private LinearLayout layout;
        private TextView txInit;

        ExamStudentInputAdapterViewHolder(View itemView) {
            super(itemView);
            studentImage = (ImageView) itemView.findViewById(R.id.f_data_student_profile);
            studentDetail = (TextView) itemView.findViewById(R.id.input_cardview_name);
            editText = (EditText) itemView.findViewById(R.id.input_cardview_score);
            layout = (LinearLayout) itemView.findViewById(R.id.input_cardview_layout);
            txInit = (TextView) itemView.findViewById(R.id.input_cardview_init);

        }

        void setView(final Student student, final int position) {
            this.student = student;
            String display = String.format(Locale.ENGLISH, "%s \t%s %s. %s - %d",
                    "1-A",
                    student.getFirstName(),
                    student.getMiddleName().substring(0, 1),
                    student.getLastName(),
                    student.getStudentNumber());
            studentDetail.setText(display);
            String init = student.getLastName().substring(0, 1);
            txInit.setText(init);
        }

        public Student getStudent() {
            return student;
        }

        public int getScore() {
            return Integer.parseInt(!editText.getText().toString().equals("") ? editText.getText().toString() : "0");
        }

        public void setStatus(boolean isSuccess) {
            if(isSuccess)
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightSuccess));
            else
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightDanger));
            studentDetail.setTextColor(context.getResources().getColor(R.color.colorMoca2));
        }
    }
}
