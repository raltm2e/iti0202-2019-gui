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
    public enum ActionType { MOVELEFT, MOVERIGHT, SLEEP, PUNCH, KICK, SPECIAL }
    public Ai(Player player, World world) {
        this.player = player;
        this.world = world;
    }
    private int getValue(ActionType action, Player player) {
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
                if (player.getStamina() < 30) {
                    return 110;
                } else {
                    return 5;
                }
            case SLEEP:
                if (player.getHp() < (player.getHp() / 4)) {
                    return 210;
                } else if (world.getHitTextPercentage() < 1 ) { // TODO add max hp

                }
                if (player.getStamina() < 20) {
                    return 120;
                } else {
                    return 20;
                }
            case SPECIAL:
                System.out.println(player.getStamina());
                if (player.getStamina() > 70) {
                    return 300;
                } else {
                    return 0;
                }
                default: return 0;
        }

    }
    private List<Action> getBests(Player player) {
        List<Action> values = new ArrayList<>();
        values.add(new Action(ActionType.PUNCH, getValue(ActionType.PUNCH, player)));
        values.add(new Action(ActionType.KICK, getValue(ActionType.KICK, player)));
        values.add(new Action(ActionType.MOVELEFT, getValue(ActionType.MOVELEFT, player)));
        values.add(new Action(ActionType.MOVERIGHT, getValue(ActionType.MOVERIGHT, player)));
        values.add(new Action(ActionType.SLEEP, getValue(ActionType.SLEEP, player)));
        values.sort(Comparator.comparing(Action::getValue).reversed());
        return values;
    }

    private List<Action> getEnemyBests() {
        List<Action> values = getBests(world.getOtherPlayer(player));
        values.add(new Action(ActionType.SPECIAL, getValue(ActionType.SPECIAL, world.getOtherPlayer(player))));
        values.sort(Comparator.comparing(Action::getValue).reversed());
        return values;
    }
    public void turn() {
        if (player instanceof Gert) {
            List<Action> actions = getBests(player);
            Action enemyAction = getEnemyBests().get(0);
            List<Prediction> predictions = new ArrayList<>();
            for (Action action: actions) {
                predictions.add(new Prediction(action, enemyAction));
            }
            predictions.sort(Comparator.comparing(Prediction::getPoints).reversed());
            System.out.println(predictions);
            System.out.println(predictions.get(0).getPoints());
            predictions.get(0).getFirst().turnAction(player);
        } else if (player instanceof Kruus) {
            getBests(player).get(0).turnAction(player);
        }
    }
}
