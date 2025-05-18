package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class MenuController extends Controller implements Initializable {

    @FXML
    private MFXButton btnSettings;
    @FXML
    private MFXButton btnNew;
    @FXML
    private MFXButton btnContinue;
    @FXML
    private MFXButton btnAchievements;
    @FXML
    private MFXButton btnStartSession;
    @FXML
    private Button btnExit;

    @FXML
    private void onActionBtnSettings(ActionEvent event) {
        FlowController.getInstance().goViewInStage("SettingsView", (Stage) btnSettings.getScene().getWindow());
    }

    @FXML
    private void onActionBtnNew(ActionEvent event) {
        FlowController.getInstance().goViewInStage("CreateGameView", (Stage) btnNew.getScene().getWindow());
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
        FlowController.getInstance().goViewInStage("GameView", (Stage) btnContinue.getScene().getWindow());
    }

    @FXML
    private void onActionBtnAchievements(ActionEvent event) {
        FlowController.getInstance().goViewInStage("AchievementsView", (Stage) btnAchievements.getScene().getWindow());
    }

    @FXML
    private void OnActionBtnStartSession(ActionEvent event) {
        FlowController.getInstance().goViewInStage("UserRegisterView", (Stage) btnStartSession.getScene().getWindow());
    }

    @FXML
    private void onActionBtnExit(ActionEvent event) {
        FlowController.getInstance().salir();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

}
