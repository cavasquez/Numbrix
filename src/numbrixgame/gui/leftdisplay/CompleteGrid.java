package numbrixgame.gui.leftdisplay;

import java.awt.event.ActionEvent;
import numbrixgame.numbrix;
import numbrixgame.system.solver.Solver;

/**
 * Displays the complete grid without posting history
 * @author Carlos Vasquez
 *
 */
public class CompleteGrid extends ComputerActionListener 
{

	public CompleteGrid(Solver solver) 
	{
		super(solver, false);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(ComputerActionListener.next != 0) numbrix.system().resetData();
		
		// Update GUI
		numbrix.system().complete(ComputerActionListener.grid, ComputerActionListener.historyLog);
		numbrix.gui().printMessage(ComputerActionListener.time + " This may take some time to load.");
		numbrix.gui().changeHistory(numbrix.system().getHistory());
		
	} /* end actionPerformed method */

}
