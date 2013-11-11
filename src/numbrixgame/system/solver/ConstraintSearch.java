package numbrixgame.system.solver;

import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import numbrixgame.numbrix;

/**
 * The constraint search to be used by the Solver.
 * @author Carlos Vasquez
 *
 */

public class ConstraintSearch extends SearchMethod
{
	/************************************ Class Attributes *************************************/
	private Stack<Triple> additions;
	
	/************************************ Class Methods *************************************/
	public ConstraintSearch(Solver solver)
	{
		super(solver);
		this.additions = new Stack<Triple>();
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
		if(forward)
		{/* go in a forward direction */
			while(nextList < SearchMethod.snake.size())
			{
				if(this.search(increment, this.findNext(nextList, forward))) constraintFound = true;
				nextList++;
			} /* end while loop */
		} /* end if */
		else
		{/* Go in a backward direction */
			increment = -1;
			
			while( (SearchMethod.snake.size() - nextList - 1) >= 0)
			{
				if(this.search(increment, this.findNext(SearchMethod.snake.size() - nextList - 1, forward))) constraintFound = true;
				nextList++;
			} /* end while loop */
		} /* end else */
					
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
		if(current.getValue() > 1 && current.getValue() < SearchMethod.system.getNumOfObjects())
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
			else if(moves.size() > 1)
			{
				int size = moves.size();
				Direction dir = null;
				
				for(int i = 0; i < size; i++)
				{
					if(secondDegreeSearch(current, dir = moves.pop(), increment)) moves.add(dir);
				} /* end for loop */
				
				/* If there is only one direction in which a node has potential, it must be that the
				 * node is the only node that can have the searched for value. That is to say, if there
				 * is only one node that can connect this node and the sought after value, it must be
				 * the case that the node is the node that can hold the value we are looking for. */
				if(moves.size() == 1)
				{
					constraintFound = true;
					this.constraintFound(moves.peek(), current, increment);
				} /* end if */
				else if(moves.size() > 1)
				{
					/* If there are multiple nodes with potential, we can still constrain the nodes.
					 * The node in which the only value that can be placed in the node is the sought
					 * for value must be the node in which the value can be placed. */
					size = moves.size();
					for(int i = 0; i < size; i++)
					{
						if(thirdDegreeSearch(current, dir = moves.pop(), increment)) moves.add(dir);
					} /* end for loop */
					
					if(moves.size() == 1)
					{
						constraintFound = true;
						this.constraintFound(moves.peek(), current, increment);
					} /* end if */
					
				} /* end else if */
				
			} /* end else if */
			
		} /* end if */
		
		return constraintFound;
	} /* end search method */
	
	/**
	 * Just check to see if this node has the potential to be the next node by checking
	 * if it is empty and legal.
	 * @param previous	the callee
	 * @param direction	the direction in which the callee called
	 * @return			whether or not the node is empty and legal
	 */
	private boolean firstDegreeSearch(Triple previous, Direction direction)
	{
		return this.emptyAndLegal(previous.getX() + direction.x, previous.getY() + direction.y);
	} /* end firstDegreeSearch method */
	
	/**
	 * Just check to see if this node is populated and legal
	 * @param previous	the callee
	 * @param direction	the direction in which the callee called
	 * @return			whether or not the node is populated and legal
	 */
	private boolean firstPrimeDegreeSearch(Triple previous, Direction direction)
	{
		return this.fullAndLegal(previous.getX() + direction.x, previous.getY() + direction.y);
	} /* end firstPrimeDegreeSearch method */
	
	/**
	 * A check to see if the searched at node can contain the value sought for. 
	 * It does so by looking to see if it can find the the increment node (two
	 * nodes after the previous node) in the surrounding nodes. If it can, then 
	 * we say that this direction has potential. 
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
		
		/* First, check to see if preliminary moves pass the first degree search. There must be three directions in preliminaryMoves.
		 * Essentially, we are looking for populated legal nodes. */
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);

		/* Now the moves list contains only directions to nodes that are populated. This constraint will
		 * now look for a  node that can connect to the previous node. Should such a node exist, 
		 * then it must be the case that this node has the potential to hold the sought after value. */
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
	 * @deprecated
	 * THIS IS NOT A FOR SURE SEARCH! DO NOT USE!
	 * A check to see if the searched at node cannot contain the sought after value. If it
	 * cannot, then we can limit the nodes for which can contain the value. 
	 * @param previous	the callee
	 * @param direction	the direction in which the callee called
	 * @param increment	forwards or backwards
	 * @return			whether or not the position can contain the searched for value
	 */
	private boolean secondPrimeDegreeSearch(Triple previous, Direction direction, int increment) 
	{
		boolean potential = false;
		Triple current = new Triple(previous.getValue() + increment, previous.getX() + direction.x, previous.getY() + direction.y);
		LinkedList<Direction> moves = this.makeDirectionStack(direction);
		Direction currentDirection = null;
		
		/* First, check for all the legal nodes that are not the callee. */
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		int size = moves.size();
		
		/* Next, check to see if the nodes are populated or not */
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		
		/* If there is a different in size between legal and populated nodes, then that means
		 * there is at least one empty node. If that is the case, then this node has "potential"
		 * to not be eliminated. */
		if(size != moves.size()) potential = true;
		else
		{
			/* It might still be possible that this node has the "potential" to not be eliminated.
			 * We must check to make sure that it cannot increment to any of it's surrounding
			 * populated nodes. */
			int currentVal;
			while(!moves.isEmpty())
			{
				potential = true;
				currentDirection = moves.pop();
				currentVal = SearchMethod.system.getVal(current.getX() + currentDirection.x, current.getY() + currentDirection.y);
				if(currentVal == current.getValue() + increment) potential = false;
				
			} /* end while loop */
		} /* end else */
		
		return potential;
	} /* end secondDegreeSerch method */
	
	/**
	 * Searches for a node in which the only value that can fit in it is the sought for value.
	 * It does so by looking at the surrounding nodes and seeing if they are all populated or
	 * legal. If they are, then this constraint can be applied. It then checks the surrounding 
	 * populated and legal nodes to see which values they still need (ie, the end values). If
	 * only one pair of end values can be found, it must be the case that this node is the only
	 * node that can house the triple we are looking for. That is because this search is called
	 * after secondDegreeSearch, hence it must be the case that this node has the potential to
	 * at least house the sought after value. Hence, if only one pair of linking values can be
	 * found, it must be the case that they link the previous node with it's second increment
	 * node.
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
		
		/* First, check to see if preliminary moves are legal. There must be three directions in preliminaryMoves */
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		currentDirection = moves.pop();
		if(legal(current.getX() + currentDirection.x, current.getY() + currentDirection.y)) moves.add(currentDirection);
		
		/* Now the moves list contains only directions to nodes that are legal. 
		 * Now we should check to see if the surrounding nodes are full. If there
		 * are any empty nodes, we cannot apply further constraints. */
		int size = moves.size();
		for(int i = 0; i < size; i++)
		{
			if(firstPrimeDegreeSearch(current, currentDirection = moves.pop())) moves.add(currentDirection);
		} /* end for loop */
		
		if(moves.size() == size)
		{
			/* It is the case that we have an equal number of legal nodes and full nodes.
			 * Now check to see if there are any "linking" nodes. This constrain works on 
			 * the premise that there will only be one pair of "linking", amongst which
			 * one of the nodes is the previous node. If there are more than one "linking"
			 * node pairs, then this constraint fails. */
			Integer[] ends = null;
 			boolean firstIntersectionFound = false;
			boolean lostPotential = false;
			int currentVal;
			Set<Integer> bag = new TreeSet<Integer>();
			
			/* We must remember to look for the previous node as well, even though
			 * we know that it will have a match in the increment direction. It may 
			 * be the case that it also has a match in the opposite of the increment
			 * direction which would kill this constraint. */
			ends = SearchMethod.snake.findEnds(previous.getValue());
			if(ends[0] != null) bag.add(ends[0]);
			if(ends[1] != null) bag.add(ends[1]);
						
			/* Find the ends of the remaining nodes */
			while( !moves.isEmpty() && !lostPotential )
			{
				currentDirection = moves.pop();
				currentVal = SearchMethod.system.getVal(current.getX() + currentDirection.x, current.getY() + currentDirection.y);
				ends = SearchMethod.snake.findEnds(currentVal);
				
				if(ends[0] != null)
				{
					if(bag.contains(ends[0]))
					{
						if(firstIntersectionFound) lostPotential = true;
						else firstIntersectionFound = true;
					} /* end if */
					else bag.add(ends[0]);
				} /* end if */
				if(ends[1] != null)
				{
					if(bag.contains(ends[1]))
					{
						if(firstIntersectionFound) lostPotential = true;
						else firstIntersectionFound = true;
					} /* end if */
					else bag.add(ends[1]);

				} /* end if */
				
			} /* end while loop */
			
			if(!lostPotential) potential = true;
			
		} /* end if */
		
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
		next = SearchMethod.snake.findTip(current.getValue(), forward); 
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
