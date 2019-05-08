package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Ago extends Player {

    public Ago(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("/characters/ago/ago-left.gif");
        setRightUrl("/characters/ago/ago-right.gif");
        setPunchUrl("/characters/ago/punch.gif");
        setRighthitUrl("/characters/ago/got-hit.gif");
        setLegUrl("/characters/ago/kick-once.gif");
        setRightFall("/characters/ago/falling-left.gif");
        setRightRise("/characters/ago/rising-left.gif");
        setDie("/characters/ago/falling-left.gif");
        setSpecialAttack("/characters/ago/special.gif");
        setSpecialPic("/characters/ago/Ago-special.PNG");
    }
}
