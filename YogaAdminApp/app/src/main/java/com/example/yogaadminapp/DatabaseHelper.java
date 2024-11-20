package com.example.yogaadminapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yogaadminapp.Class.ClassModel;
import com.example.yogaadminapp.Course.YogaCourse;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yoga.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_CLASSES = "courses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COURSE_NAME = "name";  // Course Name
    private static final String COLUMN_CLASS_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCourseTable = "CREATE TABLE " + TABLE_CLASSES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE_NAME + " TEXT, " +
                COLUMN_DAY + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_CAPACITY + " INTEGER, " +
                COLUMN_DURATION + " INTEGER, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createCourseTable);

        // Tạo bảng classes
        String createClassTable = "CREATE TABLE classes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLASS_NAME + " TEXT, " +
                "teacher TEXT, " +
                "date TEXT, " +
                "comments TEXT, " +
                "courseId INTEGER, " +
                "FOREIGN KEY(courseId) REFERENCES courses(id))";
        db.execSQL(createClassTable);
    }


    public void insertClass(ClassModel classModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLASS_NAME, classModel.getName()); // New
        values.put("teacher", classModel.getTeacher());
        values.put("date", classModel.getDate());
        values.put("comments", classModel.getComments());
        values.put("courseId", classModel.getCourseId());

        db.insert("classes", null, values);
        db.close();
    }

    public ArrayList<ClassModel> getClassesByCourse(int courseId) {
        ArrayList<ClassModel> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("classes", null, "courseId = ?",
                new String[]{String.valueOf(courseId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ClassModel classModel = new ClassModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow("teacher")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("comments")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("courseId"))
                );
                classList.add(classModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return classList;
    }


    public void updateClass(ClassModel classModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", classModel.getName());
        values.put("teacher", classModel.getTeacher());
        values.put("date", classModel.getDate());
        values.put("comments", classModel.getComments());

        db.update("classes", values, "id = ?", new String[]{String.valueOf(classModel.getId())});
        db.close();
    }

    public void deleteClass(int classId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("classes", "id = ?", new String[]{String.valueOf(classId)});
        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS courses");
        db.execSQL("DROP TABLE IF EXISTS classes");  // Xóa bảng classes nếu tồn tại
        onCreate(db); // Tạo lại tất cả các bảng
    }



    public void deleteAllCourses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CLASSES);
        db.close();
    }

    public void updateCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, yogaCourse.getName());
        values.put(COLUMN_DAY, yogaCourse.getDay());
        values.put(COLUMN_TIME, yogaCourse.getTime());
        values.put(COLUMN_CAPACITY, yogaCourse.getCapacity());
        values.put(COLUMN_DURATION, yogaCourse.getDuration());
        values.put(COLUMN_PRICE, yogaCourse.getPrice());
        values.put(COLUMN_TYPE, yogaCourse.getType());
        values.put(COLUMN_DESCRIPTION, yogaCourse.getDescription());

        db.update(TABLE_CLASSES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(yogaCourse.getId())});
        db.close();
    }



    public void deleteCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("courses", "day = ? AND time = ?", new String[]{yogaCourse.getDay(), yogaCourse.getTime()});
        db.close();
    }

    public void insertCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_COURSE_NAME, yogaCourse.getName()); // New
        values.put(COLUMN_DAY, yogaCourse.getDay());
        values.put(COLUMN_TIME, yogaCourse.getTime());
        values.put(COLUMN_CAPACITY, yogaCourse.getCapacity());
        values.put(COLUMN_DURATION, yogaCourse.getDuration());
        values.put(COLUMN_PRICE, yogaCourse.getPrice());
        values.put(COLUMN_TYPE, yogaCourse.getType());
        values.put(COLUMN_DESCRIPTION, yogaCourse.getDescription());

        db.insert(TABLE_CLASSES, null, values);
        db.close();
    }

    public ArrayList<YogaCourse> getAllCourses() {
        ArrayList<YogaCourse> courseList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CLASSES, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                YogaCourse yogaCourse = new YogaCourse(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),  // Lấy id
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAPACITY)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                );
                courseList.add(yogaCourse);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return courseList;
    }
}
