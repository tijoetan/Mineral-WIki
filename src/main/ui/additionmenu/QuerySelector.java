package ui.additionmenu;

import utils.fieldnames.Option;

import javax.swing.*;

public class QuerySelector extends JPanel {
    private JRadioButton mineralChoice;
    private JRadioButton familyChoice;
    private ButtonGroup buttonGroup;

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

    public Option getChosenButton() {
        return mineralChoice.isSelected() ? Option.MINERAL : Option.FAMILY;
    }


    public static Option chooseOption() {
        QuerySelector selector = new QuerySelector();
        JOptionPane.showMessageDialog(null, selector, "Choose", JOptionPane.INFORMATION_MESSAGE);
        return selector.getChosenButton();
    }
}
