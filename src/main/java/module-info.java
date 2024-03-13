module pizza.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens pizza.app to javafx.fxml;
    exports pizza.app;
    opens pizza.app.controller to javafx.fxml;
    opens pizza.app.models to javafx.base;
}