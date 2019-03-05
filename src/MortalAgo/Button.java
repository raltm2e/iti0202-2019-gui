package MortalAgo;

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

    public void leftButton(){
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRetangle().setFill(new ImagePattern(player.getLogo()));
            Timeline animation = new Timeline();
            animation.setCycleCount(20);
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        player.moveLeft();
                    }));
            animation.play();
        });

    }

    public void rightButton(){
        this.button.setOnMouseClicked(mouseEvent -> {
            player.getRetangle().setFill(new ImagePattern(player.getLogo()));
            Timeline animation = new Timeline();
            animation.setCycleCount(20);
            animation.getKeyFrames().add(new KeyFrame(Duration.millis(25),
                    actionEvent1 -> {
                        player.moveRight();
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
