package numbrixgame.system.solver;

import java.util.ArrayList;

import numbrixgame.numbrix;

/**
 * Abstract class that will form the basis for the tests of the search methods
 * @author Carlos Vasquez
 *
 */

public abstract class SearchTest extends numbrix
{
	/************************************ Class Constants *************************************/
	public static final int TEST_COUNT = 3;
	
	/************************************ Class Attributes *************************************/
	protected ArrayList<Integer[][]> grid;
	protected ArrayList<Integer> gridSize;
	protected int gridsize;
	protected static HeuristicSearch heuristic;
	protected boolean printOn;
	protected int testNum;
	protected Solver solver;
	
	/************************************ Class Methods *************************************/
	public abstract void startTest();
	
	public SearchTest(int testNum)
	{
		this.testNum = testNum;
		this.initialize();
		this.printOn = true;
	} /* end constructor method */
	
	private final void initialize()
	{
		this.initializeGrid();
		this.initializeNumbrix();
		this.initializeSolver();
	} /* end initialize method */
	
	protected void initializeSolver()
	{
		this.solver = new Solver(numbrix.system);
		this.solver.initialize();
	} /* end initializeSolver method */
	
	protected void initializeNumbrix()
	{
		numbrix.system = new TestSystem(gridsize, grid.get(testNum), findStatic(grid.get(testNum)));
	} /* end initializeNumbrix method */
	
	private final void initializeGrid()
	{
		this.grid = new ArrayList<Integer[][]>();
		this.gridSize = new ArrayList<Integer>();
		int size;
		
		/* Initialize grid sizes */
		this.gridSize.add(9);
		this.gridSize.add(9);
		this.gridSize.add(3);
		this.gridsize = gridSize.get(testNum);
		
		for(int k = 0; k < TEST_COUNT; k++)
		{
			// Initialize grid
			Integer newGrid[][] = null;
			size = gridSize.get(k);
			newGrid = new Integer[size][];
			for (int i = 0; i < size; i++)
			{
				newGrid[i] = new Integer[size];
				for (int j = 0; j < size; j++)
				{
					newGrid[i][j] = null;
				} /* end j loop */
			} /* end for loop */
			
			this.grid.add(newGrid);
		} /* end for loop */
		
		
		this.populateGrid();
		
	} /* end initializeGrid method */
	
	protected void populateGrid()
	{
		Integer newGrid[][] = this.grid.get(0);
		newGrid[8][0] = 25;
		newGrid[8][2] = 27;
		newGrid[8][4] = 33;
		newGrid[8][6] = 37;
		newGrid[8][8] = 39;
		newGrid[6][0] = 21;
		newGrid[6][8] = 41;
		newGrid[4][0] = 3;
		newGrid[4][8] = 57;
		newGrid[2][0] = 5;
		newGrid[2][8] = 63;
		newGrid[0][0] = 9;
		newGrid[0][2] = 11;
		newGrid[0][4] = 77;
		newGrid[0][6] = 71;
		newGrid[0][8] = 69;
		
		newGrid = this.grid.get(1);
		newGrid[8][0] = 55;
		newGrid[8][2] = 57;
		newGrid[8][4] = 67;
		newGrid[8][6] = 69;
		newGrid[8][8] = 71;
		newGrid[7][1] = 53;
		newGrid[7][3] = 65;
		newGrid[7][5] = 63;
		newGrid[7][7] = 77;
		newGrid[6][0] = 51;
		newGrid[6][8] = 73;
		newGrid[5][1] = 49;
		newGrid[5][7] = 75;
		newGrid[4][0] = 13;
		newGrid[4][8] = 39;
		newGrid[3][1] = 11;
		newGrid[3][7] = 41;
		newGrid[2][0] = 3;
		newGrid[2][8] = 37;
		newGrid[1][1] = 1;
		newGrid[1][3] = 21;
		newGrid[1][5] = 29;
		newGrid[1][7] = 35;
		newGrid[0][0] = 5;
		newGrid[0][2] = 7;
		newGrid[0][4] = 23;
		newGrid[0][6] = 31;
		newGrid[0][8] = 33;
		
		newGrid = this.grid.get(2);
		newGrid[0][0] = 1;
		newGrid[2][0] = 3;
		newGrid[0][2] = 7;
		newGrid[2][2] = 9;
		
	} /* end populateGrid method */
	
	protected boolean[][] findStatic(Integer[][] grid)
	{
		boolean[][] returner = null;
		returner = new boolean[gridsize][];
		for (int i = 0; i < gridsize; i++)
		{
			returner[i] = new boolean[gridsize];
			for (int j = 0; j < gridsize; j++)
			{
				if(grid[i][j] == null) returner[i][j] = false;
				else returner[i][j] = true;
			} /* end j loop */
		} /* end for loop */
		
		
		return returner;
	} /* end findStatic method */
	
	public void print(String print)
	{
		if(this.printOn) System.out.println(print);
	} /* end print method */
	
} /* end SearchTest method */
