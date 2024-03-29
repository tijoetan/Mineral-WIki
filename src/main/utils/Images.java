package utils;

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

public class Images {
    private static Images instance;

    private Map<Cleavage, Image> cleavageImageMap;
    private Map<CrystalStructure, ImageIcon> crystalStructureImageMap;


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
    }

    private ImageIcon getCleavageImage(Cleavage cleavage) {
        return new ImageIcon(cleavageImageMap.get(cleavage)
                .getScaledInstance(300, 150, Image.SCALE_SMOOTH));
    }


    public static ImageIcon getInstanceCleavageImage(Cleavage key) {
        if (instance == null) {
            instance = new Images();
        }

        return instance.getCleavageImage(key);
    }
}
