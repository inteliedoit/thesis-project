package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.lieverandiver.thesisproject.adapter.ProjectResultAdapter;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.ProjectResult;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ClassService;
import com.remswork.project.alice.service.ProjectService;
import com.remswork.project.alice.service.impl.ClassServiceImpl;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Activity_E_Result_Project extends AppCompatActivity {

    private static final String TAG = Activity_E_Result_Project.class.getSimpleName();

    private final ProjectService projectService = new ProjectServiceImpl();
    private final ClassService classService = new ClassServiceImpl();
    private final List<ProjectResult> resultList = new ArrayList<>();
    private Project project;

    private TextView textViewDate;
    private TextView textViewName;
    private TextView textViewTotal;
    private RecyclerView recyclerViewView;

    private long classId;
    private long termId;
    private long examId;

    private class ExamViewThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String date = project.getDate();
                        String title = project.getTitle();
                        String itemTotal = String.valueOf(project.getItemTotal());

                        textViewDate.setText(date);
                        textViewName.setText(title);
                        textViewTotal.setText(itemTotal);

                        for (Student s : classService.getStudentList(classId)) {
                            ProjectResult result = projectService.getProjectResultByProjectAndStudentId(project.getId(), s.getId());
                            if(result != null)
                                resultList.add(result);
                        }

                        ProjectResultAdapter simpleProjectAdapter = new ProjectResultAdapter(Activity_E_Result_Project.this, resultList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_E_Result_Project.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerViewView.setAdapter(simpleProjectAdapter);
                        recyclerViewView.setLayoutManager(layoutManager);
                        recyclerViewView.setItemAnimator(new DefaultItemAnimator());
                    } catch (ClassException| GradingFactorException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void init() {
        textViewDate = (TextView) findViewById(R.id.result_date5);
        textViewName = (TextView) findViewById(R.id.result_name5);
        textViewTotal = (TextView) findViewById(R.id.result_total5);
        recyclerViewView = (RecyclerView) findViewById(R.id.result_recycler5);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            Log.i(TAG, "onCreate");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_z_result_project);

            classId = getIntent().getExtras().getLong("classId");
            examId = getIntent().getExtras().getLong("projectId");
            termId = getIntent().getExtras().getLong("termId");
            project = projectService.getProjectById(examId);

            init();
            new ExamViewThread().start();
        }catch (GradingFactorException e) {
            e.printStackTrace();
        }
    }
}
