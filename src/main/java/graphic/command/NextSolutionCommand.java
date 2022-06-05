package graphic.command;

import backtracking.Backtracking;
import model.GridGame;
import model.MementoTable;

public class NextSolutionCommand implements Command {
    private static Direction previousDir = null;
    private static Direction nextDir = null;

    private interface GoOn{
        void goOn(GridGame gg);
    }

    public enum Direction implements GoOn {
        NEXT {
            public void goOn(GridGame gg) {
                Backtracking s = gg.getBacktracking();
                MementoTable currSol = (MementoTable) s.nextSol();
                if (currSol != null) {
                    gg.setMemento(currSol);
                    System.out.println("NEXT SOLUTION");
                }
            }
        },
        PREVIOUS {
            public void goOn(GridGame gg) {
                Backtracking s = gg.getBacktracking();
                MementoTable currSol = (MementoTable) s.prevSol();
                if (currSol != null) {
                    gg.setMemento(currSol);
                    System.out.println("PREV SOLUTION");
                }
            }
        }
    }

    public NextSolutionCommand(Direction dir){
        if(previousDir==null)
            previousDir=dir;
        nextDir=dir;
    }



    @Override
    public void execute(GridGame gg) {
        if(previousDir!=nextDir)
            nextDir.goOn(gg);
        nextDir.goOn(gg);
        previousDir=nextDir;
    }

}

