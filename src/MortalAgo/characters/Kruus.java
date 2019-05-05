package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Kruus extends Player {

    public Kruus(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("file:src\\MortalAgo\\media\\characters\\kruus\\kruus_jump-left-once.gif");
        setRightUrl("file:src\\MortalAgo\\media\\characters\\kruus\\kruus_jump-right-once.gif");
        setLefthitUrl("file:src\\MortalAgo\\media\\characters\\kruus\\kruus_got-hit.gif");
        setPunchUrl("file:src\\MortalAgo\\media\\characters\\kruus\\kruus_hit-left.gif");
        setLegUrl("file:src\\MortalAgo\\media\\characters\\kruus\\kick-left_kruus.gif");
        setLeftFall("file:src\\MortalAgo\\media\\characters\\kruus\\falling_kruus.gif");
        setLeftRise("file:src\\MortalAgo\\media\\characters\\kruus\\rising_kruus.gif");
        setDie("file:src\\MortalAgo\\media\\characters\\kruus\\falling_kruus.gif");
    }
}
