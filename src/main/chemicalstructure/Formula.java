package chemicalstructure;

import java.util.ArrayList;
import java.util.List;

// Data representation for chemical formulas
public class Formula {
    private final List<String> moleculeList;

    public Formula() {
        this.moleculeList = new ArrayList<>();
    }

    public void addConstituent(String constituent) {
        this.moleculeList.add(constituent);
    }

    public java.lang.String getFormulaAsString() {
        StringBuilder toReturn = new StringBuilder();
        for (String constituent : moleculeList) {
            toReturn.append(constituent);
        }
        return toReturn.toString();
    }

}
