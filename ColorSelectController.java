/**
 * ColorSelectController
 * A program for registering the color buttons 
 * Author: Huzaifa A.
 * Since: January 20th
 */
package MasterM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ColorSelectController implements ActionListener {

    private MasterMind model;
    private JButton colorButton;
    private char color;

    /**
     * Constructor for the Controller that retrieves button input from the GUI
     * @param someModel - Helps set the model to send the information to afterwards
     * @param aButton - Sets the target button
     * @param c - The selected color represented by a character
     */
    public ColorSelectController(MasterMind someModel, JButton aButton, char c) {
        this.model = someModel;
        this.colorButton = aButton;
        this.color = c;
    }

    /**
     * A void method used to get the data from the button and send it back to the model
     */
    public void actionPerformed(ActionEvent e) {
        model.registerMove(color);
    }
}