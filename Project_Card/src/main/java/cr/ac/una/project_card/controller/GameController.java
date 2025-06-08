package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.model.StackcardxcardDto;
import cr.ac.una.project_card.service.CardService;
import cr.ac.una.project_card.service.GameService;
import cr.ac.una.project_card.service.StackcardxcardService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
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

/**
 * * * FXML Controller class * * @author ashly
 */
public class GameController extends Controller implements Initializable {

    // Variables del juego
    private PlayerDto player = new PlayerDto();
    private GameDto game;
    private ImagesUtil getBackground;
    private Mensaje message = new Mensaje();
    private List<VBox> columns = new ArrayList<>(); // columnas
    private List<CardDto> cards = new ArrayList<>(); // Mazo completo
    private CardService cardService = new CardService();
    private String style;
    private ColorAdjust colorAdjust = new ColorAdjust();
    private MouseEvent mouse;
    private List<StackcardxcardDto> allStackcardxcard;

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
        if (!cards.isEmpty()) {

            if (enableAddingCardsColumns()) {
                StackcardxcardDto newStackcardxcardDto;
                List<StackcardxcardDto> newStackcardxcardDtoList = new ArrayList<>();
                if (hasSaveStackcardxcardList(newStackcardxcardDtoList)) {
                    for (int i = 0; i < 10; i++) {
                        newStackcardxcardDto = newStackcardxcardDtoList.get(i);
                        allStacks.get(i).getStackCardxCards().add(newStackcardxcardDto);
                        allStackcardxcard.add(newStackcardxcardDto);
                        columns.get(i).getChildren().add(setupCard(newStackcardxcardDto));
                        //animación
                        if (newStackcardxcardDtoList.isEmpty()) {
                            return;
                        }
                    }
                }
            } else {
                message.showModal(Alert.AlertType.WARNING, "¡Alto ahí, tahúr impaciente!", getStage(), "¡No puedes repartir si una columna está vacía.!\n\n"
                        + "Las reglas del juego son claras: antes de tocar el mazo, asegúrate de que todas las columnas tengan al menos una carta. Arrastra una carta a esa columna vacía... "
                        + "¡y entonces sí, reparte como un verdadero maestro del Solitario!");
            }
        }
    }

    public Boolean hasSaveStackcardxcardList(List<StackcardxcardDto> newStackcardxcardDtoList) {
        StackcardxcardDto newStackcardxcardDto;
        for (int i = 0; i < 10; i++) {
            newStackcardxcardDto = new StackcardxcardDto(true,Long.valueOf( columns.get(i).getChildren().size()+(i+1)));
            newStackcardxcardDto.setCard(cards.remove(cards.size() - 1));
            newStackcardxcardDto.setStackCard(allStacks.get(i));
            newStackcardxcardDtoList.add(newStackcardxcardDto);
            if (cards.isEmpty()) {
                colorAdjust.setSaturation(-1);
                colorAdjust.setBrightness(-0.3);
                colorAdjust.setContrast(-0.2);
                mgvMaze.setEffect(colorAdjust);
                break;
            }
        }
        StackcardxcardService stackcardxcardService = new StackcardxcardService();
        Respuesta answer = stackcardxcardService.SaveStackcardxCardList(newStackcardxcardDtoList);
        if (answer.getEstado()) {
            newStackcardxcardDtoList = (List<StackcardxcardDto>) answer.getResultado("Stackcardxcard");
            return true;
        }
        return false;
    }

    public Boolean enableAddingCardsColumns() {
        for (VBox currentVBox : columns) {
            if (currentVBox.getChildren().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public StackcardxcardDto searchStackcardxcardDto(Pane pane) {
        if (pane.getId() != null && !pane.getId().isBlank()) {
            Long idPane = Long.valueOf(pane.getId());
            for (StackcardxcardDto currentStackcardxcard : allStackcardxcard) {
                if (currentStackcardxcard.getId().equals(idPane)) {
                    return currentStackcardxcard;
                }
            }
            return null;
        }
        return null;
    }

    private boolean isValidSequence(VBox sourceColumn, Pane cardPane) {
        try {
            int indexPane = sourceColumn.getChildren().indexOf(cardPane);
            if (indexPane == -1) {
                message.showModal(Alert.AlertType.ERROR, "Error en verificación", getStage(), "No se encontró la carta en la columna.");
                return false;
            }
            int sourceIndex = columns.indexOf(sourceColumn);
            List<StackcardxcardDto> cards = allStacks.get(sourceIndex).getStackCardxCards();
            if (indexPane >= cards.size()) {
                message.showModal(Alert.AlertType.ERROR, "Error en verificación", getStage(), "Índice de carta inválido.");
                return false;
            }

            for (int i = indexPane; i < cards.size(); i++) {
                if (!cards.get(i).getIsFaceUp()) {
                    message.showModal(Alert.AlertType.WARNING, "Movimiento inválido", getStage(), "No se pueden mover cartas boca abajo.");
                    return false;
                }
            }
            String suitType = cards.get(indexPane).getCard().getType();
            Long expectedNumber = cards.get(indexPane).getCard().getNumber();
            for (int i = indexPane + 1; i < cards.size(); i++) {
                String currentSuit = cards.get(i).getCard().getType();
                Long currentNumber = cards.get(i).getCard().getNumber();
                if (!currentSuit.equals(suitType) || currentNumber != expectedNumber - 1) {
                    message.showModal(Alert.AlertType.WARNING, "Movimiento inválido", getStage(), "Las cartas no forman una escalera válida.");
                    return false;
                }
                expectedNumber = currentNumber;
            }
            return true;
        } catch (Exception e) {
            message.showModal(Alert.AlertType.ERROR, "Error en verificación", getStage(), "No se pudo verificar la secuencia de cartas: " + e.getMessage());
            return false;
        }
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
                    allStackcardxcard = new ArrayList<>();
                    this.cards.addAll(game.getCards());
                    Collections.shuffle(cards);
                    mgvMaze.setEffect(null);
                    if (cards.isEmpty()) {
                        colorAdjust.setSaturation(-1);
                        colorAdjust.setBrightness(-0.3);
                        colorAdjust.setContrast(-0.2);
                        mgvMaze.setEffect(colorAdjust);
                    }

                    allStacks = game.getStackCards();

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

    private void deleteFullSuit(VBox from) {     //Borra un palo completo, cuando se alcanza la escalera de As a K
        List<Node> cards = from.getChildren();
        for (int i = cards.size(); i > 13; i--) {
            game.setScore(game.getScore() + 100);
            cards.remove(i);    //Agregar los 100pts por completar palos
        }
    }

    private VBox getColumn(Point2D ubication) {   //Extrae los VBox y los identifica como si fueran columnas
        for (VBox column : columns) {
            Point2D parentUbication = column.sceneToLocal(ubication);
            if (column.contains(parentUbication)) {
                return column;
            }
        }
        return null;
    }

    private List<Pane> laderCards(Pane selected) {
        List<Pane> laderList = new ArrayList<>();
        VBox parent = (VBox) selected.getParent();
        for (int i = parent.getChildren().indexOf(selected); i < parent.getChildren().size(); i++) {
            laderList.add((Pane) parent.getChildren().get(i));
        }
        return laderList;
    }

    private void turnCards(VBox actualColumn) {
        Pane toTurn = (Pane) actualColumn.getChildren().get(actualColumn.getChildren().size() - 1);
        StackcardxcardDto ubication = searchStackcardxcardDto(toTurn);
        CardDto cardDto = ubication.getCard();
        if (!ubication.getIsFaceUp()) {
            ubication.setIsFaceUp(true);
            String rute = ImagesUtil.getCardPath(player.getCardStyle() + "/", cardDto.getNumber() + cardDto.getType());
            ImageView card = (ImageView) toTurn.getChildren().get(0);
            card.setImage(new Image(rute));
        }
    }
    
    public void setupBoard() { //Alistará el tablero completo para el modo de juego
        Platform.runLater(() -> {
            for (int i = 0; i < 10; i++) {
                columns.get(i).getChildren().clear();   //Limpia las columnas en caso de que existan cartas previas
                for (StackcardxcardDto stackDto : allStacks.get(i).getStackCardxCards()) {
                    columns.get(i).getChildren().add(setupCard(stackDto));    //Da la carta en un pane para los VBox
                }
            }
        });
    }

    private Pane setupCard(StackcardxcardDto stackcardxcardDto) { //Se encarga de generar automaticamente las carta en columnas
        Boolean isFaceUp = stackcardxcardDto.getIsFaceUp();
        CardDto cardDto = stackcardxcardDto.getCard();
        allStackcardxcard.add(stackcardxcardDto);
        Pane space = new Pane();
        DoubleProperty width = new SimpleDoubleProperty();
        width.bind(columns.get(0).widthProperty());
        DoubleProperty height = new SimpleDoubleProperty();
        height.bind(width.divide(3));
        space.prefWidthProperty().bind(width);  //Es para agarrar el ancho de un 
        space.prefHeightProperty().bind(height);

        ImageView card = new ImageView();
        String rute;
        if (isFaceUp) {
            rute = ImagesUtil.getCardPath(player.getCardStyle() + "/", cardDto.getNumber() + cardDto.getType());
            card.setImage(new Image(rute));
        } else {
            rute = ImagesUtil.getBackCardPath(setupStyle());
            card.setImage(new Image(rute));
        }

        space.setId(String.valueOf(stackcardxcardDto.getId()));
        card.fitWidthProperty().bind(width);
        card.setPreserveRatio(true);
        space.getChildren().add(card);
        
        space.setOnMousePressed(pressEvent -> {
            ImageView copyCard = new ImageView(card.getImage());
            copyCard.setFitWidth(card.getImage().getWidth() * 0.065);
            copyCard.setFitHeight(card.getImage().getHeight() * 0.065);
            copyCard.setOpacity(1.0);

            System.out.println("Entro en click");
            root.getChildren().add(copyCard);
            copyCard.setLayoutX(pressEvent.getSceneX() - copyCard.getFitWidth() / 2);
            copyCard.setLayoutY(pressEvent.getSceneY() - copyCard.getFitHeight() / 2);
            List<Pane> laderList = laderCards(space);
            for (Pane pane : laderList) {
                pane.setVisible(false);
            }

            space.setOnMouseDragged(dragEvent -> {
                System.out.println("Hello drag");
                copyCard.setLayoutX(dragEvent.getSceneX() - copyCard.getFitWidth() / 2);
                copyCard.setLayoutY(dragEvent.getSceneY() - copyCard.getFitHeight() / 2);
            });

            space.setOnMouseReleased(releaseEvent -> {
                System.out.println("Bye drag");
                Point2D mousePosition = new Point2D(releaseEvent.getSceneX(), releaseEvent.getSceneY());
                VBox currentColumn = getColumn(mousePosition);
                VBox actualColumn = (VBox) space.getParent();
                //Método de movimientos validos
                if (true && currentColumn != null) {
                    for (Pane pane : laderList) {
                        actualColumn.getChildren().remove(pane);
                        currentColumn.getChildren().add(pane);
                    }
                    turnCards(actualColumn);
                    //Agregar el -1pt para mantener los puntos al día
                }
                root.getChildren().remove(copyCard);
                for (Pane pane : laderList) {
                    pane.setVisible(true);
                }
            });
        });
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
        String rute = ImagesUtil.getBackCardPath(setupStyle());
        mgvMaze.setImage(new Image(rute));
        columns.clear();
        for (Node node : hBxBoard.getChildren()) {
            if (node instanceof VBox) {
                columns.add((VBox) node);
            }
        }
    }

}
