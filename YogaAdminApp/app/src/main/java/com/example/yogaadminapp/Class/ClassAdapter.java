package com.example.yogaadminapp.Class;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaadminapp.R;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ClassModel> classList;

    public ClassAdapter(Context context, ArrayList<ClassModel> classList) {
        this.context = context;
        this.classList = classList;
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

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditClassActivity.class);
            intent.putExtra("classModel", classModel);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtClassName;
        Button btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtClassName = itemView.findViewById(R.id.txtClassName);
            btnEdit = itemView.findViewById(R.id.btnEditClass);
        }
    }
}

