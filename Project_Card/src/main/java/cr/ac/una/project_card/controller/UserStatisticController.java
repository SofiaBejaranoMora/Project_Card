package cr.ac.una.project_card.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/** * * @author ashly */
public class UserStatisticController extends Controller implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Label lablUser;
    @FXML
    private ImageView mgvUserPhoto;
    @FXML
    private PieChart pctGamesData;
    @FXML
    private Label lblVictories;
    @FXML
    private Label lblNote;
    @FXML
    private BarChart<?, ?> bctPlayGames;
    @FXML
    private Label lblAcomulatePoints;
    @FXML
    private Label lblNoteAcomulatePoints;
    @FXML
    private Label lblBestPuntuation;
    @FXML
    private Label lblNoteBestPuntuation;
    @FXML
    private BarChart<?, ?> bctAveragePoints;

    @FXML
    private void onActionBtnBack(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }
    
    @Override
    public void initialize() {
    
    }

}
