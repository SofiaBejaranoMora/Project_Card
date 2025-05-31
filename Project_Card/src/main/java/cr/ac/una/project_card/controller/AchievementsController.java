package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.AchievementDto;
import cr.ac.una.project_card.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AchievementsController extends Controller implements Initializable {

    private Image image;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnStatistics;
    
    @FXML
    private void onActionBtnStatistics(ActionEvent event) {
        FlowController.getInstance().goView("UserStatisticView");
    }

    @FXML
    private void onActionBtnBack(ActionEvent event) {
        FlowController.getInstance().goView("MenuView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void UploadReview(AchievementDto achievement, VBox vBox) {

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setSpacing(15);
        HBox.setMargin(hbox, new Insets(15));

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setSpacing(15);
        VBox.setMargin(vbox, new Insets(15));

        image = new Image(/*raqui va la ruta +*/ achievement.getName()+ ".png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(182);
        imageView.setFitHeight(40);
        imageView.setPreserveRatio(true);
        
        TextField textField = new TextField("Name: "+achievement.getName());
       // textField.setWrapText(true);
        textField.setDisable(true);
       
        TextArea textArea = new TextArea("Tipo: "+achievement.getType()+ "\n" +"Cantidad: "+ achievement.getAmount()+ "\n" + achievement.getDescription());
        textArea.setWrapText(true);
        textArea.setDisable(true);

        textArea.setPrefWidth(200);
        textArea.setPrefHeight(90);

        hbox.getChildren().addAll(imageView, textArea);
        hbox.prefWidthProperty().bind(hbox.widthProperty().multiply(0.98));
        vBox.getChildren().addAll(hbox);
    }

    @Override
    public void initialize() {

    }


}
