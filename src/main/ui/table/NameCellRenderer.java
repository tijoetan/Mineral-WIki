package ui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// Name Cell renderer for name column in TableView

public class NameCellRenderer extends DefaultTableCellRenderer {

    // EFFECTS: produces rendered component
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        String text = (String) table.getModel().getValueAt(row, column);
        JLabel label = new JLabel("<html><font color='blue'><u>"
                + text
                + "</u></font></html>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }
}
