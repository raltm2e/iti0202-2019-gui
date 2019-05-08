package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Gert extends Player {
    public Gert(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("/characters/gert/jump-left.gif");
        setRightUrl("/characters/gert/jump-right.gif");
        setPunchUrl("/characters/gert/hit-left.gif");
        setLefthitUrl("/characters/gert/got-hit.gif");
        setLegUrl("/characters/gert/kick-left.gif");
        setLeftFall("/characters/gert/falling.gif");
        setLeftRise("/characters/gert/rising.gif");
        setDie("/characters/gert/falling.gif");
        setSpecialAttack("/characters/gert/special.gif");
        setSpecialPic("/characters/gert/Gert_eliit.gif");
    }
}
