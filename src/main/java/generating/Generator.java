package generating;

import model.Constraint;
import model.GridGame;

//TEMPLATE METHOD
public abstract class Generator {

    protected static GridGame gg;
    protected static Constraint constraint = null; //prototype
    //todo aggiungi parametri
    protected static int maxGrandezza;
    protected static int minGrandezza;

    public void generate(){
        insertNumbers();
        shuffleNumbers();
        addConstraints();
        eraseNumbers();
    }

    public abstract void insertNumbers();
    public abstract void shuffleNumbers();
    public abstract void addConstraints();
    public abstract void eraseNumbers();

    public void setPrototypeConstraint(Constraint c){
        constraint=c;
    }

}
