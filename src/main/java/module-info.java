module com.example.user {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.dlsc.formsfx;

    opens com.example.user to javafx.fxml;
    opens com.example.user.models to javafx.base;
    exports com.example.user;
    exports com.example.user.controllers;
    opens com.example.user.controllers to javafx.fxml;
}