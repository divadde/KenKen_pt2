package backtracking;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


//TEMPLATE METHOD
public abstract class Backtracking<P,C,S> {
    protected List<P> choosingPoints;

    public abstract void solve();

    protected void execute(P point) {
        Collection<C> choices=admissibleChoices(point);
        for(C choice: choices) {
            if(stop(point))
                break;
            submit(point,choice);
            if(foundSolution(point)) submitSolution(point);
            else execute(nextPoint(point));
            remove(point,choice);
        }
    }

    protected abstract Collection<C> admissibleChoices(P point);

    protected boolean stop(P point) {return false;}

    protected abstract boolean admissible(P point,C choice);

    protected abstract void submit(P point,C choice);

    protected abstract void remove(P point,C choice);

    protected abstract void submitSolution(P point);

    private P nextPoint(P point) {
        if(foundSolution(point)) throw new NoSuchElementException();
        int i=choosingPoints.indexOf(point);
        return choosingPoints.get(i+1);
    }

    protected boolean foundSolution(P point) {
        return false;
    }

    protected abstract List<P> computeChoosingPoints();


    public abstract S nextSol();
    public abstract S prevSol();

    public abstract int numSol();
    public abstract void setMaxSol(int maxSol);
}