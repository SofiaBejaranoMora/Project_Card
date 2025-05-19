package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
import cr.ac.una.project_card.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ashly
 */
public class CreateGameController extends Controller implements Initializable {

    @Override
    public void initialize() {
    }

    public class CardView extends ImageView {
        private boolean isFlipped = false;
        private String frontImagePath;
        private String backImagePath;
        private ImagesUtil imageUtility;

        public CardView(String frontImagePath, String backImagePath, ImagesUtil imageUtility) {
            this.frontImagePath = frontImagePath;
            this.backImagePath = backImagePath;
            this.imageUtility = imageUtility;

            setFitWidth(150);
            setFitHeight(200);
            setPreserveRatio(true);

            updateImage();
            setupHoverEffect();
        }

        private void updateImage() {
            String imagePath = isFlipped ? frontImagePath : backImagePath;
            String url = imageUtility.getCardPath(imagePath);
            if (url != null) {
                setImage(new Image(url));
            } else {
                System.err.println("No se pudo cargar la imagen para: " + imagePath);
            }
        }

        private void setupHoverEffect() {
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), this);
            rotateTransition.setAxis(Rotate.Y_AXIS);

            DropShadow glow = new DropShadow();
            glow.setColor(Color.YELLOW);
            glow.setRadius(10);
            glow.setSpread(0.3);

            setOnMouseEntered(event -> {
                setEffect(glow);
                if (rotateTransition.getStatus() == Animation.Status.RUNNING) {
                    return;
                }
                rotateTransition.setToAngle(isFlipped ? 0 : 180);
                rotateTransition.setOnFinished(e -> {
                    isFlipped = !isFlipped;
                    updateImage();
                });
                rotateTransition.play();
            });

            setOnMouseExited(event -> setEffect(null));
        }

        public String getBackImagePath() {
            return backImagePath;
        }

        public String getFrontImagePath() {
            return frontImagePath;
        }

        public boolean isFlipped() {
            return isFlipped;
        }
    }

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

    private ImagesUtil imageUtility = new ImagesUtil();
    private Mensaje messageUtil = new Mensaje();
    private CardView easyModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView mediumModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView hardModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);

    private String nombrePartida;
    private String difficulty;
    private Set<String> existingGames = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureHBox();
        initialConditionsCards();
        setupCardInteractions();
        selectDifficulty();
    }

    private void configureHBox() {
        HBox cardHBox = (HBox) mgvEasyMode.getParent();
        if (cardHBox != null) {
            cardHBox.setAlignment(Pos.CENTER);
            cardHBox.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(cardHBox, Priority.ALWAYS);

            cardHBox.widthProperty().addListener((obs, old, newVal) -> {
                double totalWidth = newVal.doubleValue();
                double cardWidth = 150 * 3;
                double spacing = (totalWidth - cardWidth) / 4;
                cardHBox.setSpacing(Math.max(20, spacing));
            });
        }
    }

    private void initialConditionsCards() {
        String easyBackUrl = imageUtility.getCardPath(easyModeCard.getBackImagePath());
        String mediumBackUrl = imageUtility.getCardPath(mediumModeCard.getBackImagePath());
        String hardBackUrl = imageUtility.getCardPath(hardModeCard.getBackImagePath());

        if (easyBackUrl != null) mgvEasyMode.setImage(new Image(easyBackUrl));
        if (mediumBackUrl != null) mgvMediumMode.setImage(new Image(mediumBackUrl));
        if (hardBackUrl != null) mgvHardMode.setImage(new Image(hardBackUrl));
    }

    private void setupCardInteractions() {
        replaceImageViewWithCard(mgvEasyMode, easyModeCard);
        replaceImageViewWithCard(mgvMediumMode, mediumModeCard);
        replaceImageViewWithCard(mgvHardMode, hardModeCard);
    }

    private void replaceImageViewWithCard(ImageView imageView, CardView card) {
        HBox parent = (HBox) imageView.getParent();
        if (parent != null) {
            int index = parent.getChildren().indexOf(imageView);
            parent.getChildren().remove(imageView);
            parent.getChildren().add(index, card);
            card.setFitWidth(imageView.getFitWidth());
            card.setFitHeight(imageView.getFitHeight());
            card.setPreserveRatio(imageView.isPreserveRatio());
        }
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            nombrePartida = txfNameGame.getText().trim();
            if (nombrePartida.isEmpty()) {
                messageUtil.show(Alert.AlertType.WARNING, "Nombre Partida", "Por favor, ingresa un nombre antes de seleccionar la dificultad.");
            } else {
                if (isNameValid()) {
                    messageUtil.show(Alert.AlertType.INFORMATION, "Dificultad", "Ahora selecciona una carta con un clic para elegir la dificultad.");
                } else {
                    messageUtil.show(Alert.AlertType.WARNING, "Nombre Partida Inválido", "El nombre de partida ya ha sido seleccionado, intenta de nuevo.");
                    txfNameGame.clear();
                }
            }
        }
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goViewInStage("MenuView", (Stage) btnBack.getScene().getWindow());
    }

    private boolean isNameValid() {
        if (existingGames.contains(nombrePartida)) {
            return false;
        }
        existingGames.add(nombrePartida); 
        return true;
    }

    private void asignarDificultad(String dificultad) {
        difficulty = dificultad;
        if (nombrePartida == null || nombrePartida.isEmpty()) {
            messageUtil.show(Alert.AlertType.WARNING, "Nombre Partida", "Por favor, escribe un nombre de partida antes de seleccionar dificultad.");
        } else if (difficulty != null) {
            messageUtil.show(Alert.AlertType.CONFIRMATION, "Creando partida", "Creando partida " + nombrePartida + " con dificultad " + difficulty);
            goToGameView();
        }
    }

    private void selectDifficulty() {
        easyModeCard.setOnMouseClicked(event -> asignarDificultad("easy"));
        mediumModeCard.setOnMouseClicked(event -> asignarDificultad("medium"));
        hardModeCard.setOnMouseClicked(event -> asignarDificultad("hard"));
    }

    private void goToGameView() {
        FlowController.getInstance().goViewInStage("GameView", (Stage) txfNameGame.getScene().getWindow());
    }
}