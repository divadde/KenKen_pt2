package model;

import java.util.List;

public interface Rules {

    List<CellIF> verifyOnGrid(int val, int x, int y);
    boolean verifyOnNumber(int val);
}
