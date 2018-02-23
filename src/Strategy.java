import java.awt.Color;
import java.awt.Font;
import java.awt.geom.RectangularShape;

/**
 * Strategy design interface
 *
 * @author Joshua Liang, Rakesh Konda, Jonathan Van
 * @version 1.3
 * @copyright: 11/8/2017
 */
public interface Strategy {
    /**
     * Sets the pit shape
     *
     * @return shape of pit
     */
    public RectangularShape setPitShape();

    /**
     * Gets the Mancala shape
     *
     * @return shape of mancala
     */
    public RectangularShape setMancalaShape();

    /**
     * Gets the stone shapes
     *
     * @param num       index of stone
     * @param total     total number of stones in pit or mancala
     * @param isMancala true if mancala false if pit
     * @return shape of stone in correct x-y position
     */
    public RectangularShape setStoneShape(int num, int total, boolean isMancala);

    /**
     * Gets the color of the board
     *
     * @return color of the board
     */
    public Color setBoardColor();

    /**
     * Gets the color of the stone
     *
     * @return color of the stone
     */
    public Color setStoneColor();

    /**
     * Gets the pit of the color
     *
     * @return color of the pit
     */
    public Color setPitColor();

    /**
     * Gets the font
     *
     * @return font object
     */
    public Font setFont();

    /**
     * Gets the font color
     *
     * @return color of font
     */
    public Color setFontColor();

    /**
     * Gets width of Mancala
     *
     * @return width of mancala
     */
    public int getMancalaWidth();

    /**
     * Gets height of Mancala
     *
     * @return height of Mancala
     */
    public int getMancalaHeight();

    /**
     * Gets width of pit
     *
     * @return width of pit
     */
    public int getPitWidth();

    /**
     * Gets pit height
     *
     * @return height of pit
     */
    public int getPitHeight();

}