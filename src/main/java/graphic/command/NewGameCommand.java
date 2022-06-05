package graphic.command;

import backtracking.Backtracking;
import generating.Generator;
import model.Cage;
import model.GridGame;
import model.Settings;

public class NewGameCommand implements Command {
    Settings s;

    public NewGameCommand(Settings s){
        this.s=s;
    }

    @Override
    public void execute(GridGame gg) {
        gg.setDimension(s.getDimension());
        Backtracking b = gg.getBacktracking();
        b.setMaxSol(s.getMaxSol());
        System.out.println("Impostate "+s.getMaxSol()+"soluzioni");
        Generator g = gg.getGenerator();
        Cage cage = new Cage();
        cage.setRelazPrecedenza(s.isPrecedenza());
        g.setPrototypeConstraint(cage);
        g.generate();
        System.out.println("Generato nuovo gioco");
    }
}
