package cr.ac.una.project_card.controller;

import cr.ac.una.project_card.model.GameDto;
import cr.ac.una.project_card.model.PlayerDto;
import cr.ac.una.project_card.util.AppContext;
import cr.ac.una.project_card.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private BarChart<String, Number> bctAveragePoints;
    
    //PlayerDto
    PlayerDto player;
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
   
    private void configurePastelGraphic() {
        pieChartData.clear();
        double accumulatedPoints = player.getAccumulatedPoint() != null ? player.getAccumulatedPoint().doubleValue() : 0;
        int gameCount = 0;
        for (GameDto game : player.getGameList()) {
            double score;
            if (game.getScore() != null) {
                score = game.getScore().doubleValue();
            } else {
                score = 0;
            }
            double percentage;
            if (accumulatedPoints > 0) {
                percentage = (score / accumulatedPoints) * 100;
            } else {
                percentage = 0;
            }
            String nombreJuego = game.getName();
            pieChartData.add(new PieChart.Data(nombreJuego, percentage));
            gameCount++;
        }
        pctGamesData.setData(pieChartData);
    }
    
    private void configureVictoriesPercentage() {
        int totalWons = 0;
        int totalGames = 0;

        if (player != null && player.getGameList() != null) {
            totalGames = player.getGameList().size();

            for (GameDto game : player.getGameList()) {
                if ("T".equals(game.getHasWon())) {
                    totalWons++;
                }
            }
        }

        double percentage = 0;
        if (totalGames > 0) {
            percentage = (totalWons * 100.0) / totalGames;
        }

        if (totalGames > 0) {
            lblVictories.setText(Double.toString(percentage));
        } else {
            lblVictories.setText("0");
        }

        String nota;
        if (totalGames == 0) {
            nota = "No hay juegos registrados.";
        } else if (percentage >= 80) {
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

        if (player != null && player.getGameList() != null) {
            for (GameDto game : player.getGameList()) {
                if (game.getTime() > 0) {
                    playedGames++;
                } else {
                    notPlayedGames++;
                }
            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Estado de Juegos");
        if (playedGames == 0 && notPlayedGames == 0) {
            series.getData().add(new XYChart.Data<>("Sin juegos", 0));
        } else {
            series.getData().add(new XYChart.Data<>("Jugados", playedGames));
            series.getData().add(new XYChart.Data<>("No Jugados", notPlayedGames));
        }

        bctPlayGames.getData().clear();
        bctPlayGames.getData().add(series);
    }
    private void configureFirstTab(){
        configurePastelGraphic();
        configureVictoriesPercentage();
        configurePlayedGames();
    }
    
    
    private void configureAcumulatePoints() {
        Long totalPoints = (player != null && player.getAccumulatedPoint() != null) ? player.getAccumulatedPoint() : 0L;
        lblAcomulatePoints.setText(totalPoints.toString());

        String note;
        if (player == null) {
            note = "No hay datos del jugador.";
        } else if (totalPoints >= 1000) {
            note = "¡Increíble! Estás en la élite";
        } else if (totalPoints >= 500) {
            note = "Vas bien, sigue acumulando";
        } else {
            note = "¡No te rindas! A por más puntos";
        }
        lblNoteAcomulatePoints.setText(note);
    }
    
    private void configureBestPuntuation() {
        double bestPuntuation = 0;
        if (player != null && player.getGameList() != null) {
            for (GameDto game : player.getGameList()) {
                Long score = game.getScore() != null ? game.getScore() : 0L;
                if (score > bestPuntuation) {
                    bestPuntuation = score;
                }
            }
        }
        String bestPuntuationStr = String.format("%.2f", bestPuntuation);
        lblBestPuntuation.setText(bestPuntuationStr);

        String nota;
        if (player == null || player.getGameList() == null || player.getGameList().isEmpty()) {
            nota = "No hay juegos registrados.";
        } else if (bestPuntuation >= 80) {
            nota = "¡Impresionante! Estás en otro nivel.";
        } else if (bestPuntuation >= 50) {
            nota = "Buen trabajo, pero puedes superarte aún más.";
        } else {
            nota = "No te rindas, sigue practicando. ¡La próxima vez será mejor!";
        }
        lblNoteBestPuntuation.setText(nota);
    }
    
    private void configureAveragePoints() {
        double totalScore = 0;
        int gameCount = 0;

        XYChart.Series<String, Number> scoreSeries = new XYChart.Series<>();
        scoreSeries.setName("Puntajes por Juego");
        XYChart.Series<String, Number> averageSeries = new XYChart.Series<>();

        if (player != null && player.getGameList() != null) {
            for (GameDto game : player.getGameList()) {
                Long score = game.getScore() != null ? game.getScore() : 0L;
                String gameName = "Juego " + (gameCount + 1);
                scoreSeries.getData().add(new XYChart.Data<>(gameName, score));
                totalScore += score;
                gameCount++;
            }
        }

        double average = gameCount > 0 ? totalScore / gameCount : 0;
        averageSeries.setName("Promedio: " + String.format("%.1f", average));

        if (gameCount == 0) {
            scoreSeries.getData().add(new XYChart.Data<>("Sin juegos", 0));
            averageSeries.getData().add(new XYChart.Data<>("Sin juegos", 0));
        } else {
            for (int i = 0; i < gameCount; i++) {
                averageSeries.getData().add(new XYChart.Data<>(scoreSeries.getData().get(i).getXValue(), average));
            }
        }

        bctAveragePoints.getData().clear();
        bctAveragePoints.getData().addAll(scoreSeries, averageSeries);
    }
    
    private void configureSecondTab(){
        configureAcumulatePoints();
        configureBestPuntuation();
        configureAveragePoints();
    }
    @FXML
    private void onActionBtnBack(ActionEvent event) {
        
     FlowController.getInstance().goView("MenuView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @Override
    public void initialize() {
        player = (PlayerDto) AppContext.getInstance().get("CurrentUser");

        configureFirstTab();
        configureSecondTab();
    }

}
