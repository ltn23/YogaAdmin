package com.example.yogaadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaadminapp.Course.AddCourseActivity;
import com.example.yogaadminapp.Course.YogaCourse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAddCourse, btnDeleteAll;
    DatabaseHelper dbHelper;
    RecyclerViewAdapter adapter;
    ArrayList<YogaCourse> courseList;
    TextView txtNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        txtNoData = findViewById(R.id.txtNoData);
        dbHelper = new DatabaseHelper(this);

        courseList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, courseList, dbHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
            startActivity(intent);
        });

        btnDeleteAll.setOnClickListener(v -> {
            dbHelper.deleteAllCourses();
            courseList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "All courses deleted", Toast.LENGTH_SHORT).show();
            checkNoData();
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
