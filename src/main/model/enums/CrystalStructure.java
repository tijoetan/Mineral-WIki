package model.enums;


public enum CrystalStructure {
    CUBIC(0), HEXAGONAL(1), MONOCLINIC(2), ORTHORHOMBIC(3), TRICLINIC(4), NA(5);
    private final int value;

    CrystalStructure(int i) {
        this.value = i;
    }


    public int getValue() {
        return this.value;
    }
}
