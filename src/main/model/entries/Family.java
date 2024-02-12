package model.entries;

public class Family extends WikiEntry {
    public Family(String name) {
        super(name);
    }

    @Override
    public void printAllAttributes() {
        System.out.println(name);
        System.out.println(generalFormula.getFormulaAsString());
    }
}
