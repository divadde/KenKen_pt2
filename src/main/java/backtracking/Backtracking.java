package backtracking;

import java.util.*;


//TEMPLATE METHOD
public abstract class Backtracking<P,C,S> {
    protected List<P> choosingPoints;

    public abstract void solve();

    protected void execute(P point) {
        List<C> choices=admissibleChoices(point);
        for(C choice: choices) {
            if(stop())
                break;
            submit(point,choice);
            if(foundSolution()) submitSolution();
            else execute(nextPoint(point));
            remove(point);
        }
    }

    protected abstract List<C> admissibleChoices(P point);

    protected boolean stop() {return false;}

    protected abstract boolean admissible(P point,C choice);

    protected abstract void submit(P point,C choice);

    protected abstract void remove(P point);

    protected abstract void submitSolution();

    private P nextPoint(P point) {
        if(foundSolution()) throw new NoSuchElementException();
        int i=choosingPoints.indexOf(point);
        return choosingPoints.get(i+1);
    }

    protected abstract boolean foundSolution();

    protected abstract List<P> computeChoosingPoints();


    public abstract S nextSol();
    public abstract S prevSol();

    public abstract int numSol();
    public abstract void setMaxSol(int maxSol);
}