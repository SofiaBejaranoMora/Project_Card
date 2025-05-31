package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * * FXML Controller class * * @author ashly
 */
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
    private MFXButton btnRegisterSession;

    @FXML
    private void onActionBtnSettings(ActionEvent event) {
        FlowController.getInstance().goView("SettingsView");
    }

    @FXML
    private void onActionBtnNew(ActionEvent event) {
        FlowController.getInstance().goView("CreateGameView");
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
        FlowController.getInstance().goView("LoadGamesView");
    }

    @FXML
    private void onActionBtnAchievements(ActionEvent event) {
        FlowController.getInstance().goView("AchievementsView");
    }

    @FXML
    private void OnActionBtnRegisterSession(ActionEvent event) {
        AppContext.getInstance().set("isRegisterSession", true);
        FlowController.getInstance().goView("UserSessionView");
    }

    @FXML
    private void OnActionBtnStartSession(ActionEvent event) {
        AppContext.getInstance().set("isRegisterSession", false);
        FlowController.getInstance().goView("UserSessionView");
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
