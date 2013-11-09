package numbrixgame.system.solver;

import java.util.LinkedList;
import numbrixgame.system.NumbrixSystem;

public abstract class SearchMethod 
{
	/************************************ Class Constant *************************************/
	public static enum Direction
	{
		BOTTOM(0, -1, 1),
		TOP(0, 1, 0),
		LEFT(-1, 0, 3),
		RIGHT(1, 0, 2),
		START(0, 0, -1);
		
		protected final int x;
		protected final int y;
		/* Actually the position of the opposite direction in the grid made by makeGrid */
		protected final int position; 
		
		Direction(int x, int y, int position)
		{
			this.x = x;
			this.y = y;
			this.position = position;
		} /* end constructor */
		
	} /* end Player enum */
	
	/************************************ Class Attributes *************************************/
	protected static NumbrixSystem system;
	protected static Snake snake;
	
	/************************************ Class Methods *************************************/
	public static void setSystem(NumbrixSystem system)
	{
		SearchMethod.system = system;
	} /* end setSystem method */
	
	public static void setSnake(Snake snake)
	{
		SearchMethod.snake = snake;
	} /* end setSnake method */
	
	/**
	 * Returns whether or not the provided coordinates are legal
	 * @param x	the x coordinate
	 * @param y	the y coordinate
	 * @return	the legality of the coordinates
	 */
	public boolean legal(int x, int y)
	{
		boolean safe = false;
		if(x >=0 && x < HeuristicSearch.system.getGridSize() && y >= 0 && y < HeuristicSearch.system.getGridSize())
		{
			safe = true;
		} /* end if */
		return safe;
	} /* end safe method */
	
	public boolean emptyAndLegal(int x, int y)
	{
		return (this.legal(x, y) && (SearchMethod.system.getVal(x, y) == null));
	} /* end emptyAndLegal method */
	
	public boolean fullAndLegal(int x, int y)
	{
		return (this.legal(x, y) && (SearchMethod.system.getVal(x, y) != null));
	} /* end fullAndLegal method */
	
	/**
	 * Returns a stack of unique directions
	 * @return	a stack of unique directions
	 */
	protected LinkedList<Direction> makeDirectionStack()
	{
		LinkedList<Direction> directions = new LinkedList<Direction>();
		
		directions.add(Direction.BOTTOM);
		directions.add(Direction.TOP);
		directions.add(Direction.LEFT);
		directions.add(Direction.RIGHT);
		
		return directions;
	} /* end makeDirectionStack method */
	
	/**
	 * Returns a stack of unique directions with the provided direction omitted
	 * @param remove	the direction to omit
	 * @return			the stack of unique directions with the provided direction omitted
	 */
	protected LinkedList<Direction> makeDirectionStack(Direction remove)
	{
		LinkedList<Direction> directions = this.makeDirectionStack();
		directions.remove(remove.position);
		return directions;
	} /* end overloaded makeDirectionStack method */
	
	
} /* end SeachMethod class */
