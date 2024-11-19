package com.example.yogaadminapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yogaadminapp.Course.YogaCourse;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yoga.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CLASSES = "courses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CLASSES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DAY + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_CAPACITY + " INTEGER, " +
                COLUMN_DURATION + " INTEGER, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        onCreate(db);
    }

    public void deleteAllCourses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CLASSES);
        db.close();
    }

    public void updateCourse(YogaCourse yogaCourse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
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
