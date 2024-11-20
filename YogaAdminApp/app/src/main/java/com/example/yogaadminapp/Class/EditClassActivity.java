package com.example.yogaadminapp.Class;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

import java.util.Calendar;

public class EditClassActivity extends AppCompatActivity {

    EditText edtTeacher, edtDate, edtComments, edtClassName;
    Button btnUpdate, btnBack;
    DatabaseHelper dbHelper;
    ClassModel currentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        // Ánh xạ các view
        edtClassName = findViewById(R.id.edtClassName);
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

        // Hiển thị DatePickerDialog khi nhấn vào trường Date
        edtDate.setOnClickListener(v -> showDatePickerDialog());

        // Nút Update để cập nhật class
        btnUpdate.setOnClickListener(v -> {
            if (validateInputs()) {
                updateClass();
                Toast.makeText(EditClassActivity.this, "Class updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBack.setOnClickListener(v -> finish()); // Quay lại trang trước
    }

    private void populateFields() {
        edtClassName.setText(currentClass.getName());
        edtTeacher.setText(currentClass.getTeacher());
        edtDate.setText(currentClass.getDate());
        edtComments.setText(currentClass.getComments());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edtDate.setText(date);
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    private boolean validateInputs() {
        if (edtClassName.getText().toString().isEmpty()) {
            edtClassName.setError("Class Name is required");
            return false;
        }
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
        currentClass.setName(edtClassName.getText().toString());
        currentClass.setTeacher(edtTeacher.getText().toString());
        currentClass.setDate(edtDate.getText().toString());
        currentClass.setComments(edtComments.getText().toString());

        dbHelper.updateClass(currentClass);
    }
}
