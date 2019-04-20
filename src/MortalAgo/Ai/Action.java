package MortalAgo.Ai;

import MortalAgo.Characters.Player;

public class Action {
    private Ai.ActionType type;
    private int value;

    public Action(Ai.ActionType type, int value) {
        this.type = type;
        this.value = value;
    }

    public Ai.ActionType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void turnAction(Player player) {
        switch (type) {
            case PUNCH:
                player.animateAttack();
                break;
            case KICK:
                player.animateKick();
                break;
            case MOVELEFT:
                player.animateMove(-4);
                break;
            case MOVERIGHT:
                player.animateMove(4);
                break;
            case SLEEP:
                player.animateSleep();
                break;
        }
    }
}
