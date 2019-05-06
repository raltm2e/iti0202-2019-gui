package MortalAgo.characters;

import MortalAgo.Button;
import MortalAgo.levels.World;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;


public class Player {

    private int hp, attack, defence, stamina, maxHp;
    private AudioClip damageSound = new AudioClip(new File("src/MortalAgo/media/characters/kruus/K2h_damage.mp3").toURI().toString());
    private AudioClip sleep = new AudioClip(new File("src/MortalAgo/media/test1.mp3").toURI().toString());
    private AudioClip hitSound = new AudioClip(new File("src/MortalAgo/media/hit.mp3").toURI().toString());
    private AudioClip kickSound = new AudioClip(new File("src/MortalAgo/media/kick.mp3").toURI().toString());
    private AudioClip jumpSound = new AudioClip(new File("src/MortalAgo/media/hüppamine.mp3").toURI().toString());
    private AudioClip specialSound = new AudioClip(new File("src/MortalAgo/media/special.mp3").toURI().toString());
    private AudioClip dyingSound = new AudioClip(new File("src/MortalAgo/media/dying.mp3").toURI().toString());
    private Rectangle player, projectile;
    private Image logo;
    private String left, right, hit, leftHit, rightHit, damageSoundurl, leg, leftFall, rightFall, leftRise, rightRise, die, specialAttack, specialPic;
    private Button moveLeft, moveRight, punch, kick, special, sleeping;
    private int counter = 0;
    private boolean outOfBounds = false, punchPlayer = false, isDead = false;
    private World world;
    private Text buttonText;

    public Player(Rectangle player, Image logo, World world, int attack, int maxHp) {
        this.player = player;
        this.attack = attack;
        this.maxHp = maxHp;
        this.defence = 1;
        if (this instanceof Ago) {
            stamina = 50;
        } else {
            stamina = 100;
        }
        this.hp = maxHp;
        this.logo = logo;
        this.world = world;
        this.isDead = false;
    }

    public void die(boolean left) {
        System.out.println("Sain surma!");
        Timeline animation = new Timeline();
        animation.setCycleCount(29);
        dyingSound.play();
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    animateDeath(left);
                }));
        animation.play();
        this.setButtonVisible(false);
    }

    private void move(int ammount, String url) {
        if (counter == 0) {
            this.getRectangle().setFill(new ImagePattern(new Image(url)));
            setButtonVisible(false);
            jumpSound.play();
        }
        if (counter < 50 && counter > 15 && !outOfBounds && !punchPlayer) {
            if (world.getWith() <= (player.getX() + 130) || 30 >= player.getX()) {
                outOfBounds = true;
            } else if (world.distanceBetween() < 60) { // distance between players
                punchPlayer = true;
            } else {
                movePlayer(ammount, false);
            }
        } else if (outOfBounds) {
            movePlayer(- (ammount / 2), false);
        } else if (punchPlayer && world.distanceBetween() < 95) {
            movePlayer(- (ammount / 4), false);
            this.world.moveOtherPlayer(this, (ammount / 4));
        }
        counter++;
        if (counter >= 62) {
            this.getRectangle().setFill(new ImagePattern(this.getLogo()));
            outOfBounds = false;
            punchPlayer = false;
            counter = 0;
            world.turnOver(this);
        }
    }

    public void animateMove(int amount) {
        String url;
        if (amount < 0) {
            url = getLeftUrl();
        } else {
            url = getRightUrl();
        }
        Timeline animation = new Timeline();
        animation.setCycleCount(62);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    move(amount, url);
                }));
        animation.play();
    }

    private void attack() {
        if (counter == 0) {
            this.getRectangle().setWidth(180.00);
            this.getRectangle().setFill(new ImagePattern(new Image(getPunchUrl())));
            if (world.getEnemy().equals(this)) {
                player.setX(player.getX() - 50);
            }
            setButtonVisible(false);
            hitSound.play();
        }
        if(counter == 21) {
            if (world.attack(this, World.AttackChoice.HIT, 2 * attack)) {
                world.getOtherPlayer(this).loseHp(attack * 2);
                punchPlayer = true;
            }
        }
        counter++;
        if (counter >= 40) {
            this.getRectangle().setWidth(130.00);
            loseStamina(14);
            if (world.getEnemy().equals(this)) {
                player.setX(player.getX() + 50);
            }
            this.getRectangle().setFill(new ImagePattern(this.getLogo()));
            counter = 0;
            if (!punchPlayer) {
                world.turnOver(this);
            }
            punchPlayer = false;
        }
    }

    public void animateAttack() {
        Timeline animation = new Timeline();
        animation.setCycleCount(40);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    attack();
                }));
        animation.play();
    }

    private void kick() {
        if (counter == 0) {
            this.getRectangle().setWidth(160.00);
            this.getRectangle().setFill(new ImagePattern(new Image(getLegUrl())));
            if (world.getEnemy().equals(this)) {
                player.setX(player.getX() - 30); // teistpidi playeri paika liigutamine
            }
            setButtonVisible(false);
            kickSound.play();
        }
        if (counter == 21) {
            if (world.attack(this, World.AttackChoice.KICK, 3 * attack)) {
                world.getOtherPlayer(this).loseHp(3 * attack);
                punchPlayer = true;
            }
        }
        counter++;
        if (counter >= 45) {
            this.getRectangle().setWidth(130.00);
            loseStamina(20);
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

    public void animateKick() {
        Timeline animation = new Timeline();
        animation.setCycleCount(45);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    kick();
                }));
        animation.play();
    }

    public void animateSpecial() {
        Timeline animation = new Timeline();
        int count = 32;
        if ((int)(world.distanceBetween() - 83) / 4 > 32) {
            count = (int) (world.distanceBetween() - 83) / 4;
        }
        animation.setCycleCount(count);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    special();
                }));
        animation.play();
    }

    public void special() {
        if (counter == 0) {
            this.getRectangle().setWidth(180.00);
            this.getRectangle().setFill(new ImagePattern(new Image(specialAttack)));
            if (world.getPlayer().equals(this)) {
                player.setX(player.getX() - 37); // teistpidi playeri paika liigutamine
            }
            setButtonVisible(false);
            specialSound.play();
        }
        counter++;
        if (counter >= 15) {
            projectile();
        }
        if (counter == 32) {
            if (this instanceof Ago) {
                player.setX(player.getX() + 37); // teistpidi playeri paika liigutamine
            }
            loseStamina(100);
            this.getRectangle().setWidth(130.00);
            this.getRectangle().setFill(new ImagePattern(this.getLogo()));
        }
    }

    private void projectile() {
        if (counter == 15) {
            projectile.setFill(new ImagePattern(new Image(specialPic)));
            if (this instanceof Ago) {
                projectile.setX(player.getX() + 180);
            } else {
                projectile.setX(player.getX() - 80);
            }
            projectile.setVisible(true);
        }
        if (this instanceof Ago) {
            projectile.setX(projectile.getX() + 4);
        } else {
            projectile.setX(projectile.getX() - 4);
        }
        if ((int)(world.distanceBetween() - 120)/ 4 > 32) {
            if (counter == (int)(world.distanceBetween() - 120)/ 4) {
                projectile.setVisible(false);
                if (world.getOtherPlayer(this).getHp() - 4 * attack > 0) {
                    if (this instanceof Ago) {
                        world.getOtherPlayer(this).gotKicked(true);
                    } else {
                        world.getOtherPlayer(this).gotKicked(false);
                    }
                }
                world.getOtherPlayer(this).loseHp(4 * attack);
            }
        } else if (projectile.isVisible()) {
            if (world.distanceProjectile(this, projectile) < 11 && world.distanceProjectile(this, projectile) > 6) {
                projectile.setVisible(false);
                if (world.getOtherPlayer(this).getHp() - 4 * attack > 0) {
                    if (this instanceof Ago) {
                        world.getOtherPlayer(this).gotKicked(true);
                    } else {
                        world.getOtherPlayer(this).gotKicked(false);
                    }
                }
                world.getOtherPlayer(this).loseHp(4 * attack);
            } else if (world.distanceBetween() < 200) {
                if (counter == 16) {
                    projectile.setVisible(false);
                    if (world.getOtherPlayer(this).getHp() - 4 * attack > 0) {
                        if (this instanceof Ago) {
                            world.getOtherPlayer(this).gotKicked(true);
                        } else {
                            world.getOtherPlayer(this).gotKicked(false);
                        }
                    }
                    world.getOtherPlayer(this).loseHp(4 * attack);
                }
                if (counter == 32) {
                    counter = 0;
                    if (world.getPlayer().equals(this)) {
                        player.setX(player.getX() + 37); // teistpidi playeri paika liigutamine
                    }
                    loseStamina(70);
                    this.getRectangle().setWidth(130.00);
                    this.getRectangle().setFill(new ImagePattern(this.getLogo()));
                }
            }
        }
    }

    private void sleep() {
        if (counter == 0) {
            sleep.play();
            setButtonVisible(false);
        }
        counter++;
        if (counter >= 72 ) {
            counter = 0;
            gainStamina(20);
            gainHp(10);
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
        if (this.hp >= 0) {
            Timeline animation = new Timeline();
            animation.setCycleCount(29);
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        animateHit(left);
                    }));
            animation.play();
        }
    }

    public void gotKicked(boolean left) {
        Timeline animation = new Timeline();
        animation.setCycleCount(70);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                actionEvent1 -> {
                    animateKick(left);
                }));
        animation.play();
    }

    private void loseHp(int amount) {
        damageSound.play();
        this.hp -= amount;
        world.drawHpRectangle(this);
    }

    private void gainHp(int amount) {
        if (this.hp + amount > this.maxHp) {
            this.hp = this.maxHp;
        } else {
            this.hp += amount;
        }
        world.drawHpRectangle(this);
    }

    private void loseStamina(int amount) {
        if (stamina - amount < 0) {
            stamina = 0;
        } else {
            this.stamina -= amount;
        }
        if (!this.isDead && !world.getOtherPlayer(this).isDead) {
            world.drawStaminaRectangle(this);
        }
    }

    public void gainStamina(int amount) {
        if (this.stamina + amount > 100) {
            this.stamina = 100;
        } else {
            this.stamina += amount;
        }
        if (!this.isDead && !world.getOtherPlayer(this).isDead) {
            world.drawStaminaRectangle(this);
        }
    }

    private void animateDeath(boolean left) {
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
                movePlayer(2, true);
            } else {
                movePlayer(-2, true);
            }
        }
        counter++;
        if (counter == 40){
            this.setButtonVisible(false);
            counter = 0;
        }
    }

    public void setDead(boolean dead) {
        Timeline animations = new Timeline();
        animations.setCycleCount(1);
        animations.getKeyFrames().add(new KeyFrame(Duration.millis(3000),
                actionEvent -> {
                    isDead = dead;
                }));
        animations.play();
    }

    public boolean isDead() {
        return isDead;
    }

    private void animateHit(boolean left) {
        if (counter == 0) {
            if (left) {
                this.getRectangle().setFill(new ImagePattern(new Image(leftHit)));
            } else {
                this.getRectangle().setFill(new ImagePattern(new Image(rightHit)));
            }
        }
        if (counter > 13 && counter < 22) {
            if (left) {
                movePlayer(2, false);
            } else {
                movePlayer(-2, false);
            }
        }
        counter++;
        if (counter >= 29) {
            this.getRectangle().setFill(new ImagePattern(logo));
            counter = 0;
            world.turnOver(world.getOtherPlayer(this));
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
                movePlayer(2, true);
            } else {
                movePlayer(-2, true);
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
        }
        if (counter >= 70) {
            counter = 0;
            world.turnOver(world.getOtherPlayer(this));
        }
    }

    public void turnOver(Player player) {
        if (counter == 0) {
            world.turnOver(player);
        }
    }

    public void setButtonVisible(Boolean value){ //TODO add attacking buttons too
        if (this instanceof Ago) {
            moveLeft.getButton().setVisible(value);
            moveRight.getButton().setVisible(value);
            punch.getButton().setVisible(value);
            kick.getButton().setVisible(value);
            sleeping.getButton().setVisible(value);
            if (!(world.getEnemy() instanceof Kruus)) {
                special.getButton().setVisible(value);
            }
        }
    }

    public void movePlayer(int ammount, boolean kicked) {
        if (this.getX() + ammount >= 30 && this.getX() + ammount < world.getWith() - 130) {
            player.setX(player.getX() + ammount);
            if (this instanceof Ago) {
                if (!(world.getEnemy() instanceof Kruus)) {
                    special.getButton().setCenterX(special.getButton().getCenterX() + ammount);
                }
                moveLeft.getButton().setCenterX(moveLeft.getButton().getCenterX() + ammount);
                moveRight.getButton().setCenterX(moveRight.getButton().getCenterX() + ammount);
                punch.getButton().setCenterX(punch.getButton().getCenterX() + ammount);
                kick.getButton().setCenterX(kick.getButton().getCenterX() + ammount);
                sleeping.getButton().setCenterX(sleeping.getButton().getCenterX() + ammount);
                buttonText.setX(buttonText.getX() + ammount);
            }
        } else if (kicked && this.getX() + ammount >= -100 && this.getX() + ammount < world.getWith()) {
            player.setX(player.getX() + ammount);
            if (this instanceof Ago) {
                if (!(world.getEnemy() instanceof Kruus)) {
                    special.getButton().setCenterX(special.getButton().getCenterX() + ammount);
                }
                moveLeft.getButton().setCenterX(moveLeft.getButton().getCenterX() + ammount);
                moveRight.getButton().setCenterX(moveRight.getButton().getCenterX() + ammount);
                punch.getButton().setCenterX(punch.getButton().getCenterX() + ammount);
                kick.getButton().setCenterX(kick.getButton().getCenterX() + ammount);
                sleeping.getButton().setCenterX(sleeping.getButton().getCenterX() + ammount);
                buttonText.setX(buttonText.getX() + ammount);
            }
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

    public int getMaxHp() {
        return this.maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
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

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    public Button getSleeping() {
        return sleeping;
    }

    public void setSleeping(Button sleeping) {
        this.sleeping = sleeping;
    }

    public void setDie(String die) {
        this.die = die;
    }

    public Text getButtonText() {
        return buttonText;
    }

    public void setButtonText(Text text) {
        this.buttonText = text;
    }

    public void setText(String text) {
        if (text.length() > 5) {
            buttonText.setX(buttonText.getX() - ((text.length() - 5) * 6));
        }
        buttonText.setText(text);
    }

    public void setSpecialAttack(String specialAttack) {
        this.specialAttack = specialAttack;
    }

    public Rectangle getProjectile() {
        return projectile;
    }

    public void setProjectile(Rectangle projectile) {
        this.projectile = projectile;
    }

    public String getSpecialPic() {
        return specialPic;
    }

    public void setSpecialPic(String specialPic) {
        this.specialPic = specialPic;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
