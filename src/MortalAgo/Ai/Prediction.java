package MortalAgo.Ai;

public class Prediction {
    private Action first;
    private Action enemyFirst;

    public Prediction(Action first, Action enemyFirst) {

        this.first = first;
        this.enemyFirst = enemyFirst;
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
    @Override
    public String toString() {
        return " "+first.getType() + " " + getPoints() ;
    }
}
