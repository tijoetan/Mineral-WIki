package ui.filebrowser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.FileSystems;

// Slightly customized JFileChooser

public class FileBrowser extends JFileChooser {
    // EFFECTS: creates and configures FileBrowser

    public FileBrowser() {
        super();
        String separator = FileSystems.getDefault().getSeparator();
        setCurrentDirectory(new File("data" + separator));
        setFileFilter(new FileNameExtensionFilter("JSON files", "json"));

    }
}
