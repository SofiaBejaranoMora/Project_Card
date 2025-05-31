package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.service.PlayerService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Formato;
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
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserSessionController extends Controller implements Initializable {

    private String saveRoute = System.getProperty("user.dir") + "/src/main/resources/cr/ac/una/project_card/resources/"; //nombreUser + .png
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
    @FXML
    private MFXButton btnRegisterUser;
    @FXML
    private StackPane stp;

    @FXML
    private void onActionChangePhoto(ActionEvent event) {
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
private void onActionRegisterUser(ActionEvent event) {
    currentName = txfUserName.getText().trim();
    PlayerService playerService = new PlayerService();
    Respuesta checkUser = playerService.findPlayerByName(currentName);
    
    if (checkUser.getEstado() && checkUser.getResultado("Jugador") != null) {
        message.showModal(Alert.AlertType.WARNING, "Registro", getStage(), "El usuario ya existe. Inicie sesión.");
        buttonManager(2);
        return;
    }   
    // Si no existe, proceder con la imagen de perfil
    if (savePorfileImage()) {
        player = new PlayerDto(currentName, 0L, 1L, "noimagen");
        Respuesta answer = playerService.SavePlayer(player);

        if (answer.getEstado()) {
            this.player = (PlayerDto) answer.getResultado("Jugador");
            AppContext.getInstance().set("CurrentUser", player);
            buttonManager(3);
            message.showModal(Alert.AlertType.INFORMATION, "Inicio de sesión", getStage(), "Sesión creada con éxito, disfrute del juego.");
            AppContext.getInstance().set("hasSectionStarted", true);
            FlowController.getInstance().goView("MenuView");
        } else {
            message.showModal(Alert.AlertType.ERROR, "Guardar Jugador", getStage(), answer.getMensaje());
        }
    }
}

    @FXML
    private void onActionBtnStartSession(ActionEvent event) {
        lblCurrentPoints.setText("0");
        buttonManager(3);
        currentName = txfUserName.getText().trim();
        //buscar usuario
        player.setName(currentName);
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
        }
        PlayerService playerService = new PlayerService(); // Este es el de buscar por nombre mas bien
        Respuesta answer = playerService.getPlayerName(player.getName());// tercera linea de error

        if (answer.getEstado()) {
            this.player = (PlayerDto) answer.getResultado("Jugador");
            AppContext.getInstance().set("CurrentUser", player);
            AppContext.getInstance().set("hasSectionStarted", true);
            message.showModal(Alert.AlertType.INFORMATION, "Inicio de sesión", getStage(), "Sesión iniciada con éxito, disfrute del juego.");
            lblCurrentPoints.setText(player.getAccumulatedPoint().toString());
            mgvUserPhoto.setImage(new Image("file:" + saveRoute + currentName + ".png"));

        } else {
            buttonManager(1);
            message.showModal(Alert.AlertType.ERROR, "Guardar Jugador", getStage(), answer.getMensaje());
        }
    }

    @FXML
    private void onActionBtnEdit(ActionEvent event) {
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            File oldFile = new File(saveRoute + currentName + ".png");
            File newFile = new File(saveRoute + txfUserName.getText().trim() + ".png");
            if (oldFile.exists()) {
                try {
                    // Actualizar nombre y datos del jugador
                    currentName = txfUserName.getText().trim();
                    player.setName(currentName);
                    PlayerService playerService = new PlayerService();
                    Respuesta answer = playerService.SavePlayer(player);
                    if (answer != null && answer.getEstado()) {
                        this.player = (PlayerDto) answer.getResultado("Jugador");
                        Files.move(oldFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        AppContext.getInstance().set("CurrentUser", player);
                        message.showModal(Alert.AlertType.INFORMATION, "Editar Jugador", getStage(), "Sesión editada con éxito, disfrute del juego.");
                        mgvUserPhoto.setImage(new Image("file:" + saveRoute + currentName + ".png"));
                        
                    } else {
                        message.showModal(Alert.AlertType.ERROR, "Editar Jugador", getStage(), answer != null ? answer.getMensaje() : "Error al guardar jugador.");
                    }
                } catch (IOException e) {
                    message.showModal(Alert.AlertType.ERROR, "Nombre de usuario", getStage(), "Error al renombrar el usuario: " + e.getMessage());
                }
            }
        } else {
            message.showModal(Alert.AlertType.WARNING, "Editar Jugador", getStage(), "Debe iniciar sesión para poder modificar el nombre.");
        }
    }

    @FXML
    private void onActionBtnCloseSession(ActionEvent event) {
        AppContext.getInstance().set("hasSectionStarted", false);
        AppContext.getInstance().set("isRegisterSession", true);
        mgvUserPhoto.setImage(null);
        lblCurrentPoints.setText("");
        txfUserName.setText("");
        buttonManager(2);
        player = new PlayerDto();
        message.showConfirmation("Sesión Cerrada", getStage(), "Se ha cerrado la sesión con éxito.");
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        //Crear un metodo de limpiar.
        FlowController.getInstance().goView("MenuView");
    }

    private void buttonManager(int situation) {
        switch (situation) {
            case 1 -> {
                btnCloseSession.setVisible(false);
                btnStartSession.setVisible(false);
                btnRegisterUser.setVisible(true);
                stp.setVisible(true);
            }
            case 2 -> {
                btnCloseSession.setVisible(false);
                btnStartSession.setVisible(true);
                btnRegisterUser.setVisible(false);
                stp.setVisible(false);
            }
            case 3 -> {
                btnCloseSession.setVisible(true);
                btnStartSession.setVisible(false);
                btnRegisterUser.setVisible(false);
                stp.setVisible(false);
            }
        }
    }

    private Boolean savePorfileImage() {
        if (selectedFile != null) {
            String name = txfUserName.getText().trim();
            if (name.isBlank()) {
                message.showModal(Alert.AlertType.ERROR, "Nombre de usuario", getStage(), "El nombre de usuario está vacío.");
                return false;
            }
            try {
                String savePath = saveRoute + name + ".png";
                Path destination = Path.of(savePath);
                
                Files.createDirectories(destination.getParent());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                return true;

            } catch (IOException e) {
                message.showModal(Alert.AlertType.ERROR, "Imagen de usuario", getStage(), "Error al guardar la imagen: " + e.getMessage());
                return false;
            }
        } else {
            message.showModal(Alert.AlertType.WARNING, "Datos de registro", getStage(), "Favor de completar los datos para continuar.");
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txfUserName.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
    }

    @Override
    public void initialize() {
        player = new PlayerDto();
        if (!"".equals(player.getName().trim())) {
            currentName = txfUserName.getText().trim();
            txfUserName.setText(currentName);
            mgvUserPhoto.setImage(new Image("file:" + saveRoute + currentName + ".png"));
        }
        if ((Boolean) AppContext.getInstance().get("isRegisterSession")) {
            buttonManager(1);
        } else {
            if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
                buttonManager(3);
            } else {
                buttonManager(2);
            }
        }
    }
}
