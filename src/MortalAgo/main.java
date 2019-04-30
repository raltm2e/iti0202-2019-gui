package MortalAgo;

import MortalAgo.Characters.*;
import MortalAgo.Levels.World;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.Arrays;
import java.util.List;

public class main extends Application {
    private int startButtonY = 200, startButtonX = 530;
    private int statpoints = 4;
    private double volumeMultiplier = 0.2;
    private MediaPlayer mediaPlayerMenu, mediaPlayerGame, mediaPlayerLose, mediaPlayerStats;

    private void startGame(Stage stage) {
        int roundcounter = 0;
        int agoAttack = 10;
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

        String musicFile = "src/MortalAgo/Media/K2h.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayerMenu = new MediaPlayer(sound);
        mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerMenu.setVolume(volumeMultiplier);
        mediaPlayerMenu.play();

        configMenuButtons(root, stage, scene, img, roundcounter, agoAttack, agoMaxHp);
    }

    private void configMenuButtons(Group root, Stage stage, Scene scene, ImageView img, int roundcounter, int agoAttack, int agoMaxHp) {
        List<MenuItem> menuItems = makeMenuButtons();
        root.getChildren().addAll(menuItems);

        MenuItem play = menuItems.get(0);
        play.setOnMouseClicked(event -> {
            mediaPlayerMenu.stop();
            root.getChildren().remove(img);
            root.getChildren().removeAll(menuItems);
            doTutorialAction(root, scene, stage, roundcounter, agoAttack, agoMaxHp);
        });

        MenuItem settings = menuItems.get(1);
        settings.setOnMouseClicked(event -> {
            root.getChildren().removeAll(menuItems);
            doSettingsAction(root, scene, stage, img, roundcounter, agoAttack, agoMaxHp);
        });

        MenuItem extras = menuItems.get(2);
        extras.setOnMouseClicked(event -> {
            root.getChildren().removeAll(menuItems);
            doExtrasAction(root, scene, stage, img, roundcounter, agoAttack, agoMaxHp);
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

    private void doTutorialAction(Group root, Scene scene, Stage stage, int roundcounter, int agoAttack, int agoMaxHp) {
        Canvas canvas = new Canvas(600, 600);
        Image background = new Image( "file:src\\MortalAgo\\Media\\Background\\IT_Kolledž.jpg" );
        World test = new World("test", background, root, scene);
        root.getChildren().add(canvas);

        Text tutorialLabel = new Text("Aaviksoo saw \nAgo's 'Social media in \ntourism' and laughed. \nAgo is mad. \nGet revenge on them!");
        formatText(tutorialLabel,40);
        tutorialLabel.setTranslateX(startButtonX - 340);
        tutorialLabel.setTranslateY(startButtonY - 70);
        root.getChildren().add(tutorialLabel);

        MenuItem startGame = new MenuItem("Start");
        startGame.setTranslateX(startButtonX - 280);
        startGame.setTranslateY(startButtonY + 200);
        root.getChildren().add(startGame);
        startGame.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(startGame);
            root.getChildren().remove(tutorialLabel);
            doPlayAction(root, scene, stage, roundcounter, agoAttack, agoMaxHp);
        });
    }

    private void doExtrasAction(Group root, Scene scene, Stage stage, ImageView img, int roundcounter, int agoAttack, int agoMaxHp) {
        Text title = new Text(startButtonX + 10, startButtonY - 20, "Credits");
        title.setFont(new Font("Comic Sans MS", 30));
        root.getChildren().add(title);

        Text raul = new Text(startButtonX + 10, startButtonY + 30, "Raul Altmäe");
        makeAuthorText(raul, root);

        Text robert = new Text(startButtonX + 10, startButtonY + 80, "Robert Altmäe");
        makeAuthorText(robert, root);

        MenuItem menuButton = new MenuItem("Main menu");
        menuButton.setTranslateX(startButtonX);
        menuButton.setTranslateY(startButtonY + 150);
        root.getChildren().add(menuButton);
        menuButton.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(menuButton);
            root.getChildren().remove(title);
            root.getChildren().remove(raul);
            root.getChildren().remove(robert);
            configMenuButtons(root, stage, scene, img, roundcounter, agoAttack, agoMaxHp);
        });
    }

    private void doPlayAction(Group root, Scene scene, Stage stage, int roundcounter, int agoAttack, int agoMaxHp) {
        Canvas canvas = new Canvas(600, 600);
        Image background = new Image( "file:src\\MortalAgo\\Media\\Background\\IT_Kolledž.jpg" );
        World test = new World("test", background, root, scene);
        root.getChildren().add(canvas);
        Rectangle player = new Rectangle(50.0, 100.0, 130, 290);
        Rectangle enemy = new Rectangle(50.0, 100.0, 130, 290);
        Image playerLogo = new Image( "file:src\\MortalAgo\\Media\\Characters\\Ago\\ago_breathing.gif" );
        Image kruusLogo = new Image("file:src\\MortalAgo\\Media\\Characters\\Kruus\\kruus_breathing.gif");
        Image gertLogo = new Image("file:src\\MortalAgo\\Media\\Characters\\Gert\\breathing.gif");
        Image aaviksooLogo = new Image("file:src\\MortalAgo\\Media\\Characters\\aaviksoo\\aaviksoo_breathing.gif");
        Player ago = new Ago(player, playerLogo, test, agoAttack, agoMaxHp);
        Player vastane = null;
        if (roundcounter == 0) {
            vastane = new Kruus(enemy, kruusLogo, test, 13, 105);
        } else if (roundcounter == 1) {
            vastane = new Gert(enemy, gertLogo, test, 15, 120);
        } else {
            vastane = new Aaviksoo(enemy, aaviksooLogo, test, 18, 130);
        }
        test.drawEnemy(vastane, 580.0, 310.0);
        test.drawAgo(ago, 100.0,310.0);
        test.drawHpRectangle(ago);
        test.drawHpRectangle(vastane);
        test.drawStaminaRectangle(ago);
        test.drawStaminaRectangle(vastane);

        String musicFile = "src/MortalAgo/Media/mission_impossible.mp3";
        String musicFile2 = "src/MortalAgo/Media/baby_im_yours.mp3";
        String musicFile3 = "src/MortalAgo/Media/mortal_kombat.mp3";

        List<String> musicList = new ArrayList<>(Arrays.asList(musicFile, musicFile2, musicFile3));
        Media sound = new Media(new File(musicList.get(roundcounter)).toURI().toString());
        mediaPlayerGame = new MediaPlayer(sound);
        mediaPlayerGame.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerGame.play();
        mediaPlayerGame.setVolume(0.3 * volumeMultiplier);
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if (test.getPlayer().isDead()) {
                    makeLoseWindow(root, stage, test);
                    mediaPlayerGame.stop();
                    this.stop();
                } else if (test.getEnemy().isDead()) {
                    if (test.getEnemy() instanceof Aaviksoo || test.getEnemy() instanceof Kruus) {
                        mediaPlayerGame.stop();
                        makeWinWindow(root, stage, test);
                        this.stop();
                    } else {
                        makeStatsWindow(root, scene, stage, test, roundcounter);
                        mediaPlayerGame.stop();
                        this.stop();
                    }
                }
            }
        }.start();
    }

    private void makeWinWindow(Group root, Stage stage, World world) {
        world.getRoot().getChildren().clear();

        ImageView sanAndreas = new ImageView(new Image("file:src\\MortalAgo\\Media\\sanAndreas.png"));
        sanAndreas.setLayoutX(50);
        sanAndreas.setLayoutY(300);
        root.getChildren().add(sanAndreas);

        String musicFile = "src/MortalAgo/Media/san_andreas.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayerGame = new MediaPlayer(sound);
        mediaPlayerGame.play();
        mediaPlayerGame.setVolume(1);

        MenuItem mainMenu = new MenuItem("Play again");
        mainMenu.setTranslateX(290);
        mainMenu.setTranslateY(170);
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

    private void doSettingsAction(Group root, Scene scene, Stage stage, ImageView img, int roundcounter, int agoAttack, int agoMaxHp) {
        Text volumeText = new Text(startButtonX, startButtonY - 20, "Volume: " + Math.round(this.volumeMultiplier * 100));
        volumeText.setFont(new Font("Comic Sans MS", 24));
        root.getChildren().add(volumeText);

        Button volumeIncreaseButton = makeStatButton(startButtonX, startButtonY, "Increase");
        root.getChildren().add(volumeIncreaseButton);
        volumeIncreaseButton.setOnMouseClicked(mouseEvent -> {
            if (Math.round(this.volumeMultiplier * 100) >= 100) {
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
            if (Math.round(this.volumeMultiplier * 100) <= 0) {
                System.out.println("Volume is at min");
            } else {
                this.volumeMultiplier -= 0.1;
                mediaPlayerMenu.setVolume(this.volumeMultiplier);
                volumeText.setText("Volume: " + Math.round((this.volumeMultiplier * 100)));
            }
        });

        MenuItem menuButton = new MenuItem("Main menu");
        menuButton.setTranslateX(startButtonX);
        menuButton.setTranslateY(startButtonY + 150);
        root.getChildren().add(menuButton);
        menuButton.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(volumeIncreaseButton);
            root.getChildren().remove(volumeDecreaseButton);
            root.getChildren().remove(volumeText);
            root.getChildren().remove(menuButton);
            configMenuButtons(root, stage, scene, img, roundcounter, agoAttack, agoMaxHp);
        });
    }

    private void formatText(Text text, int size) {
        text.setFont(new Font("Comic Sans MS", size));
        text.setFill(Color.WHITE);
        text.setStrokeWidth(1);
        text.setStroke(Color.BLACK);
    }

    private void makeStatsWindow(Group root, Scene scene, Stage stage, World world, int roundcounter) {
        world.getRoot().getChildren().clear();
        statpoints = 6;

        String musicFile = "src/MortalAgo/Media/jan_uuspõld.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayerStats = new MediaPlayer(sound);
        mediaPlayerStats.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerStats.play();
        mediaPlayerStats.setVolume(volumeMultiplier);

        if (roundcounter == 0) {
            Text text = new Text(240, 130, "You unlocked special");
            formatText(text, 40);
            root.getChildren().add(text);
        }

        Text stats = new Text(280, 180, "Statpoints: " + statpoints);
        formatText(stats, 30);
        root.getChildren().add(stats);

        Text Attack = new Text(280, 230, "Attack: " + world.getPlayer().getAttack());
        formatText(Attack, 27);
        root.getChildren().add(Attack);

        Text hitPoints = new Text(280, 280, "Hitpoints: " + world.getPlayer().getMaxHp());
        formatText(hitPoints,27);
        root.getChildren().add(hitPoints);

        Button AttackButton = makeStatButton(490, 200, "+1 Attack");
        root.getChildren().add(AttackButton);
        AttackButton.setOnMouseClicked(mouseEvent -> {
            if (statpoints <= 0) {
                System.out.println("Not enough statpoints");
            } else {
                world.getPlayer().setAttack(world.getPlayer().getAttack() + 1);
                reduceStatpoints(stats, Attack, hitPoints, world);
            }
        });

        Button hitPointsButton = makeStatButton(490, 250, "+5 Hitpoints");
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
            mediaPlayerStats.stop();
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

    private void makeLoseWindow(Group root, Stage stage, World world) {
        world.getRoot().getChildren().clear();

        String musicFile = "src/MortalAgo/Media/spanish_laugh.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayerLose = new MediaPlayer(sound);
        mediaPlayerLose.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerLose.play();
        mediaPlayerLose.setVolume(1.0);

        Text text = new Text("You died, git gud");
        text.setFont(new Font("Comic Sans MS", 36));
        text.setX(260);
        text.setY(100);
        root.getChildren().add(text);

        MenuItem mainMenu = new MenuItem("Play again");
        mainMenu.setTranslateX(290);
        mainMenu.setTranslateY(150);
        root.getChildren().add(mainMenu);
        mainMenu.setOnMouseClicked(mouseEvent -> {
            mediaPlayerLose.stop();
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
        AttackButton.setStyle("-fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Comic Sans MS\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-padding: 10 20 10 20;");
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

    private void makeAuthorText(Text text, Group root) {
        text.setFont(new Font("Verdana", 26));
        text.setFill(Color.BLACK);
        text.setStrokeWidth(0.2);
        text.setStroke(Color.WHITE);
        root.getChildren().add(text);
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
            text.setFont(Font.font("Comic Sans MS", FontWeight.SEMI_BOLD,20));

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
