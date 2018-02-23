/**
 * Mancala Tester class that runs the Mancala Board Game
 *
 * @author Joshua Liang, Rakesh Konda, Jonathan Van
 * @copyright: 12/9/2017
 */
public class MancalaTester {
    public static void main(String[] args) {
        Model modelData = new Model();
        Board gameBoard = new Board(modelData);
        modelData.attach(gameBoard);

    }

}