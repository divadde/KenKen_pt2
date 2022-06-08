package model;

import backtracking.*;
import generating.*;
import model.constraints.Constraint;
import model.rule.KenKenRules;
import model.rule.Rules;
import model.util.MementoTable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public final class KenKen implements GridGame, Serializable {
    private static KenKen INSTANCE = null;
    private static int dimension;
    private static Cell[][] table;
    private static Rules rules;

    private static final long serialVersionUID = 9177631848186263965L;

    private KenKen() {
    }

    public static synchronized KenKen getInstance(){
        if(INSTANCE==null)
            INSTANCE=new KenKen();
        return INSTANCE;
    }

    //OK
    @Override
    public MementoTable createMemento(){
        return new MementoTable(getTable());
    }
    @Override
    public void setMemento(MementoTable memento) {
        setTable(memento.getTable());
    }

    //copia profonda, usato dal memento per memorizzare le soluzioni.
    private CellIF[][] getTable(){
        Cell[][] ret = new Cell[dimension][dimension];
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++)
                ret[i][j] = new Cell(table[i][j]);
        }
        return ret;
    }
    //usato per impostare lo stato dal memento
    private void setTable(CellIF[][] table){
        clean();
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                addValue(table[i][j].getValue(),table[i][j].getX(),table[i][j].getY());
            }
        }
    }


    //usato per memorizzare l'informazione (Serializzazione)
    @Override
    public CellIF[][] getReferenceTable(){
        return table;
    }
    //usato per cambiare riferimento alla tabella (Serializzazione)
    @Override
    public void changeReferenceTable(CellIF[][] table){
        this.table=(Cell[][]) table;
    }


    @Override
    public void addValue(int val, int x, int y) {
        if(x>=0 && x<dimension && y>=0 && y<dimension)
            table[x][y].setValue(val);
        //System.out.println(this);
    }
    @Override
    public int getValue(int x, int y){
        if(x>=0 && x<dimension && y>=0 && y<dimension)
            return table[x][y].getValue();
        return -1;
    }

    @Override
    public void removeValue(int x, int y) {
        if(x>=0 && x<dimension && y>=0 && y<dimension)
            table[x][y].clean();
    }
    @Override
    public boolean isLegal(int val, int x, int y){
        if(x>=0 && x<dimension && y>=0 && y<dimension) {
            addValue(val, x, y);
            boolean ret = table[x][y].getState();
            removeValue(x, y);
            return ret;
        }
        return false;
    }


    @Override
    public boolean isCompleted(){
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++) {
                if (!table[i][j].getState())
                    return false;
            }
        }
        return true;
    }

    @Override
    public void clean() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                table[i][j].clean();
            }
        }
    }

    @Override
    public void setConstraint(Constraint c, int x, int y) {
        table[x][y].setConstraint(c);
    }
    @Override
    public Constraint getConstraint(int x, int y) {
        return table[x][y].getConstraint();
    }


    @Override
    public CellIF getCell(int x, int y) {
        if(x>=0 && x<dimension && y>=0 && y<dimension)
            return table[x][y];
        return null;
    }

    @Override
    public boolean getState(int x, int y) {
        if(x>=0 && x<dimension && y>=0 && y<dimension)
            return table[x][y].getState();
        return false;
    }

    @Override
    public void setRules(Rules rules){
        this.rules=rules;
    }

    @Override
    public void setDimension(int n) {
        this.dimension = n;
        table = new Cell[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                table[i][j] = new Cell(i, j);
            }
        }
    }
    @Override
    public int getDimension() {
        return dimension;
    }

    //scambio solo i valori!
    @Override
    public void switchRow(int i, int j) {
        for (int n = 0; n < dimension; n++) {
            table[i][n].switchValue(table[j][n]);
        }
    }
    //scambio solo i valori!
    @Override
    public void switchColumn(int i, int j) {
        for (int n = 0; n < dimension; n++) {
            table[n][i].switchValue(table[n][j]);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            sb.append("\n");
            for (int j = 0; j < dimension; j++) {
                sb.append(table[i][j].toString() + "\s");
            }
        }
        return sb.toString();
    }

    @Override
    public String constrString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            sb.append("\n");
            for (int j = 0; j < dimension; j++) {
                sb.append(table[i][j].getConstraint().toString() + "\s");
            }
        }
        return sb.toString();
    }

    @Override
    public List<Constraint> listOfConstraint(){
        List<Constraint> list = new LinkedList<>();
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if(!(list.contains(getConstraint(i,j))))
                    list.add(getConstraint(i,j));
            }
        }
        return list;
    }

    @Override
    public KenKenGenerator getGenerator() {
        return KenKenGenerator.getInstance(this);
    }

    @Override
    public KenKenSolver getBacktracking() {
        return KenKenSolver.getInstance(this);
    }


    private class Cell implements CellIF, Serializable {
        private int value;
        private int x;
        private int y;
        private Constraint cage;
        private boolean stateRules;
        private boolean stateCage;
        private List<CellIF> inContrast;

        private static final long serialVersionUID = 4177631348182261145L;

        public Cell(int x, int y) {
            inContrast=new LinkedList<>();
            this.x = x;
            this.y = y;
            value = 0;
            stateRules=false;
            stateCage=false;
        }

        public Cell (Cell c){
            this.x = c.getX();
            this.y = c.getY();
            this.value = c.getValue();
        }

        public void switchValue(Cell c){
            int tmp=this.value;
            this.value=c.getValue();
            c.setValueNoControl(tmp);
        }
        //per distinguersi dall'aggiunta dei valori con controllo, metodo usato in fase di generazione
        private void setValueNoControl(int x){
            value=x;
        }

        @Override
        public void addInContrast(CellIF c){
            inContrast.add(c);
        }
        @Override
        public void removeInContrast(CellIF c){inContrast.remove(c); }

        @Override
        public boolean getState(){
            return stateRules && stateCage;
        }

        @Override
        public void setRulesState(boolean state){
            stateRules=state;
        }
        @Override
        public void setCageState(boolean state){
            stateCage=state;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void clean() {
            List<CellIF> daEliminare = new LinkedList<CellIF>();
            for(CellIF c: inContrast){
                c.removeInContrast(this);
                daEliminare.add(c);
            }
            for(CellIF c: daEliminare) {
                c.setRulesState(c.getInContrast().isEmpty());
            }
            inContrast.removeAll(daEliminare);
            value = 0;
            stateRules=false;
            stateCage=false;
        }

        @Override
        public List<CellIF> getInContrast(){
            return inContrast;
        }

        @Override
        public void setValue(int value) {
            this.value = value;
            List<CellIF> ver = rules.verifyOnGrid(value,x,y);
            for(CellIF c : ver){
                if(!(inContrast.contains(c))){
                    c.setRulesState(false);
                    c.addInContrast(this);
                    addInContrast(c);
                }
            }
            List<CellIF> daEliminare = new LinkedList<CellIF>();
            for(CellIF c: inContrast){
                if(!(ver.contains(c))){
                    c.removeInContrast(this);
                    c.setRulesState(c.getInContrast().isEmpty());
                    daEliminare.add(c);
                }
            }
            inContrast.removeAll(daEliminare);
            if(!rules.verifyOnNumber(value))
                this.value=0;
            stateRules=rules.verifyOnNumber(value) && inContrast.isEmpty();
            stateCage=true;
            if (cage != null)
                stateCage=cage.verify();
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public boolean hasConstraint() {
            return cage != null;
        }

        @Override
        public void setConstraint(Constraint cage) {
            this.cage = cage;
            this.cage.addCell(this);
        }

        @Override
        public Constraint getConstraint() {
            return cage;
        }

        @Override
        public String toString() {
            return x+","+y;
        }

        /*
        @Override
        public boolean equals(Object o){
            if(!(o instanceof Cell))
                return false;
            Cell c = (Cell) o;
            return c.getX()==this.x && c.getY()==this.y;
        }
         */

    }
}