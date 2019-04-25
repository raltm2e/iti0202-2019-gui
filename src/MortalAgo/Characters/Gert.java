package MortalAgo.Characters;

import MortalAgo.Levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Gert extends Player {
    public Gert(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("file:src\\MortalAgo\\Media\\Characters\\Gert\\jump-left.gif");
        setRightUrl("file:src\\MortalAgo\\Media\\Characters\\Gert\\jump-right.gif");
        setPunchUrl("file:src\\MortalAgo\\Media\\Characters\\Gert\\hit-left.gif");
        setLefthitUrl("file:src\\MortalAgo\\Media\\Characters\\Gert\\got-hit.gif");
        setLegUrl("file:src\\MortalAgo\\Media\\Characters\\Gert\\kick-left.gif");
        setLeftFall("file:src\\MortalAgo\\Media\\Characters\\Gert\\falling.gif");
        setLeftRise("file:src\\MortalAgo\\Media\\Characters\\Gert\\rising.gif");
        setDie("file:src\\MortalAgo\\Media\\Characters\\Gert\\falling.gif");
        setSpecialAttack("file:src\\MortalAgo\\Media\\Characters\\Gert\\special.gif");
        setSpecialPic("file:src\\MortalAgo\\Media\\Characters\\Gert\\Gert_eliit.gif");
    }
}
