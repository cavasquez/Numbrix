package numbrixgame.gui.menubar;

import javax.swing.JMenuBar;

/**
 * Toolbar will create the toolbar to be used by Numbrix
 */

public class Menubar extends JMenuBar
{	
	/*************************** Class Constants ***************************/
	private static final long serialVersionUID = -8862283887174113187L;

	/*************************** Class Methods ***************************/
	public Menubar() 
	{
		super();
		this.add(new FileMenu());
	} /* end constructor */
	
} /* end Toolbar class */
