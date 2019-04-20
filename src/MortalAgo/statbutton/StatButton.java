package MortalAgo.statbutton;

import javafx.scene.control.Button;

public class StatButton extends Button{
    public StatButton(String text, int x, int y) {
        Button buyMouse = new Button();
        buyMouse.setLayoutX(x);
        buyMouse.setLayoutY(y);
        buyMouse.setText(text);
        buyMouse.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);\n" +
                "    -fx-background-radius: 100;\n" +
                "    -fx-background-insets: 0;\n" +
                "    -fx-font-size: 18;\n" +
                "    -fx-text-fill: white;");
        buyMouse.setOnMouseEntered(event -> {
            buyMouse.setOpacity(0.7);
        });
        buyMouse.setOnMouseExited(event -> {
            buyMouse.setOpacity(1);
        });
        buyMouse.setOnMousePressed(event -> {
            buyMouse.setOpacity(0.3);
        });
        buyMouse.setOnMouseReleased(event -> {
            buyMouse.setOpacity(1);
        });
    }

    public StatButton getButton() {
        return this;
    }
}
