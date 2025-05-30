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

    private boolean isFirstOpen = true;
    
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
        sessionChecker(true);
    }

    @FXML
    private void OnActionBtnStartSession(ActionEvent event) {
        sessionChecker(false);
    }

    @FXML
    private void onActionBtnExit(ActionEvent event) {
        FlowController.getInstance().salir();
    }

    private void sessionChecker(boolean situation) {
        if (situation) {
            AppContext.getInstance().set("isRegisterSession", true);
            isFirstOpen = false;
            FlowController.getInstance().goView("UserSessionView");
        } else {
            AppContext.getInstance().set("isRegisterSession", false);
            FlowController.getInstance().goView("UserSessionView");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("hasSectionStarted", false);
        AppContext.getInstance().set("isRegisterSession", true);
    }

    @Override
    public void initialize() {
        if (((Boolean) AppContext.getInstance().get("hasSectionStarted")) || !(Boolean) AppContext.getInstance().get("isRegisterSession")) {
            btnRegisterSession.setVisible(false);
            btnStartSession.setText("Cerrar Sesión");
            btnStartSession.setVisible(true);
        } else {
            if (!isFirstOpen) {
                btnRegisterSession.setVisible(false);
                btnStartSession.setText("Iniciar Sesión");
                btnStartSession.setVisible(true);
            }
        }
    }

}
