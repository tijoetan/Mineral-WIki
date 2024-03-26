package ui.filebrowser;

import utils.fieldnames.PropertyNames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadSavePopupMenu extends JButton implements ActionListener {

    private final JPopupMenu buttonMenu;
    private final JMenuItem load;
    private final JMenuItem save;


    private int savePath;
    private String loadFile;


    public int getSavePath() {
        return savePath;
    }

    public String getLoadPath() {
        return loadFile;
    }

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



    private boolean loadFile() {
        FileBroswer browser = new FileBroswer();
        int result = browser.showOpenDialog(this);
        if (result == FileBroswer.APPROVE_OPTION) {
            loadFile = browser.getSelectedFile().getPath();
            return true;
        }
        return false;
    }

    private void saveFile() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(load)) {
            boolean didLoadFile = loadFile();
            if (didLoadFile) {
                firePropertyChange(PropertyNames.LOAD_BUTTON_CLICKED, true, false);
            }
        } else if (e.getSource().equals(save)) {
            saveFile();
            firePropertyChange(PropertyNames.SAVE_BUTTON_CLICKED, true, false);
        } else {
            buttonMenu.show(this, this.getX(), this.getY() + this.getHeight());
        }
    }
}
