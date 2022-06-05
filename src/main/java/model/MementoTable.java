package model;


//MEMENTO
public class MementoTable {
    private CellIF[][] table;

    public MementoTable(CellIF[][] table){
        this.table=table;
    }

    public CellIF[][] getTable(){
        return table;
    }
}
