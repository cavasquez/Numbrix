package numbrixgame.system;

import numbrixgame.numbrix;
import numbrixgame.system.solver.SearchMethod;


/*****************************************************************************************************
 * Validator will check the grid for correctness. It well then return a constant pertaining to the state
 * of the board.
 *****************************************************************************************************/

public class Validator 
{
	/************************************ Class Constants *************************************/
	private static int X = 1;
	private static int Y = 0;
	public static enum State
	{
		CORRECT ("Congratulations, the grid is correct! To start a new game, go to File -> New Game. To play again, go to File -> Reset"),
		INCORRECT_GRID("The input provided does not correctly complete the game."),
		INCORRECT_ELEMENT ("There is an empty cell or a cell is invalid. Please populate all cells with an integer value."),
		INCORRECT_SIZE("There is a cell that is too big or too small.");
		
		private final String message;
		
		State(String message)
		{
			this.message = message;
		} /* end TargetCount index */
		
		public String string()
		{
			return message;
		} /* end size method */
		
	} /* end Player enum */
	
	/************************************ Class Attributes *************************************/
	private State state;	// The state of correctness of the grid
	
	/************************************ Class Methods *************************************/
	public Validator(int gridSize, Integer[][] grid)
	{
		this.validate(gridSize, grid);
	} /* end constructor */
	
	/*------------------ Public methods ------------------*/
	public State getState()
	{
		return state;
	} /* end getState method */
	
	public static State validateInput(Integer value, int gridSize)
	{
		State check = null;
		
		// Check for empty or non integer values
		if(value == null) check = State.INCORRECT_ELEMENT;
		
		// Check for out of bound elements
		else if(value < 1 || value > (gridSize*gridSize)) check = State.INCORRECT_SIZE;
		
		return check;
	} /* end validateInput metho */
	
	/*------------------ Private methods ------------------*/
	private final void validate(int gridSize, Integer[][] grid)
	{// Check the grid for correctness
		// First, check for null or out of bound values aswell as the location of the first value(1)
		boolean correct = true;
		this.state = null;
		int[] start = new int[2];
		
		for(int y = gridSize-1; y >= 0 && correct; y--)
		{
			for(int x = 0; x < gridSize && correct; x++)
			{
				// Check for empty or non integer values
				if(grid[y][x] == null) this.state = State.INCORRECT_ELEMENT;
				
				// Check for out of bound elements
				else if(grid[y][x] < 1 || grid[y][x] > (gridSize*gridSize)) this.state = State.INCORRECT_SIZE;
				
				else if(grid[y][x] == 1)
				{// Find location of 1
					start[X] = x;
					start[Y] = y;
				} /* end else if */
				
				if(this.state != null) correct = false;
			} /* end for loop */
		} /* end for loop */
		if(correct) this.state = trace(gridSize, start, grid);
	} /* end validate method */
	
	private final State trace(int gridSize, int[] pos, Integer[][] grid)
	{// trace is the function that will check the grid for actual game completion
		// Note that pos should start with the position of element 1
		State returner = State.CORRECT; // assume correctness

		int curVal = 1;
		boolean done = false;
		int terminal = gridSize * gridSize;
		
		while(!done)
		{
			/* First, check for terminal case */
			if(curVal == terminal) done = true; // Check to see if all values have been found
			else
			{
				// Find where the next value is
				pos = findNext(pos, curVal + 1, gridSize, grid);
				
				// Check if next value was found
				if (pos[X] == -1)
				{// next value not found in correct order
					done = true;
					returner = State.INCORRECT_GRID;
				} /* end if */
				
				// Update values
				curVal++;
			} /* end else */
			
		} /* end while loop */
		
		return returner;
	} /* end trace method */
	
	private final int[] findNext(int[] pos, int nextVal, int gridSize, Integer[][] grid)
	{
		int[] returner = {-1,-1};
		
		// Look at all the possible directions
		if(checkVal(pos[X] + SearchMethod.Direction.LEFT.x, pos[Y] + SearchMethod.Direction.LEFT.y, nextVal, gridSize, grid))
		{
			returner[X] = pos[X] + SearchMethod.Direction.LEFT.x;
			returner[Y] = pos[Y] + SearchMethod.Direction.LEFT.y;
		} /* end if */
		else if (checkVal(pos[X] + SearchMethod.Direction.RIGHT.x, pos[Y] + SearchMethod.Direction.RIGHT.y, nextVal, gridSize, grid))
		{
			returner[X] = pos[X] + SearchMethod.Direction.RIGHT.x;
			returner[Y] = pos[Y] + SearchMethod.Direction.RIGHT.y;
		} /* end else if */
		else if (checkVal(pos[X] + SearchMethod.Direction.TOP.x, pos[Y] + SearchMethod.Direction.TOP.y, nextVal, gridSize, grid))
		{
			returner[X] = pos[X] + SearchMethod.Direction.TOP.x;
			returner[Y] = pos[Y] + SearchMethod.Direction.TOP.y;
		} /* end else if */
		else if (checkVal(pos[X] + SearchMethod.Direction.BOTTOM.x, pos[Y] + SearchMethod.Direction.BOTTOM.y, nextVal, gridSize, grid))
		{
			returner[X] = pos[X] + SearchMethod.Direction.BOTTOM.x;
			returner[Y] = pos[Y] + SearchMethod.Direction.BOTTOM.y;
		} /* end else if */
		
		return returner;
	} /* end findNext method */
	
	private final boolean checkVal(int x, int y, int val, int gridSize, Integer[][] grid)
	{// Check to see if the coordinates are correct and if the provided val is at the coordinate
		boolean found = false;
		// First, check for bounds
		if ( (x < 0 || x  >= gridSize) || (y < 0 || y >= gridSize) ) found = false;
		
		// next, check if found
		else if (grid[y][x] == val) found = true;
		return found;
	} /* end checkVal method */
	
} /* end Validator class */
