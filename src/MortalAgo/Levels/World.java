package MortalAgo.Levels;

import MortalAgo.Button;
import MortalAgo.Characters.Ago;
import MortalAgo.Characters.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static java.lang.Math.abs;

public class World {
    public static final int BUTTON_SIZE = 15;
    public static final int BUTTON_Y_CORRECTION = 20;
    public static final int BUTTON_X_CORRECTION = 80;
    private Rectangle playerHp;
    private Rectangle enemyHp;
    private boolean created1rect = false;
    private String name;
    private Image background;
    private Group root;
    private Scene scene;
    private Player player, enemy;
    public World(String name, Image background, Group root, Scene scene){
        this.name = name;
        this.background = background;
        this.root = root;
        this.scene = scene;
        scene.setFill(new ImagePattern(background));
    }

    public World(String name, Group root, Scene scene){
        this.name = name;
        this.background = null;
        this.root = root;
        this.scene = scene;
    }

    /**
     *  It draws player into given world with all buttons
     *
     * @param player Player
     * @param x double
     * @param y double
     */

    private void drawPlayer(Player player, double x, double y){
        Image right = new Image("file:src\\MortalAgo\\Media\\right.png");
        Image left = new Image("file:src\\MortalAgo\\Media\\left.png");
        Image punche = new Image("file:src\\MortalAgo\\Media\\punch.png");
        player.getRectangle().setFill(new ImagePattern(player.getLogo()));
        player.getRectangle().setX(x);
        player.getRectangle().setY(y);
        Circle moveRight = new Circle(x + 45 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 40, BUTTON_SIZE);
        Circle moveLeft = new Circle(x + 17 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 13, BUTTON_SIZE);
        Circle punch = new Circle(x - 20 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION, BUTTON_SIZE);
        Button rightMove = new Button("left", right, moveRight, player);
        Button leftMove = new Button("left", left, moveLeft, player);
        Button hit = new Button("hit", punch, player);
        moveRight.setFill(new ImagePattern(right));
        moveLeft.setFill(new ImagePattern(left));
        punch.setFill(new ImagePattern(punche));
        root.getChildren().add(player.getRectangle());
        root.getChildren().add(moveLeft);
        root.getChildren().add(moveRight);
        root.getChildren().add(punch);
        player.setMoveRight(rightMove);
        player.setMoveLeft(leftMove);
        player.setPunch(hit);
        rightMove.moveButton(4);
        leftMove.moveButton(-4);
        hit.attackButton();
    }

    public void drawEnemy(Player enemy, double x, double y){
        drawPlayer(enemy, x, y);
        enemy.setButtonVisible(false);
        this.enemy = enemy;
    }

    public void drawAgo(Player ago, double x, double y) {
        drawPlayer(ago, x, y);
        this.player = ago;
    }

    public boolean isAttacked(Player attacker) {
        if (attacker.equals(this.player)) {
            return (abs(attacker.getX() - enemy.getX()) < 135);
        } else {
            return (abs(attacker.getX() - player.getX()) < 85);
        }
    }

    public double distanceBetween(){
        return abs(player.getX()- enemy.getX());
    }

    public void turnOver(Player player) {
        player.setButtonVisible(false);
        getOtherPlayer(player).setButtonVisible(true);

    }

    public void moveOtherPlayer(Player player, int ammount){
        if (this.player.equals(player)) {
            enemy.movePlayer(ammount);
        } else {
            player.movePlayer(-ammount);
        }
    }

    public void attack(Player attacker) {
        if (isAttacked(attacker)) {
            if (attacker.equals(player)) {
                enemy.gotHit(true);
            } else {
                player.gotHit( false);
            }
        }
    }

    public void drawHpRectangle(Player player) {
        if (player instanceof Ago) {
            Rectangle playerHpOutline = new Rectangle(35 - 5, 50 - 5 , 210, 30);
            this.playerHp = new Rectangle(35, 50, 20 * player.getHp(), 20);
            playerHpOutline.setFill(Color.BLACK);
            playerHp.setFill(Color.RED);
            root.getChildren().add(playerHpOutline);
            root.getChildren().add(playerHp);
        } else {
            Rectangle playerHpOutline = new Rectangle(560 - 5, 50 - 5 , 210, 30);
            this.enemyHp = new Rectangle(560, 50, 20 * player.getHp(), 20);
            playerHpOutline.setFill(Color.BLACK);
            enemyHp.setFill(Color.RED);
            root.getChildren().add(playerHpOutline);
            root.getChildren().add(enemyHp);
        }
    }

    public void gameAnimation(){
    }

    public Image getBackground(){
        return this.background;
    }

    public void setBackground(Image background){
        this.background = background;
        this.scene.setFill(new ImagePattern(background));
    }

    public String getName(){
        return this.name;
    }

    public Group getRoot(){
        return this.root;
    }

    public double getWith() {
       return this.scene.getWidth();
    }

    public double getHeight() {
        return this.scene.getHeight();
    }

    public Player getEnemy(){
        return this.enemy;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Player getOtherPlayer(Player player) {
        if (player.equals(this.player)) {
            return enemy;
        } else {
            return this.player;
        }
    }
}
