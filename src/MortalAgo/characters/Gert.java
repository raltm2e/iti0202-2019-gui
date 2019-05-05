package MortalAgo.characters;

import MortalAgo.levels.World;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class Gert extends Player {
    public Gert(Rectangle player, Image logo, World world, int attack, int maxHp) {
        super(player, logo, world, attack, maxHp);
        setLeftUrl("file:src\\MortalAgo\\media\\characters\\gert\\jump-left.gif");
        setRightUrl("file:src\\MortalAgo\\media\\characters\\gert\\jump-right.gif");
        setPunchUrl("file:src\\MortalAgo\\media\\characters\\gert\\hit-left.gif");
        setLefthitUrl("file:src\\MortalAgo\\media\\characters\\gert\\got-hit.gif");
        setLegUrl("file:src\\MortalAgo\\media\\characters\\gert\\kick-left.gif");
        setLeftFall("file:src\\MortalAgo\\media\\characters\\gert\\falling.gif");
        setLeftRise("file:src\\MortalAgo\\media\\characters\\gert\\rising.gif");
        setDie("file:src\\MortalAgo\\media\\characters\\gert\\falling.gif");
        setSpecialAttack("file:src\\MortalAgo\\media\\characters\\gert\\special.gif");
        setSpecialPic("file:src\\MortalAgo\\media\\characters\\gert\\gert_eliit.gif");
    }
}
