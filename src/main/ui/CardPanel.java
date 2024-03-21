package ui;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {
    private CardLayout layout;

    public CardPanel() {
        layout = new CardLayout();
        setLayout(layout);
    }

    public CardLayout getCardLayout() {
        return layout;
    }

    public void showPanel(String pageName) {
        layout.show(this, pageName);
    }
}
