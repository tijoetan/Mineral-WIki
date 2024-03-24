package ui.filebrowser;

import javax.swing.*;
import java.io.File;
import java.nio.file.FileSystems;

public class FileBroswer extends JFileChooser {
    public FileBroswer() {
        super();
        String separator = FileSystems.getDefault().getSeparator();
        setCurrentDirectory(new File("data" + separator));


    }
}
