package model;

import java.io.Serializable;

public class Informations implements Serializable {
    private int dimension;
    private CellIF[][] table; //todo vedi compatibilita con Cell

    private static final long serialVersionUID = 6174631868186263965L;


    public Informations(int dimension, CellIF[][] table){
        this.dimension=dimension;
        this.table=table;
    }

    public int getDimension() {
        return dimension;
    }

    public CellIF[][] getTable() {
        return table;
    }
}
