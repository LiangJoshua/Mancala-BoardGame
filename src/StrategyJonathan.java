import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;


/**
 * a strategy that implement strategy interface of the display of the game
 *
 * @author Jonathan Van
 * @copyright: 11/14/2017
 * @verion 1.1
 */
public class StrategyJonathan implements Strategy {
    final static int PIT_WIDTH = 90;
    final static int PIT_HEIGHT = 90;
    final static int BOARD_WIDTH = 90;
    final static int BOARD_HEIGHT = 180;
    final static int STONE_WIDTH = 10;
    final static int STONE_HEIGHT = 10;
    private boolean meh = false;

    /**
     * Determines the shape of the pit
     */
    public RectangularShape setPitShape() {
        return new Rectangle2D.Double(0, 0, PIT_WIDTH, PIT_HEIGHT);
    }

    /**
     * Determiens the shape of the mancala board.
     */
    public RectangularShape setMancalaShape() {
        return new Rectangle2D.Double(0, 15, BOARD_WIDTH, BOARD_HEIGHT);
    }

    /**
     * Determines the layout/arrangment of the marbles for the mancala.
     */
    public RectangularShape setStoneShape(int num, int total, boolean mancala) {
        // double x = 0;
        // double y = 0;
        if (mancala) {
            double angle = 2 * Math.PI * num / total;
            double x = BOARD_WIDTH / 2 + 30 * Math.cos(angle);
            double y = BOARD_HEIGHT / 2 + 30 * Math.sin(angle);

            return new Ellipse2D.Double(x, y, STONE_WIDTH, STONE_HEIGHT);
        } else {
            double angle = 2 * Math.PI * num / total;
            double x = PIT_WIDTH / 2 + 30 * Math.cos(angle);
            double y = PIT_HEIGHT / 2 + 30 * Math.sin(angle);
            return new Ellipse2D.Double(x, y, STONE_WIDTH, STONE_HEIGHT);
        }

    }

    /**
     * sets the board a certain color, in this case green
     */
    public Color setBoardColor() {
        return new Color(34, 139, 34);
    }

    /**
     * Alternates and sets teh color of hte marbles either white or gold.
     */
    public Color setStoneColor() {
        if (meh == false) {
            meh = true;
            return new Color(255, 255, 255);
        } else {
            meh = false;
            return new Color(255, 215, 0);
        }
    }

    /**
     * Sets the pit color to red.
     */
    public Color setPitColor() {
        return new Color(220, 20, 60);
    }

    /**
     * Sets the font to chalkboard font.
     */
    public Font setFont() {
        // TODO Auto-generated method stub
        return new Font("Chalkboard", Font.BOLD, 20);
    }

    /**
     * Sets the font to white
     */
    public Color setFontColor() {
        return Color.WHITE;
    }

    /**
     * Getter method for hte mancala board width
     */
    public int getMancalaWidth() {
        return BOARD_WIDTH;
    }

    /**
     * Getter method for hte mancala board height
     */
    public int getMancalaHeight() {
        return BOARD_HEIGHT;
    }

    /**
     * Getter method for the width of the pit
     */
    public int getPitWidth() {
        return PIT_WIDTH;
    }

    /**
     * Getter method for hte hieght of the pit.
     */
    public int getPitHeight() {
        return PIT_HEIGHT;
    }
}