package tables;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public abstract class TableController {
    @FXML
    private TableColumn<LoanRow, Integer> monthCol;

    @FXML
    private TableColumn<LoanRow, String> loanLeftCol, monthlyPaymentCol, interestCol, creditCol;

    @FXML
    protected TextField minMonthField, maxMonthField;

    @FXML
    protected Button exportBtn;

    @FXML
    protected Text totalInterest;

    protected double sum, interest;
    protected int year, month;
    protected double determentInterest;
    protected int minDetermentMonth, maxDetermentMonth;
    protected ObservableList<LoanRow> data = FXCollections.observableArrayList();
    protected FilteredList<LoanRow> filteredData;

    /** Metodas skirtas inicializuoti grafiko duomenis **/
    public void initData(double sum, double interest, int year, int month, int minDetermentMonth, int maxDetermentMonth, double determentInterest) {
        this.sum = sum;
        this.interest = interest / 100;
        this.year = year;
        this.month = month;
        this.minDetermentMonth = minDetermentMonth;
        this.maxDetermentMonth = maxDetermentMonth;
        this.determentInterest = determentInterest / 100;

        monthCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMonth()).asObject());
        loanLeftCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoanLeft()));
        monthlyPaymentCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMonthlyPayment()));
        interestCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInterest()));
        creditCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCredit()));

        initTable();
    }

    /** Isfiltruoja pagal min ir max mėnesius **/
    protected void applyFilter(int period) {
        try {
            /**
             * Jei laukas tuščias priskiria min arba max reikšmę,
             * Jei laukas ne tuščias priskiria įvesta reikšme jei ji tinkama
             **/
            int minMonth = minMonthField.getText().isEmpty() || Integer.parseInt(minMonthField.getText()) < 0 ? 1 : Integer.parseInt(minMonthField.getText());
            int maxMonth = maxMonthField.getText().isEmpty() || Integer.parseInt(maxMonthField.getText()) < 0 ? period : Integer.parseInt(maxMonthField.getText());

            filteredData.setPredicate(row -> row.getMonth() >= minMonth && row.getMonth() <= maxMonth);
        } catch (NumberFormatException e) {
            /** Grįžta atgal jei įvestis netinkama **/
            filteredData.setPredicate(p -> true);
        }
    }

    /** Funckija logikai paspaudus mygtuką **/
    protected abstract void onExportBtnPressed();

    protected abstract void initTable();

}
