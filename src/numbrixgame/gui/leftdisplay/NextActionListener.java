package numbrixgame.gui.leftdisplay;

import java.awt.event.ActionEvent;

import numbrixgame.numbrix;
import numbrixgame.system.Log;
import numbrixgame.system.solver.Solver;

/**
 * The NextActionListener will define the functionality of the Next button. It will 
 * allow the user to step through the Solvers steps to see the approach the Solver
 * took to obtain the solution. It will update the history with the next step and
 * update the BottomDisplay with the step count and completion time.
 * @author Carlos Vasquez
 *
 */

public class NextActionListener extends ComputerActionListener
{
	/************************************ Class Attributes *************************************/
	private int totalMoves;
	
	/************************************ Class Methods *************************************/
	public NextActionListener(Solver solver)
	{
		super(solver, true);
		this.totalMoves = ComputerActionListener.historyLog.size();
	} /* end NextActionListener */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// If no moves have been made yet, numbrix has retained the old data
		if(ComputerActionListener.next == 0) numbrix.system().resetData();
		if(next < this.totalMoves)
		{
			Log log = ComputerActionListener.historyLog.get(next);
			numbrix.system().modifyGrid(log.getX(), log.getY(), log.getVal());
			numbrix.gui().revalidateTable();
			String message = ComputerActionListener.time + " Currently at move " + (next + 1) + " of " + this.totalMoves;
			numbrix.gui().printMessage(message);
			numbrix.gui().changeHistory(numbrix.system().getIncrementLog());
			ComputerActionListener.next++;
		} /* end if */
		
	} /* end actionPerformed method */

} /* end NextActionListener class */
