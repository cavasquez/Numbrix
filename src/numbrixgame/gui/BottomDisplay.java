package numbrixgame.gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;

/*****************************************************************************************************
 * BottomDisplay will take care of the bottom display in the GUI. This will contain a text box that will
 * be used to display messages to the user.
 *****************************************************************************************************/

public class BottomDisplay extends TextArea
{
	/*************************** Class Cosntants ***************************/
	private static final long serialVersionUID = 7643025143020057152L;
	private static final String START = 
			"Welcome to Numbrix. To start a new game, please go to File -> New Game.\n" +
			"The rules of the game are simple. The goal is to create a grid of numbers\n" +
			"such that you can create an unbroken line (vertical or horizonal but not\n" +
			"diagonal) from the number 1 to the last number. To check if your solution\n" +
			"is correct, hit the 'Finish' button. To input a value, simply click on the\n" +
			"cell you wish to modify and type in the number you wish to place into the\n" +
			"cell. To delete a value, simply click on the cell you wish to remove a value\n" +
			"from and hit backspace until there is no value left. Good luck!";

	/*************************** Class Methods ***************************/
	public BottomDisplay()
	{
		super(START);
		this.setFont(new Font("Serif", Font.PLAIN, 15));
		this.setPreferredSize(new Dimension(GUI.WIDTH, 150));
		
	} /* end constructor */
} /* end BottomDisplay class */
