package cr.ac.una.project_card.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/** * FXML Controller class * * @author ashly */
public class UserRegisterViewController extends Controller implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private ImageView mgvUserPhoto;
    @FXML
    private Button btnHelp;
    @FXML
    private MFXTextField txfUserName;
    @FXML
    private Label lblCurrentPoints;
    @FXML
    private MFXButton btnUploadPhoto;
    @FXML
    private MFXButton btnRegister;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
    }

    @FXML
    private void onActionBtnHelp(ActionEvent event) {
        //message.show(Alert.AlertType.INFORMATION, saveRoute, saveRoute);//Hacer la explicación del juego
    }

    @FXML
    private void onActionBtnUploadPhoto(ActionEvent event) {
    }

    @FXML
    private void onActionBtnRegister(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

}
