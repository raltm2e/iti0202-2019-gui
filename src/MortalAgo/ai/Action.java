package MortalAgo.ai;

import MortalAgo.characters.Player;

public class Action {
    private Ai.ActionType type;
    private int value;

    Action(Ai.ActionType type, int value) {
        this.type = type;
        this.value = value;
    }

    Ai.ActionType getType() {
        return type;
    }

    int getValue() {
        return value;
    }

    void turnAction(Player player) {
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
            case SPECIAL:
                player.animateSpecial();
                break;
        }
    }
}
