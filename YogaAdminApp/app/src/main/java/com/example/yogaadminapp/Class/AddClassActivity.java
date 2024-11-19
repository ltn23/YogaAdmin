package com.example.yogaadminapp.Class;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

public class AddClassActivity extends AppCompatActivity {

    EditText edtTeacher, edtDate, edtComments;
    Button btnSaveClass;
    DatabaseHelper dbHelper;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        edtTeacher = findViewById(R.id.edtTeacher);
        edtDate = findViewById(R.id.edtDate);
        edtComments = findViewById(R.id.edtComments);
        btnSaveClass = findViewById(R.id.btnSaveClass);

        dbHelper = new DatabaseHelper(this);
        courseId = getIntent().getIntExtra("courseId", -1);

        btnSaveClass.setOnClickListener(v -> {
            if (validateInputs()) {
                String teacher = edtTeacher.getText().toString();
                String date = edtDate.getText().toString();
                String comments = edtComments.getText().toString();

                ClassModel newClass = new ClassModel(teacher, date, comments, courseId);
                dbHelper.insertClass(newClass);

                Toast.makeText(AddClassActivity.this, "Class added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
}
