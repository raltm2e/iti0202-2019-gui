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
    private Image logo;
    private String left = "file:src\\MortalAgo\\Media\\Characters\\Ago\\ago-left.gif",
            right = "file:src\\MortalAgo\\Media\\Characters\\Ago\\ago-right.gif";
    private Button moveLeft, moveRight, punch, kick, special;
    private int counter = 0;

    public Player(String name, Rectangle player, Image logo) {
        this.player = player;
        this.attack = 1;
        this.defence = 1;
        this.hp = 10;
        this.logo = logo;
    }
    void move(int ammount, String url){
        if (counter == 0) {
            this.getRetangle().setFill(new ImagePattern(new Image(url)));
            moveLeft.getButton().setVisible(false);
            moveRight.getButton().setVisible(false);
        }
        if ( counter < 50 && counter > 15) {
            player.setX(player.getX() + ammount);
            moveLeft.getButton().setCenterX(moveLeft.getButton().getCenterX() + ammount);
            moveRight.getButton().setCenterX(moveRight.getButton().getCenterX() + ammount);//TODO add attacking buttons too
        }
        counter++;
        System.out.println(counter);
        if (counter == 62) {
            moveLeft.getButton().setVisible(true);
            moveRight.getButton().setVisible(true);
            this.getRetangle().setFill(new ImagePattern(this.getLogo()));
            counter = 0;
        }
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
    public String getLeftUrl() { return this.left; }
    public String getRightUrl() { return this.right; }

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
