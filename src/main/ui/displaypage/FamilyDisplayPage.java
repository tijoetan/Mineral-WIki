package ui.displaypage;

import model.entries.Family;
import model.entries.WikiEntry;

public class FamilyDisplayPage extends DisplayPage {
    private Family family;

    protected FamilyDisplayPage(Family family) {
        super(family);
        this.family = family;
    }

    @Override
    protected void setupSidePanel() {

    }
}
