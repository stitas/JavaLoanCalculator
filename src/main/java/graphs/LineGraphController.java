package graphs;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class LineGraphController extends GraphController {
    /** Grafiko objektas **/
    @FXML
    protected LineChart<Number, Number> lineGraph;

    @Override
    protected void initGraph() {
        int period = year * 12 + month;
        double credit = sum / period;

        /** Reikšmės sudedamos į grafiką **/
        for(int i = 1; i <= period + (maxDetermentMonth - minDetermentMonth + 1); i++){
            if(i >= minDetermentMonth && i <= maxDetermentMonth){
                series.getData().add(new XYChart.Data<>(i, sum));
            }
            else {
                series.getData().add(new XYChart.Data<>(i, sum));
                sum -= credit;
            }
        }

        this.lineGraph.getData().add(series);
    }
}
