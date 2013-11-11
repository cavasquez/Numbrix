package numbrixgame.system;

import java.util.ArrayList;

/*****************************************************************************************************
 * History will keep track of all the player made changes made to the grid.
 ******************************************************************************************************/

public class History 
{
	/************************************ Class Constants *************************************/
	public static enum Modification
	{
		ADD(),
		DELETE(),
		MODIFY();
	} /* end Modification enum */
	
	/************************************ Class Attributes *************************************/
	private ArrayList<Log> historyLog;			// The history
	private boolean[][] staticVals;				// Grid indicating the cells that start populated
	private boolean[][] hasVal;					// Grid that indicates if a grid has a value (it ignores staticVals) 
	private int gridSize;						// Size of the grid
	
	/************************************ Class Methods *************************************/
	public History(int gridSize, boolean[][] staticVals)
	{
		this.historyLog = new ArrayList<Log>();
		this.staticVals = staticVals;
		this.gridSize = gridSize;
		
		// initialize hasVal
		hasVal = new boolean[gridSize][];
		for(int i = 0; i < gridSize; i++)
		{
			hasVal[i] = new boolean[gridSize];
			for(int j = 0; j < gridSize; j++)
			{
				hasVal[i][j] = false;
			} /* end for loop */
		} /* end for loop */
		
	} /* end History class */
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param newVal
	 */
	public void logChange(int row, int column, Integer newVal)
	{
		Log newLog = null;
		
		if(this.staticVals[row][column]) {/* do nothing if original data is changed. this happens at start. */}
		else if(newVal == null && hasVal[row][column])
		{// Check if deletion occurred but do not log deletions of deletions
			newLog = new Log(column, row, newVal, Modification.DELETE);
			hasVal[row][column] = false;
		} /* end if */
		else if(hasVal[row][column])
		{// check if modification was made.
			newLog = new Log(column, row, newVal, Modification.MODIFY);
		} /* end else if */
		else if(newVal != null)
		{// an addition has occurred (unless null. do not log random deletions)
			newLog = new Log(column, row, newVal, Modification.ADD);
			hasVal[row][column] = true;
		} /* end else */
			
		if(newLog != null) this.historyLog.add(newLog);
	} /* end logChange method */
	
	public String getLog()
	{// Create a string of the changes
		String changes = "";
		
		for(Log log : historyLog)
		{
			switch(log.getChange())
			{// Here, we subtract the gridsize from y because the graph is "inverted"
				case ADD:
					changes += Integer.toString(log.getVal()) + " was added to (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(log.getY()) + ")\n";
					break;
				case DELETE:
					changes += "The value at (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(log.getY()) + ") was deleted\n";
					break;
				case MODIFY:
					changes += "The value at (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(log.getY()) + ") was changed to " + Integer.toString(log.getVal()) +"\n";
					break;
				default:
					break;
			} /* end switch */
		} /* end for loop */
		
		return changes;
	} /* end getLog method */
	
	public int getSize()
	{
		return this.historyLog.size();
	} /* end getSize method */
	
} /* end History class */
