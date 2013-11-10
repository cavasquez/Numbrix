package numbrixgame.system.solver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import numbrixgame.system.NumbrixSystem;
import numbrixgame.system.Validator;

/**
 * Solver will solve the Numbrix game
 * @author Carlos
 *
 */

public class Solver 
{
	/************************************ Class Constants *************************************/
	
	/************************************ Class Attributes *************************************/
	private static NumbrixSystem system;
	private static  Snake snake;
	private static HeuristicSearch heuristic;
	private ConstraintSearch constraint;
	private long startTime;
	private long endTime;
	
	/************************************ Class Methods *************************************/
	public Solver(NumbrixSystem system)
	{
		this();
		Solver.system = system;
	} /* end constructor */
	
	public Solver()
	{
		this.constraint = new ConstraintSearch(this);
	} /* end overdriven constructor */
	
	/**
	 * Solves the Numbrix problem
	 */
	public void solve()
	{
		// Start by getting start time
		Calendar cal = Calendar.getInstance();
		this.startTime = cal.getTimeInMillis();
		
		// Do Search
		this.initialize();
		this.constraintSatisfactionSearch();
		
		// End by getting end time
		this.endTime = cal.getTimeInMillis();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(dateFormat.format(cal.getTime()));
	} /* end solve method */
	
	/**
	 * Initialize datastructures for search
	 */
	protected void initialize()
	{
		Solver.snake = new Snake(Solver.system.getGridSize(), Solver.system.getGrid());
		Solver.heuristic = new HeuristicSearch();
		SearchMethod.setSnake(Solver.snake);
		SearchMethod.setSystem(Solver.system);
	} /* end initialize method */
	
	/**
	 * A Constraint Satisfaction Search on Numbrix that will be used recursively by 
	 * HeuristicSearch and return whether or not a solution was found.
	 * @return	whether or not a solution was found
	 */
	protected boolean constraintSatisfactionSearch()
	{
		boolean solved = constraintSearch();
		/* If no constraints were found, attempt the heuristic */
		if (!solved) solved = Solver.heuristic.startSearch(this);
		
		return solved;
	} /* end constraintSatisfactionSearch method */
	
	/**
	 * Performs the constraint search and returns if the grid is solved.
	 * @return	state of solution
	 */
	protected boolean constraintSearch()
	{
		boolean solved = false;
		boolean constraintFound = true;
		boolean forwardFound = true;
		boolean backwardFound = true;
		
		/* Keep doing search until no more changes can be made */
		while( (forwardFound || backwardFound) && !solved)
		{
			/* Re-initialize variables */
			constraintFound = true;
			forwardFound = false;
			backwardFound = false;
			
			/* First, do a constraint search in the forward direction until no constraints are left */
			while(constraintFound)
			{
				constraintFound = this.constraint.startSearch(true);
				if(!forwardFound && constraintFound) forwardFound = true;
			} /* end constraint search in forwards direction */
			
			/* Secondly, attempt the constraint in the backwards direction until no constraints are left */
			constraintFound = true;
			while(constraintFound)
			{
				constraintFound = this.constraint.startSearch(false);
				if(!backwardFound && constraintFound) backwardFound = true;
			} /* end constraint search in backwards direct */
			
			/* Check to see if the grid is solved */
			solved = this.check();
			
		} /* end while loop */
		
		return solved;
	} /* end constraintSearch method */
	
	/**
	 * Returns the amount of time spent solving the Numbrix grid
	 * @return	the time spent solving the Numbrix grid
	 */
	public long getTimeSpent()
	{
		return this.endTime - this.startTime;
	} /* end getTimeSpent method */
	
	/**
	 * Check to see if the gird has been solved
	 * @return	whether or not the grid has been solved
	 */
	public boolean check()
	{
		boolean solved = false;
		
		/* Check to see if done */
		if(Solver.snake.size() == 1 && Solver.snake.count() == Solver.system.getNumOfObjects())
		{
			/* Check to see if solved */
			Validator.State state;
			state = Solver.system.verify();
			if(state == Validator.State.CORRECT) solved = true;
		} /* end if */
		
		return solved;
	} /* end check method */
	
	/**
	 * Add the given values to the grid and structures
	 * @param x		the x position of the object being added
	 * @param y		the y position of the object being added
	 * @param val	the value of the object being added
	 */
	public void add(int x, int y, int val)
	{
		this.add(new Triple(x,y,val));
	} /* end add method */
	
	/**
	 * Add the given triple to the grid and structures
	 * @param triple	the triple being added
	 */
	public void add(Triple triple)
	{
		Solver.system.modifyGrid(triple.getX(), triple.getY(), triple.getValue());
		Solver.snake.add(triple);
	} /* end overloaded add method */
	
	/**
	 * Remove the given values from the grid and structures
	 * @param x		the x position of the object being removed
	 * @param y		the y position of the object being removed
	 * @param val	the value of the object being removed
	 */
	public void remove(int x, int y, int val)
	{
		Solver.system.modifyGrid(x, y, null);
		Solver.snake.remove(val);
	} /* end remove method */
	
	/**
	 * Remove the triple from the grid and structures
	 * @param triple	the triple to be removed
	 */
	public void remove(Triple triple)
	{
		this.remove(triple.getX(), triple.getY(), triple.getValue());
	} /* end overloaded remove method */
	
	/**
	 * Removes modifications made form the constraint search
	 */
	public void undo()
	{
		this.constraint.undo();
	} /* end undo method */
	
	public String snakeString()
	{
		return Solver.snake.toString();
	} /* end snakeString method */
	
	protected ConstraintSearch getConstraint()
	{
		return this.constraint;
	} /* end getConstraintSearch method */
	
	protected HeuristicSearch getHeuristic()
	{
		return Solver.heuristic;
	} /* end getConstraintSearch method */
	
} /* end Solver class */
