package numbrixgame.system.solver;

import numbrixgame.numbrix;

public class HeuristicTest extends SearchTest
{
	public HeuristicTest(int testNum) 
	{
		super(testNum);
	} /* end constructor */

	public static void main(String args[])
	{
		HeuristicTest test = new HeuristicTest(0);
		test.startTest();
	} /* end main method */

	@Override
	public void startTest() 
	{
		numbrix.system.printGrid();
		print(this.solver.snakeString());
		print("Starting Heuristic Test");
//		HeuristicSearch test = new HeuristicSearch(this);
		
	} /* end startTest method */

} /* end HeuristicTest class */
