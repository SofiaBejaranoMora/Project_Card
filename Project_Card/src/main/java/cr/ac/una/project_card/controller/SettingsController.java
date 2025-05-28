package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class SettingsController extends Controller implements Initializable {

    private String saveRoute = System.getProperty("user.dir") + "/src/main/resources/cr/ac/una/project_card/resources/Cards/";
    Mensaje message = new Mensaje();
    private File selectedFile;
    
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
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnGrassBackground(ActionEvent event) {
        AppContext.getInstance().set("GrassBackground", "Background");
    }

    @FXML
    private void onActionBtnWoodBackground(ActionEvent event) {
        AppContext.getInstance().set("WoodBackground", "Background");
    }

    @FXML
    private void onActionBtnNormalMass(ActionEvent event) {
        AppContext.getInstance().set("1", "Cards");
    }

    @FXML
    private void onActionBtnMandalaMass(ActionEvent event) {
        AppContext.getInstance().set("2", "Cards");
    }

    @FXML
    private void onActionBtnVictoriaMass(ActionEvent event) {
        AppContext.getInstance().set("3", "Cards");
    }

    @FXML
    private void onActionBtnPersonalizeBack(ActionEvent event) {
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
            String name = (String) AppContext.getInstance().get("CurrentUser");
            if (name.isEmpty()) {
                message.showModal(Alert.AlertType.ERROR, "Usuario indefinido", getStage(), "El usuario está vacío o no existe.");
                return;
            }

            try {
                String savePath = saveRoute + name + ".png";
                Path destination = Path.of(savePath);
                
                Files.createDirectories(destination.getParent());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                message.showConfirmation("Selección de espalda", getStage(), "'Reves de carta escogida con exitó, disfrute del juego.");
                
            } catch (IOException e) {
                message.showModal(Alert.AlertType.ERROR, "Selección de espalda", getStage(), "Error al guardar la imagen: " + e.getMessage());
            }
        } else {
            message.showModal(Alert.AlertType.WARNING, "Selección de espalda", getStage(), "No hay imagen seleccionada para guardar.");
        }
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

}
