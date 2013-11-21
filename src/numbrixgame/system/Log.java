package numbrixgame.system;

/*****************************************************************************************************
 * Log is a data structure that will be used by history to keep track of changes to the grid.
 ******************************************************************************************************/

public class Log 
{
	/************************************ Class Attributes *************************************/
	private int X;							// X position of changed cell
	private int Y;							// Y position of changed cell
	private Integer val;					// New value of cell;
	private History.Modification change;	// Type of change made
	
	/************************************ Class Methods *************************************/
	/**
	 * Constructor
	 * @param x			the x value of the log
	 * @param y			the y value of the log
	 * @param val		the value of the log
	 * @param change	the type of change
	 */
	public Log(int x, int y, Integer val, History.Modification change)
	{
		this.X = x;
		this.Y = y;
		this.val = val;
		this.change = change;
	} /* end constructor */
	
	/*------------------ Getter methods ------------------*/
	public int getX()
	{
		return this.X;
	} /* end getX method */
	
	public int getY()
	{
		return this.Y;
	} /* end getY method */
	
	public Integer getVal()
	{
		return this.val;
	} /* end getVal method */
	
	public History.Modification getChange()
	{
		return this.change;
	} /* end getChange method */
	
//	@Override
//	public String toString()
//	{
//		String returner = "";
//		returner = "X: " + this.X + " Y: " + this.Y + " val: " + this.val + " change: " + this.change; 
//		return returner;
//	} /* end toString method */
	
} /* end Log class */
