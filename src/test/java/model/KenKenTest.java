package model;

import generating.Generator;
import model.constraints.Cage;
import model.rule.KenKenRules;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class KenKenTest {
    private static KenKen kenKen;

    @BeforeEach
    public void runOnceBeforeEachTests() {
        System.out.println("Inizio test");
        kenKen=KenKen.getInstance();
        kenKen.setDimension(6);
        Generator g = kenKen.getGenerator();
        KenKenRules rules = new KenKenRules(kenKen);
        kenKen.setRules(rules);
        Cage c = new Cage();
        c.setRelazPrecedenza(false);
        g.setPrototypeConstraint(c);
        g.generate();
        System.out.println("Generato il kenken da testare");
    }

    @AfterEach
    public void runOnceAfterEachTests() {
        System.out.println("Terminato test.");
    }

    @Test
    void getCell() {
        CellIF cell = kenKen.getCell(3,3);
        assertNotNull(cell,"chiedo cella nella tabella");
        cell = kenKen.getCell(5,5);
        assertNotNull(cell,"chiedo cella al contorno");
        cell = kenKen.getCell(6,5);
        assertNull(cell,"chiedo cella fuori dalla tabella");
        cell = kenKen.getCell(-1,-2);
        assertNull(cell,"chiedo cella fuori dalla tabella");
    }

    @Test
    void isLegal() {
        boolean test = kenKen.isLegal(1,2,2);
        assertTrue(test,"inserimento di un valore positivo in una cella della tabella");
        test = kenKen.isLegal(0,2,2);
        assertFalse(test,"inserimento di un valore neutro in una cella della tabella");
        test = kenKen.isLegal(-2,2,2);
        assertFalse(test,"inserimento di un valore negativo in una cella della tabella");
        test = kenKen.isLegal(8,2,2);
        assertFalse(test,"inserimento di un valore superiore alla dimensione in una cella della tabella");
        test = kenKen.isLegal(2,7,7);
        assertFalse(test,"inserimento di un valore in una cella fuori dalla tabella");
    }

    @Test
    void addValue(){
        kenKen.addValue(1,0,0);
        assertSame(1,kenKen.getValue(0,0),"Il valore inserito è 1");
    }

    @Test
    void addNegativeValue(){
        kenKen.addValue(-2,0,0);
        assertNotSame(-2,kenKen.getValue(0,0),"Non è inserito un numero negativo");
    }

    @Test
    void addTwoValuesNotInContrast() {
        kenKen.addValue(1,0,0);
        kenKen.addValue(2,4,0);
        assertTrue(kenKen.getState(0,0),"lo stato della cella 0,0 è true");
        assertTrue(kenKen.getState(4,0),"lo stato della cella 4,0 è true");
    }

    @Test
    void addTwoValuesInContrastInSameRow() {
        kenKen.addValue(1,0,0);
        kenKen.addValue(1,4,0);
        assertFalse(kenKen.getState(0,0),"lo stato della cella 0,0 è false");
        assertFalse(kenKen.getState(4,0),"lo stato della cella 4,0 è false");
    }

    @Test
    void addTwoValuesInContrastInSameColumn() {
        kenKen.addValue(1,0,0);
        kenKen.addValue(1,0,4);
        assertFalse(kenKen.getState(0,0),"lo stato della cella 0,0 è false");
        assertFalse(kenKen.getState(0,4),"lo stato della cella 0,4 è false");
    }

    @Test
    void afterRemoveInContrast() {
        kenKen.addValue(1,0,0);
        kenKen.addValue(1,0,4);
        kenKen.removeValue(0,4);
        assertTrue(kenKen.getState(0,0),"lo stato della cella 0,0 è true");
    }


    @Test
    void isCompleted() {
    }

    @Test
    void clean() {
    }

    @Test
    void setConstraint() {
    }

    @Test
    void getConstraint() {
    }

    @Test
    void switchRow() {
    }

    @Test
    void switchColumn() {
    }
}