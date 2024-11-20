package com.example.yogaadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaadminapp.Course.AddCourseActivity;
import com.example.yogaadminapp.Course.YogaCourse;
import com.example.yogaadminapp.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import android.view.View;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dbHelper;
    RecyclerViewAdapter adapter;
    ArrayList<YogaCourse> courseList;
    TextView txtNoData;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        txtNoData = findViewById(R.id.txtNoData);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        dbHelper = new DatabaseHelper(this);

        courseList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, courseList, dbHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Xử lý Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Map<Integer, Runnable> actions = new HashMap<>();
            actions.put(R.id.nav_add, () -> {
                Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
                startActivity(intent);
            });
            actions.put(R.id.nav_home, () -> {
                Toast.makeText(MainActivity.this, "Already on Home Screen", Toast.LENGTH_SHORT).show();
            });
            actions.put(R.id.nav_search, () -> {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            });


            Runnable action = actions.get(item.getItemId());
            if (action != null) {
                action.run();
                return true;
            } else {
                return false;
            }
        });




        // Xử lý nút Delete All
        findViewById(R.id.btnDeleteAll).setOnClickListener(v -> {
            dbHelper.deleteAllCourses();
            courseList.clear();
            adapter.notifyDataSetChanged();
            checkNoData();
            Toast.makeText(this, "All courses deleted", Toast.LENGTH_SHORT).show();
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
            txtNoData.setVisibility(View.VISIBLE);  // View.VISIBLE được sử dụng chính xác
        } else {
            txtNoData.setVisibility(View.GONE);  // View.GONE được sử dụng chính xác
        }
    }


}
