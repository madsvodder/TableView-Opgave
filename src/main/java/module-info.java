module com.example.tableview {
    requires javafx.fxml;
    requires java.sql;
    requires atlantafx.base;


    opens com.example.tableview to javafx.fxml;
    exports com.example.tableview;
}