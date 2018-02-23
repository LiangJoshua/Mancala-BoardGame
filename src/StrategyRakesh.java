import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

/**
 * Concrete class of a strategy that implement strategy interface
 * @author Rakesh
 * @copyright: 12/9/2017
 */
public class StrategyRakesh implements Strategy {
    private final static int PIT_WIDTH = 100;
    private final static int PIT_HEIGHT = 100;
    private final static int MANCALA_WIDTH = 150;
    private final static int MANCALA_HEIGHT = 300;
    public final static int ARC_W = 40;
    public final static int ARC_H = 40;
    private int STONE_WIDTH = 10;
    private int STONE_HEIGHT = 10;
    private int colorCode = 0;
    private int stoneCode = 0;

    /**
     * Returns an Ellipse object for the pitshape
     * @return round rectangle object
     */
    public RectangularShape setPitShape() {
        return new RoundRectangle2D.Double(0, 0, PIT_WIDTH, PIT_HEIGHT, ARC_H, ARC_W);
    }

    /**
     * Sets the Mancala player point pit shape
     * @return an Ellipse2D for the Mancala Shape
     */
    public RectangularShape setMancalaShape() {
        return new Ellipse2D.Double(0, 0, MANCALA_WIDTH, MANCALA_HEIGHT);
    }

    /**
     * Updates the location of the stones in Mancala pit
     *
     * @param stoneNum the location of stones
     * @param totalStone the total number of stones
     * @param inside true if the pit contains the stones, else false
     * @return shape of stone in correct x-y position
     */
    public RectangularShape setStoneShape(int stoneNum, int totalStone, boolean inside) {
        if (inside) {
            Random random = new Random(50);
            int x = random.nextInt(((((MANCALA_WIDTH - STONE_HEIGHT) / 2) - 0) + 1) + 0);
            int y = random.nextInt(((((MANCALA_WIDTH - STONE_HEIGHT) / 2) - 0) + 1) + 0);
            setShape();
            return new Ellipse2D.Double(x, y, STONE_WIDTH, STONE_HEIGHT);
        } else {
            Random random = new Random();
            int x = random.nextInt(((((PIT_WIDTH - STONE_HEIGHT) / 2 ) - 0) + 1) + 0);
            int y = random.nextInt(((((MANCALA_WIDTH - STONE_HEIGHT) / 2 )- 0) + 1) + 0);
            setShape();
            return new Ellipse2D.Double(x, y, STONE_WIDTH, STONE_HEIGHT);
        }
    }

    /**
     * Sets the shape of stones
     */
    public void setShape() {
        if(stoneCode == 0) {
            stoneCode = 1;
            STONE_HEIGHT = 11;
            STONE_WIDTH = 9;
        }
        else if(stoneCode == 1) {
            stoneCode = 2;
            STONE_HEIGHT = 12;
            STONE_WIDTH = 15;
        }
        else if(stoneCode == 2) {
            stoneCode = 0;
            STONE_HEIGHT = 15;
            STONE_WIDTH = 10;
        }
    }

    /**
     * Sets the Mancala Board
     * @return color of the board
     */
    public Color setBoardColor() {
        return new Color(210, 105, 30);
    }

    /**
     * Sets the color of stones.
     * Alternates colors to make it more realistic.
     * @return color of the stone
     */
    public Color setStoneColor() {
        if(colorCode == 0 ) {
            colorCode = 1;
            return new Color(107,142,35);
        }
        else if(colorCode == 1) {
            colorCode = 2;
            return new Color( 70,130,180);
        }
        else if(colorCode == 2) {
            colorCode = 3;
            return new Color( 184,134,11);
        }
        else{
            colorCode = 0;
            return new Color(47,79,79);
        }
    }

    /**
     * Sets the color of the Pit
     * @return color of the pit
     */
    public Color setPitColor() {
        return new Color(160,82,45);
    }

    /**
     * Gets the width of the Mancala
     * @return width of mancala
     */
    public int getMancalaWidth() {
        return MANCALA_WIDTH;
    }

    /**
     * Gets the height of the Mancala
     * @return height of mancala
     */
    public int getMancalaHeight() {
        return MANCALA_HEIGHT;
    }

    /**
     * Gets the width of the pit
     * @return the pit width
     */
    public int getPitWidth() {
        return PIT_WIDTH;
    }

    /**
     * Gets the height of the pit
     * @return the pit height
     */
    public int getPitHeight() {
        return PIT_HEIGHT;
    }

    /**
     * Sets the font color of text
     * @return the font color
     */
    public Color setFontColor() {
        return Color.BLACK;
    }

    /**
     * Sets the font type, size and format of the text
     * @return the font object with attributes
     */
    public Font setFont() {
        return new Font("SansSerif", Font.BOLD, 30);
    }
}