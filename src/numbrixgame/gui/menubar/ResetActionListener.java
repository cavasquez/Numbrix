package numbrixgame.gui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import numbrixgame.numbrix;

/*****************************************************************************************************
 * ResetActionListener will define what is to be done when Reset is clicked. It will reset the grid
 * back to its original values.
 *****************************************************************************************************/

public class ResetActionListener implements ActionListener
{
	/*************************** Class Methods ***************************/
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		numbrix.system().reset();
		
	} /* end actionPerformed method */

} /* end ResetActionListener class */
