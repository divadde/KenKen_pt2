package model.rule;

import model.CellIF;
import model.GridGame;

import java.util.LinkedList;
import java.util.List;

public class KenKenRules implements Rules {
    private static KenKenRules INSTANCE = null;
    private static GridGame gridGame;

    private KenKenRules(GridGame gridGame){
        this.gridGame=gridGame;
    }

    public static synchronized KenKenRules getInstance(GridGame gridGame){
        if(INSTANCE==null)
            return new KenKenRules(gridGame);
        return INSTANCE;
    }

    @Override
    public List<CellIF> verifyOnGrid(int val, int x, int y) {
        List<CellIF> ret = new LinkedList<>();
        for (int i = 0; i < gridGame.getDimension(); i++) {
            if (i != y && val == gridGame.getValue(x,i)) {
                ret.add(gridGame.getCell(x,i));
            }
            if (i != x && val == gridGame.getValue(i,y)) {
                ret.add(gridGame.getCell(i,y));
            }
        }
        return ret;
    }

    @Override
    public boolean verifyOnNumber(int val) {
        return val>=1 && val<=gridGame.getDimension();
    }
}
