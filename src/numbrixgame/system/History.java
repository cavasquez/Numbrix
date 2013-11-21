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
	private String incrementLog;				// Increment log
	
	/************************************ Class Methods *************************************/
	public History(int gridSize, boolean[][] staticVals)
	{
		this.historyLog = new ArrayList<Log>();
		this.staticVals = staticVals;
		this.gridSize = gridSize;
		this.incrementLog = "";
		
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
	
	public History(int gridSize, boolean[][] staticVals, ArrayList<Log> historyLog, boolean[][] hasVal)
	{
		this(gridSize, staticVals);
		this.historyLog.addAll(historyLog);
		this.hasVal = hasVal;
	} /* end overloaded constructor */
	
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
			changes += this.logToString(log);
		} /* end for loop */
		
		return changes;
	} /* end getLog method */
	
	/**
	 * Used by a process that knows the log has been updated and wishes to update the increment log string
	 */
	public void incrementLog()
	{/* Add the last change */
		this.incrementLog += this.logToString(this.historyLog.get(this.historyLog.size()-1));
	} /* end incrementLog method */
	
	private String logToString(Log log)
	{
		String change = "";
		switch(log.getChange())
		{// Here, we subtract the gridsize from y because the graph is "inverted"
			case ADD:
				change = Integer.toString(log.getVal()) + " was added to (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(log.getY()) + ")\n";
				break;
			case DELETE:
				change = "The value at (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(log.getY()) + ") was deleted\n";
				break;
			case MODIFY:
				change = "The value at (" + Integer.toString(log.getX()) + ", " + Integer.toHexString(log.getY()) + ") was changed to " + Integer.toString(log.getVal()) +"\n";
				break;
			default:
				break;
		} /* end switch */
		return change;
	} /* end changeToString */
	
	public String getIncrementLog()
	{
		return this.incrementLog;
	} /* end getIncrementLog methoe */
	
	public ArrayList<Log> getHistoryLog()
	{
		return this.historyLog;
	} /* end getHistoryLog method */
	
	public int getSize()
	{
		return this.historyLog.size();
	} /* end getSize method */
	
} /* end History class */
