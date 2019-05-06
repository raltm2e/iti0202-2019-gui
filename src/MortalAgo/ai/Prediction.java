package MortalAgo.ai;

public class Prediction {
    private Action first;
    private Action enemyFirst;
    private Action second;

    public Prediction(Action first, Action enemyFirst) {

        this.first = first;
        this.enemyFirst = enemyFirst;
    }

    public Prediction(Action first, Action enemyFirst, Action second) {

        this.first = first;
        this.enemyFirst = enemyFirst;
        this.second = second;
    }

    public Action getFirst() {
        return first;
    }

    public Action getEnemyFirst() {
        return enemyFirst;
    }

    public int getPoints() {
        return first.getValue() - enemyFirst.getValue();
    }

    public int getDeeperPoints() { return first.getValue() - enemyFirst.getValue() + second.getValue(); }
    @Override
    public String toString() {
        return " "+first.getType() + " " + getPoints() ;
    }
}
