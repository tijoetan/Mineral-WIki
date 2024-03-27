package model.enums;

import utils.StringUtils;

// Contains the Different Cleavage types for a mineral
public enum Cleavage {
    NA, CUBIC, RHOMBOHEDRAL, PRISMATIC, OCTAHEDRAL, BASAL, NONE;

    @Override
    public String toString() {
        return (this == NA) ? "?" :
                StringUtils.getSentenceCase(super.toString());
    }

    public String getLiteralString() {
        return super.toString();
    }

//    @
//    public static Cleavage valueOf(String ei) {
//
//    }

}
