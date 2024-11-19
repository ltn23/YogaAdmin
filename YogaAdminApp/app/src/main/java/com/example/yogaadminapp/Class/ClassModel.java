package com.example.yogaadminapp.Class;

import java.io.Serializable;

public class ClassModel implements Serializable {
    private int id;
    private String teacher;
    private String date;
    private String comments;
    private int courseId; // Liên kết với Course

    public ClassModel(int id, String teacher, String date, String comments, int courseId) {
        this.id = id;
        this.teacher = teacher;
        this.date = date;
        this.comments = comments;
        this.courseId = courseId;
    }

    public ClassModel(String teacher, String date, String comments, int courseId) {
        this.teacher = teacher;
        this.date = date;
        this.comments = comments;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }

    public int getCourseId() {
        return courseId;
    }
}
