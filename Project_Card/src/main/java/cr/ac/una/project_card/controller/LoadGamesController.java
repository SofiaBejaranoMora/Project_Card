package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.PlayerDto;
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
    private PlayerDto player;
    private GameDto game;
    private List<GameDto> saveGames = new ArrayList();
    private ObservableList<GameDto> observableSaveGames = FXCollections.observableArrayList();
    
    @FXML
    private Button btnBack;
    @FXML
    private TableView<GameDto> tbvSaveGames;
    @FXML
    private TableColumn<GameDto, String> cmnSaveGames;
    @FXML
    private MFXButton btnDelete;
    @FXML
    private MFXButton btnContinue;
    @FXML
    private Button btnStatistics;

    @FXML
    private void onActionBtnStatistics(ActionEvent event) {
        FlowController.getInstance().goView("UserStatisticView");
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnDelete(ActionEvent event) {
        GameDto selectedGame = tbvSaveGames.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            saveGames.remove(selectedGame);
            observableSaveGames.setAll(saveGames);
        } else {
            message.showModal(Alert.AlertType.WARNING, "Eliminar partida", getStage(), "Por favor selecciona una partida antes de eliminar.");
        }
    }

    @FXML
    private void onActionBtnContinue(ActionEvent event) {
        game = tbvSaveGames.getSelectionModel().getSelectedItem();
        
        if (game != null && game.getName() != null && !game.getName().trim().isEmpty()) {
            AppContext.getInstance().set("CurrentGame", game);
            FlowController.getInstance().goView("GameView");
            
        } else {
            message.showModal(Alert.AlertType.WARNING, "Cargar partida", getStage(), "Por favor selecciona una partida válida para continuar.");
        }
    }

    private void initializeSaveGames() {
        if (player == null || player.getName().trim().isEmpty()) {
            message.showModal(Alert.AlertType.ERROR, "Cargando partidas guardadas", getStage(), "Favor de revisar el inicio de sesión para cargar partidas anteriores.");
            return;
        }

        saveGames.clear();
        saveGames.addAll(player.getGameList());
        observableSaveGames.setAll(saveGames);
        cmnSaveGames.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbvSaveGames.setItems(observableSaveGames);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
        if (player != null) {
            game = new GameDto();
            saveGames = player.getGameList();
        } else {
            message.showModal(Alert.AlertType.ERROR, "Error", getStage(), "No se encontró información del usuario.");
        }
    }

}
