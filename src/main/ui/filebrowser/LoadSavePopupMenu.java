package ui.filebrowser;

import utils.fieldnames.PropertyNames;

import javax.swing.*;

// Button that allows loading and saving to file

public class LoadSavePopupMenu extends JButton {

    private final JPopupMenu buttonMenu;
    private final JMenuItem load;
    private final JMenuItem save;
    private final JMenuItem saveAs;


    private String savePath;
    private String loadFile;

    // EFFECTS: creates and configures menu button For loading and saving
    public LoadSavePopupMenu(String name) {
        super(name);
        buttonMenu = new JPopupMenu();
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        saveAs = new JMenuItem("Save As");

        load.addActionListener(e -> loadFile());
        save.addActionListener(e -> saveFile());
        saveAs.addActionListener(e -> saveFileAs());

        buttonMenu.add(load);
        buttonMenu.add(saveAs);
        buttonMenu.add(save);

        addActionListener(e -> showMenu());
    }

    // getters
    public String getSavePath() {
        return savePath;
    }

    public String getLoadPath() {
        return loadFile;
    }

    // MODIFIES: this
    // EFFECTS: allows user to set savePath and notifies superior to save
    private void saveFileAs() {
        FileBrowser browser = new FileBrowser();
        int result = browser.showSaveDialog(this);
        if (result == FileBrowser.APPROVE_OPTION) {
            savePath = browser.getSelectedFile().getPath();
            firePropertyChange(PropertyNames.SAVE_AS_BUTTON_CLICKED, true, false);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to specify loading path and notifies superior to load this file
    public void loadFile() {
        FileBrowser browser = new FileBrowser();
        int result = browser.showOpenDialog(this);
        if (result == FileBrowser.APPROVE_OPTION) {
            loadFile = browser.getSelectedFile().getPath();
            firePropertyChange(PropertyNames.LOAD_BUTTON_CLICKED, true, false);
        }
    }

    // EFFECTS: notifies superior to save file
    public void saveFile() {
        firePropertyChange(PropertyNames.SAVE_BUTTON_CLICKED, true, false);
    }

    // EFFECTS: displays popupmenu
    public void showMenu() {
        buttonMenu.show(this, this.getX(), this.getY() + this.getHeight());
    }


}
