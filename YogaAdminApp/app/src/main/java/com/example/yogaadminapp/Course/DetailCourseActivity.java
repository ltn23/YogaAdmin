package com.example.yogaadminapp.Course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

import java.util.Calendar;

import android.app.TimePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;

public class DetailCourseActivity extends AppCompatActivity {

    EditText edtStartTime, edtEndTime, edtCapacity, edtDuration, edtPrice, edtDescription, edtCourseName;
    Spinner spinnerDay;
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
        spinnerDay = findViewById(R.id.spinnerDay);
        edtStartTime = findViewById(R.id.edtStartTime);
        edtEndTime = findViewById(R.id.edtEndTime);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        rgType = findViewById(R.id.rgType);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnEdit = findViewById(R.id.btnEdit);
        btnBack = findViewById(R.id.btnBack);

        dbHelper = new DatabaseHelper(this);

        // Khởi tạo Adapter cho Spinner trước khi populateFields được gọi
        setupDaySpinner();

        // Nhận dữ liệu lớp học từ Intent
        Intent intent = getIntent();
        currentCourse = (YogaCourse) intent.getSerializableExtra("course");

        if (currentCourse != null) {
            populateFields();  // Hiển thị thông tin lớp học
        }

        btnEdit.setOnClickListener(v -> enableEditing(true));
        btnUpdate.setOnClickListener(v -> {
            if (validateInputs()) {
                updateCourse();
                Toast.makeText(DetailCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnBack.setOnClickListener(v -> finish());

        edtStartTime.setOnClickListener(v -> showTimePickerDialog(edtStartTime));
        edtEndTime.setOnClickListener(v -> showTimePickerDialog(edtEndTime));

        enableEditing(false);
    }

    private void setupDaySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.days_of_week, // Đảm bảo mảng được định nghĩa trong strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
    }

    private void populateFields() {
        edtCourseName.setText(currentCourse.getName());

        // Kiểm tra và đặt Adapter cho Spinner
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinnerDay.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(currentCourse.getDay());
            spinnerDay.setSelection(position);
        }

        String[] times = currentCourse.getTime().split(" - ");
        if (times.length == 2) {
            edtStartTime.setText(times[0]);
            edtEndTime.setText(times[1]);
        }

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


    private void showTimePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (TimePicker view, int selectedHour, int selectedMinute) -> {
                    String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                    editText.setText(time);

                    if (!edtStartTime.getText().toString().isEmpty() && !edtEndTime.getText().toString().isEmpty()) {
                        calculateDuration();
                    }
                },
                hour,
                minute,
                true
        );
        timePickerDialog.show();
    }

    private void calculateDuration() {
        String startTime = edtStartTime.getText().toString();
        String endTime = edtEndTime.getText().toString();

        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        int startTotalMinutes = startHour * 60 + startMinute;
        int endTotalMinutes = endHour * 60 + endMinute;

        int duration = endTotalMinutes - startTotalMinutes;

        if (duration < 0) {
            Toast.makeText(this, "End Time must be after Start Time", Toast.LENGTH_SHORT).show();
            edtEndTime.setError("Invalid End Time");
        } else {
            edtDuration.setText(String.valueOf(duration)); // Hiển thị Duration dưới dạng phút
        }
    }



    private boolean validateInputs() {
        if (edtCourseName.getText().toString().isEmpty()) {
            edtCourseName.setError("Course Name is required");
            return false;
        }
        if (spinnerDay.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a day of the week", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStartTime.getText().toString().isEmpty()) {
            edtStartTime.setError("Start Time is required");
            return false;
        }
        if (edtEndTime.getText().toString().isEmpty()) {
            edtEndTime.setError("End Time is required");
            return false;
        }
        if (edtCapacity.getText().toString().isEmpty()) {
            edtCapacity.setError("Capacity is required");
            return false;
        }
        if (edtPrice.getText().toString().isEmpty()) {
            edtPrice.setError("Price is required");
            return false;
        }
        if (rgType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a type of course", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (edtDuration.getText().toString().isEmpty() || Integer.parseInt(edtDuration.getText().toString()) <= 0) {
            Toast.makeText(this, "Invalid Duration", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateCourse() {
        currentCourse.setName(edtCourseName.getText().toString());
        currentCourse.setDay(spinnerDay.getSelectedItem().toString());
        currentCourse.setTime(edtStartTime.getText().toString() + " - " + edtEndTime.getText().toString());
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
        spinnerDay.setEnabled(enable);
        edtStartTime.setEnabled(enable);
        edtEndTime.setEnabled(enable);
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
