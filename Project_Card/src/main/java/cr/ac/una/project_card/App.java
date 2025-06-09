package cr.ac.una.project_card;

import cr.ac.una.project_card.model.AnimationAndSound;
import cr.ac.una.project_card.util.FlowController;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
        Font.loadFont(App.class.getResourceAsStream("/cr/ac/una/project_card/resources/ProgramFonts/FredokaOne-Regular.ttf"), 12);
        scene = new Scene(loadFXML("PrincipalView"), 800, 500);
        MFXThemeManager.addOn(scene,Themes.DEFAULT,Themes.LEGACY);
        stage.setTitle("Solitario WiashlyCreations");
        stage.getIcons().add(new Image(getClass().getResource("/cr/ac/una/project_card/resources/ProgramImages/CardsIcon.png").toExternalForm()));
        stage.setScene(scene);
        stage.show();
        FlowController.getInstance().goView("MenuView");
        AnimationAndSound.playMusic();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/"+ fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}