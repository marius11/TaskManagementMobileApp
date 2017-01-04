package com.example.marius.taskmanager;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marius.taskmanager.SQLiteDB.TaskOperations;

public class ActiveTab extends Fragment {

    private TaskOperations taskOps;
    public ActiveTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskOps = new TaskOperations(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.active_tab, container, false);
    }
}