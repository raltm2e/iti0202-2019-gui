package MortalAgo.Levels;

import MortalAgo.Ai.Ai;
import MortalAgo.Button;
import MortalAgo.Characters.Ago;
import MortalAgo.Characters.Kruus;
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
    public static final int BUTTON_X_CORRECTION = 90;
    private Rectangle playerHp, enemyHp, playerStamina, enemyStamina;
    private String name;
    private Image background;
    private Group root;
    private Scene scene;
    private Player player, enemy;
    private Ai enemyAi;

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
        player.getRectangle().setFill(new ImagePattern(player.getLogo()));
        player.getRectangle().setX(x);
        player.getRectangle().setY(y);
        Rectangle projectile = new Rectangle(player.getX() + 180, player.getY() + 140, 70, 40);
        projectile.setVisible(false);
        player.setProjectile(projectile);
        root.getChildren().add(projectile);
        root.getChildren().add(player.getRectangle());
    }

    private void drawButtons(Player player, double x, double y) {
        if (!(enemy instanceof Kruus)) {
            Image specialPic = new Image("file:src\\MortalAgo\\Media\\Special.png");
            Circle specialAttack = new Circle(x - 100 + BUTTON_X_CORRECTION, y + BUTTON_Y_CORRECTION + 105, BUTTON_SIZE);
            Button special = new Button("special", specialAttack, player);
            specialAttack.setFill(new ImagePattern(specialPic));
            root.getChildren().add(specialAttack);
            player.setSpecial(special);
            special.attackButton();
        }
        Image right = new Image("file:src\\MortalAgo\\Media\\right.png");
        Image left = new Image("file:src\\MortalAgo\\Media\\left.png");
        Image punche = new Image("file:src\\MortalAgo\\Media\\punch.png");
        Image kicke = new Image("file:src\\MortalAgo\\Media\\kick.png");
        Image sleepe = new Image("file:src\\MortalAgo\\Media\\sleep.png");

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

        root.getChildren().add(moveLeft);
        root.getChildren().add(moveRight);
        root.getChildren().add(punch);
        root.getChildren().add(leg);
        root.getChildren().add(sleep);
        root.getChildren().add(buttonText);

        player.setMoveRight(rightMove);
        player.setMoveLeft(leftMove);
        player.setPunch(hit);
        player.setKick(kick);
        player.setSleeping(sleeping);
        player.setButtonText(buttonText);

        rightMove.moveButton(4);
        leftMove.moveButton(-4);
        hit.attackButton();
        kick.kickButton();
        sleeping.sleepButton();
    }

    public void drawEnemy(Player enemy, double x, double y){
        drawPlayer(enemy, x, y);
        this.enemy = enemy;
        enemyAi = new Ai(enemy, this);
    }

    public void drawAgo(Player ago, double x, double y) {
        drawPlayer(ago, x, y);
        drawButtons(ago, x, y);
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
        if (attacker instanceof Ago) {
            return getProbabilityBoolean(getPrecentage((int) distanceBetween(), 135));
        } else {
            return getProbabilityBoolean(getPrecentage((int) distanceBetween() + 50, 135));
        }
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
        if (!player.isDead() && !getOtherPlayer(player).isDead()) {
            player.setCounter(0);
            if (getOtherPlayer(player).getStamina() == 0) {
                getOtherPlayer(player).animateSleep();
            } else if (player instanceof Ago) {
                player.setButtonVisible(false);
                getOtherPlayer(player).gainStamina(2);
                enemyAi.turn();
            } else {
                getOtherPlayer(player).setButtonVisible(true);
                getOtherPlayer(player).gainStamina(2);
            }
        }
    }

    public void moveOtherPlayer(Player player, int ammount){
        if (this.player.equals(player)) {
            enemy.movePlayer(ammount,false);
        } else {
            this.player.movePlayer(ammount, false);
        }
    }

    public double simulateMoving(Player player, double ammount) {
        double playerX = player.getX();
        double enemyX = getOtherPlayer(player).getX();
        boolean punchPlayer = false;
        boolean outOfBounds = false;
        for (int i = 0; i < 46 ; i++) {
            if (i < 34 && !outOfBounds && !punchPlayer) {
                if (getWith() <= (playerX + 130) || 0 > playerX) {
                    outOfBounds = true;
                } else if (abs(playerX - enemyX) < 60) { // distance between players
                    punchPlayer = true;
                } else {
                    playerX += ammount;
                }
            } else if (outOfBounds) {
                playerX -= ammount / 2;
            } else if (punchPlayer && abs(playerX - enemyX) < 105) {
                playerX -= ammount / 4;
                enemyX += ammount / 4;
            }
        }
        return playerX;
    }
    public boolean attack(Player attacker, AttackChoice choice, int dmg) {
        if (getOtherPlayer(attacker).getHp() - dmg > 0) {
            switch (choice) {
                case HIT:
                    if (isAttacked(attacker)) {
                        if (attacker.equals(player)) {
                            enemy.gotHit(true);
                        } else {
                            player.gotHit(false);
                        }
                        return true;
                    }
                    break;
                case KICK:
                    if (isKicked(attacker)) {
                        if (attacker.equals(player)) {
                            enemy.gotKicked(true);
                        } else {
                            player.gotKicked(false);
                        }
                        return true;
                    }
                    break;
                default:
                    return false;
            }
            return false;
        } else {
            return true;
        }

    }

    public void drawHpRectangle(Player player) {
        if (player.getHp() <= 0) {
            if (player instanceof Ago) {
                player.die(false);
            } else {
                player.die(true);
            }
            player.setDead(true);
        }
        int hp = 0;
        if (player.getHp() > 0) {
            hp = player.getHp();
        }
        Text hpText = new Text(hp + " / " + player.getMaxHp());
        formatText(hpText, 17);
        if (player instanceof Ago) {
            Rectangle playerHpOutline = new Rectangle(35 - 5, 50 - 5 , 2.1 * player.getMaxHp(), 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.playerHp = new Rectangle(35, 50, 2 * player.getHp(), 20);
            playerHp.setFill(Color.RED);
            root.getChildren().add(playerHp);
            hpText.setLayoutX(100);
            hpText.setLayoutY(68);
        } else {
            Rectangle playerHpOutline = new Rectangle(520 - 5, 50 - 5 , 2.1 * player.getMaxHp(), 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.enemyHp = new Rectangle(520, 50, 2 * player.getHp(), 20);
            enemyHp.setFill(Color.RED);
            root.getChildren().add(enemyHp);
            hpText.setLayoutX(585);
            hpText.setLayoutY(68);
        }
        root.getChildren().add(hpText);
    }

    private void formatText(Text text, int fontSize) {
        text.setFont(new Font("Comic Sans MS", fontSize));
        text.setFill(Color.WHITE);
        text.setStrokeWidth(0.3);
        text.setStroke(Color.BLACK);
    }

    public void drawStaminaRectangle(Player player) {
        Text staminaText = new Text(player.getStamina() + " / 100");
        formatText(staminaText, 17);
        if (player instanceof Ago) {
            Rectangle playerHpOutline = new Rectangle(35 - 5, 120 - 5 , 210, 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.playerStamina = new Rectangle(35, 120, 2 * player.getStamina(), 20);
            playerStamina.setFill(Color.GREEN);
            root.getChildren().add(playerStamina);
            staminaText.setLayoutX(100);
            staminaText.setLayoutY(138);
        } else {
            Rectangle playerHpOutline = new Rectangle(520 - 5, 120 - 5 , 210, 30);
            playerHpOutline.setFill(Color.BLACK);
            root.getChildren().add(playerHpOutline);
            this.enemyStamina = new Rectangle(520, 120, 2 * player.getStamina(), 20);
            enemyStamina.setFill(Color.GREEN);
            root.getChildren().add(enemyStamina);
            staminaText.setLayoutX(585);
            staminaText.setLayoutY(138);
        }
        root.getChildren().add(staminaText);
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

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
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
