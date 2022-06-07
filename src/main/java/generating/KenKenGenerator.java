package generating;

import model.*;
import model.constraints.Constraint;

import java.util.LinkedList;
import java.util.List;

public final class KenKenGenerator extends Generator {
    private static KenKenGenerator INSTANCE = null;


    private KenKenGenerator(GridGame gg){
        this.gg=gg;
    }

    public static synchronized KenKenGenerator getInstance(GridGame gg){
        if(INSTANCE==null)
            INSTANCE=new KenKenGenerator(gg);
        return INSTANCE;
    }


    @Override //OK
    protected void insertNumbers() {
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                gg.addValue(((i+j)%gg.getDimension())+1,i,j);
            }
        }
    }

    @Override //OK
    protected void shuffleNumbers() { //shuffle pari alla dimensione
        for(int i=0; i<gg.getDimension(); i++) {
            int x = (int) (Math.random() * gg.getDimension());
            int y = (int) (Math.random() * gg.getDimension());
            if (x != y) gg.switchRow(x, y);
            int z = (int) (Math.random() * gg.getDimension());
            int k = (int) (Math.random() * gg.getDimension());
            if (z != k) gg.switchColumn(z, k);
        }
    }

    @Override //OK
    protected void addConstraints() {
        if(constraint!=null) {
            while (true) {
                CellIF nextCell = nextPoint();
                if (nextCell == null) break; //se non c'è altra cella, abbiamo finito
                else {
                    Constraint c = constraint.clone();
                    gg.setConstraint(c, nextCell.getX(), nextCell.getY());
                    int n = chooseCageDimension(); //scegli numero randomico di passi
                    for (int i = 0; i < n; i++) {
                        CellIF lastCell = null;
                        if (nextCell != null) lastCell = nextCell;
                        else break;
                        Direction d = Direction.chooseRandomly(); //scegli direzioni randomiche
                        nextCell = d.doOp(lastCell);
                        if (nextCell != null) gg.setConstraint(c, nextCell.getX(), nextCell.getY());
                        else if (i == 0) { //in caso di cella singola
                            List<Direction> l = addDirections();
                            while (nextCell == null) {
                                l.remove(d);
                                if (l.isEmpty()) { //cella singola ingabbiata, mi faccio inglobare da altri constraint
                                    Constraint vicino = searchNear(lastCell).getConstraint();
                                    gg.setConstraint(vicino, lastCell.getX(), lastCell.getY());
                                    vicino.setValues();
                                    break;
                                }
                                d = l.get(chooseRandomic(l.size()));
                                nextCell = d.doOp(lastCell);
                                if (nextCell != null) {
                                    gg.setConstraint(c, nextCell.getX(), nextCell.getY());
                                }
                            }
                        }
                    }
                    c.setValues(); //setta i valori del constraint
                }
            }
            //System.out.println(gg.constrString());
        }
    }

    //OK: aggiunta selezione sulle dimensioni?
    private int chooseCageDimension(){
        if (gg.getDimension()<=4) {
            return (int) (Math.random() * gg.getDimension());
        }
        return 2+(int) (Math.random() * 1);
    }

    //OK
    private CellIF searchNear(CellIF c){
        CellIF ret=null;
        if(c.getY()>0) {//CONTROLLO NORD
            ret = gg.getCell(c.getX(), c.getY() - 1);
        }
        else if(c.getY()<gg.getDimension()-1) {//CONTROLLO SUD
            ret = gg.getCell(c.getX(), c.getY() + 1);
        }
        else if(c.getX()<gg.getDimension()-1) {//CONTROLLO EST
            ret = gg.getCell(c.getX() + 1, c.getY());
        }
        else if(c.getX()>0) {//CONTROLLO OVEST
            ret=gg.getCell(c.getX()-1,c.getY());
        }
        return ret;
    }

    //OK
    private int chooseRandomic(int n){
        return (int) Math.random()*n;
    }
    private List<Direction> addDirections() {
        List<Direction> l = new LinkedList<>();
        l.add(Direction.DESTRA); l.add(Direction.GIU); l.add(Direction.SINISTRA);
        return l;
    }

    //OK
    private interface Directionable {
        CellIF doOp(CellIF ec);
    }
    private enum Direction implements Directionable{
        DESTRA {
            public CellIF doOp(CellIF currPoint) {
                int nextY = currPoint.getY()+1;
                if(nextY<gg.getDimension() && !(gg.getCell(currPoint.getX(),nextY).hasConstraint()))
                    return gg.getCell(currPoint.getX(),nextY);
                return null;
            }
        }, SINISTRA {
            public CellIF doOp(CellIF currPoint) {
                int nextY = currPoint.getY()-1;
                if(nextY>=0 && !(gg.getCell(currPoint.getX(),nextY).hasConstraint()))
                    return gg.getCell(currPoint.getX(),nextY);
                return null;
            }
        }, GIU {
            public CellIF doOp(CellIF currPoint) {
                int nextX = currPoint.getX()+1;
                if(nextX<gg.getDimension() && !(gg.getCell(nextX,currPoint.getY()).hasConstraint()))
                    return gg.getCell(nextX,currPoint.getY());
                return null;
            }
        };

        public static Direction chooseRandomly() {
            int choose = (int) (Math.random() * 3);
            switch (choose) {
                case 0: return Direction.DESTRA;
                case 1: return Direction.SINISTRA;
                default: return Direction.GIU;
            }
        }
    }

    //OK
    private CellIF nextPoint(){
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++){
                if (!(gg.getCell(i,j).hasConstraint()))
                    return gg.getCell(i,j);
            }
        }
        return null;
    }


    @Override //OK
    protected void eraseNumbers() {
        gg.clean();
    }
}
