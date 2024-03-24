package ui.filebrowser;

import utils.fieldnames.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class LoadSavePopupMenu extends JButton implements ActionListener {

    private final JPopupMenu buttonMenu;
    private final JMenuItem load;
    private final JMenuItem save;

    Random numSeed = new Random(10);

    private int savePath;

    public int getSavePath() {
        return savePath;
    }

    public int getLoadPath() {
        return loadPath;
    }

    private int loadPath;

    public LoadSavePopupMenu(String name) {
        super(name);
        buttonMenu = new JPopupMenu();
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        load.addActionListener(this);
        save.addActionListener(this);
        buttonMenu.add(load);
        buttonMenu.add(save);

        addActionListener(this);
    }



    private void saveFile() {
        FileBroswer broswer = new FileBroswer();
        broswer.showOpenDialog(this);
    }

    private void loadFile() {
        loadPath = numSeed.nextInt();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(load)) {
            loadFile();
            firePropertyChange(Constants.LOAD_BUTTON_CLICKED, true, false);
        } else if (e.getSource().equals(save)) {
            saveFile();
            firePropertyChange(Constants.SAVE_BUTTON_CLICKED, true, false);
        } else {
            buttonMenu.show(this, this.getX(), this.getY() + this.getHeight());
        }
    }
}
