package MortalAgo;

import MortalAgo.Characters.Player;
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
        });
        button.setOnMouseExited(mouseEvent -> {
            button.setEffect(null);
        });
    }

    private void clickAnimation(int ammount) {
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
        String url;
        if (amount < 0) {
            url = player.getLeftUrl();
        } else {
            url = player.getRightUrl();
        }
        switch (name) {
            case "left": case "right":
                Timeline animation = new Timeline();
                animation.setCycleCount(62);
                animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                        actionEvent1 -> {
                            player.move(amount, url);
                        }));
                animation.play();
                break;
            case "hit":
                Timeline animation1 = new Timeline();
                animation1.setCycleCount(40);
                animation1.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                        actionEvent1 -> {
                            player.attack();
                        }));
                animation1.play();
                break;
            case "kick":
                Timeline animation2 = new Timeline();
                animation2.setCycleCount(45);
                animation2.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                        actionEvent1 -> {
                            player.kick();
                        }));
                animation2.play();
                break;
            case "sleep":
                player.animateSleep();
                break;
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
