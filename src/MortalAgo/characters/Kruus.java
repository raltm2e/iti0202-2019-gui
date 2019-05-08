package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Kruus extends Player {

    public Kruus(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("/characters/kruus/kruus_jump-left-once.gif");
        setRightUrl("/characters/kruus/kruus_jump-right-once.gif");
        setLefthitUrl("/characters/kruus/kruus_got-hit.gif");
        setPunchUrl("/characters/kruus/kruus_hit-left.gif");
        setLegUrl("/characters/kruus/kick-left_kruus.gif");
        setLeftFall("/characters/kruus/falling_kruus.gif");
        setLeftRise("/characters/kruus/rising_kruus.gif");
        setDie("/characters/kruus/falling_kruus.gif");
    }
}
