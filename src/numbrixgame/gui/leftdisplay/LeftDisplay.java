package numbrixgame.gui.leftdisplay;

import javax.swing.JButton;
import javax.swing.JToolBar;
import numbrixgame.numbrix;
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
	{// Give the user an option to complete the table and see the entire board or step through
		JButton next = new JButton("Next Move");
		next.setToolTipText("Click to step through to the next move in the solution.");
		next.addActionListener(new NextActionListener(numbrix.system().getSolver()));
		this.add(next);
		
		JButton complete = new JButton("Complete");
		complete.setToolTipText("Click to see the completed board and the history of the steps to achieve completion.");
		complete.addActionListener(new CompleteActionListener(numbrix.system().getSolver()));
		this.add(complete);
		
	} /* end initializeComputer method */

} /* end LeftDisplay class */
