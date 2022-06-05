package graphic;

import graphic.command.NewGameCommand;
import graphic.mediator.Mediator;
import graphic.mediator.Request;
import graphic.mediator.Subject;
import model.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameSettings extends JFrame implements ActionListener, Subject {
    private Mediator mediator;
    private JTextField dimTextField, maxSolTextField;
    private JButton ok;
    private JRadioButton b1,b2;
    private Settings settings;

    public GameSettings(Mediator mediator) {
        setMediator(mediator);

        //impostazioni di default
        settings = new Settings(6,10,false);

        setTitle("Impostazioni di gioco");
        setResizable(false);
        setSize(300, 350);
        setLocation(500, 300);

        Container c = getContentPane();
        c.setLayout(null);

        JLabel l1 = new JLabel("Inserisci dimensione del KenKen: ");
        l1.setSize(300, 30);
        l1.setLocation(30, 30);
        JLabel l2 = new JLabel("Numero massimo di soluzioni: ");
        l2.setSize(300, 30);
        l2.setLocation(30, 100);

        c.add(l1);
        c.add(l2);

        dimTextField = new JTextField();
        dimTextField.setText(Integer.toString(settings.getDimension()));
        dimTextField.setSize(40, 30);
        dimTextField.setLocation(30, 60);
        dimTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    settings.setDimension(Integer.parseInt(dimTextField.getText()));
                } catch(NumberFormatException exc){
                    settings.setDimension(6);
                }
            }
        });
        maxSolTextField = new JTextField();
        maxSolTextField.setText(Integer.toString(settings.getMaxSol()));
        maxSolTextField.setSize(40, 30);
        maxSolTextField.setLocation(30, 130);
        maxSolTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    settings.setMaxSol(Integer.parseInt(maxSolTextField.getText()));
                } catch(NumberFormatException exc){
                    settings.setMaxSol(10);
                }
            }
        });

        c.add(dimTextField);
        c.add(maxSolTextField);

        JLabel l3 = new JLabel("Relazione di precedenza: ");
        l3.setSize(300, 30);
        l3.setLocation(30,180);
        b1 = new JRadioButton("Sì");
        b1.setSize(60,30);
        b1.setLocation(30,200);
        b2 = new JRadioButton("No");
        b2.setSize(60,30);
        b2.setLocation(90,200);
        b2.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(b1);
        group.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        c.add(l3);
        c.add(b1);
        c.add(b2);

        ok = new JButton("Ok");
        ok.setSize(50, 30);
        ok.setLocation(120, 270);
        ok.addActionListener(this);

        c.add(ok);


        addWindowListener( new WindowAdapter() {
                            public void windowClosing(WindowEvent e){
                                mediator.notify(new Request(Request.Tipo.READY,new NewGameCommand(settings)));
                                } } );

    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator=mediator;
        mediator.addSubject(this);
    }

    //todo aggiungi controlli
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok) {
            mediator.notify(new Request(Request.Tipo.READY,new NewGameCommand(settings)));
        }
        else if(e.getSource()==b1){
            settings.setPrecedenza(true);
        }
        else if(e.getSource()==b2){
            settings.setPrecedenza(false);
        }
    }

    @Override
    public void handleRequest(Request request) {
        if(request.getTipo()==Request.Tipo.NEWGAME){
            this.setVisible(true);
        }
    }

}
