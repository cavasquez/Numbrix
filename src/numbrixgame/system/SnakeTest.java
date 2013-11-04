package numbrixgame.system;

import java.util.ArrayList;

/**
 * Test for the snake class
 * @author Carlos Vasquez
 *
 */
public class SnakeTest 
{
	public static void main(String[] args)
	{
		// Initialize grid
		Integer[][] grid = new Integer[9][];
		for (int i = 0; i < 9; i++)
		{
			grid[i] = new Integer[9];
		} /* end for loop */
		
		// Populate grid
		grid[8][0] = 25;
		grid[8][2] = 27;
		grid[8][4] = 33;
		grid[8][6] = 37;
		grid[8][8] = 39;
		grid[6][0] = 21;
		grid[6][8] = 41;
		grid[4][0] = 3;
		grid[4][8] = 57;
		grid[2][0] = 5;
		grid[2][8] = 63;
		grid[0][0] = 9;
		grid[0][2] = 11;
		grid[0][4] = 77;
		grid[0][6] = 71;
		grid[0][8] = 69;
		
		// Modify
		/*
		grid[0][1] = 10;
		grid[1][0] = 8;
		grid[3][8] = 62;
		grid[3][0] = 4;
		*/
		// Make snake
		Snake snake = new Snake(9, grid);
		
		// Print snake
		System.out.println("The snake: size = " + snake.size() + " count = " + snake.count());
		System.out.println(snake.toString());
		
		// Add new sturff
		ArrayList<Triple> add = new ArrayList<Triple>();
		add.add(new Triple(10, 1, 0));
		add.add(new Triple(8, 0, 1));
		add.add(new Triple(62, 8, 3));
		add.add(new Triple(4, 0, 3));
		add.add(new Triple(6, 1, 2));
		add.add(new Triple(7, 1, 1));
		add.add(new Triple(81, 3, 3));
		
		for (int i = 0; i < add.size(); i++)
		{
			snake.add(add.get(i));
			System.out.println("Adding " + add.get(i).getValue() + " at (" + add.get(i).getX() + ", " + add.get(i).getY() + ")");
			System.out.println("The snake: size = " + snake.size() + " count = " + snake.count());
			System.out.println(snake.toString());
		} 
		
		// Add new sturff
		ArrayList<Integer> remove = new ArrayList<Integer>();
		remove.add(7);
		remove.add(11);
		remove.add(3);
		remove.add(63);
		remove.add(62);
		remove.add(81);
		
		for (int i = 0; i < remove.size(); i++)
		{
			snake.remove(remove.get(i));
			System.out.println("Removing " + remove.get(i));
			System.out.println("The snake: size = " + snake.size() + " count = " + snake.count());
			System.out.println(snake.toString());
		} 
		
		System.out.println("Done");
	} /* end main class */
	
} /* end SnakeTest */
