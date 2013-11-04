package numbrixgame.system;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Data structure that will manage the data and segment it accordingly.
 * The snake will keep a list of lists where each sub list contains
 * elements in consecutively increasing order. Each parent list will
 * contain lists in consecutively increasing order.
 * @author Carlos
 *
 */

public class Snake 
{
	/************************************ Class Attributes *************************************/
	private LinkedList<LinkedList<Triple>> snake;
	
	/************************************ Class Methods *************************************/
	public Snake(int gridSize, Integer[][] grid)
	{
		this.snake = new LinkedList<LinkedList<Triple>>();
		this.snake.add(new LinkedList<Triple>());
		PriorityQueue<Triple> queue = new PriorityQueue<Triple>();
		
		// Get grid values in ascending order
		for(int i = 0; i < gridSize; i++)
		{
			for(int j = 0; j < gridSize; j++)
			{
				if(grid[j][i] != null) queue.add(new Triple(grid[j][i], j, i));
			} /* end for j */
		} /* end for i */
		// Construct snake
		Triple currentTriple = queue.remove();
		int currentList = 0;
		int size = queue.size();
		snake.get(currentList).add(currentTriple);
		for(int i = 0; i < size; i++)
		{
			if(queue.peek().getValue() == (currentTriple.getValue() + 1))
			{/* Still encountering consecutive values, add to current list */
				currentTriple = queue.remove();
				this.snake.get(currentList).add(currentTriple);
			} // end if
			else
			{/* No longer a consecutive element. Create a list */
				currentList++;
				this.snake.add(new LinkedList<Triple>());
				currentTriple = queue.remove();
				this.snake.get(currentList).add(currentTriple);
			} /* end else */
		} /* end for loop */
		
	} /* end constructor */
	
	/*-------------------------------------- Data Structure Manipulation --------------------------------------*/
	/**
	 * Add new value and positions
	 */
	public void add(Triple triple)
	{
		int current = 0;
		boolean found = false;
		LinkedList<Triple> currentList = null;
		LinkedList<Triple> previousList = null;
		
		while( (current < this.snake.size()) && !found)
		{
			currentList = this.snake.get(current);
			if (current > 0) previousList = this.snake.get(current - 1);
			//  Look to see if snake is bounded above
			if(currentList.getFirst().getValue() > triple.getValue())
			{/* triple is bounded above by the head of the current list */
				found = true;
				
				// Check to see if triple is consecutive with above
				if(current!= 0)
				{/* Check if bounded below by consecutive triple */
					if(currentList.getFirst().getValue() == (triple.getValue() + 1))
					{/* List consecutively bounds from above */
						// Now we must also see if list consecutively bounds from below
						if(previousList.getLast().getValue() == (triple.getValue() - 1))
						{/* List is consecutively bounded from below by previous list */
							// Merge both lists with triple between
							previousList.addLast(triple);
							previousList.addAll(this.snake.remove(current));
						} /* end if */
						else
						{/* List is not consecutively bounded from below by previous list */
							// Add triple to front of current list
							currentList.addFirst(triple);
						} /* end else */
					} /* end if */
					else
					{/* List does not consecutively bound from above */
						if(previousList.getLast().getValue() == (triple.getValue() - 1))
						{/* List is consecutively bounded from below by previous list */
							// Add triple to end of previous list
							previousList.addLast(triple);
						} /* end if */
						else
						{/* List is not consecutively bounded from below by previous list */
							// Make a new list before current list and add triple to the list
							this.snake.add(current - 1, new LinkedList<Triple>());
							currentList.add(triple);
						} /* end else */
					}/* end else */
				} /* end if */
				else
				{/* There is nothing to bound below, check to see if it is consecutive with head */
					if(currentList.getFirst().getValue() == (triple.getValue() + 1))
					{/* Triple is consecutive. Make it new head */
						currentList.offerFirst(triple);
					} /* end if */
					else
					{/* Triple is not consecutive. Make new list at beginning */
						this.snake.addFirst(new LinkedList<Triple>());
						this.snake.getFirst().add(triple);
					} /* end else */
				} /* end else */
			} /* end if */
			else current++;
		} /* end while loop */
		
		// Make sure spot was found
		if(!found)
		{/* if not found, it must be the case that the triple belongs to the head in some way */
			if(this.snake.getLast().getLast().getValue() == (triple.getValue() - 1))
			{/* Triple is consecutive. Make it new tail */
				currentList.offerLast(triple);
			} /* end if */
			else
			{/* Triple is not consecutive. Make new list at end */
				this.snake.addLast(new LinkedList<Triple>());
				this.snake.getLast().add(triple);
			} /* end else */
		} /* end if */
	
	} /* end add method */
	
	/**
	 * Remove the given value.
	 * @param value	the value of the triple being removed
	 * @return		the triple that was removed
	 */
	public Triple remove(int value)
	{
		int currentList = 0;
		LinkedList<Triple> list = null;
		boolean found = false;
		Triple returner = null;
		
		while( (currentList < this.snake.size()) && !found)
		{
			list = this.snake.get(currentList);
			// Check to see if triple is bounded on top by any of the lists
			if(list.getFirst().getValue() == value)
			{/* Triple is head of list. Remove it */
				returner = list.removeFirst();
				
				// Check to see if list is empty. If so, remove it
				if(list.size() == 0) this.snake.remove(currentList);
				found = true;
			} /* end if */
			else if(list.getLast().getValue() == value)
			{/* Triple is tail of list. Remove it */
				returner = list.removeLast();
				
				// Check to see if list is empty. If so, remove it
				if(list.size() == 0) this.snake.remove(currentList);
				found = true;
			} /* end else if */
			else if( (list.getFirst().getValue() < value) && 
					(list.getLast().getValue() > value) )
			{/* Triple is within list. */
				// Calculate position of triple
				int position = value - list.getFirst().getValue();
				int last = list.size();
				
				// Remove triple and partition lists
				returner = list.remove(position);
				this.snake.add(currentList + 1, new LinkedList<Triple>());
				for(int i = position; i < (last - 1); i++)
				{
					this.snake.get(currentList + 1).add(list.remove(position));
				} /* end for loop */
			} /* end else if */
			else currentList++;
			
		} /* end while */
		
		return returner;
	} /* end remove method */
	
	/*-------------------------------------- Data Structure Meta Data --------------------------------------*/
	/**
	 * Returns the triple with the given value
	 * @param value	the value of the triple being searched for
	 * @return		the triple with the given value
	 */
	public Triple find(int value)
	{
		int currentList = 0;
		LinkedList<Triple> list = null;
		boolean found = false;
		Triple returner = null;
		
		while( (currentList < this.snake.size()) && !found)
		{
			list = this.snake.get(currentList);
			// Check to see if triple is bounded on top by any of the lists
			if(list.getFirst().getValue() == value)
			{/* Triple is head of list. Remove it */
				returner = list.getFirst();
				found = true;
			} /* end if */
			else if(list.getLast().getValue() == value)
			{/* Triple is tail of list. */
				returner = list.getLast();
				found = true;
			} /* end else if */
			else if( (list.getFirst().getValue() < value) && 
					(list.getLast().getValue() > value) )
			{/* Triple is within list. */
				// Calculate position of triple
				int position = value - list.getFirst().getValue();
				
				// Find the triple in the list
				returner = list.get(position);
			} /* end else if */
			else currentList++;
			
		} /* end while */
		
		return returner;
	} /* end find method */
	
	/**
	 * Returns the number of lists in the snake
	 * @return	the number of lists in the snake
	 */
	public int size()
	{
		return this.snake.size();
	} /* end size method */
	
	/**
	 * Returns the size of the list at the position
	 * @param position	the position of the list queried
	 * @return			the size of the list at the position
	 */
	public int sizeOf(int position)
	{
		return this.snake.get(position).size();
	} /* end sizeOf method */
	
	/**
	 * Returns the number of Triples in the snake
	 * @return
	 */
	public int count()
	{
		int returner = 0;
		for(int i = 0; i < this.snake.size(); i ++)
		{
			returner += this.snake.get(i).size();
		} /* end for loop */
		
		return returner;
	} /* end count method */
	
	@Override
	public String toString()
	{
		String returner = "";
		for(int i = 0; i < snake.size(); i++)
		{
			returner += "{ ";
			for(int j = 0; j < snake.get(i).size(); j++)
			{
				returner += ( snake.get(i).get(j).toString() + ", ");
			} /* end for loop */
			returner += "}\n";
		} /* end for loop */
		return returner;
	} /* end toString method */
	
} /* end Snake class */
