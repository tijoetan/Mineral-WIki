package ui.misc;

import model.enums.Cleavage;
import model.enums.CrystalStructure;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Contains images used in enum panels

public class Images {
    private static Images instance;

    private final Map<Cleavage, Image> cleavageImageMap;
    private final Map<CrystalStructure, Image> crystalStructureImageMap;

    // EFFECTS: initializes and populates maps with Enum Image pairs
    private Images() {
        cleavageImageMap = new HashMap<>();
        crystalStructureImageMap = new HashMap<>();

        for (Cleavage cleavage : Cleavage.values()) {
            try {
                cleavageImageMap.put(cleavage, ImageIO.read(new File("data/icons/mineralCleavage/"
                        + cleavage.name().toLowerCase()
                        + ".png")));
            } catch (IOException e) {
                cleavageImageMap.put(cleavage, new BufferedImage(10, 10, 10));
            }
        }

        for (CrystalStructure structure : CrystalStructure.values()) {
            try {
                crystalStructureImageMap.put(structure, ImageIO.read(new File("data/icons/crystalStructures/"
                        + structure.name().toLowerCase()
                        + ".png")));
            } catch (IOException e) {
                crystalStructureImageMap.put(structure, new BufferedImage(10, 10, 10));
            }
        }
    }

    //getters
    private ImageIcon getCleavageImage(Cleavage cleavage) {
        return new ImageIcon(cleavageImageMap.get(cleavage)
                .getScaledInstance(300, 200, Image.SCALE_SMOOTH));
    }

    private ImageIcon getCrystalImage(CrystalStructure structure) {
        return new ImageIcon(crystalStructureImageMap.get(structure)
                .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    }


    // EFFECTS: returns cleavage image from instance corresponding to given key
    public static ImageIcon getInstanceCleavageImage(Cleavage key) {
        if (instance == null) {
            instance = new Images();
        }

        return instance.getCleavageImage(key);
    }

    // EFFECTS: returns crystalStructure image from instance corresponding to given key
    public static ImageIcon getInstanceCrystalImage(CrystalStructure key) {
        if (instance == null) {
            instance = new Images();
        }

        return instance.getCrystalImage(key);
    }
}
