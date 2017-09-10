package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lieverandiver.thesisproject.adapter.AssignmentAdapter;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.service.AssignmentService;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;

import java.util.List;

import static com.lieverandiver.thesisproject.R.id.add_add2;
import static com.lieverandiver.thesisproject.R.id.add_back2;

public class Activity_B_Add_Assignment extends AppCompatActivity implements AssignmentAdapter.OnClickListener,
        View.OnClickListener {

    private static final String TAG = Activity_B_Add_Assignment.class.getSimpleName();
    final AssignmentService aassignmentService = new AssignmentServiceImpl();
    private Button btnBackButton;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutActivity;
    private long classId;
    private long termId;

    private class AssignmentAddThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Assignment> assignmentList = aassignmentService.getAssignmentListByClassId(classId);
                        AssignmentAdapter assignmentAdapter = new AssignmentAdapter(Activity_B_Add_Assignment.this, assignmentList);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_B_Add_Assignment.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerView.setAdapter(assignmentAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    }catch (GradingFactorException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private void init() {
        linearLayoutActivity = (LinearLayout) findViewById(R.id.add_add2);
        recyclerView = (RecyclerView) findViewById(R.id.add_recycler2);
        btnBackButton = (Button) findViewById(R.id.add_back2);
        linearLayoutActivity.setOnClickListener(this);
        btnBackButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_add_assignment);
        try {
            classId = getIntent().getExtras().getLong("classId");
            termId = getIntent().getExtras().getLong("termId");

            init();
            new AssignmentAddThread().start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(Assignment assignment, long assignmentId) {
        Intent intent = getIntent();
        intent.putExtra("assignmentId", assignmentId);
        intent.setClass(this, Activity_B_Result_Assignment.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case add_add2 :
                Intent intent = getIntent().setClass(this, AssignmentInputActivity.class);
                startActivity(intent);
                break;
            case add_back2 :
                finish();
                break;
        }
    }
}