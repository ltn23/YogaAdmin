package com.example.yogaadminapp.Search;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaadminapp.Class.ClassAdapter;
import com.example.yogaadminapp.Class.ClassModel;
import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    EditText edtSearchTeacher, edtSearchDate;
    Button btnSearch;
    RecyclerView recyclerViewSearchResults;
    DatabaseHelper dbHelper;
    ClassAdapter adapter;
    ArrayList<ClassModel> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearchTeacher = findViewById(R.id.edtSearchTeacher);
        edtSearchDate = findViewById(R.id.edtSearchDate);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);

        dbHelper = new DatabaseHelper(this);
        searchResults = new ArrayList<>();
        adapter = new ClassAdapter(this, searchResults);
        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSearchResults.setAdapter(adapter);

        edtSearchDate.setOnClickListener(v -> showDatePickerDialog());

        btnSearch.setOnClickListener(v -> performSearch());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    edtSearchDate.setText(date);
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    private void performSearch() {
        String teacherName = edtSearchTeacher.getText().toString().trim();
        String date = edtSearchDate.getText().toString().trim();

        searchResults.clear();

        if (!teacherName.isEmpty() && !date.isEmpty()) {
            // Tìm kiếm theo cả teacher và date
            searchResults.addAll(dbHelper.getClassesByTeacherAndDate(teacherName, date));
        } else if (!teacherName.isEmpty()) {
            // Tìm kiếm theo teacher
            searchResults.addAll(dbHelper.getClassesByTeacher(teacherName));
        } else if (!date.isEmpty()) {
            // Tìm kiếm theo date
            searchResults.addAll(dbHelper.getClassesByDate(date));
        } else {
            Toast.makeText(this, "Enter Teacher Name or Date to search", Toast.LENGTH_SHORT).show();
            return;
        }

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

}

