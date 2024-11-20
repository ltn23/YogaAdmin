package com.example.yogaadminapp.Course;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.MainActivity;
import com.example.yogaadminapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Calendar;
import java.util.HashMap;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.TimePickerDialog;
import android.widget.TimePicker;

public class AddCourseActivity extends AppCompatActivity {


    EditText edtStartTime, edtEndTime;
    Spinner spinnerDay;
    EditText edtCapacity, edtDuration, edtPrice, edtDescription, edtCourseName;
    RadioGroup rgType;
    DatabaseHelper dbHelper;
    HashMap<Integer, Runnable> menuActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Ánh xạ các thành phần từ giao diện
        spinnerDay = findViewById(R.id.spinnerDay);
        edtCourseName = findViewById(R.id.edtCourseName);
        edtStartTime = findViewById(R.id.edtStartTime);
        edtEndTime = findViewById(R.id.edtEndTime);
        edtCapacity = findViewById(R.id.edtCapacity);
        edtDuration = findViewById(R.id.edtDuration);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        rgType = findViewById(R.id.rgType);

        dbHelper = new DatabaseHelper(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Khởi tạo HashMap để ánh xạ menu item ID với hành động
        menuActions = new HashMap<>();
        menuActions.put(R.id.nav_home, this::navigateToHome);
        menuActions.put(R.id.nav_add, this::showAlreadyOnPage);

        // Xử lý sự kiện BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Runnable action = menuActions.get(item.getItemId());
                if (action != null) {
                    action.run();
                    return true;
                }
                return false;
            }
        });

        setupDaySpinner();
        edtStartTime.setOnClickListener(v -> showTimePickerDialog(edtStartTime));
        edtEndTime.setOnClickListener(v -> showTimePickerDialog(edtEndTime));

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (validateInputs()) {
                saveCourse();
            }
        });
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

                    // Tự động tính Duration nếu cả Start Time và End Time đã được chọn
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

        // Parse giờ bắt đầu và kết thúc thành phút
        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        // Chuyển đổi thành phút kể từ 00:00
        int startTotalMinutes = startHour * 60 + startMinute;
        int endTotalMinutes = endHour * 60 + endMinute;

        // Tính khoảng cách thời gian
        int duration = endTotalMinutes - startTotalMinutes;

        if (duration < 0) {
            Toast.makeText(this, "End Time must be after Start Time", Toast.LENGTH_SHORT).show();
            edtEndTime.setError("Invalid End Time");
        } else {
            edtDuration.setText(String.valueOf(duration)); // Hiển thị Duration dưới dạng phút
        }
    }


    private void setupDaySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.days_of_week,  // Refer to strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
    }

    private void saveCourse() {
        String courseName = edtCourseName.getText().toString();
        String day = spinnerDay.getSelectedItem().toString();
        String startTime = edtStartTime.getText().toString();
        String endTime = edtEndTime.getText().toString();
        int capacity = Integer.parseInt(edtCapacity.getText().toString());
        int duration = Integer.parseInt(edtDuration.getText().toString());
        double price = Double.parseDouble(edtPrice.getText().toString());
        String description = edtDescription.getText().toString();

        int selectedTypeId = rgType.getCheckedRadioButtonId();
        RadioButton selectedTypeButton = findViewById(selectedTypeId);
        String type = selectedTypeButton.getText().toString();

        YogaCourse newCourse = new YogaCourse(courseName, day, startTime + " - " + endTime, capacity, duration, price, type, description);
        dbHelper.insertCourse(newCourse);

        Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void navigateToHome() {
        Intent homeIntent = new Intent(AddCourseActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void showAlreadyOnPage() {
        Toast.makeText(this, "You're already on Add Course page", Toast.LENGTH_SHORT).show();
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

        // Kiểm tra Duration sau khi đã tính toán
        if (edtDuration.getText().toString().isEmpty() || Integer.parseInt(edtDuration.getText().toString()) <= 0) {
            Toast.makeText(this, "Invalid Duration", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
