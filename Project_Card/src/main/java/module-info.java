module cr.ac.una.project_card {
    requires javafx.controls;
    requires javafx.fxml;

    opens cr.ac.una.project_card to javafx.fxml;
    exports cr.ac.una.project_card;
}
