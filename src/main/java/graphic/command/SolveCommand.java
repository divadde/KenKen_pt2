package graphic.command;

import backtracking.Backtracking;
import model.GridGame;

public class SolveCommand implements Command {

    public SolveCommand(){}

    @Override
    public void execute(GridGame gg) {
        Backtracking s = gg.getBacktracking();
        s.solve();
        System.out.println("SolveCommand: soluzioni trovate");
    }

}
