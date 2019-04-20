package MortalAgo;

import MortalAgo.Characters.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Button {
    private String name;
    private Image logo;
    private Circle button;
    private Player player;
    private int counter = 0;

    public Button(String name, Image logo, Circle button, Player player){
        this.name = name;
        this.logo = logo;
        this.button = button;
        this.player = player;
        event();
    }

    public Button(String name, Circle button, Player player){
        this.name = name;
        this.button = button;
        this.player = player;
        event();
    }

    private void event() {
        Glow glow = new Glow();
        glow.setLevel(1);
        button.setOnMouseEntered(mouseEvent ->  {
            button.setEffect(glow);
            player.setText(getText());
            player.getButtonText().setVisible(true);
        });
        button.setOnMouseExited(mouseEvent -> {
            button.setEffect(null);
            player.getButtonText().setVisible(false);
            if (player.getButtonText().getText().length() > 5) {
                player.getButtonText().setX(player.getButtonText().getX() + ((player.getButtonText().getText().length() - 5) * 6));
            }
        });
    }

    private String getText() {
        switch (name) {
            case "left":
                return "Jump Left";
            case "right":
                return "Jump Right";
            case "hit":
                return "Punch(" + player.getWorld().getHitTextPercentage() + "%)";
            case "kick":
                return "Kick(" + player.getWorld().getKickTextPercentage() + "%)";
            case "sleep":
                return "Sleep";
            case "special":
                if (player.getStamina() < 70) {
                    return "No!";
                }
                return "Special";
                default:
                    return "";
        }
    }

    private void clickAnimation(int ammount) {
        this.button.setOnMouseClicked(mouseEvent -> {

        });
        Timeline animation = new Timeline();
        animation.setCycleCount(10);
        animation.getKeyFrames().add(new KeyFrame(Duration.millis(15),
                actionEvent1 -> {
                    animateClick(name, ammount);
                }));
        animation.play();
    }

    private void animateClick(String name, int ammount) {
        if (counter < 5) {
            button.setRadius(button.getRadius() + 1);
        } else {
            button.setRadius(button.getRadius() - 1);
        }
        counter++;
        if (counter == 10) {
            counter = 0;
            animateButton(name, ammount);
        }
    }
    private void animateButton(String name, int amount) {
        switch (name) {
            case "left": case "right":
                player.animateMove(amount);
                moveButton(amount);
                break;
            case "hit":
                player.animateAttack();
                attackButton();
                break;
            case "kick":
                player.animateKick();
                kickButton();
                break;
            case "sleep":
                player.animateSleep();
                sleepButton();
                break;
            case "special":
                if (player.getStamina() >= 70) {
                    Timeline animation3 = new Timeline();
                    int count = 32;
                    if ((player.getWorld().distanceBetween() - 120) / 4 > 32) {
                        count = (int) (player.getWorld().distanceBetween() - 120) / 4;
                    }
                    animation3.setCycleCount(count);
                    animation3.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                            actionEvent1 -> {
                                player.special();
                            }));
                    animation3.play();
                    attackButton();
                } else {
                    attackButton();
                }
        }
    }

    public void moveButton(int amount){
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRectangle().setFill(new ImagePattern(player.getLogo()));
            clickAnimation(amount);
        });
    }

    public void attackButton() {
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRectangle().setFill(new ImagePattern(player.getLogo()));
            clickAnimation(0);
        });
    }

    public void kickButton() {
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRectangle().setFill(new ImagePattern(player.getLogo()));
            clickAnimation(0);
        });
    }

    public void sleepButton() {
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRectangle().setFill(new ImagePattern(player.getLogo()));
            clickAnimation(0);
        });
    }

    public void setLogo(Image logo){
        this.logo = logo;
    }

    public Image getLogo(){
        return this.logo;
    }

    public String getName(){
        return this.name;
    }

    public Circle getButton(){
        return this.button;
    }

}
