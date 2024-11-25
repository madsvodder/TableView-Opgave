module com.example.tableview {
    requires javafx.fxml;
    requires atlantafx.base;
    requires com.fasterxml.jackson.databind;


    opens com.example.tableview to javafx.fxml;
    exports com.example.tableview;
}

