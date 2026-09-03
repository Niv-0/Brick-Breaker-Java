package Utils;

import java.awt.Color;
import java.util.Random;

/**
 * The Utils.ColorUtils class provides utility methods for working with colors.
 */
public class ColorUtils {
    private static final Random RANDOM = new Random();

    /**
     * Returns a random color that is generated with random RGB components.
     * @return a randomly generated Color object.
     */
    public static Color getRandomColor() {
        int red = RANDOM.nextInt(256);
        int green = RANDOM.nextInt(256);
        int blue = RANDOM.nextInt(256);
        return new Color(red, green, blue);
    }
}
