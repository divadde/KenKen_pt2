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


    public BacktrackingPanel(Mediator mediator){
        setMediator(mediator);

        setLayout(null);
        solverButton=new JButton("Mostra soluzioni");
        solverButton.setSize(140,30);
        solverButton.setLocation(0,0);
        next=new JButton("->");
        next.setSize(60,30);
        next.setLocation(80,40);
        previous=new JButton("<-");
        previous.setSize(60,30);
        previous.setLocation(0,40);
        add(solverButton); add(next); add(previous);
        solverButton.addActionListener(this);
        next.addActionListener(this);
        previous.addActionListener(this);
        setSize(200,200);
        setLocation(600,350);
        setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==solverButton){
            next.setEnabled(true);
            previous.setEnabled(true);
            mediator.notify(new Request(Request.Tipo.SHOWSOLUTION,new SolveCommand()));
        }
        if(e.getSource()==next){
            mediator.notify(new Request(Request.Tipo.NEXTSOLUTION,new NextSolutionCommand(NextSolutionCommand.Direction.NEXT)));
        }
        if(e.getSource()==previous){
            mediator.notify(new Request(Request.Tipo.PREVIOUSSOLUTION,new NextSolutionCommand(NextSolutionCommand.Direction.PREVIOUS)));
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.READY){
            setVisible(true);
            next.setEnabled(false);
            previous.setEnabled(false);
        }
        else if(request.getTipo()==Request.Tipo.SHOWSOLUTION){
            next.setEnabled(true);
            previous.setEnabled(true);
        }
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }

}
