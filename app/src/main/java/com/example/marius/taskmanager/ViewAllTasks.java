package com.example.marius.taskmanager;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.marius.taskmanager.Model.Task;
import com.example.marius.taskmanager.SQLiteDB.TaskOperations;

import java.util.List;

public class ViewAllTasks extends ListActivity {

    private TaskOperations taskOps;
    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tasks);
        taskOps = new TaskOperations(this);
        taskOps.open();
        tasks = taskOps.getAllTasks();
        taskOps.close();
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, tasks);
        setListAdapter(adapter);
    }
}