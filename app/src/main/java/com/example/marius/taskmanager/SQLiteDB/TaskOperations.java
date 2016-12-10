package com.example.marius.taskmanager.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Date;

import com.example.marius.taskmanager.Model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskOperations {
    public static final String LOGTAG = "TASK_MANAGEMENT_SYS";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            TaskDBHandler.COLUMN_ID,
            TaskDBHandler.COLUMN_USERNAME,
            TaskDBHandler.COLUMN_IS_ADMIN,
            TaskDBHandler.COLUMN_TASK_TEXT,
            TaskDBHandler.COLUMN_TASK_DATE,
            TaskDBHandler.COLUMN_IS_ACTIVE
    };

    public TaskOperations(Context context) {
        dbHandler = new TaskDBHandler(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database opened");
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database closed");
        dbHandler.close();
    }

    public Task addTask(Task task) {
        ContentValues values = new ContentValues();

        values.put(TaskDBHandler.COLUMN_USERNAME, task.getUserName());
        values.put(TaskDBHandler.COLUMN_IS_ADMIN, task.getIsAdmin());
        values.put(TaskDBHandler.COLUMN_TASK_TEXT, task.getTaskText());
        values.put(TaskDBHandler.COLUMN_TASK_DATE, task.getTaskDate());
        values.put(TaskDBHandler.COLUMN_IS_ACTIVE, task.getIsActive());
        long insertid = database.insert(TaskDBHandler.TABLE_TASKS, null, values);
        task.setTaskId(insertid);
        return task;
    }

    public Task getTask(long id) {
        Cursor cursor = database.query(TaskDBHandler.TABLE_TASKS, allColumns, TaskDBHandler.COLUMN_ID + " = ? ",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Task t = new Task(Long.parseLong(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)),
                cursor.getString(3), cursor.getString(4), Integer.parseInt((cursor.getString(5))));
        return t;
    }

    public List<Task> getAllTasks() {
        Cursor cursor = database.query(TaskDBHandler.TABLE_TASKS, allColumns, null, null, null, null, null);

        List<Task> tasks = new ArrayList<>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Task task = new Task();
                task.setTaskId(cursor.getLong(cursor.getColumnIndex(TaskDBHandler.COLUMN_ID)));
                task.setUserName(cursor.getString(cursor.getColumnIndex(TaskDBHandler.COLUMN_USERNAME)));
                task.setIsAdmin(cursor.getInt(cursor.getColumnIndex(TaskDBHandler.COLUMN_IS_ADMIN)));
                task.setTaskText(cursor.getString(cursor.getColumnIndex(TaskDBHandler.COLUMN_TASK_TEXT)));
                task.setTaskDate(cursor.getString(cursor.getColumnIndex(TaskDBHandler.COLUMN_TASK_DATE)));
                task.setIsActive(cursor.getInt(cursor.getColumnIndex(TaskDBHandler.COLUMN_IS_ACTIVE)));
                tasks.add(task);
            }
        }

        return tasks;
    }

    public int updateTask(Task task) {
        ContentValues values = new ContentValues();

        values.put(TaskDBHandler.COLUMN_USERNAME, task.getUserName());
        values.put(TaskDBHandler.COLUMN_IS_ADMIN, task.getIsAdmin());
        values.put(TaskDBHandler.COLUMN_TASK_TEXT, task.getTaskText());
        values.put(TaskDBHandler.COLUMN_TASK_DATE, task.getTaskDate());
        values.put(TaskDBHandler.COLUMN_IS_ACTIVE, task.getIsActive());

        return database.update(TaskDBHandler.TABLE_TASKS, values, TaskDBHandler.COLUMN_ID + " = ? ",
                new String[] { String.valueOf(task.getTaskId()) });
    }

    public int removeTask(Task task) {
        return database.delete(TaskDBHandler.TABLE_TASKS, TaskDBHandler.COLUMN_ID + " = ? ",
                new String[] { String.valueOf(task.getTaskId()) });
    }
}