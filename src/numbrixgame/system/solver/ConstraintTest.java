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
		System.out.println("Starting Cosntraint Test 2");
		ConstraintTest test = new ConstraintTest(1);
		test.startTest();
	} /* end main method */

	@Override
	public void startTest() 
	{
		this.printOn = true;
		numbrix.system.printGrid();
		print(this.solver.snakeString());
		
		print("Actual test starts now");
		boolean constraintFound = false;
		Triple triple = null;
		constraint = this.solver.getConstraint();
		
//		triple = this.constraint.snake.find(5);
//		print(triple.toString());
		constraintFound = this.constraint.startSearch(true);
		print("Constraint found?: " + constraintFound);
		
		numbrix.system.printGrid();
		print(this.solver.snakeString());
		
//		constraintFound = this.constraint.startSearch(true);
//		print("Constraint found?: " + constraintFound);
//		
//		numbrix.system.printGrid();
//		print(this.solver.snakeString());

	} /* end startTest method */

} /* end HeuristicTest class */
