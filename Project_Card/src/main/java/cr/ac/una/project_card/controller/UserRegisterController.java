package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class UserRegisterController extends Controller implements Initializable {

    private File selectedFile;
    @FXML
    private ImageView mgvUserPhoto;
    @FXML
    private MFXTextField txfUserName;
    @FXML
    private Label lblCurrentPoints;
    @FXML
    private MFXButton btnUploadPhoto;
    @FXML
    private MFXButton btnStartSession;
    @FXML
    private MFXButton btnCloseSession;
    @FXML
    private Button btnBack;

@FXML
private void onActionBtnUploadPhoto(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Seleccionar imagen");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

    Stage stage = (Stage) btnUploadPhoto.getScene().getWindow();
    selectedFile = fileChooser.showOpenDialog(stage);

    if (selectedFile != null) {
        Image image = new Image(selectedFile.toURI().toString());
        mgvUserPhoto.setImage(image);
    }
}

@FXML
private void onActionBtnStartSession(ActionEvent event) {
    if (selectedFile != null) {
        String name = txfUserName.getText().trim();
        if (name.isEmpty()) {
            System.out.println("Error: El nombre de usuario está vacío.");
            return;
        }

        try {
            String savePath = System.getProperty("user.dir") + "/src/main/resources/cr/ac/una/project_card/resources/" + name + ".png";
            Path destination = Path.of(savePath);

            Files.createDirectories(destination.getParent());
            Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Imagen guardada en: " + destination.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al guardar la imagen: " + e.getMessage());
        }
    } else {
        System.out.println("No hay imagen seleccionada para guardar.");
    }
    btnStartSession.setVisible(false);
    btnCloseSession.setVisible(true);
}

    @FXML
    private void onActionBtnCloseSession(ActionEvent event) {
        btnCloseSession.setVisible(false);
        btnStartSession.setVisible(true);
    }
    
    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goViewInStage("MenuView", (Stage) btnBack.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    
    }

}
