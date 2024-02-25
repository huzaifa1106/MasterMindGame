/**
 * MasterMindGUI
 * A program for visual interface of MasterMind
* @Author: Huzaifa A.
 * Since: January 20th
 */

package MasterM;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

/**
 * The graphical user interface for the MasterMind game.
 */
public class MasterMindGUI extends JPanel {

    private MasterMind model;
    JFrame frame;
    private static BufferedImage originalImage;
    private BorderLayout mainLayout;
    private FlowLayout buttonPanelLayout;

    // Panels for different views
    private JPanel startPanel;
    private JPanel gamePanel;
    private JPanel winningPanel;

    private JPanel buttonsPanel;
    private JPanel outputGrid;
    private JPanel generalPanel;

    // Color selection buttons
    private JButton blueButton, greenButton, orangeButton, purpleButton, redButton, goldButton;

    private JButton[][] outputArray;

    private boolean rowsFull;

    private JButton checkB;
    private JButton deleteButton;
    private JButton newGame;

    private JPanel[] hintPanels;
    private JButton[][] hintButtonsArray;

    private static final String IMAGE_PATH = "programs/MasterM/source.jpg";
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 600;
    private static final int IMAGE_WIDTH = 500;
    private static final int IMAGE_HEIGHT = 450;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 60;
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);
    private JButton enterButton;

    /**
     * Constructs the MasterMind GUI.
     * 
     * @param someModel The MasterMind model.
     */
    public MasterMindGUI(MasterMind someModel) {
        super();
        this.model = someModel;
        this.model.setGUI(this);
        this.createHintPanels();
        this.StartView();
        this.winnerPanel();
        this.layoutView();
        this.registerControllers();
        this.update(false);
    }

    /**
     * Initializes the layout of the GUI.
     */
    public void layoutView() {
        mainLayout = new BorderLayout();
        buttonPanelLayout = new FlowLayout();

        // Initialize buttons for game control
        checkB = new JButton("Check");
        deleteButton = new JButton("Delete");
        newGame = new JButton("New Game");

        gamePanel = new JPanel();
        buttonsPanel = new JPanel();
        outputGrid = new JPanel(new GridLayout(7, 5));
        generalPanel = new JPanel();

        // Creating color selection buttons
        blueButton = createColorButton("Blue", Color.BLUE);
        greenButton = createColorButton("Green", Color.GREEN);
        orangeButton = createColorButton("Orange", Color.ORANGE);
        purpleButton = createColorButton("Purple", new Color(128, 0, 128));
        redButton = createColorButton("Red", Color.RED);
        goldButton = createColorButton("Gold", new Color(255, 215, 0));

        buttonsPanel.setLayout(buttonPanelLayout);
        buttonsPanel.add(blueButton);
        buttonsPanel.add(greenButton);
        buttonsPanel.add(orangeButton);
        buttonsPanel.add(purpleButton);
        buttonsPanel.add(redButton);
        buttonsPanel.add(goldButton);

        // Creating the grid of buttons representing the game board
        outputArray = new JButton[7][4];

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 4; y++) {
                outputArray[x][y] = new JButton();
                outputArray[x][y].setPreferredSize(new Dimension(50, 50));
                outputGrid.add(outputArray[x][y]);
            }

            // Add hint panels for each row
            outputGrid.add(hintPanels[x]);

            JLabel rowLabel = new JLabel("       Row " + (x + 1));
            outputGrid.add(rowLabel);
        }

        gamePanel.setLayout(mainLayout);
        gamePanel.add(outputGrid, BorderLayout.CENTER);
        gamePanel.add(buttonsPanel, BorderLayout.SOUTH);
        general();

        // Creating the main JFrame
        frame = new JFrame();

        gamePanel.setBackground(Color.BLACK);
        frame.setLocation(450, 50);
        frame.setSize(500, 600);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Mastermind Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initializes the Start View of the GUI.
     */
    public void StartView() {
        frame = new JFrame("MasterMind");
        startPanel = new JPanel();

        // Load and resize image to fit the initial frame size
        ImageIcon imageIcon = getImage(IMAGE_PATH, IMAGE_WIDTH, IMAGE_HEIGHT);
        JLabel imageLabel = new JLabel(imageIcon);

        // Set preferred size for the button
        enterButton = new JButton("START");
        enterButton.setFont(BUTTON_FONT);
        enterButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        startPanel.setLayout(new BorderLayout());
        startPanel.add(imageLabel, BorderLayout.CENTER);
        startPanel.add(enterButton, BorderLayout.SOUTH);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    /**
     * Initializes the Winner Panel of the GUI.
     */
    public void winnerPanel() {
        frame = new JFrame("Winner");
        winningPanel = new JPanel();

        // Load and resize image to fit the initial frame size
        ImageIcon imageIcon = getImage("programs/MasterM/winningImage.png", IMAGE_WIDTH, IMAGE_HEIGHT);
        JLabel imageLabel = new JLabel(imageIcon);

        winningPanel.setLayout(new BorderLayout());
        winningPanel.add(imageLabel, BorderLayout.CENTER);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }

    /**
     * Creates a color selection button with the specified label and color.
     * 
     * @param label The label for the button.
     * @param color The color for the button.
     * @return The created JButton.
     */
    private JButton createColorButton(String label, Color color) {
        JButton button = new JButton(label);
        button.setBackground(color);
        return button;
    }

    /**
     * Creates the hint panels for each row in the game.
     */
    private void createHintPanels() {
        hintPanels = new JPanel[7]; // 7 rows
        hintButtonsArray = new JButton[7][4]; // Initialize hintButtonsArray

        for (int i = 0; i < hintPanels.length; i++) {
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(new GridLayout(1, 4));

            for (int j = 0; j < 4; j++) {
                hintButtonsArray[i][j] = new JButton();
                hintButtonsArray[i][j].setPreferredSize(new Dimension(25, 25));
                hintPanels[i].add(hintButtonsArray[i][j]);
            }
        }
    }

    /**
     * Deletes hints in the hint panels.
     */
    public void deleteHints() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 4; y++) {
                hintButtonsArray[x][y].setBackground(outputArray[0][0].getBackground());
            }
        }
    }

    /**
     * Updates the hint panels based on the secret code and user input.
     * 
     * @param currentRow The current row being updated.
     */
    public void updateHintPanels(int currentRow) {
        char[] secretCode = model.getSecretCode();
        char[] userValues = model.getUserValues();

        for (int i = 0; i < secretCode.length; i++) {
            char secretColor = secretCode[i];
            char userColor = userValues[i];

            // Check for correct positions
            if (userColor == secretColor) {
                hintButtonsArray[currentRow - 1][i].setBackground(Color.WHITE); // Correct letter and correct position
            } else if (containsColor(secretCode, userColor)) {
                hintButtonsArray[currentRow - 1][i].setBackground(Color.BLACK); // Correct letter but wrong position
            } else {
                hintButtonsArray[currentRow - 1][i].setBackground(Color.RED); // Incorrect letter
            }
        }

        outputGrid.revalidate();
        outputGrid.repaint();
    }

    /**
     * Registers controllers for various buttons in the GUI.
     */
    private void registerControllers() {
        // Color buttons
        ColorSelectController blueController = new ColorSelectController(model, blueButton, 'B');
        ColorSelectController greenController = new ColorSelectController(model, greenButton, 'G');
        ColorSelectController orangeController = new ColorSelectController(model, orangeButton, 'O');
        ColorSelectController purpleController = new ColorSelectController(model, purpleButton, 'P');
        ColorSelectController redController = new ColorSelectController(model, redButton, 'R');
        ColorSelectController goldController = new ColorSelectController(model, goldButton, 'Y');

        blueButton.addActionListener(blueController);
        greenButton.addActionListener(greenController);
        orangeButton.addActionListener(orangeController);
        purpleButton.addActionListener(purpleController);
        redButton.addActionListener(redController);
        goldButton.addActionListener(goldController);

        // Start button
        StartGameController startController = new StartGameController(model, newGame);
        newGame.addActionListener(startController);

        // Enter Button
        EnterGameController enterController = new EnterGameController(model, enterButton);
        enterButton.addActionListener(enterController);

        // Delete button
        DeleteController deleteController = new DeleteController(model, deleteButton);
        deleteButton.addActionListener(deleteController);

        // Check button
        CheckButtonController checkController = new CheckButtonController(model, checkB);
        checkB.addActionListener(checkController);
    }

    /**
     * Loads an image from the specified path and resizes it.
     * 
     * @param path   The path to the image file.
     * @param width  The desired width of the image.
     * @param height The desired height of the image.
     * @return An ImageIcon containing the resized image.
     */
    private static ImageIcon getImage(String path, int width, int height) {
        try {
            originalImage = ImageIO.read(new File(path));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Configures and adds additional components to the general panel.
     */
    private void general() {
        checkB = new JButton("Check");
        deleteButton = new JButton("Delete");
        newGame = new JButton("New Game");

        generalPanel.setLayout(new FlowLayout());

        generalPanel.add(checkB);
        generalPanel.add(deleteButton);
        generalPanel.add(newGame);

        gamePanel.add(generalPanel, BorderLayout.NORTH);
    }

    /**
     * Checks if a given color is present in an array of colors.
     * 
     * @param array The array of colors.
     * @param color The color to check for.
     * @return True if the color is present, false otherwise.
     */
    private boolean containsColor(char[] array, char color) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == color) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the GUI based on the game model.
     * 
     * @param clearRow Indicates whether to clear the current row or update hint
     *                 panels.
     */
    public void update(boolean clearRow) {
        if (!model.getEnterGame()) {
            frame.setContentPane(startPanel);
            frame.setVisible(true);
        } else if (model.getEnterGame() && !model.getWinGame()) {
            frame.setContentPane(gamePanel);
            frame.setVisible(true);
            this.checkFill();
            if (clearRow) {
                clearCurrentRow();
            } else {
                if (model.getUpdateHint()) {
                    this.updateHintPanels(model.getCurrentRow());
                }
            }
        } else if (model.getWinGame()) {
            frame.setContentPane(winningPanel);
            frame.setVisible(true);
        }
        frame.revalidate(); // Add revalidate to refresh the frame
        frame.repaint(); // Add repaint to ensure the changes are visible
    }

    /**
     * Clears the current row in the GUI.
     */
    private void clearCurrentRow() {
        int currentRow = model.getCurrentRow();
        for (int col = 0; col < outputArray[currentRow].length; col++) {
            JButton button = outputArray[currentRow][col];
            button.setBackground(null); // Set the background color to default or null
        }
    }

    /**
     * Checks the map of whats full in the GUI.
     */
    public void checkFill() {
        char[][] gameMap = model.getGameMap();

        for (int row = 0; row < gameMap.length; row++) {
            boolean rowFull = true;

            for (int col = 0; col < gameMap[row].length; col++) {
                char colorCode = gameMap[row][col];

                if (colorCode == '\0') {
                    rowFull = false;
                    break;
                }

                // Find the corresponding JButton and set its background color
                JButton button = outputArray[row][col];
                setColor(button, colorCode);
            }

            // If the row is full, update the rowsFull variable
            if (rowFull) {
                rowsFull = true;
            }
        }
    }

    /**
     * Sets button color
     */
    private void setColor(JButton button, char colorCode) {
        switch (colorCode) {
            case 'B':
                button.setBackground(Color.BLUE);
                break;
            case 'G':
                button.setBackground(Color.GREEN);
                break;
            case 'O':
                button.setBackground(Color.ORANGE);
                break;
            case 'P':
                button.setBackground(new Color(128, 0, 128)); // Purple
                break;
            case 'R':
                button.setBackground(Color.RED);
                break;
            case 'Y':
                button.setBackground(new Color(255, 215, 0)); // Gold
                break;
            default:
                break;
        }
    }

    /**
     * Updates the GUI when correct positions are found.
     * 
     * @param count The count of correct positions.
     */
    public void correctPos(int count) {
        updateHintPanels(model.getCurrentRow());
    }

    /**
     * Updates the GUI when correct characters are found.
     * 
     * @param count The count of correct characters.
     */
    public void correctChar(int count) {
        updateHintPanels(model.getCurrentRow());
    }
}
