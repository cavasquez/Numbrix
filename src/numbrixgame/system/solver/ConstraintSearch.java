package numbrixgame.system.solver;

import java.util.LinkedList;
import java.util.Stack;

/**
 * The constraint search to be used by the Solver.
 * @author Carlos Vasquez
 *
 */

public class ConstraintSearch extends SearchMethod
{
	/************************************ Class Attributes *************************************/
	private Stack<Triple> additions;
	private Solver solver;
	
	/************************************ Class Methods *************************************/
	public ConstraintSearch(Solver solver)
	{
		this.additions = new Stack<Triple>();
		this.solver = solver;
	} /* end the constructor */
	
	/**
	 * Start the constraint search
	 * @param forward	the direction that the constraint is searching in
	 * @return			the success of the search
	 */
	public boolean startSearch(boolean forward)
	{
		boolean constraintFound = false;
		int nextList = 0;
		int increment = 1;
		if(forward != true) increment = -1;
		
		while(nextList < SearchMethod.snake.size())
		{
			if(this.search(increment, this.findNext(nextList, forward))) constraintFound = true;
			nextList++;
		} /* end while loop */
		
		return constraintFound;
	} /* end startSearch method */
	
	/**
	 * Recursive function that searches for the next constraint
	 * @param increment	forward or backwards
	 * @param previous	the previous node (the callee)
	 * @param direction	the direction of the search
	 * @return			the success of the search
	 */
	protected boolean search(int increment, Triple current)
	{
		boolean constraintFound = false;
		
		/* Check the terminal cases */
		if(current.getValue() >= 0 && current.getValue() <= SearchMethod.system.getNumOfObjects())
		{
			LinkedList<Direction> moves = new LinkedList<Direction>(); // stack of valid moves
			if(firstDegreeSearch(current, Direction.TOP)) moves.push(Direction.TOP);
			if(firstDegreeSearch(current, Direction.BOTTOM)) moves.push(Direction.BOTTOM);
			if(firstDegreeSearch(current, Direction.LEFT)) moves.push(Direction.LEFT);
			if(firstDegreeSearch(current, Direction.RIGHT)) moves.push(Direction.RIGHT);
			
			/* Based on the number of valid moves possible, different actions can be performed
			 * that can help constrain the potential nodes */
			if(moves.size() == 1)
			{
				/* The best case. There is only one direction to go , hence
				 * the next node must be the one node found */
				constraintFound = true;
				this.constraintFound(moves.peek(), current, increment);
			} /* end if */
			else if(moves.size() != 0)
			{
				int size = moves.size();
				Direction dir = null;
				
				for(int i = 0; i < size; i++)
				{
					if(secondDegreeSearch(current, dir = moves.pop(), increment)) moves.add(dir);
				} /* end for loop */
				
				/* If there is only one direction in which a node has potential, it must be that the
				 * node is the only node that can have the searched for value */
				if(moves.size() == 1)
				{
					constraintFound = true;
					this.constraintFound(moves.peek(), current, increment);
				} /* end if */
				
				/* If there are multiple nodes with potential, we can still constrain the nodes.
				 * The node in which the only value that can be placed in the node is the sought
				 * for value must be the node in which the value can be placed. */
				
			} /* end else if */
			
		} /* end if */
		
		return constraintFound;
	} /* end search method */
	
	/**
	 * Just check to see if this node has the potential to be the next node
	 * @param previous	the callee
	 * @param direction	the direction in which the callee called
	 * @return			whether or not the node is empty and legal
	 */
	private boolean firstDegreeSearch(Triple previous, Direction direction)
	{
		return this.emptyAndLegal(previous.getX() + direction.x, previous.getY() + direction.y);
	} /* end firstDegreeSearch method */
	
	/**
	 * Just check to see if this node is populated
	 * @param previous	the callee
	 * @param direction	the direction in which the callee called
	 * @return			whether or not the node is populated and legal
	 */
	private boolean firstPrimeDegreeSearch(Triple previous, Direction direction)
	{
		return this.fullAndLegal(previous.getX() + direction.x, previous.getY() + direction.y);
	} /* end firstPrimeDegreeSearch method */
	
	/**
	 * A check to see if the searched at node can contain the value sought for
	 * @param previous	the callee
	 * @param direction	the direction in which the callee called
	 * @param increment	forwards or backwards
	 * @return			whether or not the position can contain the searched for value
	 */
	private boolean secondDegreeSearch(Triple previous, Direction direction, int increment) 
	{
		boolean potential = false;
		Triple current = new Triple(previous.getValue() + increment, previous.getX() + direction.x, previous.getY() + direction.y);
		LinkedList<Direction> moves = this.makeDirectionStack(direction);
		Direction currentDirection = null;
		
		/* First, check to see if preliminary moves pass the first degree search. There must be three directions in preliminaryMoves */
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);

		/* Now the moves list contains only directions to nodes that are populated.
		 * These nodes will help us determine if the current node still has the potential 
		 * to hold the necessary value */
		
		/* If the ends match with the increment value
		 * of the current triple, then the current examined empty node has the potential to be the
		 * incremented node of the previous triple. */
		int currentVal;
		while(!moves.isEmpty())
		{
			currentDirection = moves.pop();
			currentVal = SearchMethod.system.getVal(current.getX() + currentDirection.x, current.getY() + currentDirection.y);
			if(currentVal == current.getValue() + increment) potential = true;
			
		} /* end while loop */
		
		return potential;
	} /* end secondDegreeSerch method */
	
	/**
	 * Searches for a node in which the only value that can fit in it is the sought for value
	 * @param previous	the callee
	 * @param direction	the direction in which the callee called
	 * @param increment	forwards or backwards
	 * @return			whether or not the position can contain the searched for value
	 */
	private boolean thirdDegreeSearch(Triple previous, Direction direction, int increment) 
	{
		boolean potential = false;
		Triple current = new Triple(previous.getValue() + increment, previous.getX() + direction.x, previous.getY() + direction.y);
		LinkedList<Direction> moves = this.makeDirectionStack(direction);
		Direction currentDirection = null;
		
		/* First, check to see if preliminary moves pass the first degree search. There must be three directions in preliminaryMoves */
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);

		/* Now the moves list contains only directions to nodes that are legal. 
		 * Now we should check to see if the surrounding nodes are end nodes in 
		 * the snake. If all the nodes are end nodes, then only this value may fit 
		 * in the node*/
		int size = moves.size();
		int nextVal = 0;
		for(int i = 0; i < size; i++)
		{
			currentDirection = moves.pop();
			nextVal = SearchMethod.system.getVal(current.getX() + currentDirection.x, current.getY() + currentDirection.y);
			if(SearchMethod.snake.isEnd(nextVal)) moves.add(currentDirection);
		} /* end for loop */
		
		/* If only one of the directions is an end node, then it must be the case
		 * that the node */
		if(moves.size() == 1) potential = true;
		
		return potential;
	} /* end secondDegreeSerch method */
	
	/**
	 * Finds the next Triple to be searched for
	 * @return	the triple being searched for
	 */
	private Triple findNext(int list, boolean forward)
	{
		Triple next = null;

		/* Find the next triple */
		if (forward == true) next = SearchMethod.snake.getLast(list);
		else next = SearchMethod.snake.getFirst(list);

		return next;
	} /* end findFirst method */
	
	private void add(Triple triple)
	{
		this.additions.push(triple);
		this.solver.add(triple);
	} /* end add method */
	
	/**
	 * Function called when constraint is found. Modifies data accordingly
	 * @param direction		the direction of the node found
	 * @param current		the current triple
	 * @param firstDegree	which search found the constraint
	 * @param increment		the direction being traveled
	 */
	private void constraintFound(Direction direction, Triple current, int increment)
	{
		Triple next = new Triple(current.getValue() + increment, current.getX() + direction.x, current.getY() + direction.y);
		this.add(next);
		/* Continue searching if the found value is an end node */
		boolean forward = false;
		if(increment == 1) forward = true;
		next = SearchMethod.snake.findTip(next.getValue(), forward); 
		if(SearchMethod.snake.isEnd(next.getValue())) this.search(increment, next);
	} /* end constraintFound method */
	
	/**
	 * Undoes additions made by this object
	 */
	public void undo()
	{
		while(!this.additions.isEmpty())
		{
			this.solver.remove(this.additions.pop());
			
		} /* end while loop */
		
	} /* end undo method */
	
} /* end ConstraintSearch method */
