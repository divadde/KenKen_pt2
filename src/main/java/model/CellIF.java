package model;

import java.io.Serializable;

public interface CellIF extends Serializable {

    //interazione col gioco
    boolean setValue(int value);
    int getValue();
    void clean();

    //modifica cella
    void setX(int x);
    int getX();
    void setY(int y);
    int getY();
    void setConstraint(Constraint c);
    Constraint getConstraint();
    void setCageState(boolean state);
    boolean getState();

    //controlli
    boolean hasConstraint();

    //util
}
