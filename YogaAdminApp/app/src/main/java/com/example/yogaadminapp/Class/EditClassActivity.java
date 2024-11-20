package com.example.yogaadminapp.Class;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

public class EditClassActivity extends AppCompatActivity {

    EditText edtTeacher, edtDate, edtComments, edtCourseName;
    Button btnUpdate, btnBack, btnDelete;
    DatabaseHelper dbHelper;
    ClassModel currentClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        // Ánh xạ các view
        edtTeacher = findViewById(R.id.edtTeacher);
        edtDate = findViewById(R.id.edtDate);
        edtComments = findViewById(R.id.edtComments);
        btnUpdate = findViewById(R.id.btnUpdateClass);
        btnBack = findViewById(R.id.btnBack);


        dbHelper = new DatabaseHelper(this);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        currentClass = (ClassModel) intent.getSerializableExtra("classModel");

        if (currentClass != null) {
            populateFields();
        }

        // Nút Update để cập nhật class
        btnUpdate.setOnClickListener(v -> {
            if (validateInputs()) {
                updateClass();
                Toast.makeText(EditClassActivity.this, "Class updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btnBack.setOnClickListener(v -> {
            finish(); // Kết thúc Activity hiện tại, quay về trang trước
        });


    }

    private void populateFields() {
        edtTeacher.setText(currentClass.getTeacher());
        edtDate.setText(currentClass.getDate());
        edtComments.setText(currentClass.getComments());
    }

    private boolean validateInputs() {
        if (edtTeacher.getText().toString().isEmpty()) {
            edtTeacher.setError("Teacher is required");
            return false;
        }
        if (edtDate.getText().toString().isEmpty()) {
            edtDate.setError("Date is required");
            return false;
        }
        return true;
    }

    private void updateClass() {
        currentClass = new ClassModel(
                currentClass.getId(),
                edtCourseName.getText().toString(),
                edtTeacher.getText().toString(),
                edtDate.getText().toString(),
                edtComments.getText().toString(),
                currentClass.getCourseId()
        );
        dbHelper.updateClass(currentClass);
    }

    private void deleteClass() {
        dbHelper.deleteClass(currentClass.getId());
    }
}

