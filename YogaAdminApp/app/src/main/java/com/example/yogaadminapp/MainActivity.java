package com.example.yogaadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaadminapp.Course.AddCourseActivity;
import com.example.yogaadminapp.Course.YogaCourse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnHome, btnAddCourse;
    DatabaseHelper dbHelper;
    RecyclerViewAdapter adapter;
    ArrayList<YogaCourse> courseList;
    TextView txtNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnHome = findViewById(R.id.btnHome);
        btnAddCourse = findViewById(R.id.btnAdd);
        txtNoData = findViewById(R.id.txtNoData);
        dbHelper = new DatabaseHelper(this);

        courseList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, courseList, dbHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete All Courses")
                    .setMessage("Are you sure you want to delete all courses?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteAllCourses();
                        courseList.clear();
                        adapter.notifyDataSetChanged();
                        checkNoData();
                        Toast.makeText(this, "All courses deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


        btnAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
            startActivity(intent);
        });

        btnHome.setOnClickListener(v -> {
            Toast.makeText(this, "Already on Home Screen", Toast.LENGTH_SHORT).show();
        });

        loadCourses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();
    }

    private void loadCourses() {
        courseList.clear();
        courseList.addAll(dbHelper.getAllCourses());
        adapter.notifyDataSetChanged();
        checkNoData();
    }

    private void checkNoData() {
        if (courseList.isEmpty()) {
            txtNoData.setVisibility(View.VISIBLE);
        } else {
            txtNoData.setVisibility(View.GONE);
        }
    }
}
