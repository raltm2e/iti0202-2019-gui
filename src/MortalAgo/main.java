package MortalAgo;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;


public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        double playerXPosition = 10, playerYPosition = 500;
        stage.setScene(scene);
        stage.show();


        Canvas canvas = new Canvas(600, 600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.fillRect(playerXPosition, playerYPosition, 40, 50);

        Rectangle player = new Rectangle(100 , 100 , 200, 300);
        Circle moveRight = new Circle(44, 470, 10);
        Circle moveLeft = new Circle(16, 470, 10);
        Image right = new Image( new FileInputStream("src\\MortalAgo\\Right.png") );
        moveRight.setFill(new ImagePattern(right));
        // scene.setFill(new ImagePattern(right));
        root.getChildren().add(moveLeft);
        root.getChildren().add(moveRight);

        moveRight.setOnMouseClicked(mouseEvent -> {
            Timeline animation = new Timeline();
            animation.setCycleCount(20);

            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        gc.fillRect( moveRight.getCenterX() - 32 , playerYPosition, 40, 50);
                        moveRight.setCenterX(moveRight.getCenterX() + 2);
                        moveLeft.setCenterX(moveLeft.getCenterX() + 2);
                    }));
            animation.play();
        });
        moveLeft.setOnMouseClicked(mouseEvent -> {
            Timeline animation = new Timeline();
            animation.setCycleCount(20);

            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        gc.fillRect( moveLeft.getCenterX() - 7 , playerYPosition, 40, 50);
                        moveRight.setCenterX(moveRight.getCenterX() - 2);
                        moveLeft.setCenterX(moveLeft.getCenterX() - 2);
                    }));
            animation.play();
        });
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


        long startNanoTime = System.nanoTime();
        String musicFile = "src/MortalAgo/K2h.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

       /** new AnimationTimer() {
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
        }.start(); */
    }

    public static void main(String[] args) {
        launch(args);
    }
}
