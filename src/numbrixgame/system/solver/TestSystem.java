package numbrixgame.system.solver;

import numbrixgame.system.History;
import numbrixgame.system.NumbrixSystem;

public class TestSystem extends NumbrixSystem
{
	public TestSystem(int gridSize, Integer[][] grid, boolean[][] staticGrid)
	{
		this.gridSize = gridSize;
		this.staticData = staticGrid;
		this.grid = grid;
		this.numOfObjects = gridSize * gridSize;
		
		// Create the history (this MUST happen before grid is made)
		this.history = new History(gridSize, staticData);
	} /* end TestSystem method */
	
} /* end TestSystem class */
