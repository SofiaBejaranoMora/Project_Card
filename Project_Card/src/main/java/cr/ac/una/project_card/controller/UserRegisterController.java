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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/** * FXML Controller class * * @author ashly */
public class UserRegisterController extends Controller implements Initializable {

    private String saveRoute = System.getProperty("user.dir") + "/src/main/resources/cr/ac/una/project_card/resources/";
    Mensaje message = new Mensaje();
    private PlayerDto player;
    private File selectedFile;
    private String currentName = "";
    
    @FXML
    private Button btnOut;
    @FXML
    private ImageView mgvUserPhoto;
    @FXML
    private Button btnHelp;
    @FXML
    private MFXTextField txfUserName;
    @FXML
    private MFXButton btnUploadPhoto;
    @FXML
    private MFXButton btnRegister;

    @FXML
    private void onActionBtnOut(ActionEvent event) {
        FlowController.getInstance().salir();
    }

    @FXML
    private void onActionBtnHelp(ActionEvent event) {
        //message.show(Alert.AlertType.INFORMATION, saveRoute, saveRoute);//Hacer la explicación del juego
    }

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
    private void onActionBtnRegister(ActionEvent event) {
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

        message.showConfirmation("Inicio de sesión", getStage(), "'Sesión creada con éxito, disfrute del juego.");// modificar por que aca tira que se guardo correctamente aun que en la base no sea asi 
        currentName = txfUserName.getText().trim();
        //agregar el usuario 
        player = new PlayerDto(currentName, 0L, 1L, "noimagen");
        PlayerService playerService = new PlayerService();
        Respuesta answer = playerService.SavePlayer(player);// tercera linea de error
        if (answer.getEstado()) {
            this.player = (PlayerDto) answer.getResultado("Jugador");
            AppContext.getInstance().set("CurrentUser", player);
            message.showModal(Alert.AlertType.INFORMATION, "Guardar Jugador", getStage(), "El jugador se guardo correctamente");
            FlowController.getInstance().goView("MenuView");
        } else {
            message.showModal(Alert.AlertType.ERROR, "Guardar Jugador", getStage(), answer.getMensaje());
        }
            } catch (IOException e) {
                message.showModal(Alert.AlertType.ERROR, "Imagen de usuario", getStage(), "Error al guardar la imagen: " + e.getMessage());
            }
        } else {
            message.showModal(Alert.AlertType.WARNING, "Datos de registro", getStage(), "Favor de completar los datos para continuar.");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        player = new PlayerDto();
    }

    @Override
    public void initialize() {
        
    }

}
