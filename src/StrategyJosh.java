import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Concrete class of a strategy that implement strategy interface
 *
 * @author Joshua Liang
 * @copyright: 11/14/2017
 * @verion 1.0
 */
public class StrategyJosh implements Strategy {
    public final static int PIT_WIDTH = 100;
    public final static int PIT_HEIGHT = 100;
    public final static int MANCALA_WIDTH = 100;
    public final static int MANCALA_HEIGHT = 300;
    public final static int STONE_WIDTH = 20;
    public final static int STONE_HEIGHT = 30;
    public final static int ARC_W = 40;
    public final static int ARC_H = 40;

    /**
     * Creates regular pit shape
     *
     * @return regular pit shape
     */
    public RectangularShape setPitShape() {
        return new Rectangle2D.Double(0, 0, PIT_WIDTH, PIT_HEIGHT);
    }

    /**
     * Creates the mancala pit shape
     *
     * @return mancala pit shape
     */
    public RectangularShape setMancalaShape() {
        return new RoundRectangle2D.Double(0, 0, MANCALA_WIDTH, MANCALA_HEIGHT, ARC_W, ARC_H);
    }

    /**
     * returns stones in a completely random position in each pit to make it look realistic.
     * Also creates the stone shapess
     *
     * @param num     stone index
     * @param total   total number of stones in pit
     * @param mancala true if it is inside the mancala pit
     * @return shape of stone and positioning of stone
     */
    public RectangularShape setStoneShape(int num, int total, boolean mancala) {
        double x = 0;
        double y = 0;
        if (mancala) {
            Random random = new Random();
            int temp1 = random.nextInt((((MANCALA_WIDTH - STONE_HEIGHT) - 0) + 1) + 0);
            int temp2 = random.nextInt((((MANCALA_WIDTH - STONE_HEIGHT) - 0) + 1) + 0);
            return new Ellipse2D.Double(temp1, temp2, STONE_WIDTH, STONE_HEIGHT);
        } else {
            Random random = new Random();
            int temp1 = random.nextInt((((PIT_WIDTH - STONE_HEIGHT) - 0) + 1) + 0);
            int temp2 = random.nextInt((((MANCALA_WIDTH - STONE_HEIGHT) - 0) + 1) + 0);
            return new Ellipse2D.Double(temp1, temp2, STONE_WIDTH, STONE_HEIGHT);
        }

    }

    /**
     * Creates board color
     *
     * @return board color
     */
    public Color setBoardColor() {
        return new Color(170, 114, 67);
    }

    /**
     * Sets the stone colors. Random colors between 3.
     *
     * @return color of the stone
     */
    public Color setStoneColor() {
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0);
        colors.add(1);
        colors.add(2);
        colors.add(3);
        Collections.shuffle(colors);
        if (colors.get(1) == 0) {
            return new Color(0, 255, 0);
        } else if (colors.get(1) == 1) {
            return new Color(255, 0, 0);
        } else if (colors.get(1) == 2) {
            return new Color(0, 0, 255);
        } else {
            return new Color(255, 255, 0);
        }
    }

    /**
     * Sets the pit color
     *
     * @return color of the pit
     */
    public Color setPitColor() {
        return new Color(160, 82, 45);
    }

    /**
     * Gets the width of the mancala pit
     *
     * @return width of the mancala pit
     */
    public int getMancalaWidth() {
        return MANCALA_WIDTH;
    }

    /**
     * Gets the height of the mancala pit
     *
     * @return height of the mancala pit
     */
    public int getMancalaHeight() {
        return MANCALA_HEIGHT;
    }

    /**
     * Gets the width of the regular pit
     *
     * @return width of the regular pit
     */
    public int getPitWidth() {
        return PIT_WIDTH;
    }

    /**
     * Gets the height of the regular pit
     *
     * @return height of the regular pit
     */
    public int getPitHeight() {
        return PIT_HEIGHT;
    }

    /**
     * Sets the font coor
     *
     * @return color of font
     */
    public Color setFontColor() {
        return Color.WHITE;
    }

    /**
     * Sets the font style
     *
     * @return font style
     */
    public Font setFont() {
        return new Font("Arial", Font.BOLD, 20);
    }
}
