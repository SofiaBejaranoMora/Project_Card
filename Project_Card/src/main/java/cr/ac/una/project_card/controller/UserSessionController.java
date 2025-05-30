package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.service.PlayerService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
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

/**
 * * FXML Controller class * * @author ashly
 */
public class UserSessionController extends Controller implements Initializable {

    private String saveRoute = System.getProperty("user.dir") + "/src/main/resources/cr/ac/una/project_card/resources/";
    Mensaje message = new Mensaje();
    private PlayerDto player;
    private File selectedFile;
    private String currentName = "";
    
    @FXML
    private ImageView mgvUserPhoto;
    @FXML
    private MFXTextField txfUserName;
    @FXML
    private Label lblCurrentPoints;
    @FXML
    private MFXButton btnStartSession;
    @FXML
    private MFXButton btnCloseSession;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnEdit;
    @FXML
    private MFXButton btnChangePhoto;

    private void onActionBtnChangePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        Stage stage = (Stage) btnChangePhoto.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            mgvUserPhoto.setImage(image);
        }
    }

    @FXML
    private void onActionBtnStartSession(ActionEvent event) {
        message.showConfirmation("Inicio de sesión", getStage(), "'Sesión creada con éxito, disfrute del juego.");// modificar por que aca tira que se guardo correctamente aun que en la base no sea asi 
        lblCurrentPoints.setText("0");
        btnStartSession.setVisible(false);
        btnCloseSession.setVisible(true);
        currentName = txfUserName.getText().trim();
        //agregar el usuario 
        player = new PlayerDto(currentName, 0L, 1L, "noimagen");
        PlayerService playerService = new PlayerService();
        Respuesta answer = playerService.SavePlayer(player);// tercera linea de error
        if (answer.getEstado()) {
            this.player = (PlayerDto) answer.getResultado("Jugador");
            AppContext.getInstance().set("CurrentUser", player);
            message.showModal(Alert.AlertType.INFORMATION, "Guardar Jugador", getStage(), "El jugador se guardo correctamente");
        } else {
            message.showModal(Alert.AlertType.ERROR, "Guardar Jugador", getStage(), answer.getMensaje());
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
                message.showConfirmation("Nombre de usuario", getStage(), "Nombre de usuario renombrado correctamente");
            } else {
                message.showModal(Alert.AlertType.ERROR, "Nombre de usuario", getStage(), "Error al renombrar el usuario");
            }
        }
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }

    private void savePorfileImage(){
        if (selectedFile != null) {
            String name = txfUserName.getText().trim();
            if (name.isBlank()) {
                message.showModal(Alert.AlertType.ERROR, "Nombre de usuario", getStage(), "El nombre de usuario está vacío.");
                return;
            }
            try {
                String savePath = saveRoute + name + ".png";
                Path destination = Path.of(savePath);

                Files.createDirectories(destination.getParent());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                message.showModal(Alert.AlertType.ERROR, "Imagen de usuario", getStage(), "Error al guardar la imagen: " + e.getMessage());
            }
        } else {
            message.showModal(Alert.AlertType.WARNING, "Imagen de usuario", getStage(), "No hay imagen seleccionada para guardar.");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player = new PlayerDto();
    }

    @Override
    public void initialize() {
        if (!"".equals(currentName.trim())) {
            txfUserName.setText(currentName);
            mgvUserPhoto.setImage(new Image(saveRoute + currentName + ".png"));
        }
    }

}
