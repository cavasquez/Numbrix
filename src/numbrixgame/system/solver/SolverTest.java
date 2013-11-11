package numbrixgame.system.solver;

import numbrixgame.numbrix;

public class SolverTest extends SearchTest
{

	public SolverTest(int testNum) 
	{
		super(testNum);
	} /* end constructor */
	
	public static void main(String args[])
	{
		SolverTest test = new SolverTest(1); // Note 0 is the only test that makes use of heuristic search
		System.out.println("Test: " + test.testNum);
		test.startTest();
	} /* end main method */

	@Override
	public void startTest() 
	{
		this.printOn = true;
		numbrix.system.printGrid();
		print(this.solver.snakeString());
		
		boolean constraintFound = false;
		Triple triple = null;
		this.solver.solve();
		
		print("Solver done!");
		numbrix.system.printGrid();
		print(this.solver.snakeString());

		System.out.println("Log:");
		System.out.println(numbrix.system.getHistory());
		
	} /* end startTest method */

} /* end SolverTest class */
