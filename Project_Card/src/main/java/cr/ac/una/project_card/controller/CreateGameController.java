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
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/** * FXML Controller class * * @author ashly */
public class CreateGameController extends Controller implements Initializable {

    private ImagesUtil imageUtility = new ImagesUtil();
    private CardView easyModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView mediumModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);
    private CardView hardModeCard = new CardView("temporaryIshakan", "temporaryIshakan", imageUtility);

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
    @FXML
    private Button btnEasyMode;
    @FXML
    private Button btnMediumMode;
    @FXML
    private Button btnHardMode;

    @FXML
    private void onKeyPressed(KeyEvent event) {
    }

    @FXML
    private void onActionBtnEasyMode(ActionEvent event) {
    }

    @FXML
    private void onActionBtnMediumMode(ActionEvent event) {
    }

    @FXML
    private void onActionBtnHardMode(ActionEvent event) {
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
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
        }
    }

    private void initialConditionsCards() {
        mgvEasyMode.setImage(new Image(imageUtility.getCardPath(easyModeCard.getBackImagePath())));
        mgvMediumMode.setImage(new Image(imageUtility.getCardPath(mediumModeCard.getBackImagePath())));
        mgvHardMode.setImage(new Image(imageUtility.getCardPath(hardModeCard.getBackImagePath())));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //initialConditionsCards();
       //setupCardInteractions();
    }
    
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
}