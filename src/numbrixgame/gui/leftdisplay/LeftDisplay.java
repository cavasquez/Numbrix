package numbrixgame.gui.leftdisplay;

import javax.swing.JButton;
import javax.swing.JToolBar;

import numbrixgame.system.NumbrixSystem;

/*****************************************************************************************************
 * LeftDisplay will create a toolbar on the left dislpay providing the player options. The options
 * provided will depend on the type of user (player or computer).
 *****************************************************************************************************/

public class LeftDisplay extends JToolBar
{
	/*************************** Class Cosntants ***************************/
	private static final long serialVersionUID = 7289612130330416712L;
	
	/*************************** Class Methods ***************************/
	public LeftDisplay(NumbrixSystem.Player playerType)
	{
		// Initialize the correct type
		switch(playerType)
		{
			case HUMAN:
				initializeHuman();
				break;
			case COMPUTER:
				initializeComputer();
				break;
			default: /* Do nothing */
				break;
		} /* end switch */
	} /* end constructor */
	
	/*------------------ Private methods ------------------*/
	private final void initializeHuman()
	{// Give the human a "finish" button to verify if the completed game is correct
		JButton finish = new JButton("Finish");
		finish.setToolTipText("Click to verify if grid is correct");
		finish.addActionListener(new FinishActionListener());
		this.add(finish);
	} /* end initializeHuman method */
	
	private final void initializeComputer()
	{
		
	} /* end initializeComputer method */

} /* end LeftDisplay class */
