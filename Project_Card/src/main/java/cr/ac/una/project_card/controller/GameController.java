package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.service.CardService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/*** FXML Controller class * * @author ashly */
public class GameController extends Controller implements Initializable {

    // Variables del juego
    private List<CardDto> card = new ArrayList<>(); // Mazo completo
    private GameDto game;
    private CardService cardService = new CardService();
    private Mensaje message = new Mensaje();
    private PlayerDto player = new PlayerDto();

    // Listas de cartas por tipo
    private List<CardDto> corazones = new ArrayList<>();
    private List<CardDto> picas = new ArrayList<>();
    private List<CardDto> treboles = new ArrayList<>();
    private List<CardDto> diamantes = new ArrayList<>();
    private Boolean isBigScreen = false;

    @FXML
    private MFXButton btnBack;
    @FXML
    private Label lblDificult;
    @FXML
    private Label lblPoints;
    @FXML
    private Label lblTimer;
    @FXML
    private Button btnSizeScreen;
    @FXML
    private ImageView mgvFull;
    @FXML
    private ImageView mgvMin;
    @FXML
    private MFXButton btnSettings;
    @FXML
    private MFXButton btnClues;
    @FXML
    private MFXButton btnUndo;
    @FXML
    private ImageView mgvBackground;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnSizeScreen(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (isBigScreen) {
            stage.setFullScreen(false);
            isBigScreen = false;
            mgvFull.setVisible(true);
            mgvMin.setVisible(false);
        } else {
            stage.setFullScreen(true);
            isBigScreen = true;
            mgvFull.setVisible(false);
            mgvMin.setVisible(true);
        }
    }

    @FXML
    private void onActionBtnSettings(ActionEvent event) {
        FlowController.getInstance().goView("SettingsView");
    }

    private void loadCards() {
        Long difficulty = game.getDifficulty();
        if (difficulty == 1) {
            card.addAll(picas);
        } else {
            while (card.size() < 104) {
                for (int i = 0; i < 13; i++) {
                    CardDto cartaPicas = getCartaByNumber(picas, i + 1);
                    if (cartaPicas != null) {
                        card.add(cartaPicas);
                    }
                    CardDto cartaCorazones = getCartaByNumber(corazones, i + 1);
                    if (cartaCorazones != null) {
                        card.add(cartaCorazones);
                    }
                }
            }

            game.setCards(card);
        }
    }

    private CardDto getCartaByNumber(List<CardDto> tipo, int number) {
        for (CardDto carta : tipo) {
            if (carta.getNumber() != null && carta.getNumber() == number) {
                return carta;
            }
        }
        return null;
    }

    private void prepareGame() {
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
            game = new GameDto();

            Respuesta heartCardsAnswer = cardService.getCardType("C");
            Respuesta diamondCardsAnswer = cardService.getCardType("D");
            Respuesta spadeCardsAnswer = cardService.getCardType("P");
            Respuesta clubCardsAnswer = cardService.getCardType("T");

            if (heartCardsAnswer.getEstado() && diamondCardsAnswer.getEstado() && spadeCardsAnswer.getEstado() && clubCardsAnswer.getEstado()) {
                corazones = (List<CardDto>) heartCardsAnswer.getResultado("Cartas");
                diamantes = (List<CardDto>) diamondCardsAnswer.getResultado("Cartas");
                picas = (List<CardDto>) spadeCardsAnswer.getResultado("Cartas");
                treboles = (List<CardDto>) clubCardsAnswer.getResultado("Cartas");
            } else {
                message.showModal(Alert.AlertType.ERROR, "Error al cargar cartas", getStage(), "No se pudieron cargar las cartas necesarias.");
                return;
            }

            loadCards();
            game.setPlayer(player);
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "Por favor inicie sesión", getStage(), "Para poder crear un juego es necesario iniciar sesión");
            FlowController.getInstance().goView("MenuView");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareGame();
    }

    @Override
    public void initialize() {

    }
}
