package numbrixgame.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import numbrixgame.gui.leftdisplay.LeftDisplay;
import numbrixgame.gui.menubar.Menubar;
import numbrixgame.system.NumbrixSystem;


/*****************************************************************************************************
 * GUI will be the gui that acts as the "view" for Numbrix
 *****************************************************************************************************/

public class GUI extends JFrame
{
	/************************************ Class Constants *************************************/
	private static final long serialVersionUID = 7225797195421921688L;
	private static final int PADDING = 10;
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
	public void printMessage(String message)
	{ /* print the message via bottom */
		bottom.setText(message);
		
		// Re render
		this.revalidate();
	} /* end printMessage method */
	
	public void addTable(int tableSize, boolean[][] staticData, Integer[][] startData)
	{
		this.removeTable();
		this.table = new Table(tableSize, staticData, startData);
		
		// Add MainDisplay to center
		this.add(table, BorderLayout.CENTER);
		
		System.out.println();
		// Re render
		this.revalidate();
	} /* end setMainDisplay */
	
	public void removeTable()
	{
		if(this.table != null) this.remove(this.table);
		this.table = null;
		
		// Re render
		this.revalidate();
	} /* end removeMainDisplay method */
	
	public void addLeftDisplay(NumbrixSystem.Player playerType)
	{
		this.removeLeftDisplay();
		this.left = new LeftDisplay(playerType);
		this.add(new LeftDisplay(playerType), BorderLayout.WEST);
		
		// Re render
		this.revalidate();
	} /* end addLeftDisplay method */
	
	public void removeLeftDisplay()
	{
		if(this.left != null)this.remove(this.left);
		this.left = null;
		
		// Re render
		this.revalidate();
	} /* end remoevLeftDisplay */
	
	public void changeHistoyr(String newHistory)
	{
		this.history.setText(newHistory);
		this.revalidate();
	} /* end changeHistory method */
	
	/*------------------ Private methods ------------------*/
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
	public Table getTable()
	{
		return this.table;
	} /* end getTable method */
	
} /* end GUI class */
