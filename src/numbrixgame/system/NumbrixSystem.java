package numbrixgame.system;

import java.io.File;

import numbrixgame.numbrix;
import numbrixgame.gui.Table;

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
	private int gridSize;				// The size of the grid
	private boolean[][] staticData;		// a grid that tells us if a position has static(provided) data
	private Integer[][] grid;			// a grid that holds the initial values in the grid
	private Player player;				// Whos playing
	private File file;					// Initial file
	private History history;			// The history of the game
	
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
		
		// Create the history (this MUST happen before grid is made)
		this.history = new History(gridSize, staticData);
		
		// Add the grid
		numbrix.gui().addTable(gridSize, staticData, grid);
		
		// Add the leftbar
		numbrix.gui().addLeftDisplay(player);
	} /* end setup method */
	
	public void reset()
	{// Reset the grid
		this.setup(this.player, this.file);
	} /* end reset method */
	
	/*------------------ Public methods ------------------*/
	public Validator.State verify(Integer[][] grid)
	{// Verify the grid and return its state
		Validator validator = new Validator(gridSize, grid);
		return validator.getState();
	} /* end verify method */
	
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
	
	public void logChange(int x, int y, Integer newVal)
	{
		this.history.logChange(x, y, newVal);
	} /* end logChange method */
	
	/*------------------ Getter methods ------------------*/
	public int getGridSize()
	{
		return gridSize;
	} /* end getGridSize method */
	
	public Player getPlayer()
	{
		return player;
	} /* end getPlayer method */
	
	public Integer[][] getGrid()
	{
		return grid;
	} /* end getGrid method */
	
	public String getHistory()
	{
		return this.history.getLog();
	} /* end getHistory method */
	
} /* end NumbrixSystem class */
