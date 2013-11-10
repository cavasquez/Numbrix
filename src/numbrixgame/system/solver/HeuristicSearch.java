package numbrixgame.system.solver;

import java.util.LinkedList;

/**
 * The heuristic search to be used by the solver.
 * @author Carlos Vasquez
 *
 */

public class HeuristicSearch extends SearchMethod
{
	/************************************ Class Attributes *************************************/
	
	
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
		 * ignores the end points (0 or the last element) because I believe it 
		 * is a "safer" search when two destinations are known as opposed to
		 * just one. */
		int listSize = SearchMethod.snake.size();
		int distance = SearchMethod.system.getNumOfObjects();
		int start = 0;
		int end = 0;
		int temp = 0;
		int startList = 0;
		
		if(listSize > 1)
		{
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
		
		Triple triple = SearchMethod.snake.getLast(startList);
		LinkedList<Direction> moves = this.makeDirectionStack();
		
		if(search(triple, moves.pop(), distance-1)) solved = true;
		else if(search(triple, moves.pop(), distance-1)) solved = true;
		else if(search(triple, moves.pop(), distance-1)) solved = true;
		else if(search(triple, moves.pop(), distance-1)) solved = true;
		
		/* Note that one of these should return true. */
		return solved;
	} /* end startSearch method */
	
	public boolean search(Triple triple, Direction direction, int nodeCount)
	{
		boolean solved = false;
		
		if(this.emptyAndLegal(triple.getX() + direction.x, triple.getY() + direction.y))
		{
			/* Since we know that this is empty and legal, now check if the count has reached 1 */
			LinkedList<Direction> moves = this.makeDirectionStack(direction);
			Triple thisNode = new Triple(triple.getValue() + 1, triple.getX() + direction.x, triple.getY() + direction.y);
			if(nodeCount == 1)
			{
				/* We are at the last node. Check to see that it can connect to a surrounding node.
				 * Note that there are exactly three direction to look in */
				boolean connects = false;
				if(connects(thisNode, moves.pop())) connects = true;
				else if(connects(thisNode, moves.pop())) connects = true;
				else if(connects(thisNode, moves.pop())) connects = true;
				
				/* If the node connects, then we add this node and make a new solver to solve
				 * the remainder of the problem. Furthermore, if the solver cannot solve the
				 * grid, then it must be the case that a bad route was taken and we must 
				 * remove this node as well as any changes made by the solver. */
				if(connects)
				{
					Solver newSolver = new Solver();
					newSolver.add(thisNode);
					solved = newSolver.constraintSatisfactionSearch();
					
					/* Check to see if solved. If not solved, undo changes and remove self */
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
				if(search(thisNode, moves.pop(), nodeCount-1)) solved = true;
				else if(search(thisNode, moves.pop(), nodeCount-1)) solved = true;
				else if(search(thisNode, moves.pop(), nodeCount-1)) solved = true;
				
				/* Check to see if solved. If not, then remove this node. */
				if(!solved) super.solver.remove(thisNode);
			} /* end else */
			
		} /* end check */
		
		return solved;
	} /* end search method */
	
	/**
	 * Returns whether or not the triple can connect with a neighbor
	 * @param triple	the triple being checked
	 * @param direction	the direction the triple is checking in 
	 * @return			whether or not the triple can connect with a neighbor
	 */
	protected boolean connects(Triple triple, Direction direction)
	{
		boolean connects = false;
		if(this.fullAndLegal(triple.getX() + direction.x, triple.getY() + direction.y) &&
				(triple.getValue() + 1) == SearchMethod.system.getVal(triple.getX() + direction.x, triple.getY() + direction.y))
		{
			connects = true;
		} /* end if */
		return connects;
	} /* end connects method */
	
} /* end HeuristicSearch class */
