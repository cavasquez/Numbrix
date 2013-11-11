package numbrixgame.system;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*****************************************************************************************************
 * Parser will parse the file provided by the user to determine the grid size and static elements
 *****************************************************************************************************/

public class Parser 
{
	/*************************** Class Attributes ***************************/
	private int gridSize;
	private boolean[][] staticElements;
	private Integer[][] grid;
	
	/*************************** Class Methods ***************************/
	public Parser(File file)
	{// Parse the file
		this.parse(file);
	} /* end constructor */
	
	/*------------------ Public methods ------------------*/	
	public int getGridSize()
	{
		return gridSize;
	} /* end getGridSize method */
	
	public boolean[][] getStatic()
	{
		return staticElements;
	} /* end getStatic */
	
	public Integer[][] getGrid()
	{
		return grid;
	} /* end getGrid method */
	
	/*------------------ Private methods ------------------*/
	private void parse(File file)
	{
		// Create the input stream
		FileReader fr;
		try {
			
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			Scanner scanner = new Scanner(br);
			
			// The first line must contian a number. It must indicate the grid size
			gridSize = scanner.nextInt();

			// initialize the arrays
			staticElements = new boolean[gridSize][];
			grid = new Integer[gridSize][];
			
			for(int i = 0; i < gridSize; i++)
			{
				staticElements[i] = new boolean[gridSize];
				grid[i] = new Integer[gridSize];
				
				for(int j = 0; j < gridSize; j++)
				{
					staticElements[i][j] = false;
					grid[i][j] = null;
				} /* end for loop */
			} /* end for loop */
			
			// check for grid data
			int x;
			int y;
			int val;
			while(scanner.hasNext())
			{
				x = scanner.nextInt();
				y = scanner.nextInt();
				val = scanner.nextInt();
				
				staticElements[y][x] = true;
				grid[y][x] = val;
			} /* end for loop */
			
			// Clean up
			scanner.close();
			try 
			{
				br.close();
			} /* end try */
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /* end catch */
			
		} /* end try */ 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} /* end catch */
		
	} /* end parse method */
	
} /* end parser class */
