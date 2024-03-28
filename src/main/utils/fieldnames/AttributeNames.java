package utils.fieldnames;

public class AttributeNames {
    public static final String NAME = "Name";
    public static final String CRYSTAL_STRUCTURE = "Crystal Structure";
    public static final String FORMULA = "Formula";
    public static final String HARDNESS = "Hardness";
    public static final String DENSITY = "Density";
    public static final String IOR = "Index of Refraction";
    public static final String CLEAVAGE = "Cleavage";

    public static final String DESCENDANTS = "Minerals with Family";


    public static final String[] MINERAL_ATTRIBUTE_NAMES =
            {NAME,
                    CRYSTAL_STRUCTURE,
                    FORMULA,
                    HARDNESS,
                    DENSITY,
                    IOR,
                    CLEAVAGE};

    public static final String[] FAMILY_ATTRIBUTE_NAMES =
            {NAME,
                    FORMULA,
                    DESCENDANTS};

    // EFFECTS: none
    public AttributeNames() {

    }
}
