package cr.ac.una.project_card.model;

import cr.ac.una.project_card.util.ImagesUtil;
import java.net.URL;
import javafx.scene.media.AudioClip;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/** * * * @author ashly */
public class AnimationAndSound {

    public static void move(Node node, Point2D rute, Double duration) {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration),
                new KeyValue(node.layoutXProperty(), rute.getX()),
                new KeyValue(node.layoutYProperty(), rute.getY())));

        timeline.play();
    }

    public static void startTransition(Pane animationPane, Runnable change) {
        Pane panel = new Pane();
        ImageView image = new ImageView();
        ImageView gif = new ImageView();

        double width = animationPane.getWidth();
        double heigth = animationPane.getHeight();

        image.setImage(new Image(ImagesUtil.getAnimationImage("Animation")));
        gif.setImage(new Image(ImagesUtil.getAnimationGif("changeViewGif")));
        gif.setFitWidth(390);
        gif.setFitHeight(390);
        panel.getChildren().add(image);
        panel.getChildren().add(gif);
        animationPane.getChildren().add(panel);
        animationPane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            panel.setPrefHeight(newHeight.doubleValue());
            image.setFitHeight(newHeight.doubleValue());
        });
        panel.setPrefWidth(width);
        panel.setPrefHeight(heigth);
        image.setFitHeight(heigth);
        image.setPreserveRatio(true);
        panel.setLayoutX(width);
        gif.layoutXProperty().bind(panel.widthProperty().subtract(gif.fitWidthProperty()).divide(0.8));
        gif.setLayoutY((heigth / 2) - 195);
        
        Timeline startTransition = new Timeline();
        startTransition.getKeyFrames().add(new KeyFrame(Duration.millis(2500),
                new KeyValue(panel.layoutXProperty(), -300)));

        Timeline finishTransition = new Timeline();
        finishTransition.getKeyFrames().add(new KeyFrame(Duration.millis(2500),
                new KeyValue(panel.layoutXProperty(), -width * 2)));

        finishTransition.setOnFinished(e -> {
            animationPane.getChildren().remove(panel);
        });

        startTransition.setOnFinished(e -> {
            change.run();
            finishTransition.play();
        });

        startTransition.play();
    }
    
    public static void turnCardsAnimation(ImageView card, Image newCard) {
        card.setRotationAxis(new Point3D(1, 0, 0));
        card.setRotate(0);
        Timeline firstPartRotate = new Timeline();
        firstPartRotate.getKeyFrames().add(
                new KeyFrame(Duration.millis(150),
                new KeyValue(card.rotateProperty(), 90)));
        
        Timeline secondPartRotate = new Timeline();
        secondPartRotate.getKeyFrames().add(
                new KeyFrame(Duration.millis(150),
                new KeyValue(card.rotateProperty(), 0)));
        
        firstPartRotate.setOnFinished(e -> {
            card.setImage(newCard);
            secondPartRotate.play();
        });
        firstPartRotate.play();
    }
    
    public static void buttonSound() {
        URL url = AnimationAndSound.class.getResource("/cr/ac/una/project_card/resources/Sounds/buttonSound.wav");
        if (url != null) {
            AudioClip clip = new AudioClip(url.toString());
            clip.play();
        }
    }
    
    public static void clueMovesSound() {
        URL url = AnimationAndSound.class.getResource("/cr/ac/una/project_card/resources/Sounds/clueMovesSound.wav");
        if (url != null) {
            AudioClip clip = new AudioClip(url.toString());
            clip.play();
        }
    }
    
    public static void clueNoMovesSound() {
        URL url = AnimationAndSound.class.getResource("/cr/ac/una/project_card/resources/Sounds/clueNoMovesSound.wav");
        if (url != null) {
            AudioClip clip = new AudioClip(url.toString());
            clip.play();
        }
    }
    
    public static void achievementSound() {
        URL url = AnimationAndSound.class.getResource("/cr/ac/una/project_card/resources/Sounds/achievementSound.wav");
        if (url != null) {
            AudioClip clip = new AudioClip(url.toString());
            clip.play();
        }
    }
    
    public static void multiUseSound() {
        URL url = AnimationAndSound.class.getResource("/cr/ac/una/project_card/resources/Sounds/multiUseSound.wav");
        if (url != null) {
            AudioClip clip = new AudioClip(url.toString());
            clip.play();
        }
    }

    public static void playMusic() {
        Media media = new Media(AnimationAndSound.class.getResource("/cr/ac/una/project_card/resources/Sounds/backSound.mp3").toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }

}
