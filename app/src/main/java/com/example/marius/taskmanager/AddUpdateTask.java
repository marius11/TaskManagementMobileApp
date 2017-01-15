package com.example.marius.taskmanager;

import android.annotation.TargetApi;
import android.content.Intent;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Date;

import com.example.marius.taskmanager.Model.Task;
import com.example.marius.taskmanager.SQLiteDB.TaskOperations;

public class AddUpdateTask extends AppCompatActivity {
    private static final String EXTRA_TASK_ID = "com.example.marius.taskmanager.taskId";
    private static final String EXTRA_ADD_UPDATE = "com.example.marius.taskmanager.add_update";
    private static final String DIALOG_DATE = "DialogDate";
    private ImageView calendarImage;
    private RadioGroup radioGroup;
    private RadioButton activeRadioButton, inactiveRadioButton;
    private EditText taskEditUsername;
    private EditText taskEditText;
    private EditText taskEditDate;
    private Button addUpdateButton;
    private Task newTask;
    private Task oldTask;
    private String mode;
    private long taskId;
    private TaskOperations taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_task);
        newTask = new Task();
        oldTask = new Task();

        taskEditUsername = (EditText) findViewById(R.id.edit_text_username);
        taskEditText = (EditText) findViewById(R.id.edit_text_note);
        taskEditDate = (EditText) findViewById(R.id.edit_text_date);
        radioGroup = (RadioGroup) findViewById(R.id.radio_is_active);
        activeRadioButton = (RadioButton) findViewById(R.id.radio_active);
        inactiveRadioButton = (RadioButton) findViewById(R.id.radio_inactive);
        calendarImage = (ImageView) findViewById(R.id.image_view_date);
        addUpdateButton = (Button) findViewById(R.id.button_add_update_task);
        taskData = new TaskOperations(this);
        taskData.open();

        mode = getIntent().getStringExtra(EXTRA_ADD_UPDATE);
        if (mode.equals("Update")) {
            addUpdateButton.setText("Update task");
            taskId = getIntent().getLongExtra(EXTRA_TASK_ID, 0);

            initializeTask(taskId);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_active) {
                    newTask.setIsActive(1);
                    if (mode.equals("Update")) {
                        oldTask.setIsActive(1);
                    }
                } else if (checkedId == R.id.radio_inactive) {
                    newTask.setIsActive(0);
                    if (mode.equals("Update")) {
                        oldTask.setIsActive(0);
                    }
                }
            }
        });

        calendarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DialogFragment dialog = new DialogFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });

        addUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("Add")) {
                    newTask.setUserName(taskEditUsername.getText().toString());
                    newTask.setIsActive(1);
                    newTask.setTaskText(taskEditText.getText().toString());
                    newTask.setTaskDate(taskEditDate.getText().toString());
                    newTask.setIsAdmin(0);
                    taskData.addTask(newTask);
                    Toast t = Toast.makeText(AddUpdateTask.this, "Task has been successfully added!", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateTask.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    oldTask.setUserName(taskEditUsername.getText().toString());
                    oldTask.setTaskText(taskEditText.getText().toString());
                    oldTask.setTaskDate(taskEditDate.getText().toString());
                    // user to set the on active or not
                    taskData.updateTask(oldTask);
                    Toast t = Toast.makeText(AddUpdateTask.this, "Task has been successfully updated!", Toast.LENGTH_SHORT);
                    t.show();
                    Intent i = new Intent(AddUpdateTask.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void initializeTask(long taskId) {
        oldTask = taskData.getTask(taskId);
        taskEditText.setText(oldTask.getTaskText());
        taskEditDate.setText(oldTask.getTaskDate());
        radioGroup.check(oldTask.getIsActive() == 1 ? R.id.radio_active : R.id.radio_inactive);
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String tDate = sdf.format(date);
        return tDate;
    }
}
