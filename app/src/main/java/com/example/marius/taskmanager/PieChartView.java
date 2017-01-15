package com.example.marius.taskmanager;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.example.marius.taskmanager.SQLiteDB.TaskOperations;

public class PieChartView extends Activity {

    int nrActiveTasks, nrInactiveTasks;
    int[] pieChartValues;

    private TaskOperations taskOps;

    private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE };
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart);

        taskOps = new TaskOperations(this);
        taskOps.open();
        nrActiveTasks = taskOps.getActiveTasks();
        nrInactiveTasks = taskOps.getInactiveTasks();
        taskOps.close();

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(30);
        mRenderer.setLegendTextSize(30);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setStartAngle(90);

        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);
            layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.FILL_PARENT));
        }
        else {
            mChartView.repaint();
        }
        fillPieChart();
    }

    public void fillPieChart() {

        String str = "";
        pieChartValues = new int[] { nrActiveTasks, nrInactiveTasks };
        for(int i = 0; i < pieChartValues.length; i++) {
            str = i == 0 ? "Active" : "Inactive";
            mSeries.add(str /*+ (mSeries.getItemCount() + 1)*/, pieChartValues[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);

            if (mChartView != null)
                mChartView.repaint();
        }
    }
}