package model.enums;

public enum Cleavage {
    CUBIC(0), RHOMBOHEDRAL(1), PRISMATIC(2), OCTAHEDRAL(3), BASAL(4), NONE(5), NA(6);
    private final int value;

    Cleavage(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
