package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.AchievementDto;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.service.AchievementsService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.Mensaje;
import cr.ac.una.project_card.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AchievementsController extends Controller implements Initializable {

    private Image image;
    private Mensaje message = new Mensaje();
    private PlayerDto player = new PlayerDto();
    private List<AchievementDto> achievementObtainedList = new ArrayList();
    private List<AchievementDto> achievementNotObtainedList = new ArrayList();
    private ObservableList<String> achievementTypeObservableList = FXCollections.observableArrayList(
            "Ganar", "Movimiento", "Partidas", "Perder", "Puntos", "Registro", "Tiempo", "Todos"
    );

    @FXML
    private Button btnBack;
    @FXML
    private MFXTextField txtSearchNameAchievementObtained;
    @FXML
    private MFXComboBox<String> cmbSearchAchievementObtainedType;
    @FXML
    private ScrollPane scrollPaneAchievementsObtained;
    @FXML
    private MFXTextField txtSearchNotObtainedAchievementsName;
    @FXML
    private MFXComboBox<String> cmbSearchNotObtainedAchievementType;
    @FXML
    private ScrollPane scrollPaneAchievementsNotObtained;
    @FXML
    private VBox vBoxAchievementsObtained;
    @FXML
    private Button btnStatistics;
    @FXML
    private VBox vBoxAchievementsNotObtained;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }
    
    @FXML
    private void onActionBtnStatistics(ActionEvent event) {
    }

    @FXML
    private void onActionCmbSearchAchievementObtainedType(ActionEvent event) {
    }

    @FXML
    private void onActionCmbSearchNoObtainedAchievementType(ActionEvent event) {
    }
    
    public void UploadAchievement(AchievementDto achievement, VBox vBox, int Saturation) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setSpacing(15);
        HBox.setMargin(hbox, new Insets(15));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(15);
        VBox.setMargin(vbox, new Insets(15));

//        image = new Image(/*raqui va la ruta +*/achievement.getName() + ".png");
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(100);
//        imageView.setFitHeight(100);
//        imageView.setPreserveRatio(true);
//        ColorAdjust colorAdjust = new ColorAdjust();
//        colorAdjust.setSaturation(Saturation); // -1 blanco y negro, 0 normal
//        imageView.setEffect(colorAdjust);

        TextField textField = new TextField("Name: " + achievement.getName());
        textField.setDisable(true);

        TextArea textArea = new TextArea("Tipo: " + achievement.getType() + "\n" + "Cantidad: " + achievement.getAmount() + "\n" + "Descripción: " + achievement.getDescription());
        textArea.setWrapText(true);
        textArea.setDisable(true);

        textArea.setPrefWidth(200);
        textArea.setPrefHeight(90);

        hbox.getChildren().addAll(/*imageView,*/ textArea);
        hbox.prefWidthProperty().bind(hbox.widthProperty().multiply(0.98));
        vBox.getChildren().addAll(hbox);
    }
    
    public void Loadachievement(List<AchievementDto> achievementLis,VBox vBox, int Saturation) {
        if ((achievementLis != null) && (!achievementLis.isEmpty())) {
            for (AchievementDto currentAchievement : achievementLis) {
                UploadAchievement(currentAchievement,vBox,Saturation);
            }
        }
    }

    public void initializeTabAchievementsNotObtained() {
        Loadachievement(achievementNotObtainedList,vBoxAchievementsNotObtained,-1);
    }

    public void initializeTabAchievementsObtained() {
        Loadachievement(achievementNotObtainedList,vBoxAchievementsNotObtained,0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbSearchAchievementObtainedType.setItems(achievementTypeObservableList);
        cmbSearchNotObtainedAchievementType.setItems(achievementTypeObservableList);
    }

    @Override
    public void initialize() {
        if ((Boolean) AppContext.getInstance().get("hasSectionStarted")) {
            player = (PlayerDto) AppContext.getInstance().get("CurrentUser");
            AchievementsService achievementsService = new AchievementsService();
            Respuesta answer = achievementsService.loadAllAchievement();

            if (answer.getEstado()) {
                this.achievementNotObtainedList = (AbstractList<AchievementDto>) answer.getResultado("Logros");
                this.achievementNotObtainedList = achievementNotObtainedList;
                message.showModal(Alert.AlertType.INFORMATION, "¡Logros actualizados con éxito!", getStage(), "Tus hazañas más épicas ya están al día. ¡Ve a echarles un vistazo y presume como se debe!");
            }
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¡Tus logros están haciendo fila!", getStage(), "Pero solo se muestran si inicias sesión o te registras desde el menú. ¡Hazlo y déjalos brillar como se merecen!");
        }
    }

}
