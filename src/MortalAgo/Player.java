package MortalAgo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;


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

    public void drawPlayer( Group root, Double x, Double y, Rectangle stickmangif) {
        Image right = null;
        Image stickf = null;
        try {
            right = new Image( new FileInputStream("src\\MortalAgo\\right.png") );

            stickf = new Image( new FileInputStream("src\\MortalAgo\\stickman.gif") );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stickmangif.setFill(new ImagePattern(stickf));
        Circle moveRight = new Circle(x + 33, y - 20, 10);
        Circle moveLeft = new Circle(x + 5, y - 20, 10);
        moveRight.setFill(new ImagePattern(right));
        root.getChildren().add(stickmangif);
        root.getChildren().add(moveLeft);
        root.getChildren().add(moveRight);


        moveRight.setOnMouseClicked(mouseEvent -> {
            Image stickman = null;
            try {
                stickman = new Image( new FileInputStream("src\\MortalAgo\\Agojump.gif") );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            stickmangif.setFill(new ImagePattern(stickman));
            Timeline animation = new Timeline();
            animation.setCycleCount(20);
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        stickmangif.setX(stickmangif.getX() + 2);
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
                        stickmangif.setX(stickmangif.getX() - 2);
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
