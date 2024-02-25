/**
 * CheckButtonController
 * A program for registering the checkButton
 * Author: Huzaifa A
 * Since: January 20th
 */
package MasterM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CheckButtonController implements ActionListener {

    private MasterMind model;
    private JButton checkWin;

    /**
     * Constructor for the Controller that retrieves button input from the GUI
     * @param someModel - Helps set the model to send the information to afterwards
     * @param aButton - Sets the target button
     */
    public CheckButtonController(MasterMind someModel, JButton aButton) {
        this.model = someModel;
        this.checkWin = aButton;
    }

    /**
     * A void method used to get the data from the button and send it back to the model
     */
    public void actionPerformed(ActionEvent e) {
        model.checkWin();
    }
}