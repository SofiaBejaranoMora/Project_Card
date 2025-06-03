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
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/*** FXML Controller class * * @author ashly */
public class GameController extends Controller implements Initializable {

    // Variables del juego
    private List<CardDto> cards = new ArrayList<>(); // Mazo completo
    private GameDto game;
    private CardService cardService = new CardService();
    private Mensaje message = new Mensaje();
    private PlayerDto player = new PlayerDto();

    // Listas de cartas por tipo
    private List<CardDto> corazones = new ArrayList<>();
    private List<CardDto> picas = new ArrayList<>();
    private List<CardDto> treboles = new ArrayList<>();
    private List<CardDto> diamantes = new ArrayList<>();
    private Boolean isBigScreen = false;
    
    StackcardDto stackcardList1=new StackcardDto();
    StackcardDto stackcardList2 = new StackcardDto();
    StackcardDto stackcardList3 = new StackcardDto();
    StackcardDto stackcardList4 = new StackcardDto(); // Hasta aquí llevan 5 cartas de espaldas y la última de frente
    StackcardDto stackcardList5 = new StackcardDto();
    StackcardDto stackcardList6 = new StackcardDto();
    StackcardDto stackcardList7 = new StackcardDto();
    StackcardDto stackcardList8 =new StackcardDto();
    StackcardDto stackcardList9 = new StackcardDto();
    StackcardDto stackcardList10 = new StackcardDto();

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
    private ImageView mgvBackground;

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
    
    private void loadCards() {
      try{
          Long difficulty = game.getDifficulty();//parece ser que la dificultad es nula
          
          if(difficulty!=1)
          {
              if(difficulty==3){
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
          }
          else
          {
              cards.addAll(picas);
          }
          game.setCards(cards);
        
      }catch(Exception e){
          Mensaje mensaje=new Mensaje();
          mensaje.show(Alert.AlertType.ERROR, "Dificultad nula", "La dificultad es nula");
      }
    }
    
    private CardDto getCartaByNumber(List<CardDto> tipo, int number) {
        for (CardDto carta : tipo) {
            if (carta.getNumber() != null && carta.getNumber() == number) {
                return carta;
            }
        }
        return null;
    }
    
    public void mixFirstCards(List<CardDto> cards, StackcardDto cardStack){
        if(!cards.isEmpty()){
            List<StackcardxcardDto> cardxcardList=new ArrayList();
            for(int i=0; i<5; i++){
                CardDto selectedCard=cards.remove(0);//saco una carta del mazo desde arriba
                StackcardxcardDto newCard=new StackcardxcardDto();//creo una stackCardXcard
                newCard.setCard(selectedCard);
                newCard.setPositionNumber((long)(i+1));
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
    
    public void mixOtherCards(List<CardDto> cards, StackcardDto cardStack){
           if(!cards.isEmpty()){
            List<StackcardxcardDto> cardxcardList=new ArrayList();
            for(int i=0; i<4; i++){
                CardDto selectedCard=cards.remove(0);//saco una carta del mazo desde arriba
                StackcardxcardDto newCard=new StackcardxcardDto();//creo una stackCardXcard
                newCard.setCard(selectedCard);
                newCard.setPositionNumber((long)(i+1));
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
    
    private void mixCards(){
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

private void prepareGame() {
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
            game = new GameDto();
            String gameName= (String) AppContext.getInstance().get("CurrentGame");
            for(GameDto gameDto:player.getGameList()){
                if(gameDto.getName()==gameName){
                    game = gameDto;
                }
            }

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

            loadCards();
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "Por favor inicie sesión", getStage(), "Para poder crear un juego es necesario iniciar sesión");
            FlowController.getInstance().goView("MenuView");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareGame();
        mixCards();
    }

    @Override
    public void initialize() {

    }

}
