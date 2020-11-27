package com.ariefzuhri.sqliteapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 1;
    private static final  String TABLE_STUDENTS = "students";

    // Nama-nama kolom
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";

    private static final String CREATE_TABLE_STUDENTS =
            "CREATE TABLE " + TABLE_STUDENTS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT,"
            + KEY_ADDRESS + " TEXT)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    // Membuat tabel baru
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STUDENTS + "'");
    }

    public long addStudent(String name, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_ADDRESS, address);

        long insert = db.insert(TABLE_STUDENTS, null, values);
        return insert;
    }

    public ArrayList<Map<String, String>> getAllStudents(){
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();

        int id;
        String name, address;

        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(
                selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));

                Map<String, String> itemMap = new HashMap<>();
                itemMap.put(KEY_ID, String.valueOf(id));
                itemMap.put(KEY_NAME, name);
                itemMap.put(KEY_ADDRESS, address);

                arrayList.add(itemMap);
            } while (cursor.moveToNext());
        }

        return arrayList;
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String databaseQuery = "DELETE FROM " + TABLE_STUDENTS
                + " WHERE " + KEY_ID + "='" + id + "'";
        db.execSQL(databaseQuery);
        db.close();
    }
}
