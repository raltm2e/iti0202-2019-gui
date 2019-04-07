package MortalAgo.Characters;

import MortalAgo.Button;
import MortalAgo.Levels.World;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;

public class Player {

    private int hp, attack, defence, stamina;
    private Media damageSound = new Media(new File("src/MortalAgo/Media/Characters/Kruus/K2h_damage.mp3").toURI().toString());
    private Media sleep = new Media(new File("src/MortalAgo/Media/test1.mp3").toURI().toString());
    private Rectangle player;
    private Image logo;
    private String left, right, hit, leftHit, rightHit, damageSoundurl, leg, leftFall, rightFall, leftRise, rightRise;
    private Button moveLeft, moveRight, punch, kick, special, sleeping;
    private int counter = 0;
    private boolean outOfBounds = false, punchPlayer = false, isDead = false;
    private World world;

    public Player(Rectangle player, Image logo, World world) {
        this.player = player;
        this.attack = 1;
        this.defence = 1;
        this.stamina = 20;
        this.hp = 10;
        this.logo = logo;
        this.world = world;
    }
    public void move(int ammount, String url) {
        if (counter == 0) {
            this.getRectangle().setFill(new ImagePattern(new Image(url)));
            setButtonVisible(false);
        }
        if ( counter < 50 && counter > 15 && !outOfBounds && !punchPlayer) {
            if (world.getWith() <= (player.getX() + 130) || 0 > player.getX()) {
                outOfBounds = true;
            } else if (world.distanceBetween() < 60) { // distance between players
                punchPlayer = true;
            } else {
                movePlayer(ammount);
            }
        } else if (outOfBounds) {
            movePlayer(- (ammount / 2));
        } else if (punchPlayer && world.distanceBetween() < 105) {
            movePlayer(- (ammount / 4));
            this.world.moveOtherPlayer(this, (ammount / 4));
        }
        counter++;
        if (counter == 62) {
            addStamina(1);
            this.getRectangle().setFill(new ImagePattern(this.getLogo()));
            outOfBounds = false;
            punchPlayer = false;
            counter = 0;
            world.turnOver(this);
        }
    }

    public void attack() {
        if (counter == 0) {
            this.getRectangle().setWidth(180.00);
            this.getRectangle().setFill(new ImagePattern(new Image(getPunchUrl())));
            if (world.getEnemy().equals(this)) {
                player.setX(player.getX() - 50);
            }
            setButtonVisible(false);
        }
        if(counter == 21) {
            world.attack(this, World.AttackChoice.HIT);
        }
        counter++;
        if (counter == 40) {
            this.getRectangle().setWidth(130.00);
            addStamina(-5);
            if (world.getEnemy().equals(this)) {
                player.setX(player.getX() + 50);
            }
            this.getRectangle().setFill(new ImagePattern(this.getLogo()));
            counter = 0;
            world.turnOver(this);

        }
    }

    public void kick() {
        if (counter == 0) {
            this.getRectangle().setWidth(160.00);
            this.getRectangle().setFill(new ImagePattern(new Image(getLegUrl())));
            if (world.getEnemy().equals(this)) {
                player.setX(player.getX() - 30); // teistipidi playeri paika liigutamine
            }
            setButtonVisible(false);
        }
        if(counter == 21) {
            if (world.attack(this, World.AttackChoice.KICK)) {
                punchPlayer = true;
            }
        }
        counter++;
        if (counter == 45) {
            this.getRectangle().setWidth(130.00);
            addStamina(-8);
            this.getRectangle().setFill(new ImagePattern(this.getLogo()));
            counter = 0;
            if (!punchPlayer) {
                world.turnOver(this);
            }
            punchPlayer = false;
            if (world.getEnemy().equals(this)) {
                player.setX(player.getX() + 30);
            }
        }
    }

    public void sleep() {
        if (counter == 0) {
            MediaPlayer sleeping = new MediaPlayer(sleep);
            sleeping.play();
            setButtonVisible(false);
        }
        counter++;
        if (counter == 72 ) {
            counter = 0;
            addStamina(5);
            gainHp(2);
            world.turnOver(this);
        }
    }

    public void animateSleep() {
        Timeline animation = new Timeline();
        animation.setCycleCount(72);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 ->  {
                    this.sleep();
                }));
        animation.play();
    }

    public void gotHit(boolean left) {
        Timeline animation = new Timeline();
        animation.setCycleCount(29);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    animateHit(left);
                }));
        animation.play();
        this.loseHp(1);
        if (this.hp <= 0) {
            this.isDead = true;
        }
    }

    public void gotKicked(boolean left) {
        Timeline animation = new Timeline();
        animation.setCycleCount(66);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    animateKick(left);
                }));
        animation.play();
        this.loseHp(2);
        if (this.hp <= 0) {
            this.isDead = true;
        }
    }



    private void loseHp(int amount) {
        MediaPlayer damageMediaPlayer = new MediaPlayer(damageSound);
        damageMediaPlayer.play();
        this.hp -= amount;
        world.drawHpRectangle(this);
    }

    private void gainHp(int amount) {
        this.loseHp(-amount);
    }

    private void animateHit(boolean left) {
        if (counter == 0) {
            if (left) {
                this.getRectangle().setFill(new ImagePattern(new Image(leftHit)));
            } else {
                this.getRectangle().setFill(new ImagePattern(new Image(rightHit)));
            }
        }
        if (counter > 13 && counter < 24) {
            if (left) {
                movePlayer(2);
            } else {
                movePlayer(-2);
            }
        }
        counter++;
        if (counter == 29) {
            this.getRectangle().setFill(new ImagePattern(logo));
            counter = 0;
        }
    }

    private void animateKick(boolean left) {
        if (counter == 0) {
            this.getRectangle().setWidth(260.00);
            if (left) {
                this.getRectangle().setFill(new ImagePattern(new Image(leftFall)));
            } else {
                player.setX(player.getX() - 130);
                this.getRectangle().setFill(new ImagePattern(new Image(rightFall)));
            }
        }
        if (counter > 13 && counter < 24) {
            if (left) {
                movePlayer(3);
            } else {
                movePlayer(-3);
            }
        }
        if (counter == 40){
            if (left) {
                this.getRectangle().setFill(new ImagePattern(new Image(leftRise)));
            } else {
                this.getRectangle().setFill(new ImagePattern(new Image(rightRise)));
            }
        }
        counter++;
        if (counter == 66) {
            if(!left) {
                player.setX(player.getX() + 130);
            }
            this.getRectangle().setWidth(130.00);
            this.getRectangle().setFill(new ImagePattern(logo));
            world.turnOver(world.getOtherPlayer(this));
            counter = 0;
        }
    }
    public void turnOver(Player player) {
        if (counter == 0) {
            world.turnOver(player);
        }
    }

    public void setButtonVisible(Boolean value){ //TODO add attacking buttons too
        moveLeft.getButton().setVisible(value);
        moveRight.getButton().setVisible(value);
        punch.getButton().setVisible(value);
        kick.getButton().setVisible(value);
        sleeping.getButton().setVisible(value);
    }

    public void movePlayer(int ammount) {
        player.setX(player.getX() + ammount);
        moveLeft.getButton().setCenterX(moveLeft.getButton().getCenterX() + ammount);
        moveRight.getButton().setCenterX(moveRight.getButton().getCenterX() + ammount);//TODO add attacking buttons too
        punch.getButton().setCenterX(punch.getButton().getCenterX() + ammount);
        kick.getButton().setCenterX(kick.getButton().getCenterX() + ammount);
        sleeping.getButton().setCenterX(sleeping.getButton().getCenterX() + ammount);
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


    public Rectangle getRectangle(){
        return this.player;
    }

    public Image getLogo(){
        return this.logo;
    }

    public String getLeftUrl() { return this.left; }

    public String getRightUrl() { return this.right; }

    public String getPunchUrl() { return this.hit; }

    public void setLeftUrl(String url) { this.left = url; }

    public void setRightUrl(String url) { this.right = url; }

    public void setPunchUrl(String url) { this.hit = url; }

    public void setRighthitUrl(String url) { this.rightHit = url; }

    public void setLefthitUrl(String url) { this.leftHit = url; }

    public void setLegUrl(String url) { this.leg = url;}

    public String getLegUrl() { return leg; }

    public void setDamageSound(String url) {
        this.damageSoundurl = url;
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

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public String getLeftFall() {
        return leftFall;
    }

    public void setLeftFall(String leftFall) {
        this.leftFall = leftFall;
    }

    public String getRightFall() {
        return rightFall;
    }

    public void setRightFall(String rightFall) {
        this.rightFall = rightFall;
    }

    public String getLeftRise() {
        return leftRise;
    }

    public void setLeftRise(String leftRise) {
        this.leftRise = leftRise;
    }

    public String getRightRise() {
        return rightRise;
    }

    public void setRightRise(String rightRise) {
        this.rightRise = rightRise;
    }

    public int getStamina() {
        return stamina;
    }

    public void addStamina(int ammount) {
        if (stamina + ammount >= 20) {
            stamina = 20;
        } else if (stamina + ammount < 20 && stamina + ammount >= 0) {
            stamina += ammount;
        } else if (stamina + ammount < 0) {
            stamina = 0;
        }
    }

    public Button getSleeping() {
        return sleeping;
    }

    public void setSleeping(Button sleeping) {
        this.sleeping = sleeping;
    }
}
