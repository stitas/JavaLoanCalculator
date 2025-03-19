module com.loancalculator.loancalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.loancalculator.loancalculator to javafx.fxml;
    exports com.loancalculator.loancalculator;
    exports graphs;
    opens graphs to javafx.fxml;
    exports tables;
    opens tables to javafx.fxml;
}