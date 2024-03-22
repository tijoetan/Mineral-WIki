package model.enums;

import utils.StringUtils;

// Contains the Crystal structures for a mineral
public enum CrystalStructure {
    NA, CUBIC, HEXAGONAL, MONOCLINIC, ORTHORHOMBIC, TRICLINIC, TRIGONAL;

    @Override
    public String toString() {
        return (this == NA) ? "?" :
                StringUtils.getSentenceCase(super.toString());
    }
}
