package numbrixgame;

import javax.swing.SwingUtilities;

import numbrixgame.gui.GUI;
import numbrixgame.system.NumbrixSystem;

/*****************************************************************************************************
 * Numbrix will be the "main" of the project. It will construct the components needed to run and then
 * run the program.
 *****************************************************************************************************/

public class numbrix
{
	/*************************** Class Attributes ***************************/
	protected static NumbrixSystem system;
	protected static GUI gui;
	
	/*************************** Class Methods ***************************/	
	/*------------------ Main method ------------------*/
	public static void main(String[] args)
	{
		numbrix.initializeSystem();
		numbrix.initializeUI();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				gui.setVisible(true);
			} /* end new runnable */
		}); /* end creation of runnable */
		
	} /* end main method */
	
	/*------------------ Public methods ------------------*/
	public static final NumbrixSystem system()
	{
		return system;
	} /* end system method */
	
	public static final GUI gui()
	{
		return gui;
	} /* end gui method */
	
	/*------------------ Private methods ------------------*/
	protected static final void initializeUI()
	{// Create the UI
		numbrix.gui = new GUI();
	} /* end initializeUI method */
	
	protected static final void initializeSystem()
	{// Create the system
		numbrix.system = new NumbrixSystem();
	} /* end initializeSystem method */
	
} /* end Numbrix class */
