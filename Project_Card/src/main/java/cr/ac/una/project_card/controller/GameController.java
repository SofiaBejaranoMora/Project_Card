package cr.ac.una.project_card.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/*** FXML Controller class * * @author ashly */
public class GameController implements Initializable {

    @FXML
    private MFXButton btnBack;
    @FXML
    private Label lblDificult;
    @FXML
    private Label lblPoints;
    @FXML
    private Label lblTimer;
    @FXML
    private Button btnSizeScreen;
    @FXML
    private ImageView mgvFull;
    @FXML
    private ImageView mgvMin;
    @FXML
    private MFXButton btnSettings;
    @FXML
    private MFXButton btnCards;
    @FXML
    private MFXButton btnClues;
    @FXML
    private MFXButton btnUndo;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSizeScreen(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSettings(ActionEvent event) {
    }

    @FXML
    private void onActionBtnCards(ActionEvent event) {
    }

    @FXML
    private void onActionBtnClues(ActionEvent event) {
    }

    @FXML
    private void onActionBtnUndo(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

}
