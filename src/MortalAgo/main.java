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
    private int statpoints = 4;
    private double volumeMultiplier = 0;
    private MediaPlayer mediaPlayerMenu, mediaPlayerGame;

    private void startGame(Stage stage) {
        int roundcounter = 0;
        int agoAttack = 8;
        int agoMaxHp = 100;
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

        String musicFile = "src/MortalAgo/Media/K2h.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayerMenu = new MediaPlayer(sound);
        mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerMenu.setVolume(volumeMultiplier);
        mediaPlayerMenu.play();

        MenuItem play = menuItems.get(0);
        play.setOnMouseClicked(event -> {
            mediaPlayerMenu.stop();
            root.getChildren().remove(img);
            root.getChildren().removeAll(menuItems);
            doPlayAction(root, scene, stage, roundcounter, agoAttack, agoMaxHp);
        });

        MenuItem settings = menuItems.get(1);
        settings.setOnMouseClicked(event -> {
            doSettingsAction(root, scene, stage);
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

    private void restartGame(Stage stage) {
        stage.close();
        startGame(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        startGame(stage);
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

    private void doPlayAction(Group root, Scene scene, Stage stage, int roundcounter, int agoAttack, int agoMaxHp) {
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
        Player ago = new Ago(player, playerLogo, test, agoAttack, agoMaxHp);
        Player vastane = null;
        if (roundcounter == 0) {
            vastane = new Gert(enemy2, enemy2Logo, test, 10, 105);
        } else {
            vastane = new Kruus(enemy, enemyLogo, test, 7, 100);
        }
        test.drawEnemy(vastane, 400.0, 310.0);
        System.out.println(test.getEnemy());
        test.drawAgo(ago, 100.0,310.0);
        test.drawHpRectangle(ago);
        test.drawHpRectangle(vastane);
        test.drawStaminaRectangle(ago);
        test.drawStaminaRectangle(vastane);

        String musicFile = "src/MortalAgo/Media/mission_impossible.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayerGame = new MediaPlayer(sound);
        mediaPlayerGame.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerGame.play();
        mediaPlayerGame.setVolume(0.3 * volumeMultiplier);
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (test.getPlayer().isDead()) {
                    makeLoseWindow(root, scene, stage, test);
                    mediaPlayerGame.stop();
                    this.stop();
                } else if (test.getEnemy().isDead()) {
                    makeStatsWindow(root, scene, stage, test, roundcounter);
                    mediaPlayerGame.stop();
                    this.stop();
                }
            }
        }.start();
    }

    private void doSettingsAction(Group root, Scene scene, Stage stage) {
        root.getChildren().clear();
        Text volumeText = new Text(startButtonX - 20, startButtonY, "Volume: " + (this.volumeMultiplier * 100));
        volumeText.setFont(new Font("Comic Sans", 24));
        root.getChildren().add(volumeText);

        MenuItem menuButton = new MenuItem("Main menu");
        menuButton.setTranslateX(startButtonX);
        menuButton.setTranslateY(startButtonY + 150);
        root.getChildren().add(menuButton);
        menuButton.setOnMouseClicked(mouseEvent -> {
            restartGame(stage);
        });

        Button volumeIncreaseButton = makeStatButton(startButtonX, startButtonY, "Increase");
        root.getChildren().add(volumeIncreaseButton);
        volumeIncreaseButton.setOnMouseClicked(mouseEvent -> {
            if (volumeMultiplier >= 1) {
                System.out.println("Volume is at max");
            } else {
                this.volumeMultiplier += 0.1;
                mediaPlayerMenu.setVolume(this.volumeMultiplier);
                volumeText.setText("Volume: " + Math.round((this.volumeMultiplier * 100)));
            }
        });

        Button volumeDecreaseButton = makeStatButton(startButtonX, startButtonY + 50, "Decrease");
        root.getChildren().add(volumeDecreaseButton);
        volumeDecreaseButton.setOnMouseClicked(mouseEvent -> {
            if (volumeMultiplier <= 0) {
                System.out.println("Volume is at min");
            } else {
                this.volumeMultiplier -= 0.1;
                mediaPlayerMenu.setVolume(this.volumeMultiplier);
                volumeText.setText("Volume: " + Math.round((this.volumeMultiplier * 100)));
            }
        });
    }

    private void makeStatsWindow(Group root, Scene scene, Stage stage, World world, int roundcounter) {
        world.getRoot().getChildren().clear();
        statpoints = 4;

        Text stats = new Text(280, 180, "Statpoints: " + statpoints);
        stats.setFont(new Font("Comic Sans", 30));
        root.getChildren().add(stats);

        Text Attack = new Text(280, 230, "Attack: " + world.getPlayer().getAttack());
        Attack.setFont(new Font("Comic Sans", 24));
        root.getChildren().add(Attack);

        Text hitPoints = new Text(280, 280, "Hitpoints: " + world.getPlayer().getMaxHp());
        hitPoints.setFont(new Font("Comic Sans", 24));
        root.getChildren().add(hitPoints);

        Button AttackButton = makeStatButton(450, 200, "+1 Attack");
        root.getChildren().add(AttackButton);
        AttackButton.setOnMouseClicked(mouseEvent -> {
            if (statpoints <= 0) {
                System.out.println("Not enough statpoints");
            } else {
                world.getPlayer().setAttack(world.getPlayer().getAttack() + 1);
                reduceStatpoints(stats, Attack, hitPoints, world);
            }
        });

        Button hitPointsButton = makeStatButton(450, 250, "+5 Hitpoints");
        root.getChildren().add(hitPointsButton);
        hitPointsButton.setOnMouseClicked(mouseEvent -> {
            if (statpoints <= 0) {
                System.out.println("Not enough statpoints");
            } else {
                world.getPlayer().setMaxHp((world.getPlayer().getMaxHp() + 5));
                reduceStatpoints(stats, Attack, hitPoints, world);
            }
        });

        MenuItem continueGame = new MenuItem("Continue");
        continueGame.setTranslateX(250);
        continueGame.setTranslateY(400);
        root.getChildren().add(continueGame);
        continueGame.setOnMouseClicked(mouseEvent -> {
            makeNewWorld(root, scene, stage, world, roundcounter, world.getPlayer().getAttack(), world.getPlayer().getMaxHp());
        });

        MenuItem quitGame = new MenuItem("Quit");
        quitGame.setTranslateX(250);
        quitGame.setTranslateY(450);
        root.getChildren().add(quitGame);
        quitGame.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
    }

    private void makeNewWorld(Group root, Scene scene, Stage stage, World world, int roundcounter, int agoAttack, int agoMaxHp) {
        world.getRoot().getChildren().clear();
        doPlayAction(root, scene, stage, roundcounter + 1, agoAttack, agoMaxHp);
    }

    private void makeLoseWindow(Group root, Scene scene, Stage stage, World world) {
        world.getRoot().getChildren().clear();

        Text text = new Text("You died, git gud");
        text.setFont(new Font("Comic Sans", 36));
        text.setX(260);
        text.setY(100);
        root.getChildren().add(text);

        MenuItem mainMenu = new MenuItem("Return to Main Menu");
        mainMenu.setTranslateX(290);
        mainMenu.setTranslateY(150);
        root.getChildren().add(mainMenu);
        mainMenu.setOnMouseClicked(mouseEvent -> {
            restartGame(stage);
        });

        MenuItem quitGame = new MenuItem("Quit");
        quitGame.setTranslateX(290);
        quitGame.setTranslateY(250);
        root.getChildren().add(quitGame);
        quitGame.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
    }

    private void reduceStatpoints(Text stats, Text Attack, Text hitPoints, World world) {
        statpoints -= 1;
        stats.setText("Statpoints: " + statpoints);
        Attack.setText("Attack: " + world.getPlayer().getAttack());
        hitPoints.setText("Hitpoints: " + world.getPlayer().getMaxHp());
    }

    private Button makeStatButton(int x, int y, String text) {
        Button AttackButton = new Button();
        AttackButton.setLayoutX(x);
        AttackButton.setLayoutY(y);
        AttackButton.setText(text);
        AttackButton.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\n" +
                "    -fx-background-radius: 100;\n" +
                "    -fx-background-insets: 0;\n" +
                "    -fx-font-size: 18;\n" +
                "    -fx-text-fill: white;");
        AttackButton.setOnMouseEntered(event -> {
            AttackButton.setOpacity(0.7);
        });
        AttackButton.setOnMouseExited(event -> {
            AttackButton.setOpacity(1);
        });
        AttackButton.setOnMousePressed(event -> {
            AttackButton.setOpacity(0.3);
        });
        AttackButton.setOnMouseReleased(event -> {
            AttackButton.setOpacity(1);
        });
        return AttackButton;
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

    public static void main(String[] args) {
        launch(args);
    }
}
