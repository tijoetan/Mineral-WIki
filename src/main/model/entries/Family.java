package model.entries;

import java.util.ArrayList;
import java.util.List;

public class Family extends WikiEntry {
    private final List<WikiEntry> mineralsWithFamily;

    public Family(String name) {
        super(name);
        this.mineralsWithFamily = new ArrayList<>();
    }

    public void addMineralsWithFamily(List<WikiEntry> minerals) {
        for (WikiEntry mineral : minerals) {
            if (!mineralsWithFamily.contains(mineral)) {
                mineralsWithFamily.add(mineral);
            }

        }
    }

    @Override
    public void printAllAttributes() {
        System.out.println(name);
        System.out.println(generalFormula.getFormulaAsString());
    }
}
