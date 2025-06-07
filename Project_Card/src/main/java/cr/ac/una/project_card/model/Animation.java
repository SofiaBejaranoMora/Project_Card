package cr.ac.una.project_card.model;

import cr.ac.una.project_card.util.ImagesUtil;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/** * * * @author ashly */
public class Animation {

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

        double width = animationPane.getWidth();
        double heigth = animationPane.getHeight();

        image.setImage(new Image(ImagesUtil.getAnimationImage("Animation")));
        panel.getChildren().add(image);
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
}

