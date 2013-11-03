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
	
	public void logChange(int x, int y, Integer newVal)
	{
		Log newLog = null;
		
		if(this.staticVals[x][y]) {/* do nothing if original data is changed. this happens at start. */}
		else if(newVal == null && hasVal[x][y])
		{// Check if deletion occurred but do not log deletions of deletions
			newLog = new Log(x, y, newVal, Modification.DELETE);
			hasVal[x][y] = false;
		} /* end if */
		else if(hasVal[x][y])
		{// check if modification was made.
			newLog = new Log(x, y, newVal, Modification.MODIFY);
		} /* end else if */
		else if(newVal != null)
		{// an addition has occurred (unless null. do not log random deletions)
			newLog = new Log(x, y, newVal, Modification.ADD);
			hasVal[x][y] = true;
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
					changes += Integer.toString(log.getVal()) + " was added to (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(gridSize - log.getY()) + ")\n";
					break;
				case DELETE:
					changes += "The value at (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(gridSize - log.getY()) + ") was deleted\n";
					break;
				case MODIFY:
					changes += "The value at (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(gridSize - log.getY()) + ") was changed to " + Integer.toString(log.getVal()) +"\n";
					break;
				default:
					break;
			} /* end switch */
		} /* end for loop */
		
		return changes;
	} /* end getLog method */
	
} /* end History class */