package MortalAgo;

import javafx.application.Application;

public abstract class player {

    private int hp;
    private int attack;
    private int defence;

    public void createPlayer(String name) {
        if (name.equals("Ago")) {
            player newPlayer = new ago();
        }
        this.attack = 1;
        this.defence = 1;
        this.hp = 10;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getHp() {
        return this.hp;
    }

}
