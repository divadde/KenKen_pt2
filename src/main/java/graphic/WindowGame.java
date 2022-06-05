package graphic;

import graphic.mediator.Mediator;

import javax.swing.*;

public class WindowGame extends JFrame {

    public WindowGame(String title){
        Mediator mediator = new Mediator();

        Menu m = new Menu(mediator);
        GamePanel gp = new GamePanel(mediator);

        add(gp);
        setJMenuBar(m);

        setTitle(title);
        setResizable(false); //todo se puoi rendila variabile
        //pack();
        setSize(800,570);
        setLocation(350,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //in realtà nel programma dovremmo dare la possibilità di salvare la partita
        setVisible(true);
    }

}
