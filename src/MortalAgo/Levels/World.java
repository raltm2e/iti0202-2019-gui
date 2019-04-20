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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Random;

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

    public void removeAll(Player player, Player enemy) {
        this.root.getChildren().clear();

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
        Circle specialAttack = new Circle( x + 130 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 75, BUTTON_SIZE);
        Rectangle projectile = new Rectangle(player.getX() + 180, player.getY() + 140, 70, 40);
        projectile.setVisible(false);
        Button rightMove = new Button("right", right, moveRight, player);
        Button leftMove = new Button("left", left, moveLeft, player);
        Button hit = new Button("hit", punch, player);
        Button kick = new Button("kick", leg, player);
        Button sleeping = new Button("sleep", sleep, player);
        Button special = new Button("special", specialAttack, player);
        Text buttonText = new Text();
        buttonText.setX(x + BUTTON_X_CORRECTION - 40);
        buttonText.setY(y - 60);
        buttonText.setFont(new Font("Comic Sans MS", 30));
        buttonText.setFill(Color.WHITE);
        buttonText.setStrokeWidth(1);
        buttonText.setStroke(Color.BLACK);
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
        root.getChildren().add(buttonText);
        root.getChildren().add(specialAttack);
        root.getChildren().add(projectile);
        player.setMoveRight(rightMove);
        player.setMoveLeft(leftMove);
        player.setPunch(hit);
        player.setKick(kick);
        player.setSleeping(sleeping);
        player.setButtonText(buttonText);
        player.setSpecial(special);
        player.setProjectile(projectile);
        rightMove.moveButton(4);
        leftMove.moveButton(-4);
        hit.attackButton();
        kick.kickButton();
        sleeping.sleepButton();
        special.attackButton();
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

    private double getPrecentage(int distance, int required) {
        if (distance > required) {
            return 0;
        } else if (distance < 11) {
            return 100.00;
        }
        double percentage = 100.00 - (((double) (distance - 101) / (required - 101)) * 100);
        return Math.round(percentage * 100.0) / 100.0;
    }

    public boolean getProbabilityBoolean(double probability) {
        Random random = new Random();
        int check = random.nextInt(10000);
        return (((int) probability * 100) >= check);
    }

    private double getHitPercentage(Player attacker) {
        if (attacker instanceof Ago) {
            return getPrecentage((int) distanceBetween(), 135);
        } else {
            return getPrecentage((int) distanceBetween() + 50, 135);
        }
    }

    public double getHitTextPercentage() {
        if (getPrecentage((int) distanceBetween(), 135) > 100) {
            return 100.00;
        }
        return getPrecentage((int) distanceBetween(), 135);
    }

    public double getKickTextPercentage() {
        if (getPrecentage((int) distanceBetween(), 120) > 100) {
            return 100.00;
        }
        return getPrecentage((int) distanceBetween(), 120);
    }

    public boolean isAttacked(Player attacker) {
        return getProbabilityBoolean(getHitPercentage(attacker));
    }

    public boolean isKicked(Player attacker) {
        if (attacker.equals(this.player)) {
            return getProbabilityBoolean(getPrecentage((int) distanceBetween(), 120));
        } else {
            return getProbabilityBoolean(getPrecentage((int) distanceBetween() + 30, 120));
        }
    }

    public double distanceBetween(){
        return abs(player.getX()- enemy.getX());
    }

    public double distanceProjectile(Player player, Rectangle projectile) { return abs(getOtherPlayer(player).getX() - projectile.getX()); }

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
                        enemy.gotHit(true, player.getAttack());
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
            player.setDead(true);
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
