package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.Player;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.model.Stackcard;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.model.StackcardxcardDto;
import cr.ac.una.project_card.service.CardService;
import cr.ac.una.project_card.service.GameService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** * * FXML Controller class * * @author ashly */
public class GameController extends Controller implements Initializable {

    // Variables del juego
    private PlayerDto player = new PlayerDto();
    private GameDto game;
    private ImagesUtil getBackground;
    private Mensaje message = new Mensaje();
    private List<VBox> columns = new ArrayList<>();
    private List<CardDto> cards = new ArrayList<>(); // Mazo completo
    private CardService cardService = new CardService();
    private String style;

    // Listas de cartas por tipo
    private List<CardDto> corazones = new ArrayList<>();
    private List<CardDto> picas = new ArrayList<>();
    private List<CardDto> treboles = new ArrayList<>();
    private List<CardDto> diamantes = new ArrayList<>();
    private Boolean isBigScreen = false;
    private List<StackcardDto> allStacks = new ArrayList<>();

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
    private AnchorPane root;
    @FXML
    private ImageView mgvMaze;
    @FXML
    private HBox hBoxSuits;
    @FXML
    private HBox hBxBoard;

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

    @FXML
    private void onActionBtnClues(ActionEvent event) {
    }

    @FXML
    private void onActionBtnUndo(ActionEvent event) {
    }

    @FXML
    private void onMouseClickedMgvMaze(MouseEvent event) {
    }

    private CardDto getCartaByNumber(List<CardDto> tipo, int number) {
        for (CardDto carta : tipo) {
            if (carta.getNumber() != null && carta.getNumber() == number) {
                return carta;
            }
        }
        return null;
    }
    
    private void loadGame() {
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            Long gameId = (Long) AppContext.getInstance().get("IdCurrentGame");
            AppContext.getInstance().set("IdCurrentGame", null);//limpio el appcontext

            player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
            GameService gameService = new GameService();

            if (gameId != null) {
                Respuesta answer = gameService.getGameID(gameId);
                if (answer != null && answer.getEstado()) {
                    this.game = (GameDto) answer.getResultado("Partida");
                    allStacks= game.getStackCards();
                    
                    lblDificult.setText("Dificultad: " + game.getDifficulty());
                    //TODO
                } else {
                    message.showModal(Alert.AlertType.INFORMATION, "Error al cargar partida", getStage(), "No se pudo cargar la partida. Por favor, selecciona una partida válida.");
                    FlowController.getInstance().goView("MenuView");
                }
            } else {
                message.showModal(Alert.AlertType.INFORMATION, "Por favor selecciona una partida", getStage(), "No hay una partida seleccionada.");
                FlowController.getInstance().goView("MenuView");
            }
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "Por favor inicie sesión", getStage(), "Para poder cargar un juego es necesario iniciar sesión");
            FlowController.getInstance().goView("MenuView");
        }
    }

    private void movedCard(VBox arrive, Pane panel) {   //Comprueba si se mueve o no una carta de un VBox a otro
        VBox start = (VBox) panel.getParent();
        Integer index = start.getChildren().indexOf(panel);
        for (int i = start.getChildren().size(); i >= index; i--) {
            arrive.getChildren().add(start.getChildren().remove(i));    //Agregar el -1pt por el movimiento de carta
        }
    }
    
    private void deleteFullSuit(VBox from) {     //Borra un palo completo, cuando se alcanza la escalera de As a K
        List<Node> cards = from.getChildren();
        for (int i = cards.size(); i > 13; i--) {
            cards.remove(i);    //Agregar los 100pts por completar palos
        }
    }
 
    private VBox getColumn(Point2D ubication){   //Extrae los VBox y los identifica como si fueran columnas
        for (VBox column : columns) {
            Point2D parentUbication = column.sceneToLocal(ubication);
            if (column.contains(parentUbication)) {
                return column;
            }
        }
        return null;
    }
       
    public void setupBoard() { //Alistará el tablero completo para el modo de juego
        Platform.runLater(() -> {
            for (int i = 0; i < 10; i++) {
                columns.get(i).getChildren().clear();   //Limpia las columnas en caso de que existan cartas previas
                for (StackcardxcardDto stackDto : allStacks.get(i).getStackCardxCards()) {
                    columns.get(i).getChildren().add(setupCard(stackDto.getIsFaceUp(), stackDto.getCard()));    //Da la carta en un pane para los VBox
                }
            }
        });
    }
    
    private Pane setupCard(Boolean isFaceUp, CardDto cardDto) { //Se encarga de generar automaticamente las carta en columnas
        Pane space = new Pane();
        DoubleProperty width = new SimpleDoubleProperty();
        width.bind(columns.get(0).widthProperty());
        DoubleProperty height = new SimpleDoubleProperty();
        height.bind(width.divide(3));
        space.prefWidthProperty().bind(width);  //Es para agarrar el ancho de un 
        space.prefHeightProperty().bind(height);
        
        ImageView card = new ImageView();
        String rute;
        if(isFaceUp){
            rute = ImagesUtil.getCardPath(player.getCardStyle() + "/", cardDto.getNumber() + cardDto.getType());
            card.setImage(new Image(rute));
        } else {
            rute = ImagesUtil.getBackCardPath(setupStyle());
            card.setImage(new Image(rute));
        }
        
        card.fitWidthProperty().bind(width);
        card.setPreserveRatio(true);
        space.getChildren().add(card);
        return space;
    }
    
    private String setupStyle() {
        Long styleType = player.getCardStyle();
        if (styleType.equals(2L)) {
            return style + "M";
        } else if (styleType.equals(3L)) {
            return style + "V";
        } else {
            return style + "N";
        }
    }

    private void setupBackground(String rute) { //Manipula la ruta que del fondo para adecuarla al anchorpane
        BackgroundImage backgroundImage = new BackgroundImage(new Image(rute),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        root.setBackground(new Background(backgroundImage));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
       loadGame();
       style = game.getDifficulty() + "";
        if (AppContext.getInstance().get("Background") == null) {
            String rute = ImagesUtil.getBackground("GrassBackground");
            setupBackground(rute);
        } else {
            String rute = ImagesUtil.getBackground((String) AppContext.getInstance().get("Background"));
            setupBackground(rute);
        }
        columns.clear();
        for(Node node: hBxBoard.getChildren()){
            if(node instanceof VBox){
                columns.add((VBox) node);
            }
        }
    }

}
