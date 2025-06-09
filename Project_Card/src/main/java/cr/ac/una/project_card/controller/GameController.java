package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.AchievementDto;
import cr.ac.una.project_card.model.AnimationAndSound;
import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.model.StackcardxcardDto;
import cr.ac.una.project_card.service.AchievementsService;
import cr.ac.una.project_card.service.GameService;
import cr.ac.una.project_card.service.PlayerService;
import cr.ac.una.project_card.service.StackcardxcardService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * * * FXML Controller class * * @author ashly
 */
public class GameController extends Controller implements Initializable {

    // Variables del juego
    private AchievementsService achievementsService = new AchievementsService();
    private PlayerDto player = new PlayerDto();
    private GameDto game;
    private List<CardDto> cards = new ArrayList<>(); // Mazo completo
    private List<StackcardDto> allStacks = new ArrayList<>();
    private List<StackcardxcardDto> allStackcardxcard;
    private List<StackcardxcardDto> chargeUnfinishGameList;
    private List<VBox> columns = new ArrayList<>(); // columnas
    private List<Pane> ladderList = new ArrayList<>();
    private List<AchievementDto> achievementNotObtainedTime = new ArrayList();
    private List<AchievementDto> achievementNotObtainedWin = new ArrayList();
    private List<AchievementDto> achievementNotObtainedLose = new ArrayList();
    private List<AchievementDto> achievementNotObtainedPoint = new ArrayList();
    private ColorAdjust colorAdjust = new ColorAdjust();
    private Mensaje message = new Mensaje();
    private String style;
    private Timeline currentTime;
    private Boolean isTimerStarted = false;
    private Boolean isBigScreen = false;
    private int originalPoints;
    private int timeLimit;
    private int timeCalculate;
    private int fillSuits = 7;

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
    private MFXButton btnHelp;
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
        AnimationAndSound.buttonSound();
        if ((game != null) && (allStackcardxcard != null && !allStackcardxcard.isEmpty())) {
            if (timeCalculate != 0) {
                currentTime = new Timeline();
                isTimerStarted = false;
                game.setTime(game.getTime() - Long.valueOf(timeCalculate));
                game.setScore(Long.valueOf(originalPoints));
            }
            GameService serviceGame = new GameService();
            StackcardxcardService serviceStackcardxcard = new StackcardxcardService();
            cards.size();
            allStackcardxcard.size();
            Respuesta answerGame = serviceGame.EditGameId(game, cards);
            Respuesta answerStackcardxcard = serviceStackcardxcard.updateStackcardxCardList(allStackcardxcard);
            if (answerGame.getEstado() && answerStackcardxcard.getEstado()) {
                PlayerService servicePlayer = new PlayerService();
                if (player.getId() != null) {
                    if (game.getHasWon().equals("F") || game.getHasWon().equals("T")) {
                        gameAchievements();
                    }
                    Respuesta answerPlayer = servicePlayer.getPlayerId(player.getId());
                    if (answerPlayer != null && answerPlayer.getEstado()) {
                        this.player = (PlayerDto) answerPlayer.getResultado("Jugador");
                        cleanList();
                        AppContext.getInstance().set("CurrentUser", player);
                        message.showModal(Alert.AlertType.INFORMATION, "¡La jugada quedó registrada!", getStage(), "Tus cartas están a salvo, listas para esperar tu próximo movimiento. Todo quedó guardado correctamente.\n\n"
                                + "¡Nos vemos en la próxima mano!");
                    } else {
                        message.showModal(Alert.AlertType.WARNING, "¡Tu perfil no pudo robar la carta que necesitaba!", getStage(), "Algo impidió que tus datos se sincronizaran con el mazo del destino.\n\n"
                                + "Te recomendamos iniciar sesión nuevamente para asegurar que tu perfil esté al día y listo para jugar sin trampas del azar.");
                    }
                } else {
                    message.showModal(Alert.AlertType.ERROR, "Esta jugada se nos escapó de las manos…", getStage(), "No pudimos guardar tu partida esta vez."
                            + " Asegúrate de estar conectado y haber iniciado sesión para no perder tu progreso.\n\n"
                            + "¡La próxima mano será mejor!");
                }
            } else {
                message.showModal(Alert.AlertType.ERROR, "Esta jugada se nos escapó de las manos…", getStage(), "No pudimos guardar tu partida esta vez."
                        + " Asegúrate de estar conectado y haber iniciado sesión para no perder tu progreso.\n\n"
                        + "¡La próxima mano será mejor!");
            }
        } else {
            message.showModal(Alert.AlertType.ERROR, "Esta jugada se nos escapó de las manos…", getStage(), "No pudimos guardar tu partida esta vez."
                    + " Asegúrate de estar conectado y haber iniciado sesión para no perder tu progreso.\n\n"
                    + "¡La próxima mano será mejor!");
        }
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnSizeScreen(ActionEvent event) {
        AnimationAndSound.buttonSound();
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
        AnimationAndSound.buttonSound();
        FlowController.getInstance().goView("SettingsView");
    }

    @FXML
    private void onActionBtnClues(ActionEvent event) {
        AnimationAndSound.buttonSound();
        suggestMove();
    }

    @FXML
    private void onActionBtnHelp(ActionEvent event) {
        AnimationAndSound.buttonSound();
    }

    @FXML
    private void onMouseClickedMgvMaze(MouseEvent event) {
        if (!cards.isEmpty()) {
            AnimationAndSound.buttonSound();
            originalPoints -= 100;    // Quita -1pt para mantener los puntos al día
            lblPoints.setText("Puntuación: " + originalPoints);
            managementPoints();

            if (enableAddingCardsColumns()) {
                StackcardxcardDto newStackcardxcardDto;
                List<StackcardxcardDto> newStackcardxcardDtoList = new ArrayList<>();
                newStackcardxcardDtoList = SaveStackcardxcardList();
                if (newStackcardxcardDtoList != null) {
                    for (int i = 0; i < 10; i++) {
                        newStackcardxcardDto = newStackcardxcardDtoList.get(i);
                        allStacks.get(i).getStackCardxCards().add(newStackcardxcardDto);
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

    public void cleanList() {
        achievementNotObtainedLose.clear();
        achievementNotObtainedPoint.clear();
        achievementNotObtainedTime.clear();
        achievementNotObtainedWin.clear();
        chargeUnfinishGameList.clear();
        allStacks.clear();
        allStackcardxcard.clear();
        ladderList.clear();
        columns.clear();
        cards.clear();
    }

    public void gameAchievements() {
        GameService gameService = new GameService();
        Respuesta answer = new Respuesta();
        List<GameDto> gameList = new ArrayList<>();
        int size = 0;
        if (game.getHasWon().equals("F")) {
            answer = gameService.getGameParameter(player.getId(), "F");
            if (answer.getEstado() != null && answer.getEstado()) {
                gameList = (List<GameDto>) answer.getResultado("Partida");
                for (int i = 0; i < achievementNotObtainedLose.size(); i++) {
                    if (UploadAchievement(achievementNotObtainedLose.get(i), gameList.size(), false)) {
                        achievementNotObtainedLose.remove(i);
                    }
                }
            }
        } else if (game.getHasWon().equals("T")) {
            answer = gameService.getGameParameter(player.getId(), "T");
            if (answer.getEstado() != null && answer.getEstado()) {
                gameList = (List<GameDto>) answer.getResultado("Partida");
                for (int i = 0; i < achievementNotObtainedWin.size(); i++) {
                    if (UploadAchievement(achievementNotObtainedWin.get(i), gameList.size(), false)) {
                        achievementNotObtainedWin.remove(i);
                    }
                }
            }
        }
    }

    public List<StackcardxcardDto> SaveStackcardxcardList() {
        StackcardxcardDto newStackcardxcardDto;
        List<StackcardxcardDto> newStackcardxcardDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            newStackcardxcardDto = new StackcardxcardDto(true, Long.valueOf(columns.get(i).getChildren().size() + (i + 1)));
            newStackcardxcardDto.setCard(cards.remove(cards.size() - 1));
            newStackcardxcardDto.setStackCard(allStacks.get(i));
            newStackcardxcardDtoList.add(newStackcardxcardDto);
            if (cards.isEmpty()) { // si se acaban las cartas pone el mazo en cris
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
            answer = stackcardxcardService.getListStackcardxCard((List<StackcardxcardDto>) answer.getResultado("Stackcardxcard"));
            if (answer.getEstado()) {
                newStackcardxcardDtoList.clear();
                newStackcardxcardDtoList = (List<StackcardxcardDto>) answer.getResultado("StackcardxCard");
                return newStackcardxcardDtoList;
            }
        }
        return null;
    }

    public Boolean enableAddingCardsColumns() {
        for (VBox currentVBox : columns) {
            if (currentVBox.getChildren().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Boolean enableCardMove(VBox newColumn, Pane firstPane) {
        if (!(newColumn != null && firstPane != null)) {
            return false;
        }
        if (!columns.contains(newColumn)) {
            //Mensaje
            return false;
        }
        if (newColumn.getChildren().isEmpty()) {
            return true;
        }
        Node node = newColumn.getChildren().getLast();
        if (!(node instanceof Pane)) { // revisa si el node es diferente a un Pane
            return false;
        }
        Pane pane = (Pane) node;
        if (!((pane.getId() != null || !pane.getId().isBlank()) && (firstPane.getId() != null || !firstPane.getId().isBlank()))) {
            return false;
        }

        StackcardxcardDto lastCardNewColumn = searchStackcardxcardDto(pane);
        StackcardxcardDto firstCardPane = searchStackcardxcardDto(firstPane);
        if (!(lastCardNewColumn != null && firstCardPane != null)) { // revisa que se encuentren las cartas
            return false;
        }

        if (!(lastCardNewColumn.getCard() != null && firstCardPane.getCard() != null)) {
            return false;
        }

        if (lastCardNewColumn.getCard().getNumber() == firstCardPane.getCard().getNumber() + 1) {
            return true;
        }

        return false;
    }

    private Boolean isValidSequence(VBox sourceColumn, Pane cardPane) {
        try {
            int indexPane = sourceColumn.getChildren().indexOf(cardPane);
            if (indexPane == -1) {
                return false;
            }
            int sourceIndex = columns.indexOf(sourceColumn);
            List<StackcardxcardDto> cards = allStacks.get(sourceIndex).getStackCardxCards();
            if (indexPane >= cards.size()) {
                return false;
            }

            for (int i = indexPane; i < cards.size(); i++) {
                if (!cards.get(i).getIsFaceUp()) {
                    return false;
                }
            }
            String suitType = cards.get(indexPane).getCard().getType();
            Long expectedNumber = cards.get(indexPane).getCard().getNumber();
            for (int i = indexPane + 1; i < cards.size(); i++) {
                String currentSuit = cards.get(i).getCard().getType();
                Long currentNumber = cards.get(i).getCard().getNumber();
                if (!currentSuit.equals(suitType) || currentNumber != expectedNumber - 1) {
                    return false;
                }
                expectedNumber = currentNumber;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean isValidSequence(List<StackcardxcardDto> columnCards, CardDto card) {
        try {
            // Buscar el índice de la carta inicial en la lista
            int indexPane = -1;
            for (int i = 0; i < columnCards.size(); i++) {
                if (columnCards.get(i).getCard().getNumber().equals(card.getNumber())
                        && columnCards.get(i).getCard().getType().equals(card.getType())
                        && columnCards.get(i).getIsFaceUp()) {
                    indexPane = i;
                    break;
                }
            }
            if (indexPane == -1) {
                System.out.println("Carta no encontrada o no boca arriba: " + card.getNumber() + card.getType());
                return false;
            }

            // Verificar que todas las cartas desde indexPane hasta el final estén boca arriba
            for (int i = indexPane; i < columnCards.size(); i++) {
                if (!columnCards.get(i).getIsFaceUp()) {
                    System.out.println("Carta en posición " + i + " no está boca arriba");
                    return false;
                }
            }

            // Verificar que la secuencia sea descendente y del mismo palo
            String suitType = columnCards.get(indexPane).getCard().getType();
            Long expectedNumber = columnCards.get(indexPane).getCard().getNumber();
            for (int i = indexPane + 1; i < columnCards.size(); i++) {
                String currentSuit = columnCards.get(i).getCard().getType();
                Long currentNumber = columnCards.get(i).getCard().getNumber();
                if (!currentSuit.equals(suitType) || !currentNumber.equals(expectedNumber - 1)) {
                    System.out.println("Secuencia inválida en posición " + i + ": " + currentNumber + currentSuit + " vs esperado " + (expectedNumber - 1) + suitType);
                    return false;
                }
                expectedNumber = currentNumber;
            }
            System.out.println("Secuencia válida desde índice " + indexPane + " con " + (columnCards.size() - indexPane) + " cartas");
            return true;
        } catch (Exception e) {
            System.out.println("Error en isValidSequence: " + e.getMessage());
            return false;
        }
    }

    private Boolean isValidColumnMove(VBox destColumn, String sourceSuit, Long sourceNumber) {
        try {
            if (destColumn.getChildren().isEmpty()) {
                return true;
            }
            Pane topPane = (Pane) destColumn.getChildren().get(destColumn.getChildren().size() - 1);
            StackcardxcardDto topCard = searchStackcardxcardDto(topPane);
            if (topCard != null) {
                String destSuit = topCard.getCard().getType();
                Long destNumber = topCard.getCard().getNumber();
                if (destSuit == sourceSuit || destNumber == sourceNumber + 1) {
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            message.showModal(Alert.AlertType.ERROR, "Error en verificación", getStage(), "No se pudo verificar el movimiento: " + e.getMessage());
            return false;
        }
    }

    private Boolean hasValidMoves() {
        try {
            for (int col = 0; col < 10; col++) {
                VBox column = columns.get(col); // Solo para compatibilidad con isValidSequence
                List<StackcardxcardDto> cards = allStacks.get(col).getStackCardxCards(); // Usamos las cartas actualizadas del modelo
                if (!cards.isEmpty() && cards.get(cards.size() - 1).getIsFaceUp()) {
                    int startIndex = cards.size() - 1;
                    while (startIndex > 0 && cards.get(startIndex - 1).getIsFaceUp()) {
                        startIndex--;
                    }
                    // Usamos el Pane correspondiente desde la vista para isValidSequence
                    Pane startPane = (Pane) column.getChildren().get(startIndex);
                    if (isValidSequence(cards, cards.get(startIndex).getCard())) {
                        for (int destCol = 0; destCol < 10; destCol++) {
                            if (destCol != col) {
                                VBox destColumn = columns.get(destCol); // Para compatibilidad
                                List<StackcardxcardDto> destCards = allStacks.get(destCol).getStackCardxCards();
                                StackcardxcardDto topCard = destCards.isEmpty() ? null : destCards.get(destCards.size() - 1);

                                boolean canMove;
                                if (destCards.isEmpty()) {
                                    canMove = true;
                                } else {
                                    if (topCard != null) {
                                        canMove = isValidColumnMove(destColumn, cards.get(startIndex).getCard().getType(), cards.get(startIndex).getCard().getNumber());
                                    } else {
                                        canMove = false;
                                    }
                                }
                                if (canMove) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            message.showModal(Alert.AlertType.ERROR, "Error al verificar movimientos", getStage(), "Algo falló al buscar jugadas: " + e.getMessage());
            return false;
        }
    }

    private Boolean isContinueGame() {
        timeLimit = game.getTime().intValue();
        int timeUsed = 0;
        if (game.getDifficulty() == null) {
            return false;
        } else {
            if (game.getDifficulty() == 3L) {
                timeUsed = (int) 1260L - timeLimit;
            } else if (game.getDifficulty() == 2L) {
                timeUsed = (int) 992L - timeLimit;
            } else {
                timeUsed = (int) 600L - timeLimit;
            }
        }
        if (timeUsed != 0 && timeUsed != 1260 && timeUsed != 992 && timeUsed != 600) {
            timeCalculate = timeUsed;
            lblTimer.setText(timerFormat(timeUsed));
            return true;
        }
        if (chargeUnfinishGames()) {
            for (StackcardxcardDto stackcardxcardDto : chargeUnfinishGameList) {
                if (stackcardxcardDto.getCard().getNumber() == 1L) {
                    String cardPath = ImagesUtil.getCardPath(player.getCardStyle() + "/", stackcardxcardDto.getCard().getNumber() + stackcardxcardDto.getCard().getType());
                    fillSuits = 7 - chargeUnfinishGameList.size();
                    for (int i = hBoxSuits.getChildren().size() - 1; i >= fillSuits; i--) {
                        ImageView suits = (ImageView) hBoxSuits.getChildren().get(i);
                        suits.setImage(new Image(cardPath));
                    }
                }
                return true;
            }
        }
        return false;
    }

    private Boolean isFullSuit(VBox from, Pane highCard) {
        int indexColumn = columns.indexOf(from);
        List<StackcardxcardDto> cards = allStacks.get(indexColumn).getStackCardxCards();

        if (cards.size() < 13) {
            return false;
        }

        int indexCard = cards.indexOf(allStackcardxcard.stream()
                .filter(stack -> stack.getId() == Integer.parseInt(highCard.getId()))
                .findFirst()
                .orElse(null));

        if (indexCard == -1 || indexCard > cards.size() - 13) {
            return false;
        }

        List<StackcardxcardDto> last13Cards = cards.subList(indexCard, indexCard + 13);
        String suitType = last13Cards.get(0).getCard().getType();
        Long expectedNumber = last13Cards.get(0).getCard().getNumber();

        if (expectedNumber != 13) {
            return false;
        }

        for (int i = 1; i < last13Cards.size(); i++) {
            String currentSuit = last13Cards.get(i).getCard().getType();
            Long currentNumber = last13Cards.get(i).getCard().getNumber();

            if (!currentSuit.equals(suitType) || currentNumber != expectedNumber - 1) {
                return false;
            }
            expectedNumber = currentNumber;
        }

        return expectedNumber == 1;
    }

    private Boolean isEndGame() {
        int empty = 0;
        if (cards.isEmpty()) {
            for (VBox column : columns) {
                if (column.getChildren().isEmpty()) {
                    empty++;
                }
            }
            if (empty == 10) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void highlightMove(Pane startCard, VBox destColumn) {
        try {
            DropShadow cardGlow = new DropShadow();
            cardGlow.setColor(Color.BLUE);
            cardGlow.setRadius(30.0);
            cardGlow.setSpread(0.5);
            startCard.setEffect(cardGlow);
            startCard.setStyle("-fx-border-color: blue; -fx-border-width: 3; -fx-border-radius: 5;");

            DropShadow columnGlow = new DropShadow();
            columnGlow.setColor(Color.GREEN);
            columnGlow.setRadius(30.0);
            columnGlow.setSpread(0.5);
            destColumn.setEffect(columnGlow);
            destColumn.setStyle("-fx-border-color: green; -fx-border-width: 3; -fx-border-radius: 5;");

            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // 3 segundos
            pause.setOnFinished(event -> {
                startCard.setStyle("");
                startCard.setEffect(null);
                destColumn.setStyle("");
                destColumn.setEffect(null);
            });
            pause.play();

        } catch (Exception e) {
            message.showModal(Alert.AlertType.ERROR, "Error al resaltar", getStage(), "No se pudo resaltar la pista: " + e.getMessage());
        }
    }

    private void suggestMove() {
        try {
            if (hasValidMoves()) {
                for (int col = 0; col < 10; col++) {
                    VBox column = columns.get(col); // ¿Qué cartas tiene esta columna según el modelo?
                    List<StackcardxcardDto> cards = allStacks.get(col).getStackCardxCards();
                    if (!cards.isEmpty() && cards.get(cards.size() - 1).getIsFaceUp()) {
                        // Buscamos la primera carta boca arriba como punto de partida
                        int startIndex = cards.size() - 1;
                        while (startIndex > 0 && cards.get(startIndex - 1).getIsFaceUp()) {
                            startIndex--; // ¿La de abajo también está boca arriba? Bajamos más
                        }
                        // ¿Es esta secuencia válida desde 'startIndex' hasta el final?
                        if (isValidSequence(column, (Pane) column.getChildren().get(startIndex))) {
                            // ¡Encontramos una secuencia! Ahora buscamos un destino
                            for (int destCol = 0; destCol < 10; destCol++) {
                                if (destCol != col) {
                                    VBox destColumn = columns.get(destCol);
                                    StackcardxcardDto topCard;
                                    if (destColumn.getChildren().isEmpty()) {
                                        topCard = null;
                                    } else {
                                        topCard = searchStackcardxcardDto((Pane) destColumn.getChildren().get(destColumn.getChildren().size() - 1));
                                    }
                                    // ¿Podemos mover la secuencia a esta columna?
                                    boolean canMove;
                                    if (destColumn.getChildren().isEmpty()) {
                                        canMove = true;
                                    } else if (topCard != null && isValidColumnMove(destColumn, cards.get(startIndex).getCard().getType(), cards.get(startIndex).getCard().getNumber())) {
                                        canMove = true;
                                    } else {
                                        canMove = false;
                                    }

                                    if (canMove) {
                                        message.showModal(Alert.AlertType.INFORMATION, "Pista", getStage(), "¡Mueve la secuencia desde la columna " + (col + 1) + " (posición " + startIndex + ") a la columna " + (destCol + 1) + "!");
                                        Pane startCard = (Pane) column.getChildren().get(startIndex);
                                        highlightMove(startCard, destColumn);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                message.showModal(Alert.AlertType.WARNING, "Sin movimientos", getStage(), "No hay movimientos válidos.");
            }
        } catch (Exception e) {
            message.showModal(Alert.AlertType.ERROR, "Error en pista", getStage(), "Algo salió mal al buscar pista: " + e.getMessage());
        }
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

    private void managementPoints() {
        for (int i = 0; i < achievementNotObtainedPoint.size(); i++) {
            if (UploadAchievement(achievementNotObtainedPoint.get(i), originalPoints, true)) {
                achievementNotObtainedPoint.remove(i);
            }
        }

        if (originalPoints == 0) {
            game.setHasWon("F");
            AnimationAndSound.looseSound();
            AnimationAndSound.looseTransition(root, () -> {
                onActionBtnBack(null);
            });
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
                    Collections.shuffle(cards, new SecureRandom());
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
        int sizeVbox = from.getChildren().size();
        if (sizeVbox > 13) {
            for (int i = 0; i < 13; i++) {
                if (sizeVbox - i > 0 && sizeVbox - i < cards.size()) {
                    Pane card = (Pane) cards.get(sizeVbox - i);
                    addCardCurrentColumn(from, card, true);
                    removeCardCurrentColumn(from, card);
                }
            }
        }
    }

    private void moveToFullSuit(ImageView fillSuitImage) {
        List<ImageView> suits = new ArrayList<>();
        for (Node node : hBoxSuits.getChildren()) {
            if (node instanceof ImageView) {
                suits.add((ImageView) node);
            }
        }
        for (int i = hBoxSuits.getChildren().size() - 1; i >= 0; i++) {
            if (i == fillSuits) {
                suits.get(i).setImage(fillSuitImage.getImage());
                fillSuits -= 1;
                originalPoints += 100;  //Da 100 por completar el palo
                lblPoints.setText("Puntuación: " + originalPoints);
                if(isEndGame()) {
                    game.setHasWon("T");
                    AnimationAndSound.winSound();
                    AnimationAndSound.winTransition(root, () -> { onActionBtnBack(null); });
                }
            }
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

    private List<Pane> ladderCards(Pane selected) {
        List<Pane> ladder = new ArrayList<>();
        VBox parent = (VBox) selected.getParent();
        for (int i = parent.getChildren().indexOf(selected); i < parent.getChildren().size(); i++) {
            ladder.add((Pane) parent.getChildren().get(i));
        }
        return ladder;
    }

    private void turnCards(VBox actualColumn) {
        if (actualColumn.getChildren().size() - 1 >= 0) {
            Pane toTurn = (Pane) actualColumn.getChildren().get(actualColumn.getChildren().size() - 1);
            StackcardxcardDto ubication = searchStackcardxcardDto(toTurn);
            CardDto cardDto = ubication.getCard();
            if (!ubication.getIsFaceUp()) {
                ubication.setIsFaceUp(true);
                String rute = ImagesUtil.getCardPath(player.getCardStyle() + "/", cardDto.getNumber() + cardDto.getType());
                Image cardImage = new Image(rute);
                ImageView card = (ImageView) toTurn.getChildren().get(0);
                AnimationAndSound.turnCardsAnimation(card, cardImage);
            }
        }
    }

    public void setupBoard() { //Alistará el tablero completo para el modo de juego
        Platform.runLater(() -> {
            for (int i = 0; i < 10; i++) {
                columns.get(i).getChildren().clear();   //Limpia las columnas en caso de que existan cartas previas
                for (StackcardxcardDto stackDtoxCard : allStacks.get(i).getStackCardxCards()) {
                    columns.get(i).getChildren().add(setupCard(stackDtoxCard));    //Da la carta en un pane para los VBox
                }
            }
        });
    }

    private Pane setupCard(StackcardxcardDto stackcardxcardDto) {   // Se encarga de generar automáticamente las cartas en columnas
        Boolean isFaceUp = stackcardxcardDto.getIsFaceUp();
        CardDto cardDto = stackcardxcardDto.getCard();
        allStackcardxcard.add(stackcardxcardDto);

        Pane space = new Pane();    // Configuración del espacio donde irá la carta
        DoubleProperty width = new SimpleDoubleProperty();
        width.bind(columns.get(0).widthProperty());

        DoubleProperty height = new SimpleDoubleProperty();
        height.bind(width.divide(3));

        space.prefWidthProperty().bind(width);  // Es para agarrar el ancho de un 
        space.prefHeightProperty().bind(height);

        ImageView card = new ImageView();   // Configuración de la imagen de la carta
        String cardPath;

        if (isFaceUp) {
            cardPath = ImagesUtil.getCardPath(player.getCardStyle() + "/", cardDto.getNumber() + cardDto.getType());
        } else {
            cardPath = ImagesUtil.getBackCardPath(setupStyle());
        }
        Image cardImage = new Image(cardPath);
        AnimationAndSound.turnCardsAnimation(card, cardImage);

        space.setId(String.valueOf(stackcardxcardDto.getId()));
        card.fitWidthProperty().bind(width);
        card.setPreserveRatio(true);
        space.getChildren().add(card);

        ImageView copyCard = new ImageView();   // Imagen duplicada para el arrastre de cartas
        ladderList.clear();

        space.setOnMousePressed(pressEvent -> { // Evento al hacer clic en la carta
            if (!isTimerStarted) {
                isTimerStarted = true;
                setupTimer();
                currentTime.play();
            } else {
                currentTime.play();
            }
            if (isValidSequence((VBox) space.getParent(), space)) {
                copyCard.setImage(card.getImage());
                copyCard.setFitWidth(card.getImage().getWidth() * 0.065);
                copyCard.setFitHeight(card.getImage().getHeight() * 0.065);
                copyCard.setOpacity(1.0);

                System.out.println("Entro en click");
                root.getChildren().add(copyCard);
                copyCard.setLayoutX(pressEvent.getSceneX() - copyCard.getFitWidth() / 2);
                copyCard.setLayoutY(pressEvent.getSceneY() - copyCard.getFitHeight() / 2);

                ladderList = ladderCards(space);
                for (Pane pane : ladderList) {
                    pane.setVisible(false);
                }

                space.setOnMouseDragged(dragEvent -> {  // Evento al arrastrar la carta
                    System.out.println("Hello drag");
                    copyCard.setLayoutX(dragEvent.getSceneX() - copyCard.getFitWidth() / 2);
                    copyCard.setLayoutY(dragEvent.getSceneY() - copyCard.getFitHeight() / 2);
                });

                space.setOnMouseReleased(releaseEvent -> {  // Evento al soltar la carta
                    System.out.println("Bye drag");
                    Point2D mousePosition = new Point2D(releaseEvent.getSceneX(), releaseEvent.getSceneY());
                    VBox newColumn = getColumn(mousePosition);  //Vbox llegada
                    VBox actualColumn = (VBox) space.getParent();   //Vbox salida

                    if (newColumn != null && enableCardMove(newColumn, space)) {
                        for (Pane pane : ladderList) {
                            removeCardCurrentColumn(actualColumn, pane);
                            addCardCurrentColumn(newColumn, pane, false);
                        }
                        turnCards(actualColumn);    //Voltea las cartas de espaldas
                        originalPoints -= 1;    // Quita -1pt para mantener los puntos al día
                        lblPoints.setText("Puntuación: " + originalPoints);
                        managementPoints();
                    } else if (isFullSuit(actualColumn, space)) {
                        Pane asPane = (Pane) actualColumn.getChildren().get(actualColumn.getChildren().size() -1);
                        ImageView spaceImage = (ImageView) asPane.getChildren().get(0);
                        moveToFullSuit(spaceImage);
                        deleteFullSuit(actualColumn);
                        turnCards(actualColumn);
                    } else {
                        AnimationAndSound.wrongActionSound();
                    }

                    root.getChildren().remove(copyCard);
                    for (Pane pane : ladderList) {
                        pane.setVisible(true);
                    }
                });

            } else {
                AnimationAndSound.wrongActionSound();
            }
        });

        return space;
    }

    public void removeCardCurrentColumn(VBox currentColumn, Pane pane) {
        int indexCurrentColumn = columns.indexOf(currentColumn);
        if (indexCurrentColumn != -1 && (allStacks != null && !allStacks.isEmpty())) {
            StackcardDto currentStackCard = allStacks.get(indexCurrentColumn);
            StackcardxcardDto card = searchStackcardxcardDto(pane);
            if (currentStackCard != null && card != null) {
                indexCurrentColumn = currentStackCard.getStackCardxCards().indexOf(card);
                currentColumn.getChildren().remove(pane);
                currentStackCard.getStackCardxCards().remove(indexCurrentColumn);
            }
        }
    }

    public void addCardCurrentColumn(VBox newColumn, Pane pane, Boolean isLastColumn) {
        int indexNewColumn = columns.indexOf(newColumn);
        if (indexNewColumn != -1 && (allStacks != null && !allStacks.isEmpty())) {
            if (isLastColumn) {
                indexNewColumn = 10;
            }
            StackcardDto newStackCard = allStacks.get(indexNewColumn);
            StackcardxcardDto card = searchStackcardxcardDto(pane);
            if (newStackCard != null && card != null) {
                newStackCard.getStackCardxCards().add(card);
                card.setStackCard(newStackCard);
                card.setPositionNumber(Long.valueOf(newColumn.getChildren().size() + 1));
                if (!isLastColumn) {
                    newColumn.getChildren().add(pane);
                }
            }
        }
    }

    private String setupStyle() {
        if ("noimagen".equals(player.getCardBackImageName())) {
            Long styleType = player.getCardStyle();
            if (styleType.equals(2L)) {
                return style + "M";
            } else if (styleType.equals(3L)) {
                return style + "V";
            } else {
                return style + "N";
            }
        } else {
            style = player.getCardBackImageName();
            return style;
        }
    }

    private String timerFormat(int totalSeconds) {
        return String.format("Tiempo: %02d:%02d", (totalSeconds / 60), (totalSeconds % 60));
    }

    private void setupTimer() {
        currentTime = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeCalculate++;
            lblTimer.setText(timerFormat(timeCalculate));

            for (int i = 0; i < achievementNotObtainedTime.size(); i++) {
                if (UploadAchievement(achievementNotObtainedTime.get(i), timeCalculate / 60, false)) {
                    achievementNotObtainedTime.remove(i);
                }

            }

            if (timeCalculate == timeLimit) {
                currentTime.stop();
                game.setHasWon("F");
                AnimationAndSound.looseSound();
                AnimationAndSound.looseTransition(root, () -> { onActionBtnBack(null); });
            }

        }));
        currentTime.setCycleCount(timeLimit);
    }

    private void setupBackground(String rute) { //Manipula la ruta que del fondo para adecuarla al anchorpane
        BackgroundImage backgroundImage = new BackgroundImage(new Image(rute),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        root.setBackground(new Background(backgroundImage));
    }

    private Boolean UploadAchievement(AchievementDto achievementDto, int amount, Boolean isPoints) {
        int amountAchievement = achievementDto.getAmount().intValue();
        if (achievementDto != null) {
            Respuesta answer = new Respuesta();
            if (!isPoints) {
                if (amountAchievement == amount) {
                    answer = achievementsService.addPlayerAchieveme(player, achievementDto.getName());
                    if (answer.getEstado() != null && answer.getEstado()) {
                        System.out.println("logro: " + achievementDto.getName());
                        AnimationAndSound.achievementSound();
                        return true;
                    }
                }
            } else {
                if (amountAchievement >= amount) {
                    answer = achievementsService.addPlayerAchieveme(player, achievementDto.getName());
                    if (answer.getEstado() != null && answer.getEstado()) {
                        System.out.println("logro: " + achievementDto.getName());
                        AnimationAndSound.achievementSound();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void loadAchievementNotObtained() {
        if (player != null && player.getId() != null) {
            Respuesta answerTime = achievementsService.getAchievemenSearchParameter("", "Tiempo", player.getId(), false);
            Respuesta answerPoints = achievementsService.getAchievemenSearchParameter("", "Puntos", player.getId(), false);
            Respuesta answerWins = achievementsService.getAchievemenSearchParameter("", "Ganar", player.getId(), false);
            Respuesta answerLose = achievementsService.getAchievemenSearchParameter("", "Perder", player.getId(), false);
            if ((answerTime != null && answerTime.getEstado()) && (answerPoints != null && answerPoints.getEstado())
                    && (answerWins != null && answerWins.getEstado()) && (answerLose != null && answerLose.getEstado())) {
                achievementNotObtainedTime = (List<AchievementDto>) answerTime.getResultado("Logro");
                achievementNotObtainedPoint = (List<AchievementDto>) answerPoints.getResultado("Logro");
                achievementNotObtainedWin = (List<AchievementDto>) answerWins.getResultado("Logro");
                achievementNotObtainedLose = (List<AchievementDto>) answerLose.getResultado("Logro");
            }
        }
    }

    public Boolean chargeUnfinishGames() {
        List<StackcardxcardDto> allStackcardxcardColumn = allStacks.get(10).getStackCardxCards();
        for (StackcardxcardDto stackcardxcardDto : allStackcardxcardColumn) {
            if (stackcardxcardDto.getCard().getNumber() == 1L) {
                chargeUnfinishGameList.add(stackcardxcardDto);
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        AnimationAndSound.startGameSound();
        loadGame();
        loadAchievementNotObtained();
        style = game.getDifficulty() + "";
        originalPoints = game.getScore().intValue();
        lblPoints.setText("Puntuación: " + originalPoints);
        if (AppContext.getInstance().get("Background") == null) {
            String rute = ImagesUtil.getBackground("GrassBackground");
            setupBackground(rute);
        } else {
            String rute = ImagesUtil.getBackground((String) AppContext.getInstance().get("Background"));
            setupBackground(rute);
        }
        String rute = ImagesUtil.getBackCardPath(setupStyle());
        mgvMaze.setImage(new Image(rute));
        if (!isContinueGame() && !isTimerStarted) {
            lblTimer.setText("Tiempo: 00:00");
        }
        columns.clear();
        for (Node node : hBxBoard.getChildren()) {
            if (node instanceof VBox) {
                columns.add((VBox) node);
            }
        }
    }

}