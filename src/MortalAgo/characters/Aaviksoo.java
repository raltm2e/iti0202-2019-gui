package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Aaviksoo extends Player {

    public Aaviksoo(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("/characters/aaviksoo/aaviksoo_jump-left-once.gif");
        setRightUrl("/characters/aaviksoo/aaviksoo_jump-right-once.gif");
        setPunchUrl("/characters/aaviksoo/aaviksoo_hit-left.gif");
        setLefthitUrl("/characters/aaviksoo/aaviksoo_got-hit.gif");
        setLegUrl("/characters/aaviksoo/aaviksoo_kick-left.gif");
        setLeftFall("/characters/aaviksoo/aaviksoo_falling.gif");
        setLeftRise("/characters/aaviksoo/aaviksoo_rising.gif");
        setDie("/characters/aaviksoo/aaviksoo_falling.gif");
        setSpecialAttack("/characters/aaviksoo/special.gif");
        setSpecialPic("/characters/aaviksoo/500_euro.jpg");
    }
}
