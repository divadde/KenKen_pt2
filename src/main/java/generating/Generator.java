package generating;

import model.constraints.Constraint;
import model.GridGame;

//TEMPLATE METHOD
public abstract class Generator {

    protected static GridGame gg;
    protected static Constraint constraint = null; //prototype

    public void generate(){
        insertNumbers();
        shuffleNumbers();
        addConstraints();
        eraseNumbers();
    }

    protected abstract void insertNumbers();
    protected abstract void shuffleNumbers();
    protected abstract void addConstraints();
    protected abstract void eraseNumbers();

    public void setPrototypeConstraint(Constraint c){
        constraint=c;
    }

}
