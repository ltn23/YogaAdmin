package com.example.yogaadminapp.Class;

import java.io.Serializable;

public class ClassModel implements Serializable {
    private int id;
    private String name;
    private String teacher;
    private String date;
    private String comments;
    private int courseId;


    public ClassModel() {
    }
    // Constructor đầy đủ
    public ClassModel(int id, String name, String teacher, String date, String comments, int courseId) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.date = date;
        this.comments = comments;
        this.courseId = courseId;
    }

    public ClassModel(String name, String teacher, String date, String comments, int courseId) {
        this.name = name;
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
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }


    public int getCourseId() {
        return courseId;
    }
}
