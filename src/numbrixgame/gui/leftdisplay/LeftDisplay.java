package numbrixgame.gui.leftdisplay;

import javax.swing.JButton;
import javax.swing.JToolBar;
import numbrixgame.numbrix;
import numbrixgame.system.NumbrixSystem;

/**
 * LeftDisplay will create a toolbar on the left dislpay providing the player options. The options
 * provided will depend on the type of user (player or computer).
 */

public class LeftDisplay extends JToolBar
{
	/*************************** Class Cosntants ***************************/
	private static final long serialVersionUID = 7289612130330416712L;
	
	/*************************** Class Methods ***************************/
	/**
	 * Creates the appropriate JButton(s) based on the playerType
	 * @param playerType	type of player (COMPUTER or HUMAN)
	 */
	public void initialize(NumbrixSystem.Player playerType)
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
	} /* end initialize method */
	
	/*------------------ Private methods ------------------*/
	/**
	 * Creates a Finish button that will check the board for completeness and correctness
	 */
	private final void initializeHuman()
	{// Give the human a "finish" button to verify if the completed game is correct
		JButton finish = new JButton("Finish");
		finish.setToolTipText("Click to verify if grid is correct");
		finish.addActionListener(new FinishActionListener());
		this.add(finish);
	} /* end initializeHuman method */
	
	/**
	 * Creates a "NextMove" and a "Complete" button that will display the next
	 * move made by the solver or display the completed board and every move made
	 * by the solver (respectively)
	 */
	private final void initializeComputer()
	{// Give the user an option to complete the table and see the entire board or step through
		// Next must be created first so it can init ComputerAcitonListener
		JButton next = new JButton("Next Move");
		next.setToolTipText("Click to step through to the next move in the solution.");
		next.addActionListener(new NextActionListener(numbrix.system().getSolver()));
		this.add(next);
		
		JButton complete = new JButton("Complete");
		complete.setToolTipText("Click to see the completed board and the history of the steps to achieve completion.");
		complete.addActionListener(new CompleteActionListener(numbrix.system().getSolver()));
		this.add(complete);
		
		JButton completeGrid = new JButton("Complete Grid");
		completeGrid.setToolTipText("Click to see the completed board without the history of the steps to achieve completion. This will save time.");
		completeGrid.addActionListener(new CompleteGrid(numbrix.system().getSolver()));
		this.add(completeGrid);
		
	} /* end initializeComputer method */

} /* end LeftDisplay class */
