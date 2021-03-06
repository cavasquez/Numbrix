package numbrixgame.gui;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import numbrixgame.numbrix;
import numbrixgame.system.Validator;

/**
 * Table will be the table created that will act as the UI for Numbrix.
 */

public class Table extends JTable
{
	/*************************** Class Constants ***************************/
	private static final long serialVersionUID = -8095017388954201753L;
	
	/*************************** Class Attributes ***************************/
	private boolean[][] startData;		// The starting data (non modifiable data)
	
	/*************************** Class Methods ***************************/
	public Table (int tableSize, boolean[][] staticData, Integer[][] grid)
	{
		super(tableSize, tableSize);
		
		// Set defaults
		this.setColumnSelectionAllowed(false);
		this.setRowSelectionAllowed(false);
		this.setDragEnabled(false);
		this.populate(tableSize, grid);
		this.getTableHeader().setReorderingAllowed(false);
		this.startData = staticData;
		
		// Center content
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		for(int i = 0; i < tableSize; i++) this.getColumnModel().getColumn(i).setCellRenderer(renderer);
		
	} /* end constructor */
	
	/*------------------ Public Methods ------------------*/
	@Override
	public boolean isCellEditable(int row, int column)
	{// Disable those cells with values that start with the board
		return !(startData[numbrix.system().getGridSize() - column - 1][row]);
	} /* end editCellAt method */
	
	/**
	 * Returns a 2D array that represents the table
	 * @return	a 2D array that represents the table
	 */
	public Integer[][] getGrid()
	{
		Integer[][] grid = numbrix.system().makeGrid();
		for(int i = 0; i < numbrix.system().getGridSize(); i++)
		{
			for(int j = 0; j < numbrix.system().getGridSize(); j++)
			{
				try
				{
					grid[i][j] = Integer.parseInt(this.getValueAt(i, j).toString());
				} /* end try */
				catch(NumberFormatException e)
				{
					grid[i][j] = null;
				} /* end catch */
				catch(NullPointerException e)
				{
					grid[i][j] = null;
				} /* end catch */
				
			} /* end for loop */
		} /* end for loop */
		
		return grid;
	} /* end getGrid method */
	
	@Override
	public void setValueAt(Object value, int row, int column)
	{
		this.setValueAt(value, row, column, true);
	} /* end setValueAt method */
	
	public void setValueAt(Object value, int row, int column, boolean modifyGrid)
	{// Keep track of changes
		boolean isNull = false;
		
		// Check if value was deleted
		try
		{
			if(value.toString().compareTo("") == 0) isNull = true;
		} /* end try */
		catch(NumberFormatException e)
		{
			isNull = true;
		} /* end catch */
		
		Integer val = null;
		// Check if value is non numeric
		try
		{
			val = Integer.parseInt(value.toString());
		} /* end try */
		catch(NumberFormatException e)
		{
			/* Do nothing */
		} /* end catch */

		if(val == null && !isNull)
		{// This is a non integer, do not add and alert user
			numbrix.gui().printMessage(Validator.State.INCORRECT_ELEMENT.string());
		} /* end if */
		else
		{// This is a valid input. Log and apply change
			if(modifyGrid) numbrix.system().modifyGrid(column, numbrix.system().getGridSize() - row - 1, val);
			super.setValueAt(value, row, column);
			
			// Update GUI
			if(modifyGrid) numbrix.gui().changeHistory(numbrix.system().getHistory());
		} /* end else */
		
	} /* end setValueAt method */
	
	/*------------------ Private methods ------------------*/
	/**
	 * Populates the table with the given grid
	 * @param tableSize	the size of the table
	 * @param grid		the grid that will populate the table
	 */
	private void populate(int tableSize, Integer[][] grid)
	{// Go through the grid and populate the table as needed
		for(int i = 0; i < tableSize; i++)
		{
			for(int j = 0; j < tableSize; j++)
			{
				if(grid[i][j] != null) this.setValueAt(grid[i][j], numbrix.system().getGridSize() - i - 1, j, false);
			} /* end for loop */
		} /* end for loop */
	} /* end  */
	
} /* end MainDisplay class */
