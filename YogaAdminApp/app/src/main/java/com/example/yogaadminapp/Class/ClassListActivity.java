package com.example.yogaadminapp.Class;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btnAddClass;
    DatabaseHelper dbHelper;
    ClassAdapter adapter;
    ArrayList<ClassModel> classList;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        recyclerView = findViewById(R.id.recyclerViewClasses);
        btnAddClass = findViewById(R.id.btnAddClass);
        dbHelper = new DatabaseHelper(this);

        courseId = getIntent().getIntExtra("courseId", -1);
        if (courseId == -1) {
            Toast.makeText(this, "Invalid course ID", Toast.LENGTH_SHORT).show();
            finish();  // Kết thúc activity nếu dữ liệu không hợp lệ
        }

        classList = dbHelper.getClassesByCourse(courseId);
        adapter = new ClassAdapter(this, classList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAddClass.setOnClickListener(v -> {
            Intent intent = new Intent(ClassListActivity.this, AddClassActivity.class);
            intent.putExtra("courseId", courseId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        classList.clear();
        classList.addAll(dbHelper.getClassesByCourse(courseId));
        adapter.notifyDataSetChanged();
    }
}

