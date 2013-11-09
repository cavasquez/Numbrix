package numbrixgame.system.solver;

/**
 * Data structure that holds unit, x, and y value
 * @author Carlos
 *
 */

public class Triple implements Comparable<Triple>
{
	/************************************ Class Attributes *************************************/
	private int value;
	private int x;
	private int y;
	
	/************************************ Class Methods *************************************/
	/**
	 * A data structure that contains a value, x, and y coordinate
	 * @param value	value of triple	
	 * @param x		x coordinate of triple
	 * @param y		y coordinate of triple
	 */
	public Triple(int value, int x, int y)
	{
		this.value = value;
		this.x = x;
		this.y = y;
	} /* end constructor */
	
	public int getValue()
	{
		return this.value;
	} /* end getValue method */
	
	public int getX()
	{
		return this.x;
	} /* end returnX */
	
	public int getY()
	{
		return this.y;
	} /* end getY method */

	/**
	 * Compare in ascending order based on value
	 */
	@Override
	public int compareTo(Triple triple) 
	{
		return this.value - triple.getValue();
	} /* end compareTo method */
	
	@Override
	public String toString()
	{
		String returner = this.value + ": (" + this.x + "," + this.y + ")";
		return returner;
	} /* end toString method */
	
} /* end Triple class */
