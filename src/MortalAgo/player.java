package MortalAgo;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.io.FileInputStream;


public abstract class player extends main{

    private int hp;
    private int attack;
    private int defence;

    public void createPlayer(String name) {
        if (name.equals("Ago")) {
            player newPlayer = new ago();
        }
        this.attack = 1;
        this.defence = 1;
        this.hp = 10;
    }

    public void drawPlayer(int x, int y) {
        Canvas canvas = new Canvas(600, 600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(x, y, 40, 50);

        Image right = new Image( new FileInputStream("src\\MortalAgo\\Right.png") );

        Circle moveRight = new Circle(44, 470, 10);
        Circle moveLeft = new Circle(16, 470, 10);
        moveRight.setFill(new ImagePattern(right));
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
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getHp() {
        return this.hp;
    }

}
