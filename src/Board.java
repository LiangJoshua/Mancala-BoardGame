import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RectangularShape;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.*;
/**
 * Class that contains view and controller components
 *
 * @author Joshua Liang, Rakesh Konda, Jonathan Van
 * @copyright: 12/9/2017
 */


/**
 * Board class that acts as the View Class
 * Displays the Mancala Board Game
 */
public class Board extends JFrame implements ChangeListener {
    private Model model;
    private Strategy strategy;
    private JPanel boardPanel;
    final JButton undoButton;
    private boolean gameOver;

    /**
     * Constructor for Board
     *
     * @param model the model object from the model class
     */
    public Board(Model model) {
        this.model = model;
        gameOver = false;
        welcomeMenu();
        setLayout(new BorderLayout());
        setLocation(300, 200);
        undoButton = new JButton("CAREFUL! DON'T RUN OUT OF THESE!        Undo : " + this.model.getUndoCounter() + "        CAREFUL! DON'T RUN OUT OF THESE!");
        undoButton.setPreferredSize(new Dimension(1000, 50));
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Board.this.model.getUndoCounter() != 0) {
                    Board.this.model.undo();
                    undoButton.setText("CAREFUL! DON'T RUN OUT OF THESE!        Undo : " + Board.this.model.getUndoCounter() + "        CAREFUL! DON'T RUN OUT OF THESE!  ");
                }
            }
        });
        JPanel undoPanel = new JPanel();
        undoPanel.add(undoButton);
        add(boardPanel, BorderLayout.CENTER);
        add(undoPanel, BorderLayout.PAGE_START);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }

    /**
     * Private class to represent Pits in the Mancala board game
     * Utilizes concrete strategy
     *
     * @author Joshua Liang, Rakesh Konda, Jonathan Van
     */
    private class Pit implements Icon {
        Shape pitShape;
        int pitNum = 0;

        /**
         * Pit constructor
         *
         * @param pitNum index in data array
         */
        public Pit(int pitNum) {
            this.pitNum = pitNum;
        }

        /**
         * Get the height of the icon
         *
         * @return the heigh of the icon
         */
        public int getIconHeight() {
            if (pitNum == 6 || pitNum == 13) {
                return strategy.getMancalaHeight();
            } else {
                return strategy.getPitHeight();
            }
        }

        /**
         * Gets the icon width
         *
         * @return returns the icon width
         */
        public int getIconWidth() {
            if (pitNum == 6 || pitNum == 13) {
                return strategy.getMancalaWidth();
            } else {
                return strategy.getPitWidth();
            }
        }

        /**
         * Paints the icon or pit with stones
         *
         * @param c component
         * @param g graphics object
         * @param x x coordinate
         * @param y y coordinate
         */
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;
            if (model.getData()[pitNum] > 5) {
            }

            if (pitNum == 6 || pitNum == 13) {
                pitShape = strategy.setMancalaShape();
                g2.setColor(strategy.setPitColor());
                g2.fill(pitShape);
                for (int i = 0; i < model.getData()[pitNum]; i++) {
                    RectangularShape temp = strategy.setStoneShape(i, model.getData()[pitNum], true);
                    g2.setColor(strategy.setStoneColor());
                    g2.draw(temp);
                    g2.fill(temp);

                }
            } else {
                pitShape = strategy.setPitShape();
                g2.setColor(strategy.setPitColor());
                g2.fill(pitShape);
                for (int i = 0; i < model.getData()[pitNum]; i++) {
                    RectangularShape temp = strategy.setStoneShape(i, model.getData()[pitNum], false);
                    g2.setColor(strategy.setStoneColor());
                    g2.draw(temp);
                    g2.fill(temp);

                }
            }
        }
    }

    /**
     * Checks if the state of the game has changed
     *
     * @param e the ChangeEvent
     */
    public void stateChanged(ChangeEvent e) {
        repaint();
        if (model.isDone() && !gameOver) {
            gameOver = true;
            JFrame frame = new JFrame("Winner");
            if (model.gameDone() == 1)
                JOptionPane.showMessageDialog(frame, "Player A is the winner. Player B come on...", "", JOptionPane.DEFAULT_OPTION);
            else if (model.gameDone() == 2) {
                JOptionPane.showMessageDialog(frame, "Player B is the winner. Player A come on...", "", JOptionPane.DEFAULT_OPTION);
            } else
                JOptionPane.showMessageDialog(frame, "Tie Game", "", JOptionPane.DEFAULT_OPTION);
        }
    }

    /**
     * The main menu of the Mancala game
     */
    public void welcomeMenu() {
        Object[] options = {"How to Play?", "Start!", "Exit", "About Us"};
        try {
            ImageIcon icon = new ImageIcon("start.gif");
            int input = JOptionPane.showOptionDialog(null, "Welcome to HOWARD Mancala", "HOWARD Mancala", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);
            if (input == 1) {
                chooseStrategy();
            } else if (input == 0) {
                howToPlay();
            } else if (input == 2) {
                System.exit(0);

            } else if (input == 3) {
                aboutUs();
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Menu that talks about us
     */
    public void aboutUs() {
        JOptionPane.showMessageDialog(null, "Hello, this is Team HOWARD. Thanks for playing our game.\n" +
                "We're called Team HOWARD because we made this game for our friend HOWARD.\n" +
                "This project took a lot of time and it was hard but we managed to get it work\n" +
                "for our CS151 Objected Oriented Design class.\n" +
                "I hope you like our game!", "About Us", JOptionPane.DEFAULT_OPTION);
        members();
    }

    /**
     * Menu that shows the members of the team
     */
    public void members() {
        Object[] options = {"Joshua Liang", "Rakesh Konda", "Jonathan Van", "Main Menu"};
        int input = JOptionPane.showOptionDialog(null, "Learn about the team members.", "Members", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (input == 0) {
            aboutJosh();
        } else if (input == 1) {
            aboutRakesh();
        } else if (input == 2) {
            aboutJonathan();
        } else if (input == 3) {
            welcomeMenu();
        } else {
            System.exit(0);
        }
    }

    /**
     * This class talks Josh
     */
    public void aboutJosh() {
        JOptionPane.showMessageDialog(null, "Josh is a third year student at SJSU majoring in Computer Science. \nHe really likes to code", "About Josh", JOptionPane.DEFAULT_OPTION);
        members();
    }

    /**
     * Talks about rakesh
     */
    public void aboutRakesh() {
        JOptionPane.showMessageDialog(null, "Rakesh is a 3rd year Computer Science student at San Jose State University \nHe likes food", "About Rakesh", JOptionPane.DEFAULT_OPTION);
        members();
    }

    /**
     * Talks about jonathan
     */
    public void aboutJonathan() {
        JOptionPane.showMessageDialog(null, "Hi I'm Jonathan. Bye.", "About Jonathan", JOptionPane.DEFAULT_OPTION);
        members();
    }

    /**
     * Menu for "How To Play" option. Displays instuctions to play Mancala
     */
    public void howToPlay() {
        Object[] options = {"Back to Main Menu", "Play", "Exit"};
        try {
            URL url = new URL("http://www.cs.sjsu.edu/~kim/cs151/contents/project/mancala.jpg");
            ImageIcon icon = new ImageIcon(url);
            JOptionPane.showMessageDialog(null, "                                                         HOWARD MANCALA INSTRUCTIONS" +
                    "\n•\tThe board consists of two rows of pits, each. " +
                    "\n•\tThree pieces of stones are placed in each of the 12 holes. " +
                    "\n•\tEach player has a large store called Mancala to the right side of the board. " +
                    "\n•\tOne player starts the game by picking up all of the stones in any one of his own pits. " +
                    "\n•\tMoving counter-clock wise, the player places one in each pit starting with the next pit until the stones run out. " +
                    "\n•\tIf you run into your own Mancala, place one stone in it. " +
                    "\n•\tIf there are more stones to go past your own Mancala, continue placing them into the opponent's pits. " +
                    "\n•\tHowever, skip your opponent's Mancala. If the last stone you drop is your own Mancala, you get a free turn . " +
                    "\n•\tIf the last stone you drop is in an empty pit on your side, you get to take that stone and all of your opponents " +
                    "\n\t  stones that are in the opposite pit. Place all captured stones in your own Mancala. " +
                    "\n•\tThe game ends when all six pits on one side of the Mancala board are empty. " +
                    "\n•\t\tThe player who still has stones on his side of the board when the game ends captures all of " +
                    "\n\t    those pieces and place them in his Mancala. The player who has the most stones in his Mancala wins.", "About", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        int input = JOptionPane.showOptionDialog(null, "", "How to Play", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (input == 0) {
            welcomeMenu();
        } else if (input == 1) {
            chooseStrategy();
        } else if (input == 2) {
            System.exit(0);
        } else {
            System.exit(0);
        }
    }

    /**
     * Sub menu that asks the user if they want to start out with 3 or 4 stones, then loads Josh's strategy
     */
    public void numberOfStonesJosh() {
        Object[] optionStones = {"3", "4"};
        int inputStones = JOptionPane.showOptionDialog(null, "How many stones per pit?:", "Initial Stones",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                optionStones, optionStones[0]);

        if (inputStones == 0) {
            model.setStone(3);
            boardPanel = loadStrategy(new StrategyJosh());
        } else if (inputStones == 1) {
            model.setStone(4);
            boardPanel = loadStrategy(new StrategyJosh());
        } else {
            System.exit(0);
        }
    }

    /**
     * Asks the user if they want to start with 3 or 4 stones and then loads the board with Jonathan's strategy
     */
    public void numberOfStonesJonathan() {
        Object[] optionStones = {"3", "4"};
        int inputStones = JOptionPane.showOptionDialog(null, "How many stones per pit?:", "Initial Stones",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                optionStones, optionStones[0]);

        if (inputStones == 0) {
            model.setStone(3);
            boardPanel = loadStrategy(new StrategyJonathan());
        } else if (inputStones == 1) {
            model.setStone(4);
            boardPanel = loadStrategy(new StrategyJonathan());
        } else {
            System.exit(0);
        }
    }

    /**
     * Asks the user if they want to start with 3 or 4 stones and then loads the board with Rakesh's strategy
     */
    public void numberOfStonesRakesh() {
        Object[] optionStones = {"3", "4"};
        int inputStones = JOptionPane.showOptionDialog(null, "How many stones per pit?:", "Initial Stones",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                optionStones, optionStones[0]);

        if (inputStones == 0) {
            model.setStone(3);
            boardPanel = loadStrategy(new StrategyRakesh());
        } else if (inputStones == 1) {
            model.setStone(4);
            boardPanel = loadStrategy(new StrategyRakesh());
        } else {
            System.exit(0);
        }
    }

    /**
     * Asks the user to confirm their strategy choice of josh's and shows an image of what the strategy looks like.
     */
    public void joshCheck() {
        Object[] options = {"Yes", "No"};

        ImageIcon icon = new ImageIcon("josh.png");
        int input = JOptionPane.showOptionDialog(null, "This is Josh's Style.\nAre You Sure?", "Board Styles", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        if (input == 0) {
            numberOfStonesJosh();
        } else if (input == 1) {
            welcomeMenu();
        } else {
            System.exit(0);
        }


    }

    /**
     * Asks the user to confirm their strategy choice of jonathan's and shows an image of what the strategy looks like.
     */
    public void jonathanCheck() {
        Object[] options = {"Yes", "No"};

        ImageIcon icon = new ImageIcon("jonathan.png");
        int input = JOptionPane.showOptionDialog(null, "This is Jonathan's Style.\nAre You Sure?", "Board Styles", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        if (input == 0) {
            numberOfStonesJonathan();
        } else if (input == 1) {
            welcomeMenu();
        } else {
            System.exit(0);
        }
    }

    /**
     * Asks the user to confirm their strategy choice of jonathan's and shows an image of what the strategy looks like.
     */
    public void rakeshCheck() {
        Object[] options = {"Yes", "No"};
        BufferedImage ics = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
        try {
            ics = ImageIO.read(new File("rakesh.png"));
            ics.setRGB(50, 50, 10);
        } catch (Exception io) {
            JOptionPane.showConfirmDialog(null, "rakesh.png not found!");
        }
        ImageIcon icon = new ImageIcon(ics);
        int input = JOptionPane.showOptionDialog(null, "This is Rakesh's Style.\nAre You Sure?", "Board Styles", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        if (input == 0) {
            numberOfStonesRakesh();
        } else if (input == 1) {
            welcomeMenu();
        } else {
            System.exit(0);
        }


    }

    /**
     * Option menu that asks which strategy game the user would like to play
     */
    public void chooseStrategy() {
        Object[] options = {"Josh's Style", "Rakesh's Style", "Jonathan's Style"};
        int input = JOptionPane.showOptionDialog(null, "What board style do you want?", "Board Styles", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (input == 0) {
            joshCheck();
        } else if (input == 1) {
            rakeshCheck();
        } else if (input == 2) {
            jonathanCheck();
        } else {
            System.exit(0);
        }

    }

    /**
     * Runs the concrete strategy with a jpanel
     *
     * @param strat the concrete strategy to use
     * @return JPanel with the board
     */
    public JPanel loadStrategy(Strategy strat) {
        this.strategy = strat;
        Color boardColor = strategy.setBoardColor();
        Color fontColor = strategy.setFontColor();
        Font font = strategy.setFont();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 6, 20, 20));
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JTextPane mancalaA = new JTextPane();
        mancalaA.setBackground(boardColor);
        mancalaA.setForeground(fontColor);
        mancalaA.setFont(font);
        mancalaA.setEditable(false);
        mancalaA.setText("M\nA\nN\nC\nA\nL\nA\n \nA");
        JTextPane mancalaB = new JTextPane();
        mancalaB.setBackground(boardColor);
        mancalaB.setForeground(fontColor);
        mancalaB.setFont(font);
        mancalaB.setEditable(false);
        mancalaB.setText("M\nA\nN\nC\nA\nL\nA\n \nB");
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());
        leftPanel.add(mancalaA, BorderLayout.WEST);
        rightPanel.add(mancalaB, BorderLayout.EAST);
        leftPanel.add(new JLabel(new Pit(13)), BorderLayout.EAST);
        rightPanel.add(new JLabel(new Pit(6)), BorderLayout.WEST);

        // B6 to B1 pits
        for (int i = 12; i > 6; i--) {
            final Pit pits = new Pit(i);
            final int pit = i;
            final JLabel pitLabel = new JLabel(pits);
            pitLabel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (Model.getPlayer() == 1) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Be patient and wait your turn my child.\nIt's Player A's turn.");
                    } else if (model.getData()[pit] == 0) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "This pit is empty...");
                    } else {
                        if (pits.pitShape.contains(e.getPoint())) {
                            model.moveStones(pit); //mutator
                            if (model.getFreeTurn() == true) {
                                JFrame frame = new JFrame();
                                JOptionPane.showMessageDialog(frame, "You get a Free Turn!\nGo Again!");
                            }
                            undoButton.setText("CAREFUL! DON'T RUN OUT OF THESE!        Undo : " + model.getUndoCounter() + "        CAREFUL! DON'T RUN OUT OF THESE!  ");
                        }
                    }
                }
            });
            JPanel pitPanel = new JPanel(new BorderLayout());
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.setBackground(boardColor);
            textPane.setForeground(fontColor);
            textPane.setFont(font);
            textPane.setText("B" + (i - 6));
            StyledDocument style = textPane.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            style.setParagraphAttributes(0, style.getLength(), center, false);
            pitPanel.add(textPane, BorderLayout.NORTH);
            pitPanel.add(pitLabel, BorderLayout.SOUTH);
            centerPanel.add(pitPanel, BorderLayout.SOUTH);
            pitPanel.setBackground(boardColor);
        }
        // A1 to A6 pits
        for (int i = 0; i < 6; i++) {
            final Pit pits = new Pit(i);
            final int pit = i;
            final JLabel pitLabel = new JLabel(pits);
            pitLabel.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (Model.getPlayer() == 2) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Be patient and wait your turn my child.\nIt's Player B's turn.");
                    } else if (model.getData()[pit] == 0) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "This pit is empty...");
                    } else {
                        if (pits.pitShape.contains(e.getPoint())) {
                            model.moveStones(pit); //mutator
                            if (model.getFreeTurn() == true) {
                                JFrame frame = new JFrame();
                                JOptionPane.showMessageDialog(frame, "You get a Free Turn!\nGo Again!");
                            }
                            undoButton.setText("CAREFUL! DON'T RUN OUT OF THESE!        Undo : " + model.getUndoCounter() + "        CAREFUL! DON'T RUN OUT OF THESE!  ");
                        }
                    }
                }
            });
            JPanel pitPanel = new JPanel(new BorderLayout());
            pitPanel.add(pitLabel, BorderLayout.NORTH);
            JTextPane textPane = new JTextPane();
            textPane.setBackground(boardColor);
            textPane.setForeground(fontColor);
            textPane.setFont(font);
            textPane.setEditable(false);
            textPane.setText("A" + (i + 1));
            StyledDocument style = textPane.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            style.setParagraphAttributes(0, style.getLength(), center, false);
            pitPanel.add(textPane, BorderLayout.SOUTH);
            pitPanel.setBackground(boardColor);
            centerPanel.add(pitPanel, BorderLayout.SOUTH);
        }
        JPanel gamePanel = new JPanel();
        gamePanel.add(leftPanel, BorderLayout.WEST);
        gamePanel.add(centerPanel, BorderLayout.CENTER);
        gamePanel.add(rightPanel, BorderLayout.EAST);
        centerPanel.setBackground(boardColor);
        leftPanel.setBackground(boardColor);
        rightPanel.setBackground(boardColor);
        gamePanel.setBackground(boardColor);
        return gamePanel;
    }


}
