package numbrixgame.gui.leftdisplay;

import java.awt.event.ActionEvent;
import numbrixgame.numbrix;
import numbrixgame.system.solver.Solver;

/**
 * CompleteActionListener will populate the board with the correct solution as
 * found by the Solver and update the history log with every move made by the solver
 * to achieve the solution. 
 * @author Carlos Vasquez
 *
 */

public class CompleteActionListener extends ComputerActionListener
{
	/************************************ Class Methods *************************************/
	public CompleteActionListener(Solver solver)
	{
		super(solver);
	} /* end constructor */
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(ComputerActionListener.next != 0) numbrix.system().resetData();
		
		// Update GUI
		numbrix.system().complete(ComputerActionListener.grid, ComputerActionListener.historyLog);
		numbrix.gui().printMessage(ComputerActionListener.time);
		numbrix.gui().changeHistory(numbrix.system().getHistory());
		
	} /* end actionPerformed method */

} /* end CompleteActionListener class */
