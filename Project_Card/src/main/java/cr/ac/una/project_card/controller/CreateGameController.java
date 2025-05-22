package cr.ac.una.project_card.controller;

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
    private MFXButton btnStartGame;

    private String nameGame;
    private String difficulty;
    private Set<String> existingGames = new HashSet<>();

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String nameGame = txfNameGame.getText().trim();
            nameGame = txfNameGame.getText().trim();
            if (nameGame.isEmpty()) {
                message.show(Alert.AlertType.WARNING, "Error", "Por favor, ingresa un nombre de partida.");
            } else if (!isNameValid()) {
                message.show(Alert.AlertType.WARNING, "Nombre Inválido", "El nombre de partida ya ha sido seleccionado, intenta de nuevo.");
                txfNameGame.clear();
            }
        }
    }

    @FXML
    private void onActionBtnEasyMode(ActionEvent event) {
        asignarDificultad("easy");
    }

    @FXML
    private void onActionBtnMediumMode(ActionEvent event) {
        asignarDificultad("medium");
    }

    @FXML
    private void onActionBtnHardMode(ActionEvent event) {
        asignarDificultad("hard");
    }

    @FXML
    private void onActionBtnStartGame(ActionEvent event) {
        validatingDataBeforeStart();
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
        existingGames.add(nameGame);
        return true;
    }

    private void asignarDificultad(String dificultad) {
        difficulty = dificultad;
        nameGame = txfNameGame.getText().trim();
        if (nameGame.isEmpty()) {
            message.show(Alert.AlertType.WARNING, "Error", "Por favor, ingresa un nombre de partida antes de seleccionar dificultad.");
            difficulty = null; // No asignar hasta que haya nombre
            btnStartGame.setDisable(true);
        } else {
            message.show(Alert.AlertType.INFORMATION, "Dificultad seleccionada", "Dificultad " + difficulty + " asignada. Usa 'Iniciar' para comenzar.");
            btnStartGame.setDisable(false);
        }
    }

    private void validatingDataBeforeStart() {
        nameGame = txfNameGame.getText().trim();
        if (nameGame.isEmpty() && difficulty == null) {
            message.show(Alert.AlertType.WARNING, "Error", "Por favor, ingresa un nombre de partida y selecciona una dificultad.");
        } else if (nameGame.isEmpty()) {
            message.show(Alert.AlertType.WARNING, "Error", "Por favor, ingresa un nombre de partida.");
        } else if (difficulty == null) {
            message.show(Alert.AlertType.WARNING, "Error", "Por favor, selecciona una dificultad.");
        } else if (!isNameValid()) {
            message.show(Alert.AlertType.WARNING, "Nombre Inválido", "El nombre de partida ya ha sido seleccionado, intenta de nuevo.");
            txfNameGame.clear();
            difficulty = null;
            btnStartGame.setDisable(true); //Mal posicionando
            List<CardView> allCards = List.of(easyModeCard, mediumModeCard, hardModeCard);
            for (CardView card : allCards) {
                if (card.getIsSelected()) {
                    card.setSelected(false);
                    if (card.isFlipped()) {
                        card.setIsFlipped(false);
                        updateCardImage(getImageViewByCard(card), card, false);
                    }
                    getButtonByCard(card).setEffect(null);
                }
            }
        } else {
            message.show(Alert.AlertType.CONFIRMATION, "Creando partida", "Creando partida " + nameGame + " con dificultad " + difficulty);
            return;
        }
    }

    private void setupCardInteractions() {
        List<CardView> allCards = List.of(easyModeCard, mediumModeCard, hardModeCard);
        setupHoverEffect(btnEasyMode, mgvEasyMode, easyModeCard, allCards);
        setupHoverEffect(btnMediumMode, mgvMediumMode, mediumModeCard, allCards);
        setupHoverEffect(btnHardMode, mgvHardMode, hardModeCard, allCards);
    }

    private void updateCardImage(ImageView imageView, CardView card, boolean showFront) {
        String imagePath = showFront ? card.getFrontImagePath() : card.getBackImagePath();
        String url = imageUtility.getCardPath(imagePath);
        if (url != null) {
            imageView.setImage(new Image(url));
        } else {
            System.err.println("No se pudo cargar la imagen para: " + imagePath);
        }
    }

    private void setupHoverEffect(Button button, ImageView imageView, CardView card, List<CardView> allCards) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), imageView);
        rotateTransition.setAxis(Rotate.Y_AXIS);

        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(10);
        glow.setSpread(0.3);

        // Efecto al pasar el mouse, solo si la carta NO está seleccionada
        button.setOnMouseEntered(event -> {
            if (!card.getIsSelected() && rotateTransition.getStatus() != Animation.Status.RUNNING) {
                button.setEffect(glow);
                rotateTransition.setToAngle(card.isFlipped() ? 0 : 180);
                rotateTransition.setOnFinished(e -> {
                    card.toggleFlipped();
                    updateCardImage(imageView, card, card.isFlipped());
                });
                rotateTransition.play();
            }
        });

        // Quitar el efecto solo si la carta NO está seleccionada
        button.setOnMouseExited(event -> {
            if (!card.getIsSelected()) {
                button.setEffect(null);
            }
        });

        // Evento de clic para selección/deselección
        button.setOnMouseClicked(event -> {
            if (card.getIsSelected()) {
                // Deseleccionar la carta actual
                card.setSelected(false);
                if (card.isFlipped()) {
                    card.toggleFlipped();
                    updateCardImage(imageView, card, false);
                }
                button.setEffect(null);
                difficulty = null;
                btnStartGame.setDisable(true);
            } else {
                // Deseleccionar todas las demás cartas
                for (CardView otherCard : allCards) {
                    if (otherCard != card && otherCard.getIsSelected()) {
                        otherCard.setSelected(false);
                        if (otherCard.isFlipped()) {
                            otherCard.toggleFlipped();
                            updateCardImage(getImageViewByCard(otherCard), otherCard, false);
                        }
                        getButtonByCard(otherCard).setEffect(null);
                    }
                }

                // Seleccionar la nueva carta y mantenerla volteada y con brillo
                card.setSelected(true);
                if (!card.isFlipped()) {
                    card.toggleFlipped();
                    updateCardImage(imageView, card, true);
                }
                button.setEffect(glow); // Mantener brillo permanentemente
                asignarDificultad(card == easyModeCard ? "easy" : card == mediumModeCard ? "medium" : "hard");
            }
        });
    }
    
    private void initialConditionsCards() {
        mgvEasyMode.setImage(new Image(imageUtility.getCardPath(easyModeCard.getBackImagePath())));
        mgvMediumMode.setImage(new Image(imageUtility.getCardPath(mediumModeCard.getBackImagePath())));
        mgvHardMode.setImage(new Image(imageUtility.getCardPath(hardModeCard.getBackImagePath())));
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialConditionsCards();
        setupCardInteractions();
    }

    @Override
    public void initialize() {
    }
    
    public class CardView {
        private boolean isFlipped = false;
        private boolean isSelected;
        private String frontImagePath;
        private String backImagePath;
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
    }
}