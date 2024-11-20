package com.example.yogaadminapp.Course;

import android.content.Intent;
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

public class DetailCourseActivity extends AppCompatActivity {

    EditText edtDay, edtTime, edtCapacity, edtDuration, edtPrice, edtDescription, edtCourseName;
    RadioGroup rgType;
    Button btnUpdate, btnEdit, btnBack;
    DatabaseHelper dbHelper;
    YogaCourse currentCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);

        // Ánh xạ các View
        edtCourseName = findViewById(R.id.edtCourseName);
        edtDay = findViewById(R.id.edtDay);
        edtTime = findViewById(R.id.edtTime);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        rgType = findViewById(R.id.rgType);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnEdit = findViewById(R.id.btnEdit);
        btnBack = findViewById(R.id.btnBack);


        dbHelper = new DatabaseHelper(this);

        // Nhận dữ liệu lớp học từ Intent
        Intent intent = getIntent();
        currentCourse = (YogaCourse) intent.getSerializableExtra("course");

        if (currentCourse != null) {
            populateFields();  // Hiển thị thông tin lớp học
        }

        // Nút Edit để bật chế độ chỉnh sửa
        btnEdit.setOnClickListener(v -> enableEditing(true));

        // Nút Update để lưu thông tin cập nhật
        btnUpdate.setOnClickListener(v -> {
            if (validateInputs()) {
                updateCourse();
                Toast.makeText(DetailCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBack.setOnClickListener(v -> {
            finish(); // Kết thúc Activity hiện tại, quay về trang trước
        });

        // Ban đầu chế độ chỉnh sửa sẽ tắt
        enableEditing(false);
    }

    private void populateFields() {
        edtCourseName.setText(currentCourse.getName());
        edtDay.setText(currentCourse.getDay());
        edtTime.setText(currentCourse.getTime());
        edtCapacity.setText(String.valueOf(currentCourse.getCapacity()));
        edtDuration.setText(String.valueOf(currentCourse.getDuration()));
        edtPrice.setText(String.valueOf(currentCourse.getPrice()));
        edtDescription.setText(currentCourse.getDescription());

        switch (currentCourse.getType()) {
            case "Flow Yoga":
                rgType.check(R.id.rbFlowYoga);
                break;
            case "Aerial Yoga":
                rgType.check(R.id.rbAerialYoga);
                break;
            case "Family Yoga":
                rgType.check(R.id.rbFamilyYoga);
                break;
        }
    }

    private boolean validateInputs() {
        if (edtCourseName.getText().toString().isEmpty()) {
            edtCourseName.setError("Name is required");
            return false;
        }
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
        if (rgType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a type of Course", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateCourse() {
        currentCourse.setName(edtCourseName.getText().toString());
        currentCourse.setDay(edtDay.getText().toString());
        currentCourse.setTime(edtTime.getText().toString());
        currentCourse.setCapacity(Integer.parseInt(edtCapacity.getText().toString()));
        currentCourse.setDuration(Integer.parseInt(edtDuration.getText().toString()));
        currentCourse.setPrice(Double.parseDouble(edtPrice.getText().toString()));
        currentCourse.setDescription(edtDescription.getText().toString());

        int selectedTypeId = rgType.getCheckedRadioButtonId();
        RadioButton selectedTypeButton = findViewById(selectedTypeId);
        currentCourse.setType(selectedTypeButton.getText().toString());

        dbHelper.updateCourse(currentCourse);
    }

    private void enableEditing(boolean enable) {
        edtCourseName.setEnabled(enable);
        edtDay.setEnabled(enable);
        edtTime.setEnabled(enable);
        edtCapacity.setEnabled(enable);
        edtDuration.setEnabled(enable);
        edtPrice.setEnabled(enable);
        edtDescription.setEnabled(enable);

        for (int i = 0; i < rgType.getChildCount(); i++) {
            rgType.getChildAt(i).setEnabled(enable);
        }

        btnUpdate.setVisibility(enable ? View.VISIBLE : View.GONE);
        btnEdit.setVisibility(enable ? View.GONE : View.VISIBLE);
    }
}
