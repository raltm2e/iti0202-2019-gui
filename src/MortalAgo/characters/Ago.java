package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Ago extends Player {

    public Ago(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("file:src\\MortalAgo\\media\\characters\\ago\\ago-left.gif");
        setRightUrl("file:src\\MortalAgo\\media\\characters\\ago\\ago-right.gif");
        setPunchUrl("file:src\\MortalAgo\\media\\characters\\ago\\punch.gif");
        setRighthitUrl("file:src\\MortalAgo\\media\\characters\\ago\\got-hit.gif");
        setLegUrl("file:src\\MortalAgo\\media\\characters\\ago\\kick-once.gif");
        setRightFall("file:src\\MortalAgo\\media\\characters\\ago\\falling-left.gif");
        setRightRise("file:src\\MortalAgo\\media\\characters\\ago\\rising-left.gif");
        setDie("file:src\\MortalAgo\\media\\characters\\ago\\falling-left.gif");
        setSpecialAttack("file:src\\MortalAgo\\media\\characters\\ago\\special.gif");
        setSpecialPic("file:src\\MortalAgo\\media\\characters\\ago\\Ago-special.PNG");
    }
}
