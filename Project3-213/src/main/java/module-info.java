module clinic.project3213 {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;


    opens clinic to javafx.fxml;
    exports clinic;
}