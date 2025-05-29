module cr.ac.una.project_card {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires java.logging;
    requires MaterialFX;
    requires java.sql;
    requires jakarta.persistence;
    requires com.oracle.database.jdbc;
    requires java.instrument;

    
    opens cr.ac.una.project_card.controller to javafx.fxml;
    opens cr.ac.una.project_card to javafx.graphics;
    opens cr.ac.una.project_card.model;
    opens cr.ac.una.project_card.util to jakarta.persistence;
    exports cr.ac.una.project_card;
    exports cr.ac.una.project_card.model ;
    exports cr.ac.una.project_card.util ;
    exports cr.ac.una.project_card.service ;
    exports cr.ac.una.project_card.controller;
}
