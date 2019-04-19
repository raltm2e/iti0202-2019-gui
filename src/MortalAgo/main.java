package MortalAgo;

import MortalAgo.Characters.Ago;
import MortalAgo.Characters.Gert;
import MortalAgo.Characters.Kruus;
import MortalAgo.Characters.Player;
import MortalAgo.Levels.World;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class main extends Application {
    private int startButtonY = 200, startButtonX = 530;

    @Override
    public void start(Stage stage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);

        ImageView img = new ImageView(new Image("file:src\\MortalAgo\\Media\\agoMenuBackground.gif"));
        img.setFitWidth(800);
        img.setFitHeight(600);
        root.getChildren().add(img);

        stage.setTitle("Mortal Ago");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        
        List<MenuItem> menuItems = makeMenuButtons();
        root.getChildren().addAll(menuItems);

        MenuItem play = menuItems.get(0);
        play.setOnMouseClicked(event -> {
            root.getChildren().remove(img);
            doPlayAction(root, scene);
            root.getChildren().removeAll(menuItems);
        });

        MenuItem settings = menuItems.get(1);
        settings.setOnMouseClicked(event -> {
            System.out.println("Settings");
        });

        MenuItem extras = menuItems.get(2);
        extras.setOnMouseClicked(event -> {
            System.out.println("Extras");
        });

        MenuItem exit = menuItems.get(3);
        exit.setOnMouseClicked(event -> {
            System.out.println("GG EZ");
            stage.close();
        });
    }

    private List<MenuItem> makeMenuButtons() {
        List<MenuItem> menuItems = new ArrayList<>();

        MenuItem play = new MenuItem("Play");
        MenuItem settings = new MenuItem("Settings");
        MenuItem extras = new MenuItem("Extras");
        MenuItem exit = new MenuItem("Exit");

        play.setTranslateX(startButtonX);
        play.setTranslateY(startButtonY);
        settings.setTranslateX(startButtonX);
        settings.setTranslateY(startButtonY + 50);
        extras.setTranslateX(startButtonX);
        extras.setTranslateY(startButtonY + 100);
        exit.setTranslateX(startButtonX);
        exit.setTranslateY(startButtonY + 150);

        menuItems.add(play);
        menuItems.add(settings);
        menuItems.add(extras);
        menuItems.add(exit);
        return menuItems;
    }

    private void doPlayAction(Group root, Scene scene) {
        Canvas canvas = new Canvas(600, 600);
        Image background = new Image( "file:src\\MortalAgo\\Media\\Background\\IT_Kolledž.jpg" );
        World test = new World("test", background, root, scene);
        System.out.println(test.getWith());
        root.getChildren().add(canvas);
        Rectangle player = new Rectangle(50.0, 100.0, 130, 290);
        Rectangle enemy = new Rectangle(50.0, 100.0, 130, 290);
        Rectangle enemy2 = new Rectangle(50.0, 100.0, 130, 290);
        Image playerLogo = new Image( "file:src\\MortalAgo\\Media\\Characters\\Ago\\ago_breathing.gif" );
        Image enemyLogo = new Image("file:src\\MortalAgo\\Media\\Characters\\Kruus\\kruus_breathing.gif");
        Image enemy2Logo = new Image("file:src\\MortalAgo\\Media\\Characters\\Gert\\breathing.gif");
        Player ago = new Ago(player, playerLogo, test);
        Player kruus = new Kruus(enemy, enemyLogo, test);
        Player gert = new Gert(enemy2, enemy2Logo, test);
        test.drawEnemy(gert, 400.0, 310.0);
        test.drawAgo(ago, 100.0,310.0);
        test.drawHpRectangle(ago);
        test.drawHpRectangle(gert);
        test.drawStaminaRectangle(ago);
        test.drawStaminaRectangle(gert);

        long startNanoTime = System.nanoTime();
        String musicFile = "src/MortalAgo/Media/K2h.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double delta = (currentNanoTime - startNanoTime) / 1_000_000_000;
                double control = 100 + 20 * delta;
                if ( control == 100) {
                    //mediaPlayer.play();
                }
            }
        }.start();
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.DARKBLUE),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKBLUE)

            });

            Rectangle bg = new Rectangle(250,50);
            bg.setOpacity(0.8);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Comic Sans", FontWeight.SEMI_BOLD,20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);

            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });
            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET);

            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });

        }
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//
//        Group root = new Group();
//        Scene scene = new Scene(root, 800, 600);
//        Canvas canvas = new Canvas(600, 600);
//        Image background = new Image( "file:src\\MortalAgo\\Media\\Background\\IT_Kolledž.jpg" );
//        World test = new World("test", background, root, scene);
//        System.out.println(test.getWith());
//        root.getChildren().add(canvas);
//        stage.setScene(scene);
//        stage.show();
//        stage.setResizable(false);
//        Rectangle player = new Rectangle(50.0, 100.0, 130, 290);
//        Rectangle enemy = new Rectangle(50.0, 100.0, 130, 290);
//        Image playerLogo = new Image( "file:src\\MortalAgo\\Media\\Characters\\Ago\\ago_breathing.gif" );
//        Image enemyLogo = new Image("file:src\\MortalAgo\\Media\\Characters\\Kruus\\kruus_breathing.gif");
//        Player ago = new Ago(player, playerLogo, test);
//        Player kruus = new Kruus(enemy, enemyLogo, test);
//        test.drawEnemy(kruus, 400.0, 310.0);
//        test.drawAgo(ago, 100.0,310.0);
//        test.drawHpRectangle(ago);
//        test.drawHpRectangle(kruus);
//
//        long startNanoTime = System.nanoTime();
//        String musicFile = "src/MortalAgo/Media/K2h.mp3";     // For example
//
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        new AnimationTimer() {
//            @Override
//            public void handle(long currentNanoTime) {
//                double delta = (currentNanoTime - startNanoTime) / 1_000_000_000;
//                double control = 100 + 20 * delta;
//                if ( control == 100) {
//                    mediaPlayer.play();
//                }
//            }
//        }.start();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
