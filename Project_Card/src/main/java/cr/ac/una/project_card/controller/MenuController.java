package cr.ac.una.project_card.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/** * FXML Controller class * * @author ashly */
public class MenuController implements Initializable {

    @FXML
    private MFXButton btnSettings;
    @FXML
    private MFXButton btnNew;
    @FXML
    private MFXButton btnContinue;
    @FXML
    private MFXButton btnAchievements;
    @FXML
    private MFXButton btnCloseSession;
    @FXML
    private Button btnSession;
    @FXML
    private ImageView mgvUserPorfile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnSettings(ActionEvent event) {
    }

    @FXML
    private void onActionBtnNew(ActionEvent event) {
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAchievements(ActionEvent event) {
    }

    @FXML
    private void OnActionBtnCloseSession(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSession(ActionEvent event) {
    }
    
}
