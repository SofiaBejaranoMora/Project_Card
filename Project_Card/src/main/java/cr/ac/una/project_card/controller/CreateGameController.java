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
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    public class CardView extends VBox {
        private boolean isFlipped = false;
        private String frontImagePath;
        private String backImagePath;
        private ImageView imageView;
        private ImagesUtil imageUtility;

        public CardView(String frontImagePath, String backImagePath, ImagesUtil imageUtility) {
            this.frontImagePath = frontImagePath;
            this.backImagePath = backImagePath;
            this.imageUtility = imageUtility;

            // Configurar VBox para que se comporte como ImageView
            setMaxSize(150, 200);
            setMinSize(150, 200);

            // Crear ImageView
            imageView = new ImageView();
            imageView.setFitWidth(150);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            getChildren().add(imageView);

            // Cargar imagen inicial (espalda)
            updateImage();

            // Configurar animación y brillo
            setupHoverEffect();
        }

        private void updateImage() {
            String imagePath = isFlipped ? frontImagePath : backImagePath;
            imageView.setImage(new Image(imageUtility.getCardImagePath(imagePath)));
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

            // Mantener márgenes del ImageView original
            HBox.setMargin(this, HBox.getMargin(imageView));
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

    private ImagesUtil imageUtility = new ImagesUtil(); // Ajusta según el constructor de ImagesUtil
    private CardView easyModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView mediumModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView hardModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialConditionsCards();
        setupCardInteractions();
    }

    private void initialConditionsCards() {
        // Configurar imágenes iniciales (espalda)
        mgvEasyMode.setImage(new Image(imageUtility.getCardImagePath(easyModeCard.getBackImagePath())));
        mgvMediumMode.setImage(new Image(imageUtility.getCardImagePath(mediumModeCard.getBackImagePath())));
        mgvHardMode.setImage(new Image(imageUtility.getCardImagePath(hardModeCard.getBackImagePath())));
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