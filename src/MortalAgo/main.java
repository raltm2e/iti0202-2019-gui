package MortalAgo;

import MortalAgo.Characters.Ago;
import MortalAgo.Characters.Kruus;
import MortalAgo.Characters.Player;
import MortalAgo.Levels.World;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;


public class main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = new Image( "file:src\\MortalAgo\\Media\\Background\\IT_Kolledž.jpg" );
        World test = new World("test", background, root, scene);
        System.out.println(test.getWith());
        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        Rectangle player = new Rectangle(50.0, 100.0, 130, 290);
        Rectangle enemy = new Rectangle(50.0, 100.0, 130, 290);
        Image playerLogo = new Image( "file:src\\MortalAgo\\Media\\Characters\\Ago\\ago_breathing.gif" );
        Image enemyLogo = new Image("file:src\\MortalAgo\\Media\\Characters\\Kruus\\kruus_breathing.gif");
        Player ago = new Ago(player, playerLogo, test);
        Player kruus = new Kruus(enemy, enemyLogo, test);
        test.drawEnemy(kruus, 400.0, 310.0);
        test.drawAgo(ago, 100.0,310.0);
        test.drawHpRectangle(ago);
        test.drawHpRectangle(kruus);

        long startNanoTime = System.nanoTime();
        String musicFile = "src/MortalAgo/Media/K2h.mp3";     // For example

        Image stick = new Image( "file:src\\MortalAgo\\Media\\stickman.gif" );
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
