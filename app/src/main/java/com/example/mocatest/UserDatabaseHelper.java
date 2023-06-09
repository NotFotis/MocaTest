package com.example.mocatest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_EDUCATION = "education";
    public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    public static final String COLUMN_DATE_REGISTERED = "date_registered";

    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " +
            TABLE_USERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FULL_NAME + " TEXT NOT NULL, " +
    COLUMN_SEX + " TEXT NOT NULL, " +
    COLUMN_EDUCATION + " TEXT NOT NULL, " +
    COLUMN_DATE_OF_BIRTH + " INTEGER NOT NULL, " +
    COLUMN_DATE_REGISTERED + " INTEGER NOT NULL" +
            ");";
    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
