package com.example.yogaadminapp.Course;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

public class AddCourseActivity extends AppCompatActivity {

    EditText edtDay, edtTime, edtCapacity, edtDuration, edtPrice, edtDescription;
    RadioGroup rgType;
    Button btnSave;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Ánh xạ các thành phần từ giao diện
        edtDay = findViewById(R.id.edtDay);
        edtTime = findViewById(R.id.edtTime);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        rgType = findViewById(R.id.rgType);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Lấy dữ liệu từ các trường nhập liệu
                    String day = edtDay.getText().toString();
                    String time = edtTime.getText().toString();
                    int capacity = Integer.parseInt(edtCapacity.getText().toString());
                    int duration = Integer.parseInt(edtDuration.getText().toString());
                    double price = Double.parseDouble(edtPrice.getText().toString());
                    String description = edtDescription.getText().toString();

                    // Lấy loại lớp học từ RadioGroup
                    int selectedTypeId = rgType.getCheckedRadioButtonId();
                    RadioButton selectedTypeButton = findViewById(selectedTypeId);
                    String type = selectedTypeButton.getText().toString();


                    YogaCourse newCourse = new YogaCourse(day, time, capacity, duration, price, type, description);
                    dbHelper.insertCourse(newCourse);

                    Toast.makeText(AddCourseActivity.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Quay về màn hình trước đó
                }
            }
        });
    }

    // Hàm kiểm tra các trường nhập liệu
    private boolean validateInputs() {
        if (edtDay.getText().toString().isEmpty()) {
            edtDay.setError("Day is required");
            return false;
        }
        if (edtTime.getText().toString().isEmpty()) {
            edtTime.setError("Time is required");
            return false;
        }
        if (edtCapacity.getText().toString().isEmpty()) {
            edtCapacity.setError("Capacity is required");
            return false;
        }
        if (edtDuration.getText().toString().isEmpty()) {
            edtDuration.setError("Duration is required");
            return false;
        }
        if (edtPrice.getText().toString().isEmpty()) {
            edtPrice.setError("Price is required");
            return false;
        }
        if (rgType.getCheckedRadioButtonId() == -1) { // Kiểm tra xem loại lớp học có được chọn không
            Toast.makeText(this, "Please select a type of course", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
