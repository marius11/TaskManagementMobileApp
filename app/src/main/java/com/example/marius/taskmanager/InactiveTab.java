package com.example.marius.taskmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import com.example.marius.taskmanager.SQLiteDB.TaskOperations;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import static com.example.marius.taskmanager.SQLiteDB.TaskOperations.LOGTAG;

public class InactiveTab extends Fragment {

    private TaskOperations taskOps;
    private PieChart pieChart;

    public InactiveTab() {
        Log.i(LOGTAG, "public InactiveTab()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inactive_tab, container, false);

        //pieChart = new PieChart(getContext());
        pieChart = (PieChart) view.findViewById(R.id.chart);

        taskOps = new TaskOperations(getActivity());
        ArrayList<Entry> entries = new ArrayList<>();
        taskOps.open();
        entries.add(new Entry(taskOps.getActiveTasks(), 0));
        entries.add(new Entry(taskOps.getInactiveTasks(), 1));
        taskOps.close();

        PieDataSet dataSet = new PieDataSet(entries, "# active and inactive tasks");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Active");
        labels.add("Inactive");

        PieData data = new PieData(labels, dataSet);
        pieChart.setData(data);

        pieChart.setDescription("Active and inactive tasks");

        return view;
    }
}