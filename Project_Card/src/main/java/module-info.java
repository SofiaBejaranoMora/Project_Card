module cr.ac.una.project_card {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires MaterialFX;

    opens cr.ac.una.project_card.controller to javafx.fxml;
    //opens cr.ac.una.project_card to javafx.fxml;
    exports cr.ac.una.project_card;
}
