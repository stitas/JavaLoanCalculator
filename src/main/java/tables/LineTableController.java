package tables;

import com.loancalculator.loancalculator.DataExport;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;

public class LineTableController extends TableController{
    @FXML
    private TableView<LoanRow> lineTable;

    @Override
    @FXML
    protected void onExportBtnPressed() {
        try {
            DataExport.exportToCSV(this.lineTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initTable() {
        int period = year * 12 + month;
        double credit = sum / period;
        double interestFee;
        double totalInterestPaid = 0;

        /** Reikšmės sudedamos į grafiką **/
        for(int i = 1; i <= period + (maxDetermentMonth - minDetermentMonth + 1); i++){
            if(i >= minDetermentMonth && i <= maxDetermentMonth){
                interestFee = (sum * determentInterest) / 12;
                data.add(i - 1, new LoanRow(i, sum, interestFee, interestFee, 0));
            }
            else {
                interestFee = (sum * interest) / 12;
                data.add(i - 1, new LoanRow(i, sum, credit + interestFee, interestFee, credit));
                sum -= credit;
            }

            totalInterestPaid += interestFee;
        }

        filteredData = new FilteredList<>(data);
        lineTable.setItems(filteredData);

        /** Apvalinti sumokėtų palūkanų skaičių iki 2 skaičių po kablelio**/
        totalInterestPaid = Math.round(totalInterestPaid * Math.pow(10, 2)) / Math.pow(10, 2);

        /** Rodyti sumokėtų palūkanų skaičių **/
        totalInterest.setText(String.format("%.2f", totalInterestPaid) + "€");

        /** Listeneriai filtruojant pagal intervala **/
        super.minMonthField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter(period));
        super.maxMonthField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter(period));
    }
}
