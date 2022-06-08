package backtracking;

import model.CellIF;
import model.GridGame;
import model.util.MementoTable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

//SINGLETON
public final class KenKenSolver extends Backtracking<CellIF,Integer,MementoTable>{
    private static KenKenSolver INSTANCE=null;
    private static int sol;
    private static GridGame gg;
    private static List<MementoTable> completeTables;
    private static ListIterator<MementoTable> lit;
    private static int maxSol;

    private KenKenSolver(GridGame gg){
        maxSol=10; //assegnate di default
        this.gg=gg;
        completeTables=new LinkedList<>();
        lit=completeTables.listIterator();
        choosingPoints=computeChoosingPoints();
        //System.out.println(choosingPoints.size());
    }

    public static synchronized KenKenSolver getInstance(GridGame gg){
        if(INSTANCE==null){
            INSTANCE=new KenKenSolver(gg);
        }
        return INSTANCE;
    }

    @Override
    protected boolean foundSolution() {
        return gg.isCompleted();
    }

    @Override
    protected boolean admissible(CellIF cell, Integer integer) {
        return gg.isLegal(integer, cell.getX(), cell.getY());
    }

    @Override
    protected void submit(CellIF cell, Integer integer) {
        gg.addValue(integer, cell.getX(), cell.getY());
        //System.out.println("Aggiunto a "+cell.getX()+","+cell.getY()+" il valore "+integer);
    }

    @Override
    protected void remove(CellIF cellIF) {
        gg.removeValue(cellIF.getX(), cellIF.getY());
    }

    @Override
    protected void submitSolution() {
        completeTables.add(gg.createMemento());
        sol++;
        System.out.println("Soluzione "+sol+" trovata!");
    }

    @Override
    protected List<CellIF> computeChoosingPoints() {
        LinkedList<CellIF> celle = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                celle.addLast(gg.getCell(i,j));
            }
        }
        return celle;
    }

    @Override
    protected List<Integer> admissibleChoices(CellIF cell) {
        LinkedList<Integer> ret = new LinkedList<>();
        for(int i=0; i<gg.getDimension(); i++){
            //System.out.println("Cella incriminata: "+cell.getX()+","+cell.getY());
            if(admissible(cell,i+1)) {
                ret.addLast(i + 1);
            }
        }
        //System.out.println("Scelte ammissibili per"+cell.getX()+","+cell.getY()+" :");
        //System.out.println(ret);
        return ret;
    }

    @Override
    public void solve() {
        completeTables=new LinkedList<>();
        sol=0;
        gg.clean();
        choosingPoints=computeChoosingPoints();
        execute(choosingPoints.get(0));
        System.out.println("Soluzioni trovate: "+sol);
        /*
        for(CellIF[][] soluzione: completeTables){
            gg.setTable(soluzione);
            System.out.println(gg.toString());
        }
        */
        System.out.println(completeTables.size());
        lit=completeTables.listIterator();
    }


    @Override
    public MementoTable nextSol() {
        if(lit.hasNext())
            return lit.next();
        return null;
    }

    @Override
    public MementoTable prevSol() {
        if(lit.hasPrevious())
            return lit.previous();
        return null;
    }

    @Override
    protected boolean stop(){
        return !(numSol()<maxSol);
    }

    @Override
    public int numSol(){
        return completeTables.size();
    }

    @Override
    public void setMaxSol(int maxSol){
        this.maxSol=maxSol;
        System.out.println("Il numero massimo di soluzioni è stato impostato a: "+maxSol);
    }


}
