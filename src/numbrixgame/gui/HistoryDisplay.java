package numbrixgame.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;

/**
 * HistoryDisplay will display the history of the game
 */

public class HistoryDisplay extends TextArea
{
	/*************************** Class Cosntants ***************************/
	private static final long serialVersionUID = -6525825207615925232L;
	
	/*************************** Class Methods ***************************/
	public HistoryDisplay()
	{
		super();
		this.setFont(new Font("Serif", Font.PLAIN, 15));
		this.setPreferredSize(new Dimension(300, GUI.HEIGHT));
		
	} /* end constructor */
	
	@Override
	public void setText(String text)
	{
		text = "History\n" + text;
		super.setText(text);
	} /* end setText method */
	
} /* end HistoryDisplay class */
