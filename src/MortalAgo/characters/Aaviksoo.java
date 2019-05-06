package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Aaviksoo extends Player {

    public Aaviksoo(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_jump-left-once.gif");
        setRightUrl("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_jump-right-once.gif");
        setPunchUrl("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_hit-left.gif");
        setLefthitUrl("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_got-hit.gif");
        setLegUrl("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_kick-left.gif");
        setLeftFall("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_falling.gif");
        setLeftRise("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_rising.gif");
        setDie("file:src\\MortalAgo\\media\\characters\\aaviksoo\\aaviksoo_falling.gif");
        setSpecialAttack("file:src\\MortalAgo\\media\\characters\\aaviksoo\\special.gif");
        setSpecialPic("file:src\\MortalAgo\\media\\characters\\aaviksoo\\500_euro.jpg");
    }
}
