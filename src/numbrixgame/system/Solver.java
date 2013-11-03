package numbrixgame.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Solver will solve the Numbrix game
 * @author Carlos
 *
 */

public class Solver 
{
	/************************************ Class Constants *************************************/
	
	/************************************ Class Attributes *************************************/
	private NumbrixSystem system;
	private long startTime;
	private long endTime;
	private Snake snake;
	
	/************************************ Class Methods *************************************/
	public Solver(NumbrixSystem system)
	{
		this.system = system;
	} /* end constructor */
	
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
	private void initialize()
	{
		this.snake = new Snake(this.system.getGridSize(), this.system.getGrid());
	} /* end initialize method */
	
	/** 
	 * A Constraint Satisfaction Search on Numbrix
	 */
	private void constraintSatisfactionSearch()
	{
		boolean solved = false;
		boolean constraintFound = false;
		while(!solved)
		{
			constraintFound = this.startConstraintSearch();
			if (constraintFound) this.startHeuristicSearch();
			constraintFound = false;
		} /* end while loop */
	} /* end constraintSatisfactionSearch method */
	
	/**
	 * Begin the constraint search
	 * @return	whether or not a constraint was found
	 */
	private boolean startConstraintSearch()
	{
		boolean returner = false;
		
		return returner;
	} /* end startConstraintSearch method */
	
	/**
	 * Attempt to solve Numbrix by applying constraints
	 */
	private boolean constraintSearch()
	{
		boolean returner = false;
		
		return returner;
	} /* end constraintSearch method */
	
	/**
	 * Start the heuristic search
	 * @return	whether or not a heuristic was found
	 */
	private boolean startHeuristicSearch()
	{
		boolean returner = false;
		
		return returner;
	} /* end startHeuristicSearch method */
	
	/**
	 * Guess the shortest path
	 */
	private boolean heuristicSearch()
	{
		boolean returner = false;
		
		return returner;
	} /* end startHeuristicSearch method */
	
} /* end Solver class */
