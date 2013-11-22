package numbrixgame.gui.leftdisplay;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import numbrixgame.numbrix;
import numbrixgame.system.Validator;

/***
 * FinishActionListener will define the action listener to be used by the finish button.
 * It should request from system a message that states the state of the grid and then return it
 * to the player via the bottom text box.
 */

public class FinishActionListener implements ActionListener
{
	/*************************** Class Methods ***************************/
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Get the grid and pass it to the validator.
		Validator.State state = numbrix.system().verify(numbrix.gui().getTable().getGrid());
		
		// Display the message
		numbrix.gui().printMessage(state.string());
		
	} /* end actionPerformed method */
	
} /* end FinishActionListener. */
