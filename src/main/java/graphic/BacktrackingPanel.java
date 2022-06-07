package graphic;

import graphic.command.*;
import graphic.mediator.Mediator;
import graphic.mediator.Request;
import graphic.mediator.Subject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BacktrackingPanel extends JPanel implements ActionListener, Subject {
    private JButton solverButton;
    private JButton next, previous;
    private Mediator mediator;

    private JButton riprendi;


    public BacktrackingPanel(Mediator mediator){
        setMediator(mediator);

        setLayout(null);
        riprendi=new JButton("Riprendi");
        riprendi.setSize(140,30);
        riprendi.setLocation(0,0);
        solverButton=new JButton("Mostra soluzioni");
        solverButton.setSize(140,30);
        solverButton.setLocation(0,50);
        next=new JButton("->");
        next.setSize(60,30);
        next.setLocation(80,90);
        previous=new JButton("<-");
        previous.setSize(60,30);
        previous.setLocation(0,90);
        add(solverButton); add(next); add(previous); add(riprendi);
        riprendi.addActionListener(this);
        solverButton.addActionListener(this);
        next.addActionListener(this);
        previous.addActionListener(this);
        setSize(200,300);
        setLocation(600,350);
        setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==solverButton){
            mediator.notify(new Request(Request.Tipo.SHOWSOLUTION,new SolveCommand()));
            mediator.notify(new Request(Request.Tipo.NEXTSOLUTION,new NextSolutionCommand(NextSolutionCommand.Direction.NEXT)));
        }
        else if(e.getSource()==next){
            mediator.notify(new Request(Request.Tipo.NEXTSOLUTION,new NextSolutionCommand(NextSolutionCommand.Direction.NEXT)));
        }
        else if(e.getSource()==previous){
            mediator.notify(new Request(Request.Tipo.PREVIOUSSOLUTION,new NextSolutionCommand(NextSolutionCommand.Direction.PREVIOUS)));
        }
        else if(e.getSource()==riprendi){
            mediator.notify(new Request(Request.Tipo.PREVGAME,new NullCommand()));
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.READY){
            setVisible(true);
            next.setEnabled(false);
            solverButton.setEnabled(true);
            previous.setEnabled(false);
            riprendi.setEnabled(false);
        }
        else if(request.getTipo()==Request.Tipo.SHOWSOLUTION){
            next.setEnabled(true);
            previous.setEnabled(true);
            solverButton.setEnabled(false);
            riprendi.setEnabled(true);
        }
        else if(request.getTipo()==Request.Tipo.PREVGAME){
            riprendi.setEnabled(false);
            next.setEnabled(false);
            previous.setEnabled(false);
            solverButton.setEnabled(true);
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }

}
