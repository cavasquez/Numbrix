package numbrixgame.system;

import java.io.File;
import java.util.ArrayList;

import numbrixgame.numbrix;
import numbrixgame.system.solver.Solver;

/*****************************************************************************************************
 * NumbrixSystem will take care of the back end for Numbrix.
 ******************************************************************************************************/

public class NumbrixSystem 
{
	/************************************ Class Constants *************************************/
	public static enum Player
	{
		HUMAN ("human"),
		COMPUTER ("computer");
		
		private final String message;
		
		Player(String message)
		{
			this.message = message;
		} /* end TargetCount index */
		
		public String string()
		{
			return message;
		} /* end size method */
		
	} /* end Player enum */
	
	/************************************ Class Attributes *************************************/
	protected int gridSize;				// The size of the grid
	protected boolean[][] staticData;	// a grid that tells us if a position has static(provided) data
	protected Integer[][] grid;			// a grid that holds the initial values in the grid
	protected Player player;			// Whos playing
	protected File file;				// Initial file
	protected History history;			// The history of the game
	protected int numOfObjects;			// Total number of objects in the grid
	private Solver solver;				// The solver
	
	/************************************ Class Methods *************************************/
	public NumbrixSystem() {/* Do nothing */}
	
	public void setup(Player player, File file)
	{// Setup is called when the user chooses to start a new game. 
		/* Setup will take in the input and finish creating the gui while
		 * creating the necessarry table data */
		this.player = player;
		this.file = file;
		
		// Parse the file
		Parser parser = new Parser(file);
		this.gridSize = parser.getGridSize();
		this.staticData = parser.getStatic();
		this.grid = parser.getGrid();
		this.numOfObjects = gridSize * gridSize;
		
		// Create the history (this MUST happen before grid is made)
		this.history = new History(gridSize, staticData);
		
		// Add the grid
		numbrix.gui().addTable(gridSize, staticData, grid);
		
		// Check to solve
		if(player == Player.COMPUTER)
		{
			// Solve
			this.solver = new Solver(this);
			this.solver.solve();
			
		} /* end if */
		
		// Add the leftbar
		numbrix.gui().addLeftDisplay(player);
		
	} /* end setup method */
	
	public void reset()
	{// Reset the grid
		this.setup(this.player, this.file);
	} /* end reset method */
	
	/**
	 * Resets the history and grid
	 */
	public void resetData()
	{
		// Parse the file
		Parser parser = new Parser(file);
		this.gridSize = parser.getGridSize();
		this.staticData = parser.getStatic();
		this.grid = parser.getGrid();
		this.numOfObjects = gridSize * gridSize;
		
		// Create the history (this MUST happen before grid is made)
		this.history = new History(gridSize, staticData);
		
		// Add the grid
		numbrix.gui().addTable(gridSize, staticData, grid);
	} /* end resetSystem method */
	
	/*------------------ Public methods ------------------*/
	public Validator.State verify(Integer[][] grid)
	{// Verify the grid and return its state
		Validator validator = new Validator(gridSize, grid);
		return validator.getState();
	} /* end verify method */
	
	public Validator.State verify()
	{
		return this.verify(this.grid);
	} /* end overloaded verify method */
	
	public Integer[][] makeGrid()
	{// Return a grid of nulls that has the correct size
		Integer[][] returner = new Integer[gridSize][];
		
		for(int i = 0; i < gridSize; i++)
		{
			returner[i] = new Integer[gridSize];
			
			for(int j = 0; j < gridSize; j++)
			{
				returner[i][j] = null;
			} /* end for loop */
		} /* end for loop */
		return returner;
	} /* end makeGrid method */
	
	/**
	 * Modifies the grid and logs the change
	 * @param x
	 * @param y
	 * @param val
	 */
	public void modifyGrid(int x, int y, Integer val)
	{
		this.grid[y][x] = val;
		this.history.logChange(y, x, val);
	} /* end modifyGrid method */
	
	/**
	 * Log the change to the position
	 * @param x			the x position
	 * @param y			the y position
	 * @param newVal	the new value of the position
	 * @deprecated
	 */
	public void logChange(int x, int y, Integer newVal)
	{
		this.history.logChange(y, x, newVal);
	} /* end logChange method */
	
	/**
	 * Applies the completed grid and history 
	 */
	public void complete(Integer[][] grid, ArrayList<Log> log)
	{
		this.resetData();
		this.grid = grid;
		numbrix.gui().addTable(this.gridSize, this.staticData, this.grid);
		
		boolean[][] completed = new boolean[this.gridSize][];
		for(int i = 0; i < this.gridSize; i++)
		{
			completed[i] = new boolean[this.gridSize];
			for(int j = 0; j < this.gridSize; j++)
			{
				completed[i][j] = true;
			} /* end for loop */
		} /* end for loop */
		
		this.history = new History(gridSize, staticData, log, completed);
		
	} /* end complete method */
	
	/**
	 * Print the system to standard out
	 */
	public void printGrid()
	{
		String print = "";
		Integer val;
		
		for(int y = gridSize - 1; y >= 0; y--)
		{
			for(int x = 0; x < this.gridSize; x++)
			{
				val = this.getVal(x, y);
				if(val == null) print += "__, ";
				else if((val/10) == 0) print += " " + Integer.toString(val) + ", ";
				else print += Integer.toString(val) + ", ";
			} /* end for loop */
			print += "\n";
			
		} /* end i loop */
		
		System.out.println(print);
	} /* end printGrid method */
 	
	/*------------------ Getter methods ------------------*/
	public int getGridSize()
	{
		return this.gridSize;
	} /* end getGridSize method */
	
	public Player getPlayer()
	{
		return this.player;
	} /* end getPlayer method */
	
	public Solver getSolver()
	{
		return this.solver;
	} /* end getStaticGrid method */
	
	public Integer[][] getGrid()
	{
		return this.grid;
	} /* end getGrid method */
	
	public Integer getVal(int x, int y)
	{
		Integer returner = null;
		if( (x >= 0 && x < this.numOfObjects ) && (y >=0 && y < this.numOfObjects) ) returner = this.grid[y][x];
		return returner;
	} /* end getVal method */
	
	public boolean[][] getStaticData()
	{
		return this.staticData;
	} /* end getStaticData method */
	
	public String getHistory()
	{
		return this.history.getLog();
	} /* end getHistory method */
	
	public ArrayList<Log> getHistoryLog()
	{
		return this.history.getHistoryLog();
	} /* end getHistoryLog method */
	
	public int getNumOfObjects()
	{
		return this.numOfObjects;
	} /* end getNumOfObjects method */
	
	public String getIncrementLog()
	{
		this.history.incrementLog();
		return this.history.getIncrementLog();
	} /* end getIncrementLog method */
	
} /* end NumbrixSystem class */
