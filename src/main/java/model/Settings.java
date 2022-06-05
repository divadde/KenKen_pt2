package model;

//possibilità di aggiunta di un Builder?
public class Settings {
    private int dimension;
    private int maxSol;
    private boolean precedenza;

    public Settings(int dimension, int maxSol, boolean precedenza){
        this.dimension=dimension;
        this.maxSol=maxSol;
        this.precedenza=precedenza;
    }

    public void setDimension(int dimension){
        this.dimension=dimension;
    }

    public void setMaxSol(int maxSol){
        this.maxSol=maxSol;
    }

    public void setPrecedenza(boolean precedenza) {
        this.precedenza = precedenza;
    }

    public int getDimension() {
        return dimension;
    }

    public int getMaxSol() {
        return maxSol;
    }

    public boolean isPrecedenza() {
        return precedenza;
    }
}
