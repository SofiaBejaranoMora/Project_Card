package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.AnimationAndSound;
import cr.ac.una.project_card.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/** * FXML Controller class * * @author ashly */
public class AboutOfController extends Controller implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        AnimationAndSound.buttonSound();
        FlowController.getInstance().goView("MenuView");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @Override
    public void initialize() {
    }

}
