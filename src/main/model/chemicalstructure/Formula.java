package model.chemicalstructure;

import model.enums.AtomicSymbols;
import model.exceptions.UnknownElementException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Data representation for chemical formulas
public class Formula {
    private final List<FormulaElement> moleculeList;
    private final List<MoleculeGroup> substitutableGroups;
    private final List<MoleculeGroup> covalentGroups;
    private final String unparsedFormula;

    private boolean isValidFormula = true;

    private static final Pattern substitutableGroup = Pattern.compile("\\([^\\)]+,[^\\)]+\\)(\\d)?");
    private static final Pattern covalentIon = Pattern.compile("\\([^\\)]+\\)\\d?");
    private static final Pattern elementMatch = Pattern.compile("[A-Z][a-z]?\\d*");
    private static final Pattern generalDigit = Pattern.compile("(\\d)+");
    private static final Pattern bracketDigit = Pattern.compile("\\)(\\d)+");

    // EFFECTS: Constructs new formula object and populates the lists with interpreted elements from unparsed formula
    //          throws UnknownElementException if unparsedFormula is not a valid formula. If unparsedFormula is "NA"
    //          serves as blank formula
    public Formula(String unparsedFormula) throws UnknownElementException {
        this.moleculeList = new ArrayList<>();
        this.substitutableGroups = new ArrayList<>();
        this.covalentGroups = new ArrayList<>();
        this.unparsedFormula = unparsedFormula;
        if (unparsedFormula.equals("NA")) {
            isValidFormula = false;
        } else {
            parseFormula(unparsedFormula);
        }
    }

    // getters
    public String getFormulaAsString() {
        return unparsedFormula;
    }

    public boolean isValidFormula() {
        return isValidFormula;
    }

    // MODIFIES: this
    // EFFECTS: populates substitutableGroups, covalentGroups and moleculeList with the correct elements and quantities
    //          if unparsedFormula contains unrecognizable terms, throws UnknownElementException
    public void parseFormula(String unparsedFormula) throws UnknownElementException {

        Matcher substitutableGroupMatcher = substitutableGroup.matcher(unparsedFormula);
        while (substitutableGroupMatcher.find()) {
            this.substitutableGroups.add(processSubGroup(substitutableGroupMatcher.group()));
        }

        String subGroupParsedFormula = unparsedFormula.replaceAll(substitutableGroup.toString(), "");
        Matcher covalentIonMatcher = covalentIon.matcher(subGroupParsedFormula);
        while (covalentIonMatcher.find()) {
            this.covalentGroups.add(processSubGroup(covalentIonMatcher.group()));
        }
        String groupParsedFormula = subGroupParsedFormula.replaceAll(covalentIon.toString(), "");
        try {
            packageElements(groupParsedFormula, this.moleculeList);
            System.out.println("Done!");
        } catch (UnknownElementException e) {
            throw new UnknownElementException();
        }

    }

    // MODIFIES: targetList
    // EFFECTS: Takes a String containing elements and their quantities, turns it to a FormulaElement and adds it to
    //          targetList
    private void packageElements(String elements, List<FormulaElement> targetList) throws UnknownElementException {
        Matcher elementMatches = elementMatch.matcher(elements);
        while (elementMatches.find()) {
            String found = elementMatches.group();
            Integer amount = getNumericalComponent(found);
            String elementComponent = getStringComponent(found);
            try {
                targetList.add(new FormulaElement(AtomicSymbols.valueOf(elementComponent.toUpperCase()), amount));
            } catch (IllegalArgumentException e) {
                throw new UnknownElementException();
            }
        }
    }

    // EFFECTS: returns the first section of a string not containing any numbers
    private String getStringComponent(String found) {
        Matcher m = Pattern.compile("[^\\d]+").matcher(found);
        if (m.find()) {
            return m.group();
        } else {
            return "";
        }
    }

    // EFFECTS: returns the first part of a string containing digits converted to an Integer
    private Integer getNumericalComponent(String found) {
        Matcher m = generalDigit.matcher(found);
        if (m.find()) {
            return Integer.parseInt(m.group());
        } else {
            return 1;
        }
    }

    // EFFECTS: Takes a string identified as a covalent group/subgroup and returns a new MoleculeGroup containing
    //          the elements and their quantities along with the quantity of the covalent group/subgroup
    private MoleculeGroup processSubGroup(String group) throws UnknownElementException {
        int amount;
        List<FormulaElement> processedSubgroups = new ArrayList<>();
        Matcher amountChecker = bracketDigit.matcher(group);
        if (amountChecker.find()) {
            amount = getNumericalComponent(amountChecker.group());
        } else {
            amount = 1;
        }
        String justElements = group.replaceAll(bracketDigit.toString(), "").replaceAll(
                "[^A-Za-z\\d]", "");
        packageElements(justElements, processedSubgroups);
        return new MoleculeGroup(amount, processedSubgroups);
    }

}
