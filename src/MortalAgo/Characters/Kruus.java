package MortalAgo.Characters;

import MortalAgo.Levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Kruus extends Player {

    public Kruus(Rectangle player, Image logo, World world) {
        super(player, logo, world);
        setLeftUrl("file:src\\MortalAgo\\Media\\Characters\\Kruus\\jump-left-once.gif");
        setRightUrl("file:src\\MortalAgo\\Media\\Characters\\Kruus\\jump-right-once.gif");
        setLefthitUrl("file:src\\MortalAgo\\Media\\Characters\\Kruus\\got-hit.gif");
        setPunchUrl("file:src\\MortalAgo\\Media\\Characters\\Kruus\\hit-left.gif");
    }

}
