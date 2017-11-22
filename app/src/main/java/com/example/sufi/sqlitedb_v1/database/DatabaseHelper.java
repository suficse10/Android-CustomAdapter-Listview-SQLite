package com.example.sufi.sqlitedb_v1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sufi.sqlitedb_v1.model.Student;

import java.util.ArrayList;

/**
 * Created by SuFi on 13-Oct-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "student_db";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        TableAttributes obj = new TableAttributes();
        String query = obj.tableCreation();

        try{
            db.execSQL(query);
            Log.i("Table Create", "Done!");
        }
        catch(SQLException e){
            Log.e("SQL Error", e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertStudent(Student std) {

        ContentValues values = new ContentValues();
        values.put(TableAttributes.STUDENT_USERNAME, std.getUsername());
        values.put(TableAttributes.STUDENT_PASSWORD, std.getPassword());
        values.put(TableAttributes.STUDENT_CGPA, std.getCgpa());
        values.put(TableAttributes.STUDENT_PHONENO, std.getPhone());

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.insert(TableAttributes.TABLE_NAME, null, values);
            Log.i("Data Insertion", "Done!");
        }
        catch (SQLException e){
            Log.e("Insertion Error", e.toString());
        }
        finally {
            db.close();
        }
    }

    public ArrayList<Student> getAllStudentData(){

        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TableAttributes.TABLE_NAME + " ORDER BY " + TableAttributes.STUDENT_ID + " DESC ";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        try {
            if (cursor.getCount() > 0) {

                while (!cursor.isAfterLast()) {

                    Student std = new Student();
                    std.setId(cursor.getInt(cursor.getColumnIndex(TableAttributes.STUDENT_ID)));
                    std.setUsername(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_USERNAME)));
                    std.setPassword(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_PASSWORD)));
                    std.setCgpa(cursor.getFloat(cursor.getColumnIndex(TableAttributes.STUDENT_CGPA)));
                    std.setPhone(cursor.getString(cursor.getColumnIndex(TableAttributes.STUDENT_PHONENO)));

                    studentList.add(std);
                    cursor.moveToNext();
                }
            }
            Log.i("Data Fetch", "Successful!");
        }
        catch (SQLException e){
            Log.e("Data Fetch Error", e.toString());
        }
        finally {
            db.close();
        }
        return studentList;
    }

    public boolean deleteStudent(int id) {

        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TableAttributes.TABLE_NAME, TableAttributes.STUDENT_ID + "=" + id, null);
            Log.i("Delete", "Done!");
            flag = true;
        }
        catch (SQLException e) {
            Log.e("Delete Error", e.toString());
        }
        finally {
            db.close();
        }
        return flag;
    }
}
