package MortalAgo.Characters;

import MortalAgo.Levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Ago extends Player {

    public Ago(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\ago-left.gif");
        setRightUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\ago-right.gif");
        setPunchUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\punch.gif");
        setRighthitUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\got-hit.gif");
        setLegUrl("file:src\\MortalAgo\\Media\\Characters\\Ago\\kick-once.gif");
        setRightFall("file:src\\MortalAgo\\Media\\Characters\\Ago\\falling-left.gif");
        setRightRise("file:src\\MortalAgo\\Media\\Characters\\Ago\\rising-left.gif");
        setDie("file:src\\MortalAgo\\Media\\Characters\\Ago\\falling-left.gif");
        setSpecialAttack("file:src\\MortalAgo\\Media\\Characters\\Ago\\special.gif");
    }

}
