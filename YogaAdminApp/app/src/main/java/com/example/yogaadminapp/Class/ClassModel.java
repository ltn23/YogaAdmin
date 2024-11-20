package com.example.yogaadminapp.Class;

import java.io.Serializable;

public class ClassModel implements Serializable {
    private int id;
    private String teacher;
    private String date;
    private String comments;
    private String name;
    private int courseId; // Liên kết với Course


    public ClassModel(int id, String name, String teacher, String date, String comments, int courseId) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
