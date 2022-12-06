package sac.model.observers;

public class ComboObserver extends ScoreCalculator{

    private int score = 0;
    private int comboCount = 0;

    @Override
    public void update(DataPackage obj) {
        if (obj.rowCleared == 0){
            comboCount = 0;
        }
        else{
            comboCount += 1;
            score += comboCount;
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    public int getCombo(){
        return comboCount;
    }
}
