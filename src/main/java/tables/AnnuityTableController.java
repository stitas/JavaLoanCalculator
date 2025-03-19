package tables;

import com.loancalculator.loancalculator.DataExport;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;

public class AnnuityTableController extends TableController{
    @FXML
    private TableView<LoanRow> annuityTable;

    @Override
    @FXML
    protected void onExportBtnPressed() {
        try {
            DataExport.exportToCSV(this.annuityTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initTable() {
        /** Apskaičiuojama mėnesinė paskolos įmoka pagal formulę:
         *                           PasiskolintaSuma * (Palūkanos / 12)
         *   MėnesinėĮmoka = ------------------------------------------------
         *                     1 - (1 + (Palūkanos / 12)^(12*Metai + Mėnesiai))
         **/
        int period = year * 12 + month;
        double interestFee = sum * (interest / 12);
        double monthlyPayment = (interestFee / (1 - Math.pow(1 + interest / 12, -1 * period)));
        double credit;
        double totalInterestPaid = 0;

        /** Reikšmės sudedamos į lentelę **/
        for(int i = 1; i <= period + (maxDetermentMonth - minDetermentMonth + 1); i++){
            if(i >= minDetermentMonth && i <= maxDetermentMonth){
                interestFee = sum * (determentInterest / 12);
                data.add(i - 1, new LoanRow(i, sum, interestFee, interestFee, 0));
            }
            else {
                interestFee = sum * (interest / 12);
                credit = monthlyPayment - interestFee;
                data.add(i - 1, new LoanRow(i, sum, monthlyPayment, interestFee, credit));

                sum -= credit;
            }
            totalInterestPaid += interestFee;
        }

        filteredData = new FilteredList<>(data);
        annuityTable.setItems(filteredData);

        /** Apvalinti sumokėtų palūkanų skaičių iki 2 skaičių po kablelio**/
        totalInterestPaid = Math.round(totalInterestPaid * Math.pow(10, 2)) / Math.pow(10, 2);

        /** Rodyti sumokėtų palūkanų skaičių **/
        totalInterest.setText(String.format("%.2f", totalInterestPaid) + "€");

        /** Listeneriai filtruojant pagal intervala **/
        super.minMonthField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter(period));
        super.maxMonthField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter(period));
    }
}
