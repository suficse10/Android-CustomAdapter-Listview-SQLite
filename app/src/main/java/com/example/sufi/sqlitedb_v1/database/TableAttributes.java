package com.example.sufi.sqlitedb_v1.database;

/**
 * Created by SuFi on 13-Oct-17.
 */

public class TableAttributes {

    public static final String TABLE_NAME = "student_table";
    public static final String STUDENT_ID = "id";
    public static final String STUDENT_USERNAME = "name";
    public static final String STUDENT_PASSWORD = "password";
    public static final String STUDENT_CGPA = "cgpa";
    public static final String STUDENT_PHONENO = "phoneno";

    public String tableCreation() {

        String query = "CREATE TABLE " + TABLE_NAME + " (" + STUDENT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                STUDENT_USERNAME + " TEXT , " + STUDENT_PASSWORD + " TEXT , " + STUDENT_CGPA + " TEXT , " +
                STUDENT_PHONENO + " TEXT );";
        return query;

    }



}
