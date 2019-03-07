package MortalAgo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class World {
    public static final int BUTTON_SIZE = 15;
    public static final int BUTTON_Y_CORRECTION = 30;
    public static final int BUTTON_X_CORRECTION = 50;
    private String name;
    private Image background;
    private Group root;
    public World(String name, Image background, Group root){
        this.name = name;
        this.background = background;
        this.root = root;
    }

    public World(String name, Group root){
        this.name = name;
        this.background = null;
        this.root = root;
    }

    public void drawPlayer(Player player, double x, double y){
        Image right = null;
        Image left = null;
        try {
            right = new Image( new FileInputStream("src\\MortalAgo\\Media\\right.png"));
            left = new Image(new FileInputStream("src\\MortalAgo\\Media\\left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        player.getRetangle().setFill(new ImagePattern(player.getLogo()));
        player.getRetangle().setX(x);
        player.getRetangle().setY(y);
        Circle moveRight = new Circle(x + 40 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION, BUTTON_SIZE);
        Circle moveLeft = new Circle(x + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION, BUTTON_SIZE);
        Button rightMove = new Button("left", right, moveRight, player);
        Button leftMove = new Button("left", left, moveLeft, player);
        moveRight.setFill(new ImagePattern(right));
        moveLeft.setFill(new ImagePattern(left));
        root.getChildren().add(player.getRetangle());
        root.getChildren().add(moveLeft);
        root.getChildren().add(moveRight);
        player.setMoveRight(rightMove);
        player.setMoveLeft(leftMove);
        rightMove.moveButton(4);
        leftMove.moveButton(-4);

    }
    public void drawEnemy(Player enemy, double x, double y){

    }
    public void gameAnimation(){

    }

    public Image getBackground(){
        return this.background;
    }

    public void setBackground(Image background){
        this.background = background;
    }

    public String getName(){
        return this.name;
    }

    public Group getRoot(){
        return this.root;
    }

}
