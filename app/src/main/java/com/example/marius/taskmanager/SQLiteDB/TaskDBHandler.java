package com.example.marius.taskmanager.SQLiteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDBHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "taskId";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_IS_ADMIN = "isAdmin";
    public static final String COLUMN_TASK_TEXT = "text";
    public static final String COLUMN_TASK_DATE = "date";
    public static final String COLUMN_IS_ACTIVE = "isActive";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_IS_ADMIN + " INTEGER, " +
                    COLUMN_TASK_TEXT + " TEXT, " +
                    COLUMN_TASK_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    COLUMN_IS_ACTIVE + " INTEGER " +
                    ")";

    public TaskDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL(TABLE_CREATE);
    }
}