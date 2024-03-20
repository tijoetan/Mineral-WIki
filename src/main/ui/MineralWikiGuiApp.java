package ui;

import model.tableentry.WikiEntryTable;
import ui.additionmenu.MineralQueryHandler;
import ui.table.TableDataHandler;

import javax.swing.*;

public class MineralWikiGuiApp {
    public MineralWikiGuiApp(WikiEntryTable table) {
        JFrame frame = new JFrame();
        JTable viewTable = new JTable(new TableDataHandler(table));
        viewTable.setBounds(0, 0, 1000, 1000);
        JScrollPane pane = new JScrollPane(viewTable);
        viewTable.setFillsViewportHeight(true);
        frame.add(pane);


        frame.setSize(800, 600);

        frame.setVisible(true);
        MineralQueryHandler.queryMineral();
    }

}
