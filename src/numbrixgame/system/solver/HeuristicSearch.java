package numbrixgame.system.solver;

import java.util.LinkedList;
import numbrixgame.numbrix;

/**
 * The heuristic search to be used by the solver. It will attempt a brute force search of the grid
 * to solved the Numbrix grid.
 * @author Carlos Vasquez
 *
 */

public class HeuristicSearch extends SearchMethod
{
	/************************************ Class Methods *************************************/
	public HeuristicSearch(Solver solver)
	{
		super(solver);
	} /* end the constructor */
	
	/**
	 * Stars the heuristic search by initializing variables and finding the
	 * shortest unsolved path in the snake.
	 * @param solver
	 * @return
	 */
	public boolean startSearch(Solver solver)
	{
		boolean solved = false;
		/* Look for shortest unsolved path in the snake. Note that this search
		 * prioritizes non-end points (0 or the last element) because I believe it 
		 * is a "safer" search when two destinations are known as opposed to
		 * just one. */
		int listSize = SearchMethod.snake.size();
		int distance = SearchMethod.system.getNumOfObjects();
		int start = 0;
		int end = 0;
		int temp = 0;
		int startList = 0;
		int increment = 1;
		
		if(listSize > 1)
		{
			/* List size is greater than one. Hence, we can find the shortest 
			 * path between two points and aim to search that path. */
			for(int i = 0; i < (listSize - 1); i++)
			{
				start = SearchMethod.snake.getLast(i).getValue();
				end = SearchMethod.snake.getFirst(i+1).getValue();
				temp = end - start;
				if(temp < distance)
				{
					distance = temp;
					startList = i;
				} /* end if */
			} /* end for loop */
			
		} /* end if */
		else
		{
			/* If the list size is 1, only endpoints remain. */
			startList = 0;
			if(SearchMethod.snake.getLast(0).getValue() != numbrix.system().getNumOfObjects())
			{
				start = SearchMethod.snake.getLast(0).getValue();
				end = numbrix.system().getNumOfObjects();
			} /* end if */
			else if(SearchMethod.snake.getFirst(0).getValue() != 1)
			{
				/* we must do a "reverse" search */
				increment = -1;
				start = 1;
				end = SearchMethod.snake.getFirst(0).getValue();
			} /* end else */
			
			distance = end - start + 1; // We must add 1 because of the missing end point
		} /* end else */
		
		Triple triple = null;
		if(increment == 1) triple = SearchMethod.snake.getLast(startList);
		else triple = SearchMethod.snake.getFirst(startList);
		LinkedList<Direction> moves = this.makeDirectionStack();
		if(search(triple, moves.pop(), distance-1, increment)) solved = true;
		else if(search(triple, moves.pop(), distance-1, increment)) solved = true;
		else if(search(triple, moves.pop(), distance-1, increment)) solved = true;
		else if(search(triple, moves.pop(), distance-1, increment)) solved = true;
		
		/* Note that one of these should return true. */
		return solved;
	} /* end startSearch method */
	
	/**
	 * A recursive search (of sorts) which checks to see if the triple can be placed and make sure
	 * that the nodeCount has not reached 1. If it is possible to place the triple in the cell in the
	 * direction, search will then search to the increment of the triple in the remaining direcetions.
	 * @param triple	the cell calling the search
	 * @param direction	the direction that triple is searching to populate
	 * @param nodeCount	the number of nodes left to search for
	 * @param increment	the "direction" the search is going in (forward or backward)
	 * @return			the status of the solution
	 */
	public boolean search(Triple triple, Direction direction, int nodeCount, int increment)
	{
		boolean solved = false;
		
		if(this.emptyAndLegal(triple.getX() + direction.x, triple.getY() + direction.y))
		{
			/* Since we know that this is empty and legal, now check if the count has reached 1 */
			LinkedList<Direction> moves = this.makeDirectionStack(direction);
			Triple thisNode = new Triple(triple.getValue() + increment, triple.getX() + direction.x, triple.getY() + direction.y);
			if(nodeCount == 1)
			{
				/* We are at the last node. Check to see that it can connect to a surrounding node.
				 * Note that there are exactly three direction to look in */
				boolean connects = false;
				if(connects(thisNode, moves.pop(), increment)) connects = true;
				else if(connects(thisNode, moves.pop(), increment)) connects = true;
				else if(connects(thisNode, moves.pop(), increment)) connects = true;
				
				/* If the node connects, then we add this node and make a new solver to solve
				 * the remainder of the problem. Furthermore, if the solver cannot solve the
				 * grid, then it must be the case that a bad route was taken and we must 
				 * remove this node as well as any changes made by the solver. */
				if(connects)
				{
					Solver newSolver = new Solver();
					newSolver.add(thisNode);
					
					/* First round of checks. If check fails, apply constraint satisfaction search. */
					solved = this.solver.check();
					if(!solved) solved = newSolver.constraintSatisfactionSearch();
					
					/* If still not solved, undo changes */
					if(!solved)
					{
						newSolver.undo();
						newSolver.remove(thisNode);
					} /* end if */
				} /* end if */
			} /* end if */
			else
			{
				/* We are not at the last node. Add this node and look for the next node. Note,
				 * there should only be 3 objects in the list.*/
				super.solver.add(thisNode);
				if(search(thisNode, moves.pop(), nodeCount-1, increment)) solved = true;
				else if(search(thisNode, moves.pop(), nodeCount-1, increment)) solved = true;
				else if(search(thisNode, moves.pop(), nodeCount-1, increment)) solved = true;
				
				/* Check to see if solved. If not, then remove this node. */
				if(!solved) super.solver.remove(thisNode);
			} /* end else */
			
		} /* end check */
		
		return solved;
	} /* end search method */
	
	/**
	 * Returns whether or not the triple can connect with a neighbor or is a terminal node
	 * @param triple	the triple being checked
	 * @param direction	the direction the triple is checking in 
	 * @return			whether or not the triple can connect with a neighbor
	 */
	protected boolean connects(Triple triple, Direction direction, int increment)
	{
		boolean connects = false;
		if((this.fullAndLegal(triple.getX() + direction.x, triple.getY() + direction.y) &&
				(triple.getValue() + increment) == SearchMethod.system.getVal(triple.getX() + direction.x, triple.getY() + direction.y) ) ||
				( triple.getValue() == numbrix.system().getNumOfObjects() || 
				triple.getValue() == 1) )
		{
			connects = true;
			
		} /* end if */
		return connects;
	} /* end connects method */
	
} /* end HeuristicSearch class */
