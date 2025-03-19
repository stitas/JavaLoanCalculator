package com.loancalculator.loancalculator;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.scene.control.TableView;
import tables.LoanRow;

import java.io.*;


public class DataExport {
    public static void exportToCSV(TableView<LoanRow> tableView) throws IOException {
        Writer writer = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                writer = new BufferedWriter(new FileWriter(file));
                ObservableList<TableColumn<LoanRow, ?>> columns = tableView.getColumns();

                StringBuilder sb = new StringBuilder();

                for(int i = 0; i < columns.size(); i++){
                    sb.append(columns.get(i).getText());

                    if(i + 1 < columns.size()){
                        sb.append(",");
                    }
                    else{
                        sb.append("\n");
                    }
                }

                writer.write(sb.toString());

                ObservableList<LoanRow> items = tableView.getItems();

                for(LoanRow item : items){
                    String text = item.getMonth() + "," + item.getLoanLeft() + "," + item.getMonthlyPayment() + "," + item.getInterest() + "," + item.getCredit() + "\n";
                    writer.write(text);
                }
            }
            catch (Exception e){
                return;
            }
            finally {
                if(writer != null){
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}