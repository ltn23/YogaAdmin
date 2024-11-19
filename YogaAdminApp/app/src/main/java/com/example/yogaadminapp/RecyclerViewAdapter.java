package com.example.yogaadminapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.Toast;

import com.example.yogaadminapp.Course.DetailCourseActivity;
import com.example.yogaadminapp.Course.YogaCourse;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<YogaCourse> courseList;
    private DatabaseHelper dbHelper;

    public RecyclerViewAdapter(Context context, ArrayList<YogaCourse> courseList, DatabaseHelper dbHelper) {
        this.context = context;
        this.courseList = courseList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YogaCourse yogaCourse = courseList.get(position);
        holder.txtCourseName.setText(yogaCourse.getDay() + " - " + yogaCourse.getTime());

//        // Xử lý nút "More" để xem chi tiết
//        holder.btnMore.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailCourseActivity.class);
//            intent.putExtra("course", yogaCourse);  // Truyền đối tượng lớp học qua Intent
//            context.startActivity(intent);
//        });

        // Sự kiện khi nhấn vào toàn bộ item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailCourseActivity.class);
            intent.putExtra("course", yogaCourse);  // Truyền đối tượng lớp học qua Intent
            context.startActivity(intent);
        });

        // Xử lý nút "Delete"
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Course")
                    .setMessage("Are you sure you want to delete this course?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteCourse(yogaCourse);
                        courseList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Course deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }



    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCourseName;
        Button btnMore, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCourseName = itemView.findViewById(R.id.txtCourseName);
            btnMore = itemView.findViewById(R.id.btnMore);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
