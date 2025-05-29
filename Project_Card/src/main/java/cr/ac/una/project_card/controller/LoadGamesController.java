package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/** * FXML Controller class * * @author ashly */
public class LoadGamesController extends Controller implements Initializable {

    private Mensaje message = new Mensaje();
    private List<String> saveGames = new ArrayList();
    private ObservableList<String> observableSaveGames = FXCollections.observableArrayList();
    
    @FXML
    private Button btnBack;
    @FXML
    private TableView<String> tbvSaveGames;
    @FXML
    private TableColumn<String, ?> cmnSaveGames;
    @FXML
    private MFXButton btnDelete;
    @FXML
    private MFXButton btnContinue;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnDelete(ActionEvent event) {
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
        FlowController.getInstance().goView("GameView");
    }
    
    private void initializeSaveGames() {
        String user = (String) AppContext.getInstance().get("CurrentUser");
        
        if ("".equals(user.trim())) {
            message.showModal(Alert.AlertType.ERROR, "Cargando partidas guardadas", getStage(), "Favor de revisar el inicio de sesión para cargar partidas anteriores.");
        } else {
            
            observableSaveGames.setAll(saveGames);
            cmnSaveGames.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbvSaveGames.setItems(observableSaveGames);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    
    }

}
