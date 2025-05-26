package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.Card;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
import cr.ac.una.project_card.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/** * FXML Controller class * * @author ashly */
public class CreateGameController extends Controller implements Initializable {

    private ImagesUtil imageUtility = new ImagesUtil();
    private Mensaje message = new Mensaje();
    private CardView easyModeCard = new CardView("1", "4", imageUtility);
    private CardView mediumModeCard = new CardView("2", "4", imageUtility);
    private CardView hardModeCard = new CardView("2", "4", imageUtility);
    private Set<String> existingGames = new HashSet<>();
    private String nameGame;
    private String difficulty;

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
    private MFXButton btnStartGame;

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            nameGame = txfNameGame.getText().trim();
            validatingDataBeforeStart();
        }
    }

    @FXML
    private void onActionBtnEasyMode(ActionEvent event) {
        signDifficulty("easy");
    }

    @FXML
    private void onActionBtnMediumMode(ActionEvent event) {
        signDifficulty("medium");
    }

    @FXML
    private void onActionBtnHardMode(ActionEvent event) {
        signDifficulty("hard");
    }

    @FXML
    private void onActionBtnStartGame(ActionEvent event) {
        if(validatingDataBeforeStart()){
            existingGames.add(nameGame);
        }
        
        FlowController.getInstance().goView("GameView"); //El onAction lleva las vistas las operaciónes previas son las que debe pasar acá
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }

    private boolean isNameValid() {
        if (existingGames.contains(nameGame)) {
            return false;
        }
        return true;
    }

    private void signDifficulty(String dificultad) {
        difficulty = dificultad;
        nameGame = txfNameGame.getText().trim();
        if (nameGame.isEmpty()) {
            message.show(Alert.AlertType.INFORMATION, "Dificultad seleccionada", "Dificultad " + difficulty + "  asignada. Dale un nombre a la partida.");
        } else {
            message.show(Alert.AlertType.INFORMATION, "Dificultad seleccionada", "Dificultad " + difficulty + " asignada. Usa 'Empezar' para comenzar el juego.");
            btnStartGame.setVisible(true);
        }
    }
    
    private void predeterminedValues(){
        txfNameGame.clear();
        difficulty = null;
        List<CardView> allCards = List.of(easyModeCard, mediumModeCard, hardModeCard);
        for (CardView card : allCards) {
            if (card.getIsSelected()) {
                card.setSelected(false);
                if (card.isFlipped()) {
                    card.setIsFlipped(false);
                    card.updateImageView(getImageViewByCard(card));
                }
                getButtonByCard(card).setEffect(null);
            }
        }
    }
    
    private boolean validatingDataBeforeStart(){
        if (nameGame.isEmpty() && difficulty == null) {
            message.show(Alert.AlertType.WARNING, "Campos vacios", "Por favor, ingresa un nombre de partida y selecciona una dificultad.");
            return false;
        } else if (nameGame.isEmpty()) {
            message.show(Alert.AlertType.WARNING, "Nombre de partida", "Por favor, ingresa un nombre de partida.");
            return false;
        } else if (difficulty == null) {
            message.show(Alert.AlertType.WARNING, "Dificultad", "Por favor, selecciona una dificultad.");
            return false;
        } else if (!isNameValid()) {
            message.show(Alert.AlertType.WARNING, "Nombre Inválido", "El nombre de partida ya ha sido seleccionado, intenta de nuevo.");
            predeterminedValues();//reestablece los valores
            return false;
        }
      return true;
    }
  
    private void setupCardInteractions() {
        List<CardView> allCards = List.of(easyModeCard, mediumModeCard, hardModeCard);
        setupCardEffects(btnEasyMode, mgvEasyMode, easyModeCard, allCards);
        setupCardEffects(btnMediumMode, mgvMediumMode, mediumModeCard, allCards);
        setupCardEffects(btnHardMode, mgvHardMode, hardModeCard, allCards);
    }

    private void initialConditionsCards() {
        mgvEasyMode.setImage(new Image(imageUtility.getCardDifficultPath(easyModeCard.getBackImagePath())));
        mgvMediumMode.setImage(new Image(imageUtility.getCardDifficultPath(mediumModeCard.getBackImagePath())));
        mgvHardMode.setImage(new Image(imageUtility.getCardDifficultPath(hardModeCard.getBackImagePath())));
        mgvEasyMode.setRotate(180);
        mgvMediumMode.setRotate(180);
        mgvHardMode.setRotate(180);
      
    }

    private ImageView getImageViewByCard(CardView card) {
        if (card == easyModeCard) return mgvEasyMode;
        if (card == mediumModeCard) return mgvMediumMode;
        if (card == hardModeCard) return mgvHardMode;
        return null;
    }

    private Button getButtonByCard(CardView card) {
        if (card == easyModeCard) return btnEasyMode;
        if (card == mediumModeCard) return btnMediumMode;
        if (card == hardModeCard) return btnHardMode;
        return null;
    }
    
    private void applyGlowEffect(Button button, boolean enable) {
        if (enable) {
            DropShadow glow = new DropShadow();
            glow.setColor(Color.YELLOW);
            glow.setRadius(10);
            glow.setSpread(0.3);
            button.setEffect(glow); // Aplica el brillo
        } else {
            button.setEffect(null); // Elimina el brillo si no está activado
        }
    }
    
    private void rollCard(Button actualButton, ImageView imageView, CardView card, List<CardView> allCards) {
        if (card.rotateTransition == null) {
            card.initializeRotation(imageView);
        }

        // Verifica si la animación está corriendo antes de iniciar una nueva
        if (card.rotateTransition.getStatus() != Animation.Status.RUNNING) {
            card.rotateTransition.setToAngle(card.isFlipped() ? 180 : 0);
            card.rotateTransition.play();
        }
    }

    private void setupCardEffects(Button button, ImageView imageView, CardView card, List<CardView> allCards) {
        // Rotar la carta al pasar el mouse, solo si no está seleccionada
        button.setOnMouseEntered(event -> {
            if (!card.getIsSelected()) {
                rollCard(button, imageView, card, allCards);
                applyGlowEffect(button, true);
            }
        });

        // Quitar brillo al salir del hover si la carta NO está seleccionada
        button.setOnMouseExited(event -> {
            if (!card.getIsSelected()) {
                applyGlowEffect(button, false);
            }
        });

        // Evento de clic para selección/deselección de cartas
        button.setOnMouseClicked(event -> {
            if (card.getIsSelected()) {
                // Deseleccionar la carta actual
                card.setSelected(false);
                if (card.isFlipped()) {
                    card.toggleFlipped();
                    card.updateImageView(imageView);
                }
                applyGlowEffect(button, false);
                difficulty = null;
            } else {
                // Deseleccionar todas las demás cartas antes de seleccionar la nueva
                for (CardView otherCard : allCards) {
                    if (otherCard != card && otherCard.getIsSelected()) {
                        otherCard.setSelected(false);
                        if (otherCard.isFlipped()) {
                            otherCard.toggleFlipped();
                            otherCard.updateImageView(getImageViewByCard(otherCard));
                        }
                        applyGlowEffect(getButtonByCard(otherCard), false);
                    }
                }

                // Seleccionar la nueva carta y aplicar efectos visuales
                card.setSelected(true);
                if (!card.isFlipped()) {
                    card.toggleFlipped();
                    card.updateImageView(imageView);
                }
                applyGlowEffect(button, true); // Aplicar brillo

                // Definir la dificultad según la carta seleccionada
                signDifficulty(
                        card == easyModeCard ? "easy"
                                : card == mediumModeCard ? "medium"
                                        : "hard"
                );
            }
        });
    }
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialConditionsCards();
        setupCardInteractions();
    }

    @Override
    public void initialize() {
        initialConditionsCards();
        setupCardInteractions();
    }
    
    public class CardView {
        private boolean isFlipped = false;
        private boolean isSelected;
        private String frontImagePath;
        private String backImagePath;
        private RotateTransition rotateTransition;
        private ImagesUtil imageUtility;

        public CardView(String frontImagePath, String backImagePath, ImagesUtil imageUtility) {
            this.frontImagePath = frontImagePath;
            this.backImagePath = backImagePath;
            this.imageUtility = imageUtility;
            this.isSelected = false;
            this.isFlipped = false;
        }

        public void setIsFlipped(boolean isFlipped) {
            this.isFlipped = isFlipped;
        }

        public boolean isFlipped() {
            return isFlipped;
        }

        public String getBackImagePath() {
            return backImagePath;
        }

        public String getFrontImagePath() {
            return frontImagePath;
        }

        public boolean getIsSelected() {
            return this.isSelected;
        }

        public void setSelected(boolean selected) {
            this.isSelected = selected;
        }
        
        public void toggleFlipped() {
            isFlipped = !isFlipped;
        }
        public void updateImageView(ImageView imageView) {
            String imagePath = isFlipped ? frontImagePath : backImagePath;
            String url = imageUtility.getCardDifficultPath(imagePath);

            if (url != null) {
                imageView.setImage(new Image(url));
            } else {
                System.err.println("No se pudo cargar la imagen para: " + imagePath);
            }
        }
        
        public void initializeRotation(ImageView imageView) {
            rotateTransition = new RotateTransition(Duration.millis(500), imageView);
            rotateTransition.setAxis(Rotate.Y_AXIS);
            rotateTransition.setToAngle(isFlipped ? 180 : 0);
            rotateTransition.setOnFinished(e -> {
                toggleFlipped();
                updateImageView(imageView); // Actualiza la imagen directamente
            });
        }
    }
}