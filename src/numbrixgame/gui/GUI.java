package numbrixgame.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import numbrixgame.numbrix;
import numbrixgame.gui.leftdisplay.LeftDisplay;
import numbrixgame.gui.menubar.Menubar;
import numbrixgame.system.NumbrixSystem;


/**
 * GUI will be the gui that acts as the "view" for Numbrix
 */

public class GUI extends JFrame
{
	/************************************ Class Constants *************************************/
	private static final long serialVersionUID = 7225797195421921688L;
	private static final String NAME = "Numbrix";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	private static final int DEFAULT_CLOSE_OP = EXIT_ON_CLOSE;
	
	/*************************** Class Attributes ***************************/
	private Table table;
	private BottomDisplay bottom;
	private LeftDisplay left;
	private HistoryDisplay history;
	
	/*************************** Class Methods ***************************/
	public GUI()
	{
		this.initializeUI();
	} /* end constructor */
	
	/*------------------ Public methods ------------------*/
	/**
	 * Sets the message as the text for bottom
	 * @param message	the message being set
	 */
	public void printMessage(String message)
	{ /* print the message via bottom */
		bottom.setText(message);
		
		// Re render
		this.revalidate();
	} /* end printMessage method */
	
	/**
	 * Overrides the current table with the startData and staticData
	 * @param tableSize		the size of the table
	 * @param staticData	the non-modifiable cells
	 * @param startData		the data to populate
	 */
	public void addTable(int tableSize, boolean[][] staticData, Integer[][] startData)
	{
		this.removeTable();
		this.table = new Table(tableSize, staticData, startData);
		
		// Add MainDisplay to center
		this.add(table, BorderLayout.CENTER);
		
		// Re render
		this.revalidate();
	} /* end setMainDisplay */
	
	/**
	 * Removes the table from GUI
	 */
	public void removeTable()
	{
		if(this.table != null) this.remove(this.table);
		this.table = null;
		
		// Re render
		this.revalidate();
	} /* end removeMainDisplay method */
	
	/**
	 * Renders the table
	 */
	public void revalidateTable()
	{
		this.addTable(numbrix.system().getGridSize(), numbrix.system().getStaticData(), numbrix.system().getGrid());
	} /* end revalidateTable */
	
	/**
	 * Creates the LeftDisplay to be used given the playerType
	 * @param playerType	the type of player (COMPUTER or HUMAN)
	 */
	public void addLeftDisplay(NumbrixSystem.Player playerType)
	{
		this.removeLeftDisplay();
		if(this.left == null)
		{
			this.left = new LeftDisplay();
			this.add(this.left, BorderLayout.WEST);
		}
		this.left.initialize(playerType);
		
		// Re render
		this.revalidate();
	} /* end addLeftDisplay method */
	
	/**
	 * Removes the contents in left and then removes left
	 */
	public void removeLeftDisplay()
	{
		if(this.left != null)this.left.removeAll();
		this.left = null;
		
		// Re render
		this.revalidate();
	} /* end remoevLeftDisplay */
	
	/**
	 * Change history to show the provided text
	 * @param newHistory	the text to be shown in history
	 */
	public void changeHistory(String newHistory)
	{
		this.history.setText(newHistory);
		this.revalidate();
	} /* end changeHistory method */
	
	/*------------------ Private methods ------------------*/
	/**
	 * Creates the basic UI
	 */
	private final void initializeUI()
	{// Create the UI
		// Set the menubar
		this.setJMenuBar(new Menubar());
		
		// Set defaults
		this.setTitle(NAME);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DEFAULT_CLOSE_OP);
		
		// Add BottomDisplay
		this.bottom = new BottomDisplay();
		this.add(this.bottom, BorderLayout.SOUTH);
		
		// Add right display
		this.history = new HistoryDisplay();
		this.add(history, BorderLayout.EAST);
		
	} /* end initializeUI method */
	
	/*------------------ Getter methods ------------------*/
	/**
	 * Returns table
	 * @return	table
	 */
	public Table getTable()
	{
		return this.table;
	} /* end getTable method */
	
} /* end GUI class */
