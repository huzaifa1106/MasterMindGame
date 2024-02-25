/**
 * MasterMind
 * A program for the main model of the MasterMind game.
* @Author: Huzaifa A.
 * Since: January 20th
 */

 package MasterM;

 import javax.swing.*;
 import java.awt.*;
 import java.util.Arrays;
 import java.util.Random;
 
 public class MasterMind extends Object {
     private MasterMindGUI view;
     private boolean enterGame;
     private boolean startGame;
     private boolean winGame;
     private boolean clearRow;
     private char[][] gameMap;
     private boolean rowsFull;
     private char[] secretCode;
     private char[] userValues;
     private Random random;
     private boolean updateHint;
 
     /**
      * Constructor for the MasterMind class.
      */
     public MasterMind() {
         super();
         startGame = false;
         rowsFull = false;
         winGame = false;
         gameMap = new char[8][4];
         secretCode = new char[4];
         userValues = new char[] { ' ', ' ', ' ', ' ' };
         random = new Random();
     }
 
     /**
      * Marks that the user has entered the game.
      */
     public void enterGame() {
         enterGame = true;
         this.update();
     }
 
     /**
      * Starts or restarts the game, initializing the secret code.
      */
     public void startGame() {
         if (!startGame) {
             startGame = true;
         } else if (startGame) {
             this.clearGrid();
         }
         initializeSecretCode();
         outputSecretCode();
         update();
     }
 
     /**
      * Clears the game grid and resets the hint panels.
      */
     public void clearGrid() {
         for (int x = 0; x < 7; x++) {
             delete();
         }
         view.deleteHints();
     }
 
     /**
      * Initializes the secret code with random colors.
      */
     private void initializeSecretCode() {
         for (int s = 0; s < secretCode.length; s++) {
             secretCode[s] = getRandomColor();
         }
     }
 
     /**
      * Generates a random color for the secret code.
      */
     private char getRandomColor() {
         char[] registeredColors = { 'B', 'G', 'O', 'P', 'R', 'Y' };
         return registeredColors[random.nextInt(registeredColors.length)];
     }
 
     /**
      * Registers the user's move, updating the game map and user values.
      */
     public void registerMove(char color) {
         if (!startGame) {
             System.out.println("Game has not started yet.");
             return;
         }
 
         int currentRow = getCurrentRow();
         for (int col = 0; col < gameMap[currentRow].length; col++) {
             if (gameMap[currentRow][col] == '\0') {
                 gameMap[currentRow][col] = color;
                 rowsFull = (col == gameMap[currentRow].length - 1);
                 userValues[col] = color;
                 break;
             }
         }
 
         update();
     }
 
     /**
      * Checks if the user has won the game, and updates the hint panels accordingly.
      */
     public void checkWin() {
         int currentRow = getCurrentRow();
 
         if (currentRow == -1) {
             System.out.println("All rows are filled. Cannot check win.");
             return;
         } else if (Arrays.equals(userValues, secretCode)) {
             winGame = true;
             System.out.println("You win!");
             // Add any additional actions you want to perform on winning
         }
 
         checkCorrectPositions();
         checkCorrectCharacters();
 
         updateHint = true;
 
         update();
     }
 
     /**
      * Checks for correct positions and updates the hint panels.
      */
     private void checkCorrectPositions() {
         int correctPosCount = 0;
         outputUserValues();
 
         for (int i = 0; i < userValues.length; i++) {
             if (userValues[i] == secretCode[i]) {
                 correctPosCount++;
             }
         }
         correctPos(correctPosCount);
     }
 
     /**
      * Checks for correct characters and updates the hint panels.
      */
     private void checkCorrectCharacters() {
         int correctCharCount = 0;
 
         for (int i = 0; i < userValues.length; i++) {
             if (userValues[i] != secretCode[i] && containsColor(secretCode, userValues[i])) {
                 correctCharCount++;
             }
         }
 
         correctChar(correctCharCount);
     }
 
     /**
      * Deletes the last row in the game grid.
      */
     public void delete() {
         if (!startGame) {
             System.out.println("Game has not started yet.");
             return;
         }
 
         for (int row = gameMap.length - 1; row >= 0; row--) {
             boolean rowEmpty = true;
             for (int col = 0; col < gameMap[row].length; col++) {
                 if (gameMap[row][col] != '\0') {
                     rowEmpty = false;
                     break;
                 }
             }
 
             if (!rowEmpty) {
                 Arrays.fill(gameMap[row], '\0');
                 rowsFull = false;
                 break;
             }
         }
 
         clearRow = true;
         update();
     }
 
     /**
      * Gets the current row in the game grid.
      *
      * @return The current row index.
      */
     public int getCurrentRow() {
         for (int row = 0; row < gameMap.length; row++) {
             boolean rowComplete = true;
             for (int col = 0; col < gameMap[row].length; col++) {
                 if (gameMap[row][col] == '\0') {
                     rowComplete = false;
                     break;
                 }
             }
             if (!rowComplete) {
                 return row;
             }
         }
         return -1;
     }
 
     /**
      * Sets the associated GUI for this model.
      *
      * @param currentView The MasterMindGUI object.
      */
     public void setGUI(MasterMindGUI currentView) {
         this.view = currentView;
     }
 
     /**
      * Gets the game map.
      *
      * @return The game map.
      */
     public char[][] getGameMap() {
         return gameMap;
     }
 
     /**
      * Checks if a color is present in the given array.
      *
      * @param array The array to check.
      * @param color The color to find.
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
      * Updates the GUI with the correct position count.
      *
      * @param count The count of correct positions.
      */
     private void correctPos(int count) {
         view.correctPos(count);
     }
 
     /**
      * Updates the GUI with the correct character count.
      *
      * @param count The count of correct characters.
      */
     private void correctChar(int count) {
         view.correctChar(count);
     }
 
     // ... (existing code)
 
     /**
      * Updates the GUI and resets flags after changes.
      */
     public void update() {
         view.update(clearRow);
         clearRow = false;
         updateHint = false;
     }
 
     /**
      * Outputs the secret code to the console.
      */
     private void outputSecretCode() {
         System.out.print("Secret Code: ");
         for (char code : secretCode) {
             System.out.print(code + " ");
         }
         System.out.println();
     }
 
     /**
      * Outputs the user values to the console.
      */
     private void outputUserValues() {
         System.out.print("User Values: ");
         for (char value : userValues) {
             System.out.print(value + " ");
         }
         System.out.println();
     }
 
     /**
      * Gets whether an update hint is needed.
      *
      * @return True if an update hint is needed, false otherwise.
      */
     public boolean getUpdateHint() {
         return (updateHint);
     }
 
     /**
      * Gets the secret code.
      *
      * @return The secret code.
      */
     public char[] getSecretCode() {
         return secretCode;
     }
 
     /**
      * Gets the user values.
      *
      * @return The user values.
      */
     public char[] getUserValues() {
         return userValues;
     }
 
     /**
      * Gets whether the user has entered the game.
      *
      * @return True if the user has entered the game, false otherwise.
      */
     public boolean getEnterGame() {
         return enterGame;
     }
 
     /**
      * Gets whether the user has won the game.
      *
      * @return True if the user has won, false otherwise.
      */
     public boolean getWinGame() {
         return winGame;
     }
 }
 