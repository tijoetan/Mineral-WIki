package ui.additionmenu;

import utils.fieldnames.Option;

import javax.swing.*;

// Popup that lets user choose what to add

public class QuerySelector extends JPanel {
    private JRadioButton mineralChoice;
    private JRadioButton familyChoice;
    private ButtonGroup buttonGroup;

    // EFFECTS: Makes new QuerySelector
    public QuerySelector() {
        mineralChoice = new JRadioButton("Mineral");
        mineralChoice.setSelected(true);
        familyChoice = new JRadioButton("Family");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(mineralChoice);
        buttonGroup.add(familyChoice);
        add(mineralChoice);
        add(familyChoice);
    }

    // getters
    public Option getChosenButton() {
        return mineralChoice.isSelected() ? Option.MINERAL : Option.FAMILY;
    }


    // EFFECTS: creates popup that allows user to choose an option and returns the selected one
    public static Option chooseOption() {
        QuerySelector selector = new QuerySelector();
        JOptionPane.showMessageDialog(null, selector, "Choose", JOptionPane.INFORMATION_MESSAGE);
        return selector.getChosenButton();
    }
}
