package com.example.yogaadminapp.Class;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaadminapp.DatabaseHelper;
import com.example.yogaadminapp.R;

import java.util.ArrayList;
import androidx.appcompat.app.AlertDialog;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ClassModel> classList;
    private DatabaseHelper dbHelper;

    public ClassAdapter(Context context, ArrayList<ClassModel> classList) {
        this.context = context;
        this.classList = classList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassModel classModel = classList.get(position);
        holder.txtClassName.setText(classModel.getTeacher() + " - " + classModel.getDate());

// Xử lý nút Edit
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditClassActivity.class);
            intent.putExtra("classModel", classModel);
            context.startActivity(intent);
        });
        // Xử lý nút Delete
        holder.btnDeleteClass.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Class")
                    .setMessage("Are you sure you want to delete this class?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteClass(classModel.getId());
                        classList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Class deleted successfully", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

    }


    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtClassName;
        Button btnEdit, btnDeleteClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtClassName = itemView.findViewById(R.id.txtClassName);
            btnEdit = itemView.findViewById(R.id.btnEditClass);
            btnDeleteClass = itemView.findViewById(R.id.btnDeleteClass);
        }
    }
}

