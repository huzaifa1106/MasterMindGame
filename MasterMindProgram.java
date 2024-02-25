/**
 * MasterMindProgram
 * A program for initializing the MasterMind game and GUI.
* @Author: Huzaifa A.
 * @since January 20th
 */
package MasterM;

import javax.swing.*;

public class MasterMindProgram {
    /**
     * The main method that initializes the MasterMind game and GUI.
     * 
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        // Create a new JFrame for the game
        JFrame frame = new JFrame();

        // Create a new instance of the MasterMind class to hold the game data
        MasterMind data = new MasterMind();

        // Create a new instance of the MasterMindGUI class to display the game
        MasterMindGUI view = new MasterMindGUI(data);

        // Configure the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);
    }
}
