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
import static java.lang.Math.abs;

public class World {
    public enum AttackChoice {HIT, KICK}
    public static final int BUTTON_SIZE = 21;
    public static final int BUTTON_Y_CORRECTION = 10;
    public static final int BUTTON_X_CORRECTION = 65;
    private Rectangle playerHp, enemyHp, playerStamina, enemyStamina;
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
        Image kicke = new Image("file:src\\MortalAgo\\Media\\kick.png");
        Image sleepe = new Image("file:src\\MortalAgo\\Media\\sleep.png");
        player.getRectangle().setFill(new ImagePattern(player.getLogo()));
        player.getRectangle().setX(x);
        player.getRectangle().setY(y);
        Circle moveRight = new Circle(x - 40 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 20, BUTTON_SIZE);
        Circle moveLeft = new Circle(x - 80 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 55, BUTTON_SIZE);
        Circle punch = new Circle(x + 5 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION, BUTTON_SIZE);
        Circle leg = new Circle(x + 50 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 20, BUTTON_SIZE);
        Circle sleep = new Circle( x + 80 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 50, BUTTON_SIZE);
        Button rightMove = new Button("right", right, moveRight, player);
        Button leftMove = new Button("left", left, moveLeft, player);
        Button hit = new Button("hit", punch, player);
        Button kick = new Button("kick", leg, player);
        Button sleeping = new Button("sleep", sleep, player);
        moveRight.setFill(new ImagePattern(right));
        moveLeft.setFill(new ImagePattern(left));
        punch.setFill(new ImagePattern(punche));
        leg.setFill(new ImagePattern(kicke));
        sleep.setFill(new ImagePattern(sleepe));
        root.getChildren().add(player.getRectangle());
        root.getChildren().add(moveLeft);
        root.getChildren().add(moveRight);
        root.getChildren().add(punch);
        root.getChildren().add(leg);
        root.getChildren().add(sleep);
        player.setMoveRight(rightMove);
        player.setMoveLeft(leftMove);
        player.setPunch(hit);
        player.setKick(kick);
        player.setSleeping(sleeping);
        rightMove.moveButton(4);
        leftMove.moveButton(-4);
        hit.attackButton();
        kick.kickButton();
        sleeping.sleepButton();
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

    public boolean isKicked(Player attacker) {
        if (attacker.equals(this.player)) {
            return (abs(attacker.getX() - enemy.getX()) < 120);
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
        if (getOtherPlayer(player).getStamina() == 0) {
            player.animateSleep();
        }
    }

    public void moveOtherPlayer(Player player, int ammount){
        if (this.player.equals(player)) {
            enemy.movePlayer(ammount);
        } else {
            this.player.movePlayer(ammount);
        }
    }

    public boolean attack(Player attacker, AttackChoice choice) {
        switch (choice) {
            case HIT:
                if (isAttacked(attacker)) {
                    if (attacker.equals(player)) {
                        enemy.gotHit(true, enemy.getAttack());
                    } else {
                        player.gotHit( false, enemy.getAttack());
                    }
                    return true;
                }
                break;
            case KICK:
                if (isKicked(attacker)) {
                    if (attacker.equals(player)) {
                        enemy.gotKicked(true, player.getAttack());
                    } else {
                        player.gotKicked( false, enemy.getAttack());
                    }
                    return true;
                }
                break;
            default: return false;
        }
        return false;
    }

    public void drawHpRectangle(Player player) {
        if (player.getHp() <= 0) {
            player.die();
        }
        if (player instanceof Ago) {
            Rectangle playerHpOutline = new Rectangle(35 - 5, 50 - 5 , 210, 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.playerHp = new Rectangle(35, 50, 2 * player.getHp(), 20);
            playerHp.setFill(Color.RED);
            root.getChildren().add(playerHp);
        } else {
            Rectangle playerHpOutline = new Rectangle(560 - 5, 50 - 5 , 210, 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.enemyHp = new Rectangle(560, 50, 2 * player.getHp(), 20);
            enemyHp.setFill(Color.RED);
            root.getChildren().add(enemyHp);
        }
    }

    public void drawStaminaRectangle(Player player) {
        if (player instanceof Ago) {
            Rectangle playerHpOutline = new Rectangle(35 - 5, 120 - 5 , 210, 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.playerStamina = new Rectangle(35, 120, 2 * player.getStamina(), 20);
            playerStamina.setFill(Color.GREEN);
            root.getChildren().add(playerStamina);
        } else {
            Rectangle playerHpOutline = new Rectangle(560 - 5, 120 - 5 , 210, 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.enemyStamina = new Rectangle(560, 120, 2 * player.getStamina(), 20);
            enemyStamina.setFill(Color.GREEN);
            root.getChildren().add(enemyStamina);
        }
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
