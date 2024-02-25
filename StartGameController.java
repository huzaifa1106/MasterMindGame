/**
 * StartGameController
 * A program for starting the program
* @Author: Huzaifa A.
 * Since: January 20th
 */
package MasterM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class StartGameController implements ActionListener {

    private MasterMind model;
    private JButton startButton;

    /**
     * Constructor for the Controller that retrieves button input from the GUI
     * 
     * @param someModel - Helps set the model to send the information to afterwards
     * @param aButton   - Sets the target button
     */
    public StartGameController(MasterMind someModel, JButton aButton) {
        this.model = someModel;
        this.startButton = aButton;
    }

    /**
     * A void method used to get the data from the button and send it back to the
     * model
     */
    public void actionPerformed(ActionEvent e) {
        model.startGame();
 
    }
}