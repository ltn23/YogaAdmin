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

import java.util.HashMap;

public class AddCourseActivity extends AppCompatActivity {

    EditText edtDay, edtTime, edtCapacity, edtDuration, edtPrice, edtDescription;
    RadioGroup rgType;
    DatabaseHelper dbHelper;
    HashMap<Integer, Runnable> menuActions;

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

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (validateInputs()) {
                saveCourse();
            }
        });
    }

    private void saveCourse() {
        String day = edtDay.getText().toString();
        String time = edtTime.getText().toString();
        int capacity = Integer.parseInt(edtCapacity.getText().toString());
        int duration = Integer.parseInt(edtDuration.getText().toString());
        double price = Double.parseDouble(edtPrice.getText().toString());
        String description = edtDescription.getText().toString();

        int selectedTypeId = rgType.getCheckedRadioButtonId();
        RadioButton selectedTypeButton = findViewById(selectedTypeId);
        String type = selectedTypeButton.getText().toString();

        YogaCourse newCourse = new YogaCourse(day, time, capacity, duration, price, type, description);
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
            Toast.makeText(this, "Please select a type of course", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
