package MortalAgo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Player extends main{

    private int hp;
    private int attack;
    private int defence;

    public void Player(String name) {
        if (name.equals("Ago")) {
            Player newPlayer = new ago();
        }
        this.attack = 1;
        this.defence = 1;
        this.hp = 10;
    }

    public void drawPlayer(GraphicsContext gc, Canvas canvas, Group root, Double x, Double y) {
        gc.fillRect(x, y, 40, 50);
        Image right = null;
        try {
            right = new Image( new FileInputStream("src\\MortalAgo\\Right.png") );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Circle moveRight = new Circle(x + 33, y - 20, 10);
        Circle moveLeft = new Circle(x + 5, y - 20, 10);
        moveRight.setFill(new ImagePattern(right));
        root.getChildren().add(moveLeft);
        root.getChildren().add(moveRight);



        moveRight.setOnMouseClicked(mouseEvent -> {
            Timeline animation = new Timeline();
            animation.setCycleCount(20);

            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        gc.clearRect(moveLeft.getCenterX() - 10, moveLeft.getCenterY() + 20, 50, 50);
                        gc.fillRect( moveRight.getCenterX() - 30, y, 40, 50);
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
                        gc.clearRect(moveRight.getCenterX() - 35, moveLeft.getCenterY() + 20, 50, 50);
                        gc.fillRect( moveLeft.getCenterX() - 5, y, 40, 50);
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
