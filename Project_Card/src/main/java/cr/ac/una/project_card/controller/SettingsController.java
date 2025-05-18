package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class SettingsController extends Controller implements Initializable {

    @FXML
    private Button btnNomalCards;
    @FXML
    private Button btnMandalaCards;
    @FXML
    private Button btnGlorianCards;
    @FXML
    private Button btnNormalBackground;
    @FXML
    private Button btnWoodBackground;
    @FXML
    private Button btnBack;

    @FXML
    private void onActionBtnNomalCards(ActionEvent event) {
    }

    @FXML
    private void onActionBtnMandalaCards(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGlorianCards(ActionEvent event) {
    }

    @FXML
    private void onActionBtnNormalBackground(ActionEvent event) {
    }

    @FXML
    private void onActionBtnWoodBackground(ActionEvent event) {
    }
    
       @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goViewInStage("MenuView", (Stage) btnBack.getScene().getWindow());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

}
