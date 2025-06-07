package cr.ac.una.project_card.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/** * FXML Controller class * * @author ashly */
public class PrincipalController extends Controller implements Initializable {
    
    @FXML
    private BorderPane root;
    @FXML
    private Pane transitionPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transitionPane.setMouseTransparent(true);
    }    

    @Override
    public void initialize() {
    }
    
}
