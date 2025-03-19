package graphs;

import javafx.scene.chart.XYChart;

public abstract class GraphController {
    protected double sum, interest;
    protected int year, month;
    protected int minDetermentMonth, maxDetermentMonth;
    protected XYChart.Series series = new XYChart.Series();

    /** Metodas skirtas inicializuoti grafiko duomenis **/
    public void initData(double sum, double interest, int year, int month, int minDetermentMonth, int maxDetermentMonth) {
        this.sum = sum;
        this.interest = interest / 100;
        this.year = year;
        this.month = month;
        this.minDetermentMonth = minDetermentMonth;
        this.maxDetermentMonth = maxDetermentMonth;

        initGraph();
    }

    protected abstract void initGraph();
}
