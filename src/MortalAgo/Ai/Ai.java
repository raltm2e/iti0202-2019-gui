package MortalAgo.Ai;

import MortalAgo.Characters.Gert;
import MortalAgo.Characters.Kruus;
import MortalAgo.Characters.Player;
import MortalAgo.Levels.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ai {
    private Player player;
    private World world;
    public enum ActionType { MOVELEFT, MOVERIGHT, SLEEP, PUNCH, KICK }
    public Ai(Player player, World world) {
        this.player = player;
        this.world = world;
    }
    private int getValue(ActionType action) {
        switch (action) {
            case PUNCH:
                if (world.getHitTextPercentage() > 50) {
                    return 100;
                } else if (world.getHitTextPercentage() > 10) {
                    return 50;
                } else {
                    return 0;
                }
            case KICK:
                if (world.getKickTextPercentage() > 50) {
                    return 200;
                } else if (world.getKickTextPercentage() > 10) {
                    return 100;
                } else {
                    return 0;
                }
            case MOVELEFT:
                if (world.getHitTextPercentage() < 1) {
                    return 100;
                } else {
                    return 10;
                }
            case MOVERIGHT:
                if (player.getStamina() < 20) {
                    return 150;
                } else {
                    return 5;
                }
            case SLEEP:
                if (player.getStamina() < 20) {
                    return 120;
                } else {
                    return 20;
                }
                default: return 0;
        }

    }
    public void turn() {
        if (player instanceof Kruus) {
            List<Action> values = new ArrayList<>();
            values.add(new Action(ActionType.PUNCH, getValue(ActionType.PUNCH)));
            values.add(new Action(ActionType.KICK, getValue(ActionType.KICK)));
            values.add(new Action(ActionType.MOVELEFT, getValue(ActionType.MOVELEFT)));
            values.add(new Action(ActionType.MOVERIGHT, getValue(ActionType.MOVERIGHT)));
            values.add(new Action(ActionType.SLEEP, getValue(ActionType.SLEEP)));
            values.sort(Comparator.comparing(Action::getValue).reversed());
            values.get(0).turnAction(player);
        } else if (player instanceof Gert) {

        }
    }
}
