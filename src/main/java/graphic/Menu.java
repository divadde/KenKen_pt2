package graphic;

import graphic.command.LoadGameCommand;
import graphic.command.NullCommand;
import graphic.command.SaveGameCommand;
import graphic.mediator.Mediator;
import graphic.mediator.Request;
import graphic.mediator.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JMenuBar implements ActionListener, Subject {
    private JMenu gioca, help;
    private JMenuItem nuovaPartita, salvaPartita, caricaPartita, istruzioni;
    private GameSettings menuSett;
    private HelpFrame helpFrame;
    private Mediator mediator;

    public Menu(Mediator mediator){
        setMediator(mediator);

        menuSett=new GameSettings(mediator);
        gioca = new JMenu("Gioca");
        help = new JMenu("Help");
        helpFrame = new HelpFrame();

        add(gioca);
        add(help);

        nuovaPartita=new JMenuItem("Nuova partita");
        salvaPartita=new JMenuItem("Salva partita");
        caricaPartita=new JMenuItem("Carica partita");
        istruzioni=new JMenuItem("Istruzioni");

        nuovaPartita.addActionListener(this);
        salvaPartita.addActionListener(this);
        caricaPartita.addActionListener(this);
        istruzioni.addActionListener(this);

        gioca.add(nuovaPartita);
        gioca.add(salvaPartita);
        gioca.add(caricaPartita);
        help.add(istruzioni);

        salvaPartita.setEnabled(false);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nuovaPartita){
            mediator.notify(new Request(Request.Tipo.NEWGAME,new NullCommand()));
        }
        else if(e.getSource()==salvaPartita){
            mediator.notify(new Request(Request.Tipo.SAVEGAME,new SaveGameCommand()));
        }
        else if(e.getSource()==caricaPartita){
            mediator.notify(new Request(Request.Tipo.LOADGAME,new LoadGameCommand()));
        }
        else if(e.getSource()==istruzioni){
            mediator.notify(new Request(Request.Tipo.HELP,new NullCommand()));
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.NEWGAME){
            menuSett.setVisible(true);
        }
        else if(request.getTipo()==Request.Tipo.READY){
            menuSett.setVisible(false);
            salvaPartita.setEnabled(true);
        }
        else if(request.getTipo()==Request.Tipo.HELP){
            helpFrame.setVisible(true);
        }
    }

}
