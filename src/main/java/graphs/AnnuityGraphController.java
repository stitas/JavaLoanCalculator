package graphs;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import tables.LoanRow;

public class AnnuityGraphController extends GraphController {
    /** Grafiko objektas **/
    @FXML
    protected LineChart<Number, Number> annuityGraph;

    @Override
    protected void initGraph() {
        /** Apskaičiuojama mėnesinė paskolos įmoka pagal formulę:
         *                           PasiskolintaSuma * (Palūkanos / 12)
         *   MėnesinėĮmoka = ------------------------------------------------
         *                     1 - (1 + (Palūkanos / 12)^(12*Metai + Mėnesiai))
         **/
        int period = year * 12 + month;
        double interestFee = sum * (interest / 12);
        double credit;
        double monthlyPayment = (float) (interestFee / (1 - Math.pow((1 + interest / 12), -1 * (period))));

        /** Reikšmės sudedamos į grafiką **/
        for(int i = 1; i <= period + (maxDetermentMonth - minDetermentMonth + 1); i++){
            // Jei atidėjimas tai nekeisti sumos ir dėti reikšmes į grafiką
            if(i >= minDetermentMonth && i <= maxDetermentMonth){
                series.getData().add(new XYChart.Data<>(i, sum));
            }
            // Skaičiuoti grąžintą sumą
            else {
                interestFee = sum * (interest / 12);
                credit = monthlyPayment - interestFee;

                series.getData().add(new XYChart.Data<>(i, sum));
                sum -= credit;
            }

        }

        this.annuityGraph.getData().add(series);
    }
}
