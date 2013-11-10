package numbrixgame.system.solver;

import numbrixgame.numbrix;

public class ConstraintTest extends SearchTest
{
	private ConstraintSearch constraint;
	
	public ConstraintTest(int testNum) 
	{
		super(testNum);
	} /* end constructor */

	public static void main(String args[])
	{
		ConstraintTest test = new ConstraintTest(1);
		System.out.println("Test: " + test.testNum);
		test.startTest();
	} /* end main method */

	@Override
	public void startTest() 
	{
		this.printOn = true;
		numbrix.system.printGrid();
		print(this.solver.snakeString());
		
		//Integer[] a = this.constraint.snake.findEnds(55);
		//print("55: " + a[0] + " " + a[1]);
		
		print("Actual test starts now");
		boolean constraintFound = false;
		Triple triple = null;
		constraint = this.solver.getConstraint();
		boolean solved = this.solver.constraintSearch();
		
		numbrix.system.printGrid();
		print(this.solver.snakeString());
		print("Solved: " + solved);
		
		constraint.undo();
		numbrix.system.printGrid();
		print(this.solver.snakeString());
		
//		triple = this.constraint.snake.find(5);
//		print(triple.toString());
//		constraintFound = this.constraint.startSearch(false);
//		print("Constraint found?: " + constraintFound);
		
//		constraintFound = this.constraint.startSearch(true);
//		print("Constraint found?: " + constraintFound);
//		
//		numbrix.system.printGrid();
//		print(this.solver.snakeString());

	} /* end startTest method */

} /* end HeuristicTest class */
