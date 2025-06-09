package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.AnimationAndSound;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.service.PlayerService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class SettingsController extends Controller implements Initializable {

    private String saveRoute = System.getProperty("user.dir") + "/src/main/resources/cr/ac/una/project_card/resources/Cards/";
    Mensaje message = new Mensaje();
    private File selectedFile;
    private PlayerDto player;
    
    @FXML
    private AnchorPane root;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnWoodBackground;
    @FXML
    private Button btnGrassBackground;
    @FXML
    private Button btnNormalMass;
    @FXML
    private Button btnMandalaMass;
    @FXML
    private Button btnVictorianMass;
    @FXML
    private Button btnPersonalizeBack;
    private ImageView mgvPersonalized;
    
       @FXML
    private void onActionBtnBack(ActionEvent event) {
        AnimationAndSound.buttonSound();
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnGrassBackground(ActionEvent event) {
        AnimationAndSound.buttonSound();
        AppContext.getInstance().set("Background", "GrassBackground");
    }

    @FXML
    private void onActionBtnWoodBackground(ActionEvent event) {
        AnimationAndSound.buttonSound();
        AppContext.getInstance().set("Background", "WoodBackground");
    }

    @FXML
    private void onActionBtnNormalMass(ActionEvent event) {
        AnimationAndSound.buttonSound();
        if (player == null) {
            message.showModal(Alert.AlertType.ERROR, "Usuario indefinido", getStage(), "El usuario está vacío o no existe.");
            return;
        }

        player.setCardStyle(Long.valueOf(1));
        System.out.println(player);
        
        PlayerService playerService = new PlayerService();
        Respuesta answer = playerService.EditPlayerId(player);// tercera linea de error
        
        if (answer.getEstado()) {
            this.player = (PlayerDto) answer.getResultado("Jugador");
            AppContext.getInstance().set("CurrentUser", player);
            message.showModal(Alert.AlertType.INFORMATION, "Selección de espalda", getStage(), "La espalda personalizada se guardo correctamente");
        
        } else {
            message.showModal(Alert.AlertType.ERROR, "Selección de espalda", getStage(), answer.getMensaje());
        }
    }

    @FXML
    private void onActionBtnMandalaMass(ActionEvent event) {
        AnimationAndSound.buttonSound();
        if (player == null) {
            message.showModal(Alert.AlertType.ERROR, "Usuario indefinido", getStage(), "El usuario está vacío o no existe.");
            return;
        }

        player.setCardStyle(Long.valueOf(2));
        System.out.println(player);
        
        PlayerService playerService = new PlayerService();
        Respuesta answer = playerService.EditPlayerId(player);// tercera linea de error
        
        if (answer.getEstado()) {
            this.player = (PlayerDto) answer.getResultado("Jugador");
            AppContext.getInstance().set("CurrentUser", player);
            message.showModal(Alert.AlertType.INFORMATION, "Selección de espalda", getStage(), "La espalda personalizada se guardo correctamente");
        
        } else {
            message.showModal(Alert.AlertType.ERROR, "Selección de espalda", getStage(), answer.getMensaje());
        }
    }

    @FXML
    private void onActionBtnVictoriaMass(ActionEvent event) {
        AnimationAndSound.buttonSound();
        if (player == null) {
            message.showModal(Alert.AlertType.ERROR, "Usuario indefinido", getStage(), "El usuario está vacío o no existe.");
            return;
        }

        player.setCardStyle(Long.valueOf(3));
        System.out.println(player);
        
        PlayerService playerService = new PlayerService();
        Respuesta answer = playerService.EditPlayerId(player);// tercera linea de error
        
        if (answer.getEstado()) {
            this.player = (PlayerDto) answer.getResultado("Jugador");
            AppContext.getInstance().set("CurrentUser", player);
            message.showModal(Alert.AlertType.INFORMATION, "Selección de espalda", getStage(), "La espalda personalizada se guardo correctamente");
        
        } else {
            message.showModal(Alert.AlertType.ERROR, "Selección de espalda", getStage(), answer.getMensaje());
        }
    }

    @FXML
    private void onActionBtnPersonalizeBack(ActionEvent event) {
        AnimationAndSound.buttonSound();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) btnPersonalizeBack.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            mgvPersonalized.setImage(image);
        }
    }
    
    private void savePersonalizedBack() {
        if(selectedFile != null) {
            if (player == null) {
                message.showModal(Alert.AlertType.ERROR, "Usuario indefinido", getStage(), "El usuario está vacío o no existe.");
                return;
            }

            try {
                String savePath = saveRoute + player.getId() + ".png";
                Path destination = Path.of(savePath);
                
                Files.createDirectories(destination.getParent());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                                
                player.setCardBackImageName(player.getId()+"");
                PlayerService playerService = new PlayerService();
                Respuesta answer = playerService.EditPlayerId(player);// tercera linea de error
                
                if (answer.getEstado()) {
                    this.player = (PlayerDto) answer.getResultado("Jugador");
                    AppContext.getInstance().set("CurrentUser", player);
                    message.showModal(Alert.AlertType.INFORMATION, "Selección de espalda", getStage(), "La espalda personalizada se guardo correctamente");
               
                }else {
                    message.showModal(Alert.AlertType.ERROR, "Selección de espalda", getStage(), answer.getMensaje());
                }
            } catch (IOException e) {
                message.showModal(Alert.AlertType.ERROR, "Selección de espalda", getStage(), "Error al guardar la imagen: " + e.getMessage());
            }
        } else {
            message.showModal(Alert.AlertType.WARNING, "Selección de espalda", getStage(), "No hay imagen seleccionada para guardar.");
        }
    }
 
    private void fitCards(Button btn) {
        btn.setMinWidth(126);
        btn.prefWidthProperty().bind(root.widthProperty().divide(4.4));
        btn.prefHeightProperty().bind(root.widthProperty().divide(5).multiply(17).divide(14.5));

        if (btn.getGraphic() instanceof ImageView image) {
            image.setPreserveRatio(true);
            image.fitWidthProperty().bind(btn.prefWidthProperty().multiply(0.9));
            image.fitHeightProperty().bind(btn.prefHeightProperty().multiply(0.9));
        }
    }

    private void fitBackgrounds(Button btn) {
        btn.setMinWidth(173);
        btn.prefWidthProperty().bind(root.widthProperty().divide(4.9));
        btn.prefHeightProperty().bind(root.widthProperty().divide(5).multiply(17).divide(27));

        if (btn.getGraphic() instanceof ImageView image) {
            image.setPreserveRatio(true);
            image.fitWidthProperty().bind(btn.prefWidthProperty().multiply(0.95));
            image.fitHeightProperty().bind(btn.prefHeightProperty().multiply(0.95));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        fitCards(btnNormalMass);
        fitCards(btnMandalaMass);
        fitCards(btnVictorianMass);
        fitCards(btnPersonalizeBack);
        fitBackgrounds(btnGrassBackground);
        fitBackgrounds(btnWoodBackground);
        player =  (PlayerDto) AppContext.getInstance().get("CurrentUser");
    }

}
