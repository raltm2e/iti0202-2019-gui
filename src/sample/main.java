package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.show();

        Canvas canvas = new Canvas(600, 600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.fillRect(100, 0, 40, 50);

        Rectangle player = new Rectangle(100 , 100 , 200, 300);
        Circle moveRight = new Circle(50, 100, 20);
        root.getChildren().add(moveRight);
        moveRight.setOnMouseClicked(mouseEvent ->
                moveRight.setCenterX(moveRight.getCenterX() + 5));
        //root.getChildren().add(player);

        player.setOnMouseClicked(mouseEvent -> {
            player.setX(player.getX() + 10);
        });
        root.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.A){
                player.setX(player.getX() - 20);
            } else if (keyEvent.getCode() == KeyCode.D){
                player.setX(player.getX() + 20);
            } else if (keyEvent.getCode() == KeyCode.W){
                player.setY(player.getY() - 20);
            } else if (keyEvent.getCode() == KeyCode.S){
                player.setY(player.getY() + 20);
            }
            System.out.println(keyEvent);
        });
        root.setFocusTraversable(true);
        root.requestFocus();

        Button play = new Button("Play");
        root.getChildren().add(play);

        play.setOnAction(actionEvent -> {
            Timeline timeline = new Timeline();
            timeline.setCycleCount(10);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    actionEvent1 -> {
                player.setY(player.getY() + 10);
                    }));
            timeline.play();
        });
        long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double delta = (currentNanoTime - startNanoTime) / 1_000_000_000;
                player.setX(player.getX() + 0.2 * delta);
                double control = 100 + 20 * delta;
                if ( control == 0 ) {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.fillRect(100, control, 40, 50);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
