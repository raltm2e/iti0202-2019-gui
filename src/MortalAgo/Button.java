package MortalAgo;

import MortalAgo.Characters.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Button {
    private String name;
    private Image logo;
    private Circle button;
    private Player player;

    public Button(String name, Image logo, Circle button, Player player){
        this.name = name;
        this.logo = logo;
        this.button = button;
        this.player = player;
    }

    public Button(String name, Circle button, Player player){
        this.name = name;
        this.button = button;
        this.player = player;
    }

    public void moveButton(int amount){
        String url;
        if (amount < 0) {
            url = player.getLeftUrl();
        } else {
            url = player.getRightUrl();
        }
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRectangle().setFill(new ImagePattern(player.getLogo()));
            Timeline animation = new Timeline();
            animation.setCycleCount(62);
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        player.move(amount , url);
                    }));
            animation.play();
        });
    }

    public void attackButton(){
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRectangle().setFill(new ImagePattern(player.getLogo()));
            Timeline animation = new Timeline();
            animation.setCycleCount(40);
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        player.attack();
                    }));
            animation.play();
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
