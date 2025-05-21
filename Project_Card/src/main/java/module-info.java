module cr.ac.una.project_card {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires java.logging;
    requires MaterialFX;
    requires java.sql;
    requires jakarta.persistence;

    opens cr.ac.una.project_card.controller to javafx.fxml;
    opens cr.ac.una.project_card to javafx.graphics;
    exports cr.ac.una.project_card;
}
