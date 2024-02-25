/**
 * DeleteController
 * A program for registering the delete button
* @Author: Huzaifa A.
 * @since January 20th
 */
package MasterM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class DeleteController implements ActionListener {

    private MasterMind model;
    private JButton deleteButton;

    /**
     * Constructor for the Controller that retrieves button input from the GUI
     * 
     * @param someModel - Helps set the model to send the information to afterwards
     * @param aButton   - Sets the target button
     */
    public DeleteController(MasterMind someModel, JButton aButton) {
        this.model = someModel;
        this.deleteButton = aButton;
    }

    /**
     * A void method used to get the data from the button and send it back to the model
     */
    public void actionPerformed(ActionEvent e) {
  
        // Call the model's delete method
        model.delete();
    }

}
