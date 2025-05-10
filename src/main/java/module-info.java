module com.company.itinfra.practika4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens com.company.itinfra.practika4 to javafx.fxml;
    opens com.company.itinfra.practika4.Models to javafx.base;
    exports com.company.itinfra.practika4;
    exports com.company.itinfra.practika4.Controllers;
    opens com.company.itinfra.practika4.Controllers to javafx.fxml;
}