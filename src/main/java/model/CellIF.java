package model;

import model.constraints.Constraint;

import java.io.Serializable;
import java.util.List;

public interface CellIF {

    //interazione col gioco
    void setValue(int value);
    int getValue();
    void clean();

    //modifica cella
    int getX();
    int getY();
    void setConstraint(Constraint c);
    Constraint getConstraint();
    void setCageState(boolean state);
    void setRulesState(boolean state);
    boolean getState();

    //controlli
    boolean hasConstraint();
    void removeInContrast(CellIF c);
    void addInContrast(CellIF c);
    List<CellIF> getInContrast();

}
