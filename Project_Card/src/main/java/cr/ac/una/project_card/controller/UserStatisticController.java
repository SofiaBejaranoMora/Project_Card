package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.Game;
import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.PlayerDto;
import io.github.palexdev.materialfx.utils.FXCollectors;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
    private BarChart<String, Integer> bctPlayGames;
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
    
    //PlayerDto
    PlayerDto player;
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
   
    private void configurePastelGraphic() {
        pieChartData.clear(); // Limpia la lista antes de agregar nuevos datos
        for (GameDto game : player.getGameList()) {
            double percentage = (game.getScore() / player.getAccumulatedPoint()) * 100; // Porcentaje
            String nombreJuego="nombre de juego";
            pieChartData.add(new PieChart.Data(nombreJuego, percentage));
        }
        pctGamesData.setData(pieChartData); // Asigna los datos al PieChart
    }
    
    private void configureVictoriesPercentage(){
        Integer totalWons=0;
        for(GameDto game: player.getGameList()){
            if(game.getHasWon().equals("S")){//creo que es asi
                totalWons++;
            }
        }
        Integer percentage = (totalWons / player.getGameList().size()) * 100;
        String percentageStr=percentage.toString();
        lblVictories.setText(percentageStr);
        
        //una notita
        String nota;
        if (percentage >= 80) {
            nota = "¡Excelente desempeño!";
        } else if (percentage >= 50) {
            nota = "Buen trabajo, sigue mejorando.";
        } else {
            nota = "Puedes hacerlo mejor, sigue practicando.";
        }
        lblNote.setText(nota);
    }
   

    private void configurePlayedGames() {
        int playedGames = 0;
        int notPlayedGames = 0;

        for (GameDto game : player.getGameList()) {
            if (game.getTime() > 0) {
                playedGames++;
            } else {
                notPlayedGames++;
            }
        }

        // Crear una serie de datos
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Estado de Juegos");
        series.getData().add(new XYChart.Data<>("Jugados", playedGames));
        series.getData().add(new XYChart.Data<>("No Jugados", notPlayedGames));

        // Limpiar el gráfico y agregar la serie
        bctPlayGames.getData().clear();
        bctPlayGames.getData().add(series);
    }
    
    private void configureFirstTab(){
        configurePastelGraphic();
        configureVictoriesPercentage();
        configurePlayedGames();
    }
    
    private void configureAcumulatePoints() {
        lblAcomulatePoints.setText(player.getAccumulatedPoint().toString());//solo pone los puntos acumulados
    }

    
    
    
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
