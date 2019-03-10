package MortalAgo.Characters;

import MortalAgo.Levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Ago extends Player {

    public Ago(Rectangle player, Image logo, World world) {
        super(player, logo, world);
        setLeftUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\ago-left.gif");
        setRightUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\ago-right.gif");
        setPunchUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\hit.gif");
        setRighthitUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\got-hit.gif");
        setLefthitUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\hit-left.gif");
    }

}
