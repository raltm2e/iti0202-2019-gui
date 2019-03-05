package MortalAgo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;


public class main extends Application {
    public double playerXPosition = 10, playerYPosition = 500;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();
        Rectangle player = new Rectangle(50.0, 100.0, 300, 400);
        Image playerLogo = new Image( new FileInputStream("src\\MortalAgo\\Media\\stickman.gif") );
        Player ago = new Player("test", player, playerLogo);
        World test = new World("test",root);
        test.drawPlayer(ago, 100.0,200.0);

        long startNanoTime = System.nanoTime();
        String musicFile = "src/MortalAgo/Media/K2h.mp3";     // For example

        Image stick = new Image( new FileInputStream("src\\MortalAgo\\Media\\stickman.gif") );
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double delta = (currentNanoTime - startNanoTime) / 1_000_000_000;
                double control = 100 + 20 * delta;
                if ( control == 100) {
                    mediaPlayer.play();
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
