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

    private int hp, attack, defence;
    private Rectangle player;
    private Image logo, leftjump;
    private Button moveLeft, moveRight, punch, kick, special;
    private int counter = 0;

    public Player(String name, Rectangle player, Image logo, Image leftjump) {
        this.player = player;
        this.attack = 1;
        this.defence = 1;
        this.hp = 10;
        this.logo = logo;
        this.leftjump = leftjump;
    }
    void moveLeft(){
        this.getRetangle().setFill(new ImagePattern(getJumpLeft()));
        player.setX(player.getX() - 2);
        moveLeft.getButton().setCenterX(moveLeft.getButton().getCenterX() - 2);
        moveRight.getButton().setCenterX(moveRight.getButton().getCenterX() - 2);//TODO add attacking buttons too
        counter++;
        System.out.println(counter);
        if (counter == 20) {
            this.getRetangle().setFill(new ImagePattern(this.getLogo()));
            counter = 0;
        }
    }
    void moveRight(){
        player.setX(player.getX() + 2);
        moveLeft.getButton().setCenterX(moveLeft.getButton().getCenterX() + 2);
        moveRight.getButton().setCenterX(moveRight.getButton().getCenterX() + 2); //TODO add attacking buttons too
    }

    public void setMoveLeft(Button left){
        this.moveLeft = left;
    }
    public Button getMoveLeft(){
        return this.moveLeft;
    }
    public void setMoveRight(Button right){
        this.moveRight = right;
    }
    public Button getMoveRight(){
        return this.moveRight;
    }
    public void setPunch(Button punch){
        this.punch = punch;
    }
    public Button getPunch(){
        return this.punch;
    }
    public void setKick(Button kick){
        this.kick = kick;
    }
    public Button getKick(){
        return this.kick;
    }
    public void setSpecial(Button special){
        this.special = special;
    }
    public Button getSpecial(){
        return this.special;
    }

    public Rectangle getRetangle(){
        return this.player;
    }
    public Image getLogo(){
        return this.logo;
    }
    public Image getJumpLeft(){
        return this.leftjump;
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

    public double getX() { return this.player.getX(); }

    public double getY() { return this.player.getY(); }

}
