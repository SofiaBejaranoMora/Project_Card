package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.AchievementDto;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.service.AchievementsService;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import cr.ac.una.project_card.util.ImagesUtil;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AchievementsController extends Controller implements Initializable {

    private Image image;
    private ImagesUtil imageUtility = new ImagesUtil();
    private Mensaje message = new Mensaje();
    private PlayerDto player = new PlayerDto();
    private AchievementsService achievementsService = new AchievementsService();
    private Respuesta answer;
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
    private Button btnSearchAchievementNotObtained;
    @FXML
    private Button btnSearchAchievementObtained;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        clean();
        FlowController.getInstance().goView("MenuView");
    }

    @FXML
    private void onActionBtnStatistics(ActionEvent event) {
        FlowController.getInstance().goView("UserStatisticView");
    }

    private void onActionBtnSearchAchievementNotObtainedName(ActionEvent event) {
        if ((txtSearchNotObtainedAchievementsName != null) && !txtSearchNotObtainedAchievementsName.getText().isBlank()) {
            cmbSearchAchievementObtainedType.setValue("Todos");
            // traer del service la lista y si es solo 1 sacamos el tipo que es y lo colocamos en set value
            if (achievementNotObtainedList.size() == 1) {
                cmbSearchNotObtainedAchievementType.setValue(achievementNotObtainedList.get(0).getType());
            }
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¿Buscando qué?", getStage(), "Parece que olvidaste escribir un nombre. ¡No te preocupes, nos pasa a todos! Escribe algo y démosle a la búsqueda.!");
        }
    }

    private void onActionBtnSearchAchievementObtainedName(ActionEvent event) {
        if ((txtSearchNameAchievementObtained != null) && !txtSearchNameAchievementObtained.getText().isBlank()) {
            cmbSearchAchievementObtainedType.setValue("Todos");
            // traer del service la lista y si es solo 1 sacamos el tipo que es y lo colocamos en set value
            if (achievementObtainedList.size() == 1) {
                cmbSearchAchievementObtainedType.setValue(achievementObtainedList.get(0).getType());
            }
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¿Buscando qué?", getStage(), "Parece que olvidaste escribir un nombre. ¡No te preocupes, nos pasa a todos! Escribe algo y démosle a la búsqueda.!");
        }
    }

    @FXML
    private void onActionBtnSearchAchievementNotObtained(ActionEvent event) {
        String selectionType = cmbSearchNotObtainedAchievementType.getValue();
        String name = txtSearchNotObtainedAchievementsName.getText();
        if (((selectionType != null) && !selectionType.isBlank()) || ((name != null) && !name.isEmpty())) {
            if (selectionType == null || selectionType.equals("Todos")) {
                answer = achievementsService.getAchievementParameter(name, "");
            } else {
                answer = achievementsService.getAchievementParameter(name, selectionType);
            }
            if (answer.getEstado()) {
                vBoxAchievementsNotObtained.getChildren().clear();
                this.achievementNotObtainedList = (List<AchievementDto>) answer.getResultado("Logro");
                Loadachievement(achievementNotObtainedList, vBoxAchievementsNotObtained, -1.0);
            }
        }
    }

    @FXML
    private void onActionBtnSearchAchievementObtained(ActionEvent event) {
        String selectionType = cmbSearchAchievementObtainedType.getValue();
        String name = txtSearchNameAchievementObtained.getText();
        if (((selectionType != null) && !selectionType.isBlank()) || ((name != null) && !name.isEmpty())) {
            if (selectionType == null || selectionType.equals("Todos")) {
                answer = achievementsService.getAchievementParameter(name, "");
            } else {
                answer = achievementsService.getAchievementParameter(name, selectionType);
            }
            if (answer.getEstado()) {
                vBoxAchievementsObtained.getChildren().clear();
                this.achievementObtainedList = (List<AchievementDto>) answer.getResultado("Logro");
                Loadachievement(achievementObtainedList, vBoxAchievementsObtained, 0.0 /*vBoxAchievementsNotObtained, -1.0*/);
            }
        }
    }

    public void UploadAchievement(AchievementDto achievement, VBox vBoxPrincipal, Double Saturation) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setSpacing(15);
        VBox.setMargin(hbox, new Insets(15));

        // Agregar la Imagen
//        String rute = imageUtility.getAchievement(achievement.getImageName());
//        System.out.println(rute);
//        ImageView imageView = new ImageView();
//        imageView.setImage(new Image(rute));
//        imageView.setFitWidth(64);
//        imageView.setFitHeight(64);
//        imageView.setPreserveRatio(true);
//
//        ColorAdjust colorAdjust = new ColorAdjust();
//        colorAdjust.setSaturation(Saturation);
//        imageView.setEffect(colorAdjust);
        // VBox del texto
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(8);
        HBox.setHgrow(vbox, Priority.ALWAYS); // permite que el VBox crezca horizontalmente

        // Campo del nombre
        TextField textField = new TextField(achievement.getName());
        textField.setDisable(true);
        textField.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: transparent; -fx-text-fill: black; -fx-border-color: transparent;");

        // Campo del texto informativo
        String fullText = "Tipo: " + achievement.getType() + "\n"
                + "Cantidad: " + achievement.getAmount() + "\n\n"
                + achievement.getDescription();
        TextArea textArea = new TextArea(fullText);
        textArea.setWrapText(true);
        textArea.setDisable(true);
        textArea.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-border-color: transparent;");
        textArea.setEditable(false);
        textArea.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        //AJUSTE DE ALTURA DEL TEXT_AREA
        // Cuenta las lineas
        int lineCount = fullText.split("\n").length + 1;
        double lineHeight = 17.0; // Aproximado de tamaño de fuente 
        double textHeight = lineCount * lineHeight + 10; // cantador * alto de la linea + margen
        textArea.setPrefHeight(textHeight);

        // Evita qeu el Hbox crezca más de lo necesario
        hbox.setMinHeight(Region.USE_PREF_SIZE);
        hbox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        hbox.setMaxHeight(Region.USE_COMPUTED_SIZE);

        // Agregar el TextArea y el textFiel al vbox
        vbox.getChildren().addAll(textField, textArea);
        vbox.prefWidthProperty().bind(hbox.widthProperty().multiply(0.85)); // Deja espacio para imagen
        vbox.setMaxWidth(Double.MAX_VALUE);

        // Agragar el vbox y imagen al Hbox
        hbox.getChildren().addAll(/*imageView,*/vbox);
        hbox.prefWidthProperty().bind(vBoxPrincipal.widthProperty().multiply(0.98));
        HBox.setHgrow(hbox, Priority.ALWAYS);

        // Agregar el Hbox el Vbox que tenemos tendro del ScrollPane
        vBoxPrincipal.getChildren().add(hbox);
    }

    public void Loadachievement(List<AchievementDto> achievementLis, VBox vBox, Double Saturation) {
        if ((achievementLis != null) && (!achievementLis.isEmpty())) {
            for (AchievementDto currentAchievement : achievementLis) {
                UploadAchievement(currentAchievement, vBox, Saturation);
            }
        }
    }

    public void clean() {
        txtSearchNameAchievementObtained.setText("");
        txtSearchNotObtainedAchievementsName.setText("");
        cmbSearchAchievementObtainedType.getSelectionModel().clearSelection();
        cmbSearchAchievementObtainedType.setValue(null);
        cmbSearchNotObtainedAchievementType.getSelectionModel().clearSelection();
        cmbSearchNotObtainedAchievementType.setValue(null);
        vBoxAchievementsNotObtained.getChildren().clear();
        vBoxAchievementsObtained.getChildren().clear();
    }

    public void initializeTabAchievements() {
        vBoxAchievementsNotObtained.prefWidthProperty().bind(scrollPaneAchievementsNotObtained.widthProperty().multiply(0.98));
        Loadachievement(achievementNotObtainedList, vBoxAchievementsNotObtained, -1.0);
        vBoxAchievementsObtained.prefWidthProperty().bind(scrollPaneAchievementsObtained.widthProperty().multiply(0.98));
        Loadachievement(achievementObtainedList, vBoxAchievementsObtained, 0.0);
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
            answer = achievementsService.loadAllAchievement();
            if (answer.getEstado()) {
                this.achievementNotObtainedList = (List<AchievementDto>) answer.getResultado("Logros");
                this.achievementObtainedList = achievementNotObtainedList;
                initializeTabAchievements();
                message.showModal(Alert.AlertType.INFORMATION, "¡Logros actualizados con éxito!", getStage(), "Tus hazañas más épicas ya están al día. ¡Ve a echarles un vistazo y presume como se debe!");
            }
        } else {
            message.showModal(Alert.AlertType.INFORMATION, "¡Tus logros están haciendo fila!", getStage(), "Pero solo se muestran si inicias sesión o te registras desde el menú. ¡Hazlo y déjalos brillar como se merecen!");
        }
    }

}
