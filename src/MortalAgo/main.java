package MortalAgo;

import MortalAgo.Characters.Ago;
import MortalAgo.Characters.Gert;
import MortalAgo.Characters.Kruus;
import MortalAgo.Characters.Player;
import MortalAgo.Levels.World;
import MortalAgo.statbutton.StatButton;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
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
        int roundcounter = 0;
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
            root.getChildren().removeAll(menuItems);
            doPlayAction(root, scene, stage, roundcounter);
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

    private void doPlayAction(Group root, Scene scene, Stage stage, int roundcounter) {
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
        Player vastane = null;
        if (roundcounter == 0) {
            vastane = new Kruus(enemy, enemyLogo, test);
        } else {
            vastane = new Gert(enemy2, enemy2Logo, test);
        }
        test.drawEnemy(vastane, 400.0, 310.0);
        test.drawAgo(ago, 100.0,310.0);
        test.drawHpRectangle(ago);
        test.drawHpRectangle(vastane);
        test.drawStaminaRectangle(ago);
        test.drawStaminaRectangle(vastane);

        long startNanoTime = System.nanoTime();
        String musicFile = "src/MortalAgo/Media/K2h.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                boolean endGame = false;
                double delta = (currentNanoTime - startNanoTime) / 1_000_000_000;
                double control = 100 + 20 * delta;
                if (test.getPlayer().isDead()) {
                    makeLoseWindow(root, scene, stage, test);
                    this.stop();
                } else if (test.getEnemy().isDead()) {
                    makeStatsWindow(root, scene, stage, test, roundcounter);
                    this.stop();
                }
                if ( control == 100) {
                    //mediaPlayer.play();
                }
            }
        }.start();
    }

    private void makeStatsWindow(Group root, Scene scene, Stage stage, World world, int roundcounter) {
        world.getRoot().getChildren().clear();

        Text strength = new Text(240, 200, "Strength");
        strength.setFont(new Font("Comic Sans", 24));
        root.getChildren().add(strength);

        Button strengthButton = makeStatButton(world, 300, 180, "+1 Strength");
        root.getChildren().add(strengthButton);

        MenuItem continueGame = new MenuItem("Continue");
        continueGame.setTranslateX(250);
        continueGame.setTranslateY(500);
        root.getChildren().add(continueGame);
        continueGame.setOnMouseClicked(mouseEvent -> {
            makeNewWorld(root, scene, stage, world, roundcounter);
        });
    }

    private void makeNewWorld(Group root, Scene scene, Stage stage, World world, int roundcounter) {
        world.getRoot().getChildren().clear();
        doPlayAction(root, scene, stage, roundcounter + 1);
    }

    private void makeLoseWindow(Group root, Scene scene, Stage stage, World world) {
        world.getRoot().getChildren().clear();

        Text text = new Text("You died, git gud");
        text.setFont(new Font("Comic Sans", 36));
        text.setX(260);
        text.setY(100);
        root.getChildren().add(text);

        MenuItem quitGame = new MenuItem("Quit");
        quitGame.setTranslateX(290);
        quitGame.setTranslateY(250);
        root.getChildren().add(quitGame);
        quitGame.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
    }

    private Button makeStatButton(World world, int x, int y, String text) {
        Button strengthButton = new Button();
        strengthButton.setLayoutX(x);
        strengthButton.setLayoutY(y);
        strengthButton.setText(text);
        strengthButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\n" +
                "    -fx-background-radius: 100;\n" +
                "    -fx-background-insets: 0;\n" +
                "    -fx-font-size: 18;\n" +
                "    -fx-text-fill: white;");
        strengthButton.setOnMouseEntered(event -> {
            strengthButton.setOpacity(0.7);
        });
        strengthButton.setOnMouseExited(event -> {
            strengthButton.setOpacity(1);
        });
        strengthButton.setOnMousePressed(event -> {
            strengthButton.setOpacity(0.3);
        });
        strengthButton.setOnMouseReleased(event -> {
            strengthButton.setOpacity(1);
        });
        strengthButton.setOnMouseClicked(mouseEvent -> {
            world.getPlayer().setAttack(world.getPlayer().getAttack() + 1);
            System.out.println(world.getPlayer().getAttack());
        });
        return strengthButton;
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
