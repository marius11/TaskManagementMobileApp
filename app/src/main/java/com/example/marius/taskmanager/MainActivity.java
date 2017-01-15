package com.example.marius.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.marius.taskmanager.Network.NetworkStateReceiver;
import com.example.marius.taskmanager.SQLiteDB.TaskOperations;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Button addTaskButton, editTaskButton, deleteTaskButton, viewAllTasksButton, viewStatsButton;
    private static final String EXTRA_TASK_ID = "com.example.marius.taskmanager.taskId";
    private static final String EXTRA_ADD_UPDATE = "com.example.marius.taskmanager.add_update";

    private TaskOperations taskOps;

    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskOps = new TaskOperations(this);
        taskOps.open();

        setContentView(R.layout.activity_main);

        addTaskButton = (Button) findViewById(R.id.button_add_task);
        editTaskButton = (Button) findViewById(R.id.button_edit_task);
        deleteTaskButton = (Button) findViewById(R.id.button_delete_task);
        viewAllTasksButton = (Button) findViewById(R.id.button_view_task);
        viewStatsButton = (Button) findViewById(R.id.button_view_stats);

        networkStateReceiver = new NetworkStateReceiver(new NetworkStateReceiver.NetworkListener() {
            @Override
            public void onNetworkAvailable() {
                /*addTaskButton.setEnabled(true);
                editTaskButton.setEnabled(true);
                deleteTaskButton.setEnabled(true);
                viewAllTasksButton.setEnabled(true);*/
            }

            @Override
            public void onNetworkUnavailable() {
                /*addTaskButton.setEnabled(false);
                editTaskButton.setEnabled(false);
                deleteTaskButton.setEnabled(false);
                viewAllTasksButton.setEnabled(false);*/
            }
        });

        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddUpdateTask.class);
                i.putExtra(EXTRA_ADD_UPDATE, "Add");
                startActivity(i);
            }
        });

        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTaskIdAndUpdateTask();
            }
        });

        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTaskIdAndRemoveTask();
            }
        });

        viewAllTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewAllTasks.class);
                startActivity(i);
            }
        });

        viewStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pieChartIntent = new Intent(v.getContext(), PieChartView.class);
                startActivity(pieChartIntent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActiveTab(), "Active");
        adapter.addFragment(new InactiveTab(), "Inactive");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.menu_item_settings || super.onOptionsItemSelected(item);
    }

    public void getTaskIdAndUpdateTask() {
        LayoutInflater li = LayoutInflater.from(this);
        View getTaskIdView = li.inflate(R.layout.dialog_get_task_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(getTaskIdView);

        final EditText userInput = (EditText) getTaskIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(MainActivity.this, AddUpdateTask.class);
                        i.putExtra(EXTRA_ADD_UPDATE, "Update");
                        i.putExtra(EXTRA_TASK_ID, Long.parseLong(userInput.getText().toString()));
                        startActivity(i);
                    }
                }).create().show();
    }

    public void getTaskIdAndRemoveTask() {
        LayoutInflater li = LayoutInflater.from(this);
        View getTaskIdView = li.inflate(R.layout.dialog_get_task_id, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(getTaskIdView);

        final EditText userInput = (EditText) getTaskIdView.findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        taskOps = new TaskOperations(MainActivity.this);
                        taskOps.open();
                        taskOps.removeTask(taskOps.getTask(Long.parseLong(userInput.getText().toString())));
                        taskOps.close();
                        Toast t = Toast.makeText(MainActivity.this, "Task successfully removed!", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }).create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        taskOps.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }
}
