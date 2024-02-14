package model.chemicalstructure;

import model.enums.AtomicSymbols;
import exceptions.UnknownElementException;

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

    private static final Pattern substitutableGroup = Pattern.compile("\\([^\\)]+,[^\\)]+\\)(\\d)?");
    private static final Pattern covalentIon = Pattern.compile("\\([^\\)]+\\)\\d?");
    private static final Pattern elementMatch = Pattern.compile("[A-Z][a-z]?\\d*");
    private static final Pattern generalDigit = Pattern.compile("(\\d)+");
    private static final Pattern bracketDigit = Pattern.compile("\\)(\\d)+");


    public Formula(String unparsedFormula) throws UnknownElementException {
        this.moleculeList = new ArrayList<>();
        this.substitutableGroups = new ArrayList<>();
        this.covalentGroups = new ArrayList<>();
        this.unparsedFormula = unparsedFormula;
        parseFormula(unparsedFormula);
    }

    public void parseFormula(String unparsedFormula) throws UnknownElementException {

        Matcher substitutableGroupMatcher = substitutableGroup.matcher(unparsedFormula);
        while (substitutableGroupMatcher.find()) {
            this.substitutableGroups.add(processSubstitutableGroup(substitutableGroupMatcher.group()));
        }

        String subGroupParsedFormula = unparsedFormula.replaceAll(substitutableGroup.toString(), "");
        Matcher covalentIonMatcher = covalentIon.matcher(subGroupParsedFormula);
        while (covalentIonMatcher.find()) {
            this.covalentGroups.add(processCovalentIon(covalentIonMatcher.group()));
        }
        String groupParsedFormula = subGroupParsedFormula.replaceAll(covalentIon.toString(), "");
        try {
            packageElements(groupParsedFormula, this.moleculeList);
            System.out.println("Done!");
        } catch (UnknownElementException e) {
            throw new UnknownElementException();
        }

    }

    private MoleculeGroup processSubstitutableGroup(String group) throws UnknownElementException {
        List<FormulaElement> substitutableMoleculeList = new ArrayList<>();
        return processSubGroup(group);
    }

    private MoleculeGroup processCovalentIon(String group) throws UnknownElementException {
        List<FormulaElement> covalentMoleculeList = new ArrayList<>();
        return processSubGroup(group);
    }

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

    private String getStringComponent(String found) {
        Matcher m = Pattern.compile("[^\\d]+").matcher(found);
        if (m.find()) {
            return m.group();
        } else {
            return "";
        }
    }

    private Integer getNumericalComponent(String found) {
        Matcher m = generalDigit.matcher(found);
        if (m.find()) {
            return Integer.parseInt(m.group());
        } else {
            return 1;
        }
    }

    private MoleculeGroup processSubGroup(String group) throws UnknownElementException {
        int amount;
        List<FormulaElement> subsitutableMoleculeList = new ArrayList<>();
        Matcher amountChecker = bracketDigit.matcher(group);
        if (amountChecker.find()) {
            amount = getNumericalComponent(amountChecker.group());
        } else {
            amount = 1;
        }
        String justElements = group.replaceAll(bracketDigit.toString(), "").replaceAll(
                "[^A-Za-z\\d]", "");
        packageElements(justElements, subsitutableMoleculeList);
        return new MoleculeGroup(amount, subsitutableMoleculeList);
    }

    public String getFormulaAsString() {
        StringBuilder toReturn = new StringBuilder();
        for (FormulaElement constituent : moleculeList) {
            toReturn.append(constituent);
        }
        return toReturn.toString();
    }
}
