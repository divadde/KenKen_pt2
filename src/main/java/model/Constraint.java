package model;

import java.io.Serializable;

public abstract class Constraint implements Cloneable, Serializable {
    protected int id;
    protected static int nextId=0;

    //modifica
    public abstract void addCell(CellIF ec);
    public abstract void setValues();

    //controllo
    public abstract boolean verify();

    public int getId(){
        return id;
    }


    @Override
    public Constraint clone() {
        try {
            id=nextId++;
            return (Constraint) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constraint that = (Constraint) o;
        return id == that.id;
    }


}
