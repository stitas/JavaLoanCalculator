package com.loancalculator.loancalculator;

import graphs.GraphController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tables.TableController;

import java.io.IOException;

public class IndexController {
    /** Teksto įvedimo laukai **/
    @FXML
    private TextField sumInput, yearInput, monthInput, interestInput, minMonthDefermentInput, maxMonthDefermentInput, interestDefermentInput;

    /** Radio mygtukai **/
    @FXML
    private RadioButton lineRadio, annuityRadio;

    @FXML
    /** Errorų pranešimo tekstas **/
    private Text errorText;

    /**
    * Grafiko tipui pasirinkti skirtų radio mygtuku grupė
    **/
    private final ToggleGroup radioGroup = new ToggleGroup();

    /**
     * Metodas skirtas inicializuoti FXML objektus, ko negalima padaryti konstruktoriuje,
     * nes jis yra užkraunamas pirmas, o tik po jo FXML objektai, todėl konstruktoriuje
     * FXML objektai yra dar nepasiekiami.
     **/
    @FXML
    public void initialize() {
        lineRadio.setToggleGroup(radioGroup);
        annuityRadio.setToggleGroup(radioGroup);
        lineRadio.setSelected(true);
    }

    /** Metodas skirtas įgyvendinti logiką paspaudus 'skaičiuoti' mygtuką **/
    @FXML
    protected void onCalculateBtnClick() {
        // Duomenys is laukų
        double sum = getSumInput();
        int year = getYearInput();
        int month = getMonthInput();
        double interest = getInterestInput();
        int minDetermentMonth = getMinMonthDetermentInput(year * 12 + month);
        int maxDetermentMonth = getMaxMonthDetermentInput();
        double determentInterest = getDetermentInterestInput();

        RadioButton selectedRadio = (RadioButton) radioGroup.getSelectedToggle();

        if(sum == -1 || year == -1 || month == -1 || interest == -1 || minDetermentMonth == -1 || maxDetermentMonth == -1 || determentInterest == -1 || maxDetermentMonth < minDetermentMonth){
            errorText.setVisible(true);
            return;
        }

        try {
            FXMLLoader graphLoader, tableLoader = null;
            Stage graphStage = new Stage();
            Stage tableStage = new Stage();

            if(selectedRadio == annuityRadio){
                graphLoader = new FXMLLoader(getClass().getResource("annuityGraph.fxml"));
                tableLoader = new FXMLLoader(getClass().getResource("annuityPaymentTable.fxml"));
                graphStage.setTitle("Anuiteto Grafikas");
                tableStage.setTitle("Anuiteto paskolos lentelė");
            }
            else {
                graphLoader = new FXMLLoader(getClass().getResource("lineGraph.fxml"));
                tableLoader = new FXMLLoader(getClass().getResource("linePaymentTable.fxml"));
                graphStage.setTitle("Linijinis Grafikas");
                tableStage.setTitle("Linijinės paskolos lentelė");
            }



            Parent graphRoot = graphLoader.load();
            GraphController graphController = graphLoader.getController();

            Parent tableRoot = tableLoader.load();
            TableController tableController = tableLoader.getController();

            graphController.initData(sum, interest, year, month, minDetermentMonth, maxDetermentMonth);
            tableController.initData(sum, interest, year, month, minDetermentMonth, maxDetermentMonth, determentInterest);

            graphStage.setResizable(false);
            graphStage.setScene(new Scene(graphRoot));
            graphStage.show();

            tableStage.setResizable(false);
            tableStage.setScene(new Scene(tableRoot));
            tableStage.show();

            errorText.setVisible(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** Metodas gauna ivestos sumos skaičių. Gražina -1 jei įvestas ne skaičius arba skaičius mažesnis už 0. **/
    private double getSumInput() {
        double sum;

        try {
            sum = Double.parseDouble(sumInput.getText());
        } catch (Exception e){
            return -1;
        }

        if(sum < 0){
            return -1;
        }

        return sum;
    }

    /** Metodas gauna ivestų metų skaičių. Gražina -1 jei įvestas ne skaičius arba skaičius mažesnis už 0. **/
    private int getYearInput() {
        int year;

        try {
            if(yearInput.getText().isEmpty()){
                year = 0;
            }
            else {
                year = Integer.parseInt(yearInput.getText());
            }

        } catch (Exception e){
            return -1;
        }

        if(year < 0){
            return -1;
        }

        return year;
    }

    /** Metodas gauna ivestų mėnesių skaičių. Gražina -1 jei įvestas ne skaičius arba skaičius mažesnis už 0. **/
    private int getMonthInput() {
        int month;

        try {
            if(monthInput.getText().isEmpty()){
                month = 0;
            }
            else {
                month = Integer.parseInt(monthInput.getText());
            }
        } catch (Exception e){
            return -1;
        }

        if(month < 0){
            return -1;
        }

        return month;
    }

    /** Metodas gauna ivestų palūkanų skaičių. Gražina -1 jei įvestas ne skaičius arba skaičius mažesnis už 0. **/
    private double getInterestInput() {
        double interest;

        try {
            interest = Double.parseDouble(interestInput.getText());
        } catch (Exception e){
            return -1;
        }

        if(interest < 0){
            return -1;
        }

        return interest;
    }

    /**
     * Metodas gauna atidėjimo menesių pradžią.
     * Gražina -1 jei įvestas ne skaičius arba skaičius mažesnis už 0 arba atidejimas prasideda veliau nei paskola baigiasi.
     **/
    private int getMinMonthDetermentInput(int loanPeriod) {
        int minMonthDeterment;

        try {
            // Priskiria 0 jei laukas tuščias
            if(minMonthDefermentInput.getText().isEmpty()){
                minMonthDeterment = 1;
            }
            else {
                minMonthDeterment = Integer.parseInt(minMonthDefermentInput.getText());
            }
        } catch (Exception e){
            return -1;
        }

        if(minMonthDeterment < 0){
            return -1;
        }
        // Patikrina ar atidejimas prasideda anksciau nei paskola baigiasi
        else if (minMonthDeterment >= loanPeriod){
            return -1;
        }

        return minMonthDeterment;
    }

    /** Metodas gauna atidėjimo menesių pabaiga. Gražina -1 jei įvestas ne skaičius arba skaičius mažesnis už 0. **/
    private int getMaxMonthDetermentInput() {
        int maxMonthDeterment;

        try {
            // Priskiria 0 jei laukas tuščias
            if(maxMonthDefermentInput.getText().isEmpty()){
                maxMonthDeterment = 1;
            }
            else {
                maxMonthDeterment = Integer.parseInt(maxMonthDefermentInput.getText());
            }
        } catch (Exception e){
            return -1;
        }

        if(maxMonthDeterment < 0){
            return -1;
        }

        return maxMonthDeterment;
    }

    /** Metodas gauna ivestų palūkanų skaičių. Gražina -1 jei įvestas ne skaičius arba skaičius mažesnis už 0. **/
    private double getDetermentInterestInput() {
        double determentInterest;

        try {
            // Priskiria 0 jei laukas tuščias
            if(interestDefermentInput.getText().isEmpty()){
                determentInterest = 0;
            }
            else {
                determentInterest = Double.parseDouble(interestDefermentInput.getText());
            }
        } catch (Exception e){
            return -1;
        }

        if(determentInterest < 0){
            return -1;
        }

        return determentInterest;
    }
}