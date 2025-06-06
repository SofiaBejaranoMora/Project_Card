package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.CardDto;
import cr.ac.una.project_card.model.Game;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.model.StackcardDto;
import cr.ac.una.project_card.model.StackcardxcardDto;
import cr.ac.una.project_card.service.CardService;
import cr.ac.una.project_card.service.GameService;
import cr.ac.una.project_card.service.PlayerService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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

/**
 * FXML Controller class
 *
 * @author ashly
 */
public class CreateGameController extends Controller implements Initializable {

    private ImagesUtil imageUtility = new ImagesUtil();
    private Mensaje message = new Mensaje();
    private PlayerDto player;
    private String style;
    private String easyCardBack;
    private String mediumCardBack;
    private String hardCardBack;
    private CardView easyModeCard = new CardView("1", easyCardBack, imageUtility);
    private CardView mediumModeCard = new CardView("2", mediumCardBack, imageUtility);
    private CardView hardModeCard = new CardView("3", hardCardBack, imageUtility);
    private List<GameDto> existingGames = new ArrayList();
    private String nameGame;
    private Long difficulty;
    private Boolean lastNameValid = true;
    
    private CardService cardService = new CardService();
    private List<CardDto> cards = new ArrayList<>(); // Mazo completo
    private List<CardDto> corazones = new ArrayList<>();
    private List<CardDto> picas = new ArrayList<>();
    private List<CardDto> treboles = new ArrayList<>();
    private List<CardDto> diamantes = new ArrayList<>();
    private StackcardDto stackcardList1 = new StackcardDto();
    private StackcardDto stackcardList2 = new StackcardDto();
    private StackcardDto stackcardList3 = new StackcardDto();
    private StackcardDto stackcardList4 = new StackcardDto();
    private StackcardDto stackcardList5 = new StackcardDto();
    private StackcardDto stackcardList6 = new StackcardDto();
    private StackcardDto stackcardList7 = new StackcardDto();
    private StackcardDto stackcardList8 = new StackcardDto();
    private StackcardDto stackcardList9 = new StackcardDto();
    private StackcardDto stackcardList10 = new StackcardDto();

    @FXML
    private Button btnEasyMode;
    @FXML
    private Button btnMediumMode;
    @FXML
    private Button btnHardMode;
    @FXML
    private MFXTextField txfNameGame;
    @FXML
    private ImageView mgvEasyMode;
    @FXML
    private ImageView mgvMediumMode;
    @FXML
    private ImageView mgvHardMode;
    @FXML
    private MFXButton btnStartGame;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnHowToPlay;

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            nameGame = txfNameGame.getText().trim();
            updateStartButtonVisibility();
            validatingDataBeforeStart();
        }
    }

    @FXML
    private void onActionBtnEasyMode(ActionEvent event) {
        player.setCardBackImageName(easyCardBack);
        signDifficulty(1L);
    }

    @FXML
    private void onActionBtnMediumMode(ActionEvent event) {
        player.setCardBackImageName(mediumCardBack);
        signDifficulty(2L);
    }

    @FXML
    private void onActionBtnHardMode(ActionEvent event) {
        player.setCardBackImageName(hardCardBack);
        signDifficulty(3L);
    }

    @FXML
    private void onActionBtnStartGame(ActionEvent event) {
        if (validatingDataBeforeStart()) {
            Long time = 600L;
            GameService gameService = new GameService();
            if (difficulty == 2L) {
                time = 992L;
            } else if (difficulty == 3L) {
                time = 1260L;
            }

            GameDto gameDto = new GameDto(nameGame, difficulty, time);
            prepareGame(gameDto);
            Respuesta answer = gameService.SaveGame(gameDto, player);
            if (answer.getEstado()) {
                gameDto = (GameDto) answer.getResultado("Partida");
                Long dificultad = gameDto.getDifficulty();
                PlayerService playerService = new PlayerService();
                answer = playerService.getPlayerName(player.getName());
                if (answer != null && answer.getEstado()) {
                    this.player = (PlayerDto) answer.getResultado("Jugador");
                    AppContext.getInstance().set("CurrentUser", player);
                    AppContext.getInstance().set("IdCurrentGame", gameDto.getId());
                    FlowController.getInstance().goView("GameView");
                }
            } else {
                message.showModal(Alert.AlertType.ERROR, "Guardar Jugador", getStage(), answer.getMensaje());
            }
        }
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnHowToPlay(ActionEvent event) {
        //POR IMPLEMENTAR, AGREGAR LAS INSTRUCCIONES
    }

    private Boolean validatingDataBeforeStart() {
        nameGame = txfNameGame.getText().trim();
        if (nameGame.isEmpty() && difficulty == null) {
            message.show(Alert.AlertType.WARNING, "Campos requeridos", "Por favor, ingresa un nombre para la partida y selecciona una dificultad.");
            return false;
        } else if (nameGame.isEmpty()) {
            message.show(Alert.AlertType.WARNING, "Nombre requerido", "Por favor, ingresa un nombre para la partida.");
            return false;
        } else if (difficulty == null) {
            message.show(Alert.AlertType.WARNING, "Dificultad requerida", "Por favor, selecciona una dificultad.");
            return false;
        }
        return true;
    }

    private ImageView getImageViewByCard(CardView card) {
        if (card == easyModeCard) {
            return mgvEasyMode;
        }
        if (card == mediumModeCard) {
            return mgvMediumMode;
        }
        if (card == hardModeCard) {
            return mgvHardMode;
        }
        return null;
    }

    private Button getButtonByCard(CardView card) {
        if (card == easyModeCard) {
            return btnEasyMode;
        }
        if (card == mediumModeCard) {
            return btnMediumMode;
        }
        if (card == hardModeCard) {
            return btnHardMode;
        }
        return null;
    }

    private CardView getCardByDifficulty(Long difficulty) {
        switch (difficulty.intValue()) {
            case 1:
                return easyModeCard;
            case 2:
                return mediumModeCard;
            case 3:
                return hardModeCard;
            default:
                return null;
        }
    }

    private void rollCard(Button actualButton, ImageView imageView, CardView card) {
        if (card.rotateTransition == null) {
            card.initializeRotation(imageView);
        }
        if (card.rotateTransition.getStatus() != Animation.Status.RUNNING) {
            card.rotateTransition.setToAngle(card.isFlipped() ? 0 : 180);
            card.rotateTransition.play();
        }
    }

    private void setupCardEffects(Button button, ImageView imageView, CardView card, List<CardView> allCards) {
        button.setOnMouseEntered(event -> {
            if (!card.getIsSelected()) {
                rollCard(button, imageView, card);
            }
        });

        button.setOnMouseClicked(event -> {
            if (card.getIsSelected()) {
                card.setSelected(false);
                button.setFocusTraversable(false);
                removeGlowEffect(button);
                difficulty = null;
                if (!card.isFlipped()) {
                    rollCard(button, imageView, card);
                }
                System.out.println("Card deselected: " + card.getFrontImagePath());
            } else {
                allCards.forEach(otherCard -> {
                    if (otherCard != card) {
                        otherCard.setSelected(false);
                        ImageView otherImageView = getImageViewByCard(otherCard);
                        if (!otherCard.isFlipped()) {
                            rollCard(getButtonByCard(otherCard), otherImageView, otherCard);
                        }
                        removeGlowEffect(getButtonByCard(otherCard));
                    }
                });
                card.setSelected(true);
                if (card.isFlipped()) {
                    rollCard(button, imageView, card);
                }
                applyGlowEffect(button);
                button.setFocusTraversable(true);
                button.requestFocus();
                System.out.println("Card selected: " + card.getFrontImagePath() + ", Glow applied: " + (button.getEffect() != null) + ", Flipped: " + card.isFlipped());
            }
            event.consume();
        });
    }

    private void applyGlowEffect(Button button) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.BLUE);
        glow.setRadius(30.0);
        glow.setSpread(0.5);
        button.setEffect(glow);
        button.setStyle("-fx-border-color: blue; -fx-border-width: 3; -fx-border-radius: 5;");
        button.applyCss();
        button.layout();
        System.out.println("Applying glow to button: " + button.getId() + ", Effect: " + button.getEffect());
    }

    private void removeGlowEffect(Button button) {
        button.setEffect(null);
        button.setStyle("");
        button.applyCss();
        button.layout();
        System.out.println("Removed glow from button: " + button.getId());
    }

    private void signDifficulty(Long dificultad) {
        difficulty = dificultad;
        nameGame = txfNameGame.getText().trim();
        CardView selectedCard = getCardByDifficulty(dificultad);
        if (selectedCard != null) {
            selectedCard.setSelected(true);
            Button button = getButtonByCard(selectedCard);
            ImageView imageView = getImageViewByCard(selectedCard);
            if (selectedCard.isFlipped()) {
                rollCard(button, imageView, selectedCard);
            }
            applyGlowEffect(button);
            List<CardView> allCards = List.of(easyModeCard, mediumModeCard, hardModeCard);
            allCards.forEach(otherCard -> {
                if (otherCard != selectedCard) {
                    otherCard.setSelected(false);
                    ImageView otherImageView = getImageViewByCard(otherCard);
                    if (!otherCard.isFlipped()) {
                        rollCard(getButtonByCard(otherCard), otherImageView, otherCard);
                    }
                    removeGlowEffect(getButtonByCard(otherCard));
                }
            });
        }
        if (nameGame.isEmpty()) {
            message.show(Alert.AlertType.INFORMATION, "Dificultad seleccionada", "Dificultad " + difficulty + " asignada. Por favor, ingresa un nombre para la partida.");
        } else {
            message.show(Alert.AlertType.INFORMATION, "Dificultad seleccionada", "Dificultad " + difficulty + " asignada. Presiona 'Empezar' para iniciar el juego.");
            lastNameValid = true;
        }
        updateStartButtonVisibility();
    }

//    private void predeterminedValues() {
//        txfNameGame.clear();
//        nameGame = "";
//        difficulty = null;
//        List<CardView> allCards = List.of(easyModeCard, mediumModeCard, hardModeCard);
//        for (CardView card : allCards) {
//            if (card.getIsSelected()) {
//                card.setSelected(false);
//                Button button = getButtonByCard(card);
//                ImageView imageView = getImageViewByCard(card);
//                removeGlowEffect(button);
//                if (!card.isFlipped()) {
//                    rollCard(button, imageView, card);
//                }
//            }
//        }
//        updateStartButtonVisibility();
//        lastNameValid = true;
//    }
    private void updateStartButtonVisibility() {
        btnStartGame.setVisible(!nameGame.isEmpty() && difficulty != null);
        System.out.println("Start button visible: " + btnStartGame.isVisible() + ", nameGame: " + nameGame + ", difficulty: " + difficulty);
    }

    private void setupCardInteractions() {
        List<CardView> allCards = List.of(easyModeCard, mediumModeCard, hardModeCard);
        setupCardEffects(btnEasyMode, mgvEasyMode, easyModeCard, allCards);
        setupCardEffects(btnMediumMode, mgvMediumMode, mediumModeCard, allCards);
        setupCardEffects(btnHardMode, mgvHardMode, hardModeCard, allCards);
    }

    private void initialConditionsCards() {
        mgvEasyMode.setImage(new Image("file:" + imageUtility.getBackCardPath(easyModeCard.getBackImagePath())));
        mgvMediumMode.setImage(new Image("file:" + imageUtility.getBackCardPath(mediumModeCard.getBackImagePath())));
        mgvHardMode.setImage(new Image("file:" + imageUtility.getBackCardPath(hardModeCard.getBackImagePath())));
        mgvEasyMode.setRotate(180);
        mgvMediumMode.setRotate(180);
        mgvHardMode.setRotate(180);
        easyModeCard.setIsFlipped(true);
        mediumModeCard.setIsFlipped(true);
        hardModeCard.setIsFlipped(true);
        easyModeCard.updateImageView(mgvEasyMode);
        mediumModeCard.updateImageView(mgvMediumMode);
        mediumModeCard.updateImageView(mgvHardMode);
    }

    private void initializeBackCardStyles(PlayerDto player) {
        if (player != null) {
            if (player.getCardStyle() == 1) {
                style = "N";
            } else if (player.getCardStyle() == 2) {
                style = "M";
            } else {
                style = "V";
            }
        } else {
            style = "N";
        }

        easyCardBack = "1" + style;
        System.out.println(easyCardBack);
        mediumCardBack = "2" + style;
        System.out.println(mediumCardBack);
        hardCardBack = "3" + style;
        System.out.println(hardCardBack);
    }
    
        private CardDto getCartaByNumber(List<CardDto> tipo, int number) {
        for (CardDto carta : tipo) {
            if (carta.getNumber() != null && carta.getNumber() == number) {
                return carta;
            }
        }
        return null;
    }
        public void mixFirstCards(List<CardDto> cards, StackcardDto cardStack) {
        if (!cards.isEmpty()) {
            List<StackcardxcardDto> cardxcardList = new ArrayList();
            for (int i = 0; i < 5; i++) {
                CardDto selectedCard = cards.remove(0);//saco una carta del mazo desde arriba
                StackcardxcardDto newCard = new StackcardxcardDto();//creo una stackCardXcard
                newCard.setCard(selectedCard);
                newCard.setPositionNumber((long) (i + 1));
                newCard.setIsFaceUp(false);//las primeras 5 cartas en agregarse irán de espaldas
                cardxcardList.add(newCard);
            }
            CardDto selectedCard = cards.remove(0);//saco una carta del mazo desde arriba
            StackcardxcardDto newCard = new StackcardxcardDto();//creo una stackCardXcard
            newCard.setCard(selectedCard);
            newCard.setPositionNumber((long) 6);
            newCard.setIsFaceUp(true);
            cardxcardList.add(newCard);

            cardStack.setStackCardxCards(cardxcardList);
        }
    }

    public void mixOtherCards(List<CardDto> cards, StackcardDto cardStack) {
        if (!cards.isEmpty()) {
            List<StackcardxcardDto> cardxcardList = new ArrayList();
            for (int i = 0; i < 4; i++) {
                CardDto selectedCard = cards.remove(0);//saco una carta del mazo desde arriba
                StackcardxcardDto newCard = new StackcardxcardDto();//creo una stackCardXcard
                newCard.setCard(selectedCard);
                newCard.setPositionNumber((long) (i + 1));
                newCard.setIsFaceUp(false);//las primeras 4 cartas en agregarse irán de espaldas
                cardxcardList.add(newCard);
            }
            CardDto selectedCard = cards.remove(0);//saco una carta del mazo desde arriba
            StackcardxcardDto newCard = new StackcardxcardDto();//creo una stackCardXcard
            newCard.setCard(selectedCard);
            newCard.setPositionNumber((long) 5);
            newCard.setIsFaceUp(true);
            cardxcardList.add(newCard);

            cardStack.setStackCardxCards(cardxcardList);
        }
    }
    
    

    private void mixCards() {
        mixFirstCards(cards, stackcardList1);
        mixFirstCards(cards, stackcardList2);
        mixFirstCards(cards, stackcardList3);
        mixFirstCards(cards, stackcardList4);
        mixOtherCards(cards, stackcardList5);
        mixOtherCards(cards, stackcardList6);
        mixOtherCards(cards, stackcardList7);
        mixOtherCards(cards, stackcardList8);
        mixOtherCards(cards, stackcardList9);
        mixOtherCards(cards, stackcardList10);
    }


    
    private void loadCards(GameDto game) {
        try {
            Long difficulty = game.getDifficulty();//parece ser que la dificultad es nula

            if (difficulty != 1) {
                if (difficulty == 3) {
                    cards.addAll(treboles);
                    cards.addAll(diamantes);
                }
                while (cards.size() < 104) {
                    for (int i = 0; i < 13; i++) {
                        CardDto cartaPicas = getCartaByNumber(picas, i + 1);
                        if (cartaPicas != null) {
                            cards.add(cartaPicas);
                        }
                        CardDto cartaCorazones = getCartaByNumber(corazones, i + 1);
                        if (cartaCorazones != null) {
                            cards.add(cartaCorazones);
                        }
                    }
                }
            } else {
                cards.addAll(picas);
            }
            game.setCards(cards);

        } catch (Exception e) {
            Mensaje mensaje = new Mensaje();
            mensaje.show(Alert.AlertType.ERROR, "Dificultad nula", "La dificultad es nula");
        }
    }
    
    private void prepareGame(GameDto game) {
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
            game = new GameDto();

            GameService gameService = new GameService();
            Respuesta answer = gameService.getGameID((Long) AppContext.getInstance().get("IdCurrentGame"));

            if (answer != null && answer.getEstado()) {
                game = (GameDto) answer.getResultado("Partida");
                Long Dificultad = game.getDifficulty();
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

                loadCards(game);
            } else {
                message.showModal(Alert.AlertType.INFORMATION, "Por favor inicie sesión", getStage(), "Para poder crear un juego es necesario iniciar sesión");
                FlowController.getInstance().goView("MenuView");
            }
        }

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
            existingGames = player.getGameList();
            initializeBackCardStyles(player);

            easyModeCard.setBackImagePath(easyCardBack);
            mediumModeCard.setBackImagePath(mediumCardBack);
            hardModeCard.setBackImagePath(hardCardBack);

            initialConditionsCards();
            setupCardInteractions();
            txfNameGame.textProperty().addListener((obs, oldValue, newValue) -> {
                nameGame = newValue.trim();

                boolean isValid;
                if (nameGame.isEmpty()) {
                    isValid = false;
                } else {
                    lastNameValid = true;
                }
                updateStartButtonVisibility();
            });
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "Por favor inicie sesión", getStage(), "Para poder crear un juego es necesario iniciar sesión");
            FlowController.getInstance().goView("MenuView");
        }

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
            this.isFlipped = true; // Start face-down
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

        public void setBackImagePath(String backImagePath) {
            this.backImagePath = backImagePath;
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
            String imagePath = isFlipped ? backImagePath : frontImagePath;
            String url;
            if (!isFlipped) {
                url = imageUtility.getCardDifficultPath(imagePath);
            } else {
                url = imageUtility.getBackCardPath(imagePath);
            }

            if (url != null) {
                imageView.setImage(new Image(url));
                System.out.println("Updated image for card: " + frontImagePath + ", Flipped: " + isFlipped + ", Image: " + url);
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
                updateImageView(imageView);
            });
        }
    }
    
    
}
