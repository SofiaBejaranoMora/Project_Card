package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class CreateGameController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfNameGame;
    @FXML
    private ImageView mgvEasyMode;
    @FXML
    private ImageView mgvMediumMode;
    @FXML
    private ImageView mgvHardMode;
    @FXML
    private Button btnBack;
    
    @FXML
    private void onKeyPressed(KeyEvent event) {
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
