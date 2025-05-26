package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class UserRegisterController extends Controller implements Initializable {

    private String saveRoute = System.getProperty("user.dir") + "/src/main/resources/cr/ac/una/project_card/resources/";
    Mensaje message = new Mensaje();
    private File selectedFile;
    private String currentName = "";    
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
    private Button btnEdit;
    @FXML
    private Button btnHelp;

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
                message.showModal(Alert.AlertType.ERROR, "Nombre de usuario", getStage(), "El nombre de usuario está vacío.");
                return;
            }

            try {
                String savePath = saveRoute + name + ".png";
                Path destination = Path.of(savePath);

                Files.createDirectories(destination.getParent());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                message.showConfirmation("Inicio de sesión", getStage(), "'Sesión creada con éxito, disfrute del juego.");
                lblCurrentPoints.setText("0");
                btnStartSession.setVisible(false);
                btnCloseSession.setVisible(true);
                
            } catch (IOException e) {
                message.showModal(Alert.AlertType.ERROR, "Imagen de usuario", getStage(), "Error al guardar la imagen: " + e.getMessage());
            }
        } else {
            message.showModal(Alert.AlertType.WARNING, "Imagen de usuario", getStage(), "No hay imagen seleccionada para guardar.");
        }
    }

    @FXML
    private void onActionBtnCloseSession(ActionEvent event) {
        mgvUserPhoto.setImage(null);
        lblCurrentPoints.setText("");
        txfUserName.setText("");
        btnCloseSession.setVisible(false);
        btnStartSession.setVisible(true);
        message.showConfirmation("Sesión Cerrada", getStage(), "Se ha cerrado la sesión con éxito.");
    }

    @FXML
    private void onActionBtnEdit(ActionEvent event) {
        File oldFile = new File(saveRoute + currentName + ".png");
        File newFile = new File(saveRoute + txfUserName.getText().trim() + ".png");

        if (oldFile.exists()) {
            boolean renamed = oldFile.renameTo(newFile);
            if (renamed) {
                message.showConfirmation("Imagen de usuario", getStage(), "Imagen renombrada correctamente");
            } else {
                message.showModal(Alert.AlertType.ERROR, "Imagen de usuario", getStage(), "Error al renombrar la imagen");
            }
        }
    }

    @FXML
    private void onActionBtnHelp(ActionEvent event) {
        message.show(Alert.AlertType.INFORMATION, saveRoute, saveRoute);//Hacer la explicación del juego
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        if (!"".equals(currentName.trim())) {
            txfUserName.setText(currentName);
            mgvUserPhoto.setImage(new Image(saveRoute + currentName + ".png"));
        }
    }

}
