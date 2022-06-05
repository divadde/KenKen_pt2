package graphic;

import javax.swing.*;
import java.awt.*;

public class HelpFrame extends JFrame {

    public HelpFrame() {

        setResizable(false);
        setSize(750, 350);
        setLocation(300, 300);
        setTitle("Istruzioni KenKen");

        Container c=getContentPane();
        c.setLayout(null);

        JTextArea text = new JTextArea("Il KENKEN\n" +
                "\n" +
                "Il KenKen è un gioco di logica, ispirato al Sudoku che consiste\n"+
                "in una griglia di dimensioni variabili (i più comuni vanno dalle\n"+
                "griglie 3x3 fino alle più difficili 6x6), nelle quali bisogna disporre\n"+
                "le cifre da 1 a n senza che ci siano ripetizioni né nelle righe né nelle colonne (come per il Sudoku).\n"+
                "La griglia inizialmente è totalmente vuota, e divisa in blocchi\n"+
                "di diverse forme da linee più spesse;in ogni blocco viene riportato un numero, seguito\n"+
                "da un operatore aritmetico (+, -, x o ÷), che indica l'operazione\n" +
                "da effettuare tra le varie cifre del blocco.\n" +
                "\n" +
                "La griglia va completata in modo che, effettuando l'operazione riportata\n"+
                "in ciascun blocco tra le sue cifre, si ottenga esattamente\n"+
                "il risultato richiesto (sempre un numero intero positivo). \n"+
                "Le cifre si possono ripetere all'interno dei blocchi, a condizione però\n"
                +"che non si trovino sulla stessa riga o colonna.\n");
        text.setEditable(false);
        text.setFont(new Font("Arial", Font.BOLD, 16));
        text.setLocation(0,0);
        text.setSize(750,350);

        c.add(text);
    }

}
