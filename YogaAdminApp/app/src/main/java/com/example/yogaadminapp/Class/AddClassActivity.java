package com.example.yogaadminapp.Class;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

import java.util.Calendar;

public class AddClassActivity extends AppCompatActivity {

    EditText edtTeacher, edtDate, edtComments, edtClassName;
    Button btnSaveClass, btnBack;
    DatabaseHelper dbHelper;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        edtClassName = findViewById(R.id.edtClassName);
        edtTeacher = findViewById(R.id.edtTeacher);
        edtDate = findViewById(R.id.edtDate);
        edtComments = findViewById(R.id.edtComments);
        btnSaveClass = findViewById(R.id.btnSaveClass);
        btnBack = findViewById(R.id.btnBack);

        dbHelper = new DatabaseHelper(this);
        courseId = getIntent().getIntExtra("courseId", -1);

        // Show DatePickerDialog on date field click
        edtDate.setOnClickListener(v -> showDatePickerDialog());

        btnSaveClass.setOnClickListener(v -> {
            if (validateInputs()) {
                String className = edtClassName.getText().toString();
                String teacher = edtTeacher.getText().toString();
                String date = edtDate.getText().toString();
                String comments = edtComments.getText().toString();

                ClassModel newClass = new ClassModel(className, teacher, date, comments, courseId);
                dbHelper.insertClass(newClass);

                Toast.makeText(AddClassActivity.this, "Class added and synced successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        btnBack.setOnClickListener(v -> {
            finish(); // Kết thúc Activity hiện tại, quay về trang trước
        });
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
}
