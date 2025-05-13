package cr.ac.una.project_card.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/** * FXML Controller class * * @author ashly */
public class CreateGameController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfNameGame;
    @FXML
    private ImageView mgvEasyMode;
    @FXML
    private ImageView mgvMediumMode;
    @FXML
    private ImageView mgvHardMode;
   
    
      //Clase auxiliar para manejar cartas
    
    public static class DynamicCard{
        private Boolean isTurned;
        private String frontImageName;
        private String backImageName;
        
        public DynamicCard(){
            isTurned=false;
            frontImageName=null;
            backImageName="temporaryIshakan";
        }
        
        public void setFrontImage(String frontImagePath){
            frontImageName=frontImagePath;
        }
    }
   
    
    @FXML
    private void onKeyPressed(KeyEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
    }
    
    public void setCardImage(){
        
    }

}
