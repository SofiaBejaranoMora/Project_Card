package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/** * * FXML Controller class * * @author ashly */
public class MenuController extends Controller implements Initializable {

    private Boolean hasStarted;
    private Mensaje message = new Mensaje();

    @FXML
    private Button btnSettings;
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
        if (hasStarted) {
            FlowController.getInstance().goView("SettingsView");
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¡Primero tu nombre, luego tu estilo!", getStage(), "Puedes cambiar el aspecto de tus cartas, pero antes, dinos quién eres.\n\n"
                    + "¡Regístrate o inicia sesión para personalizar tu experiencia!");
        }
    }

    @FXML
    private void onActionBtnNew(ActionEvent event) {
        if (hasStarted) {
            FlowController.getInstance().goView("CreateGameView");
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¡Sin identidad, no hay batalla!", getStage(), "Antes de barajar tu mazo y empezar la aventura, necesitas registrarte o iniciar sesión.\n\n"
                    + "¡No puedes lanzar cartas si nadie sabe quién eres!");
        }
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
        if (hasStarted) {
            FlowController.getInstance().goView("LoadGamesView");
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¿Tú otra vez? ¡Demuéstralo!", getStage(), "Parece que ya habías empezado una partida… pero para continuarla, necesitamos que inicies sesión o te registres.\n\n"
                    + "¡No vale hacer trampa con cartas fantasmas!");
        }
    }

    @FXML
    private void onActionBtnAchievements(ActionEvent event) {
        if (hasStarted) {
            FlowController.getInstance().goView("AchievementsView");
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¡Los logros no aparecen por arte de magia!", getStage(), "Tus victorias están a salvo, pero solo podemos mostrártelas si sabemos quién eres.\n \n"
                    + "Inicia sesión o regístrate para ver tus hazañas.");
        }
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
        AppContext.getInstance().set("isRegisterSession", situation);
        FlowController.getInstance().goView("UserSessionView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AppContext.getInstance().set("hasSectionStarted", false);
        AppContext.getInstance().set("isRegisterSession", true);
    }

    @Override
    public void initialize() {
        hasStarted = (Boolean) AppContext.getInstance().get("hasSectionStarted");
        Boolean isRegistering = (Boolean) AppContext.getInstance().get("isRegisterSession");

        hasStarted = hasStarted != null ? hasStarted : false;
        isRegistering = isRegistering != null ? isRegistering : true;

        if (hasStarted) {
            btnRegisterSession.setVisible(false);
            btnStartSession.setText("Cerrar Sesión");
            btnStartSession.setVisible(true);
        } else {
            btnRegisterSession.setVisible(!isRegistering);
            btnStartSession.setText("Iniciar Sesión");
            btnStartSession.setVisible(true);
        }
    }

}
