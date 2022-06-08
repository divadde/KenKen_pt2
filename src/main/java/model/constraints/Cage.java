package model.constraints;

import model.CellIF;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Cage extends Constraint implements Serializable {
    private Operation op;
    private LinkedList<CellIF> cells;
    private int result;
    private boolean relazPrecedenza;

    private static final long serialVersionUID = 7477631348882269149L;


    private interface Operable {
        int doOp(List<Integer> l);
    }
    private enum Operation implements Operable, Serializable {
        ADDIZIONE {
            @Override
            public int doOp(List<Integer> l){
                int result=0;
                for(Integer i: l)
                    result+=i;
                return result;
            }
        },SOTTRAZIONE{
            @Override
            public int doOp(List<Integer> l){
                int result=0;
                boolean first=true;
                for(Integer i: l){
                    if(first) { result=i; first=false; }
                    else result-=i;
                }
                return result;
            }
        },MOLTIPLICAZIONE{
            @Override
            public int doOp(List<Integer> l){
                int result=0;
                boolean first=true;
                for(Integer i: l){
                    if(first) { result=i; first=false; }
                    else result*=i;
                }
                return result;
            }
        },DIVISIONE {
            @Override
            public int doOp(List<Integer> l) {
                int result = 0;
                boolean first = true;
                for (Integer i : l) {
                    if (first) {
                        result = i;
                        first = false;
                    } else result /= i;
                }
                return result;
            }
        };

        public static Operation getRandomOp(List<Operation> notIncluded){
            List<Operation> available = availableOperations(notIncluded);
            int choose = (int) (Math.random()*available.size());
            return available.get(choose);
        }

        private static List<Operation> availableOperations(List<Operation> notIncluded){
            List<Operation> list = new LinkedList<>();
            list.add(Operation.ADDIZIONE); list.add(Operation.SOTTRAZIONE);
            list.add(Operation.MOLTIPLICAZIONE); list.add(Operation.DIVISIONE);
            list.removeAll(notIncluded);
            return list;
        }

    }

    public Cage(){
        cells=new LinkedList<CellIF>();
        relazPrecedenza=false; //di default non attivata
    }

    public void setRelazPrecedenza(boolean relazPrecedenza) {
        this.relazPrecedenza = relazPrecedenza;
    }

    @Override
    public void setValues(){
        //System.out.println(relazPrecedenza);
        setRandomOp();
        List<Integer> listOfInteger = getValues();
        result=op.doOp(listOfInteger);
    }

    //OK: ma, possiamo eliminare la dipendenza dalla dimensione?
    private void setRandomOp(){
        List<Operation> excludedOp = new LinkedList<>();
        if (cells.size() == 2 && (cells.get(0).getValue() - cells.get(1).getValue()>0)) {
            //se le celle sono solo due, allora posso dare anche la sottrazione o la divisione
            if (cells.get(0).getValue() % cells.get(1).getValue() != 0) {
                excludedOp.add(Operation.DIVISIONE);
            }
        }
        else {
            excludedOp.add(Operation.DIVISIONE);
            excludedOp.add(Operation.SOTTRAZIONE);
        }
        op = Operation.getRandomOp(excludedOp);
    }

    @Override //lista di celle ordinata dalla prima aggiunta all'ultima
    public void addCell(CellIF ec){
        cells.addLast(ec);
    }

    @Override
    public Cage clone() {
        Cage c = (Cage) super.clone();
        c.cells = new LinkedList<>(); //assegno una nuova lista vuota per evitare aliasing con le altre gabbie
        return c;
    }

    @Override
    public boolean verify() {
        if (!arePositive(getValues()) || (arePositive(getValues()) && result == op.doOp(getValues())))
            validaCelle(true);
        else if (arePositive(getValues()) && !(result == op.doOp(getValues())))
            validaCelle(false);
        return !arePositive(getValues()) || result == op.doOp(getValues());
    }

    private void validaCelle(boolean state){
        for(CellIF cell: cells) {
            cell.setCageState(state);
        }
    }

    //ritorna true se tutte le celle contengono numeri positivi
    private boolean arePositive(List<Integer> l){
        for(Integer i: l){
            if(i<1) return false;
        }
        return true;
    }

    private List<Integer> getValues(){
        LinkedList<Integer> ret = new LinkedList<>();
        for(CellIF ec: cells) ret.addLast(ec.getValue());
        if(!relazPrecedenza) //se non c'è relazione di precedenza i valori sono ordinati in maniera decrescente
            ret.sort((Integer i1, Integer i2)->i2.compareTo(i1));
        return ret;
    }

    public String toString(){
        if(op.equals(Operation.ADDIZIONE)){
            return result+"+";
        }
        if(op.equals(Operation.SOTTRAZIONE)){
            return result+"-";
        }
        if(op.equals(Operation.MOLTIPLICAZIONE)){
            return result+"x";
        }
        return result+"/";
    }
}
