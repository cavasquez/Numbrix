package numbrixgame.gui.leftdisplay;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import numbrixgame.numbrix;
import numbrixgame.system.Log;
import numbrixgame.system.solver.Solver;

/**
 * Abstract class that will be used by the action listeners implemented when the computer
 * is chosen as the player.
 * @author Carlos Vasquez
 *
 */

public abstract class ComputerActionListener implements ActionListener
{
	/************************************ Class Attributes *************************************/
	protected static int next;
	protected static int logSize;
	protected static Integer[][] grid;
	protected static  ArrayList<Log> historyLog;
	protected static String time;
	protected static Solver solver;
	
	/************************************ Class Methods *************************************/
	public ComputerActionListener(Solver solver, boolean init)
	{
		ComputerActionListener.solver = solver;
		if(ComputerActionListener.solver.getSolutionFound()) ComputerActionListener.time = "Solution found. ";
		else ComputerActionListener.time = "Solution not found. ";
		ComputerActionListener.time += "Time consumed was " + solver.getTimeElsapsed() + " (MM:SS:mm). ";
		ComputerActionListener.next = 0;
		
		
		if(init == true)
		{
			// Keep a record of the solvers history
			ComputerActionListener.historyLog = new ArrayList<Log>();
			ComputerActionListener.historyLog.addAll(numbrix.system().getHistoryLog());
			
			// Keep a copy of the solved grid
			Integer[][] tempGrid = numbrix.system().getGrid();
			ComputerActionListener.grid = new Integer[numbrix.system().getGridSize()][];
			for(int i = 0; i < numbrix.system().getGridSize(); i++)
			{
				ComputerActionListener.grid[i] = new Integer[numbrix.system().getGridSize()];
				for(int j = 0; j < numbrix.system().getGridSize(); j++)
				{
					ComputerActionListener.grid[i][j] = tempGrid[i][j];
				} /* end for loop */
			} /* end for loop */
			
			// Remove old history
			numbrix.gui().changeHistory("");
			
		} /* end if */
		
		// Update Message
		String message = ComputerActionListener.time + " To step through the solution, click next. To see the solution, click complete.";
		numbrix.gui().printMessage(message);
		
	} /* end ComputerActionListener */
	
} /* end ComputerActionListener */
