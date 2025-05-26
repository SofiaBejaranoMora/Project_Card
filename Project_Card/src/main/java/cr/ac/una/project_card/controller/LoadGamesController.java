package cr.ac.una.project_card.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/** * FXML Controller class * * @author ashly */
public class LoadGamesController extends Controller implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TableView<?> tbvSaveGames;
    @FXML
    private TableColumn<?, ?> cmnSaveGames;
    @FXML
    private MFXButton btnDelete;
    @FXML
    private MFXButton btnContinue;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
    }

    @FXML
    private void onActionBtnDelete(ActionEvent event) {
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    
    }

}
