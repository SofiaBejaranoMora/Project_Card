package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

            // Configurar tamaño como los ImageView originales
            setFitWidth(150);
            setFitHeight(200);
            setPreserveRatio(true);

            // Cargar imagen inicial (espalda)
            updateImage();

            // Configurar animación y brillo
            setupHoverEffect();
        }

        private void updateImage() {
            String imagePath = isFlipped ? frontImagePath : backImagePath;
            String url = imageUtility.getCardImagePath(imagePath);
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
                if (rotateTransition.getStatus() == Animation.Status.RUNNING) return;

                setEffect(glow);

                rotateTransition.setToAngle(isFlipped ? 0 : 180);
                rotateTransition.setOnFinished(e -> {
                    isFlipped = !isFlipped;
                    updateImage();
                });
                rotateTransition.play();
            });

            setOnMouseExited(event -> {
                setEffect(null);
            });
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
    private CardView easyModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView mediumModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView hardModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureHBox();
        initialConditionsCards();
        setupCardInteractions();
    }

    private void configureHBox() {
        // Obtener el HBox que contiene las cartas
        HBox cardHBox = (HBox) mgvEasyMode.getParent();
        if (cardHBox != null) {
            // Centrar las cartas
            cardHBox.setAlignment(Pos.CENTER);
            // Asegurar que el HBox use todo el espacio disponible
            cardHBox.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(cardHBox, Priority.ALWAYS);

            // Espaciado dinámico basado en el ancho del HBox
            cardHBox.widthProperty().addListener((obs, old, newVal) -> {
                double totalWidth = newVal.doubleValue();
                double cardWidth = 150 * 3; // 3 cartas de 150px
                double spacing = (totalWidth - cardWidth) / 4; // Espaciado entre y alrededor
                cardHBox.setSpacing(Math.max(20, spacing)); // Mínimo 20px
            });
        }
    }

    private void initialConditionsCards() {
        // Configurar imágenes iniciales (espalda)
        String easyBackUrl = imageUtility.getCardImagePath(easyModeCard.getBackImagePath());
        String mediumBackUrl = imageUtility.getCardImagePath(mediumModeCard.getBackImagePath());
        String hardBackUrl = imageUtility.getCardImagePath(hardModeCard.getBackImagePath());

        if (easyBackUrl != null) {
            mgvEasyMode.setImage(new Image(easyBackUrl));
        }
        if (mediumBackUrl != null) {
            mgvMediumMode.setImage(new Image(mediumBackUrl));
        }
        if (hardBackUrl != null) {
            mgvHardMode.setImage(new Image(hardBackUrl));
        }
    }

    private void setupCardInteractions() {
        // Reemplazar ImageView con CardView en el HBox
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
            // Copiar propiedades del ImageView original, sin márgenes
            card.setFitWidth(imageView.getFitWidth());
            card.setFitHeight(imageView.getFitHeight());
            card.setPreserveRatio(imageView.isPreserveRatio());
        }
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        // Implementar si necesitas
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goViewInStage("MenuView", (Stage) btnBack.getScene().getWindow());
    }
}