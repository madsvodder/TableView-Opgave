module com.example.tableview {
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.sql;


    opens com.example.tableview to javafx.fxml;
    exports com.example.tableview;
}

