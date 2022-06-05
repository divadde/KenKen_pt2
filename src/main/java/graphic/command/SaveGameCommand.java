package graphic.command;

import model.GridGame;
import model.Informations;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGameCommand implements Command {

    public SaveGameCommand(){}

    @Override
    public void execute(GridGame gg) {
        String nomeFile = null;
        String absolutePath = null;
        JFileChooser jfc = new JFileChooser();
        int val = jfc.showOpenDialog(null);
        if(val==JFileChooser.APPROVE_OPTION) {
            absolutePath = jfc.getSelectedFile().getAbsolutePath();
            nomeFile = jfc.getSelectedFile().getName();
            JOptionPane.showMessageDialog(null,"File salvato: "+nomeFile);
        }
        else if(val==JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null,"Annullata scelta del file");
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(absolutePath));
            Informations info = new Informations(gg.getDimension(), gg.getReferenceTable());
            oos.writeObject(info);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
