package model;

import generating.Generator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KenKenTest {
    private static KenKen kenKen;

    @BeforeAll
    public static void runOnceBeforeAllTests() {
        System.out.println("Inizio fase di testing");
        kenKen=KenKen.getInstance();
        kenKen.setDimension(6);
        Generator g = kenKen.getGenerator();
        Cage c = new Cage();
        c.setRelazPrecedenza(false);
        g.setPrototypeConstraint(c);
        g.generate();
        System.out.println("Generato il kenken da testare");
    }

    @AfterAll
    public static void runOnceAfterAllTests() {
        System.out.println("Terminata fase di testing.");
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
    void addValue() {
    }

    @Test
    void removeValue() {
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