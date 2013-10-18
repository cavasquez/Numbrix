package numbrixgame.gui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/*****************************************************************************************************
 * FileMenu will create the file menu that will be added to the menu tool bar
 *****************************************************************************************************/

public class FileMenu extends JMenu
{
	/*************************** Class Constants ***************************/
	private static final long serialVersionUID = 5538567671007854115L;

	/*************************** Class Methods ***************************/
	public FileMenu() 
	{
		super("File");
		this.setMnemonic(KeyEvent.VK_F);

		// Add the menu items to the file menu
		this.add(newMenuItem());
        this.add(resetMenuItem());
        this.add(exitMenuItem());

	} /* end constructor */
	
	private static final JMenuItem newMenuItem()
	{
		JMenuItem newMenuItem = new JMenuItem("New Game");
		newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setToolTipText("Start a new game");
        newMenuItem.addActionListener(new NewActionListener());
        return newMenuItem;
	} /* end  */
	
	private static final JMenuItem resetMenuItem()
	{
		JMenuItem resetMenuItem = new JMenuItem("Reset");
		resetMenuItem.setMnemonic(KeyEvent.VK_R);
        resetMenuItem.setToolTipText("Reset the game");
        resetMenuItem.addActionListener(new ResetActionListener());
        return resetMenuItem;
	} /* end  */
	
	private static final JMenuItem exitMenuItem()
	{
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
        exitMenuItem.setToolTipText("Exit application");
        exitMenuItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) 
            {
                System.exit(0);
            } /* end actionPerformed method */
        }); /* end addActionListener */
        return exitMenuItem;
	} /* end  */
	
} /* end FileMenu class */
