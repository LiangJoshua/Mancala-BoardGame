import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model class of MVC pattern. Contains all core data and listeneres
 *
 * @author Joshua Liang, Rakesh Konda, Jonathan Van
 * @copyright: 11/2/2017
 */
public class Model {
    private static int currentPlayer;
    private int undoPlay;
    private int undoCount;
    private boolean undo;
    private boolean freebie;
    private int[] dataHolder;
    private int[] undoHolder;
    private int[] undoTurnHolder;
    private ArrayList<ChangeListener> listenerList;

    /**
     * Constructor of the model class, sets up data arrays, Arraylists, booleans
     * and integers to be used during the game as indicators.
     */
    public Model() {
        dataHolder = new int[14]; // 14 pits total, 12 small pits and 2 Mancala
        // pits
        undoCount = 3; // Amount of undo moves
        undoTurnHolder = new int[]{3, 3, 3}; // intialized to 3, for player
        // is
        // allowed 3 undo moves in one turn
        undoHolder = new int[14]; // holds undo data
        listenerList = new ArrayList<ChangeListener>(); // ArrayList of
        // changelisteners
        currentPlayer = 1; // the players
        undoPlay = 2; // player in an undo state
        undo = false; // indicates if player has any undo moves left
        freebie = false; // indicates if a player has any free turns after he
        // is done with his move
    }


    /**
     * Gets the freeTurn variable.
     *
     * @return freeTurn - True if the player's last stone landed in the big pit.
     */
    public boolean getFreeTurn() {
        return freebie;
    }

    /**
     * Gets the player currently playing.
     *
     * @return player - the current turn player.
     */
    public static int getPlayer() {
        return currentPlayer;
    }

    /**
     * a void method that updates the number of stones in the pit, notifies the
     * listeners afterwards.
     *
     * @param stones the number of stones each pit
     */
    public void setStone(int stones) {
        for (int i = 0; i < dataHolder.length - 1; i++) {
            dataHolder[i] = stones;
            if (i == 6)
                dataHolder[i] = 0;
        }
        notifyListeners();
    }

    /**
     * Reverts the most recently made move of a current player.
     */
    public void undo() {
        if (freebie) {
            freebie = false;
            currentPlayer++;
            currentPlayer = (((currentPlayer) % 2) != 0) ? 1 : 2; // Goes
            // between
            // players
        }
        if (undo && undoTurnHolder[currentPlayer] != 0) {
            undo = false;
            dataHolder = Arrays.copyOf(undoHolder, undoHolder.length);
            undoTurnHolder[currentPlayer]--;
            undoCount = undoTurnHolder[currentPlayer];
            undoTurnHolder[undoPlay] = 3;
            currentPlayer = undoPlay;
            currentPlayer = ((currentPlayer % 2) != 0) ? 1 : 2;
            notifyListeners();
        }
    }

    /**
     * Gets the undoCount, how many times the undo button has been used (there
     * is a limit of3 per player's turn).
     *
     * @return undoCount The current Undo count.
     */
    public int getUndoCounter() {
        return undoCount;
    }

    /**
     * Checks if all the stones in the smaller spots are empty to determine if
     * hte game is truly over.
     *
     * @return false - game is not over yet.
     */
    public boolean isDone() {
        boolean status = false;
        int bottomSide = 0;
        int topSide = 0;
        for (int i = 0; i < dataHolder.length - 1; i++) {
            if (i > 6)
                topSide += dataHolder[i];
            if (i < 6)
                bottomSide += dataHolder[i];
        }

        if (bottomSide == 0 || topSide == 0) {
            dataHolder[13] = dataHolder[13] + topSide;
            dataHolder[6] = dataHolder[6] + bottomSide;

            for (int i = 0; i < dataHolder.length - 1; i++) {
                if (i != 6) {
                    dataHolder[i] = 0;
                }
            }
            status = true;
        }
        return status;
    }

    /**
     * Gets data of the circles/pits.
     *
     * @return data - integer array holding the number of rocks per slot.
     */
    public int[] getData() {
        return dataHolder;
    }

    /**
     * Attaches a changelistenger on the Model class's changeListener arraylist.
     *
     * @param change a new addition to the arraylist of changelisteners.
     */
    public void attach(ChangeListener change) {
        listenerList.add(change);
    }

    /**
     * Makes sure that all ChangeListeners in teh Model class are aware that a
     * change has been made.
     */
    public void notifyListeners() {
        for (ChangeListener listee : listenerList) {
            listee.stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * Relocates the stones after a pit has been chosen and changes the model,
     * and then view after accordingly.
     *
     * @param pitCoordinate the selected pit
     */
    public void moveStones(int pitCoordinate) {
        undo = true;
        freebie = false;
        if (undoPlay != currentPlayer) {
            undoCount = 3;
        }
        undoHolder = Arrays.copyOf(dataHolder, dataHolder.length);
        int starterPit = 0;
        if ((currentPlayer == 1) && (starterPit > 6))
            starterPit = 6;
        if ((currentPlayer == 2) && (starterPit < 6))
            starterPit = 13;

        starterPit = pitCoordinate;
        int marbleCount = dataHolder[starterPit];
        int endingPit = (starterPit + dataHolder[starterPit]) % (dataHolder.length);
        int endPitCount = dataHolder[endingPit];

        dataHolder[starterPit] = 0;
        int displace = marbleCount + starterPit;
        for (int i = starterPit + 1; i <= displace; i++) {
            int pitNum = i;
            if (currentPlayer == 1 && pitNum == 13)
                pitNum++;
            if (currentPlayer == 2 && pitNum == 6)
                pitNum++;
            if (pitNum >= dataHolder.length)
                pitNum = pitNum % (dataHolder.length);
            dataHolder[pitNum]++;
        }

        if ((endingPit == 6 && currentPlayer == 1) || (endingPit == 13 && currentPlayer == 2)) {
            undoTurnHolder[currentPlayer] = 3;
            freebie = true;
        }

        if (endingPit != 13) {
            if ((endingPit < 6 && endPitCount == 0) && currentPlayer == 1) {
                if (dataHolder[12 - endingPit] != 0) {
                    int mancalaStones = dataHolder[12 - endingPit] + 1;
                    dataHolder[6] += mancalaStones;
                    dataHolder[endingPit] = 0;
                    dataHolder[12 - endingPit] = 0;
                }
                undoTurnHolder[currentPlayer] = 3;
            }
            if ((endPitCount == 0 && endingPit > 6) && currentPlayer == 2) {
                if (dataHolder[12 - endingPit] != 0) {
                    int mancalaStones = dataHolder[12 - endingPit] + 1;
                    dataHolder[13] += mancalaStones;
                    dataHolder[endingPit] = 0;
                    dataHolder[12 - endingPit] = 0;
                }
                undoTurnHolder[currentPlayer] = 3;
            }
        }
        if (isDone()) {
            gameDone();
        }

        undoPlay = currentPlayer;

        if (freebie) {
            currentPlayer++;
        }
        currentPlayer++;
        currentPlayer = ((currentPlayer % 2) != 0) ? 1 : 2;
        notifyListeners();
    }

    /**
     * Method returns which player won the game, or if it was a tie. retuens and
     * integer.
     *
     * @return 1 or 2 if player a or b wins, 3 if a tie.
     */
    public int gameDone() {
        int aVictory = 1;
        int bVictory = 2;
        int neither = 3;

        if (dataHolder[6] > dataHolder[13]) {
            return aVictory;
        }
        if (dataHolder[6] < dataHolder[13]) {
            return bVictory;
        }
        return neither;

    }

}