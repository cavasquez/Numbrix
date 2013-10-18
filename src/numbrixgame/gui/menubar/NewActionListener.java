package numbrixgame.gui.menubar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import numbrixgame.numbrix;
import numbrixgame.system.NumbrixSystem;

/*****************************************************************************************************
 * The ActionListener that will be used by the New Menu Item. The action should open up a JFileChooser
 * and interact with the system accordingly
 *****************************************************************************************************/

public class NewActionListener implements ActionListener
{
	/*************************** Class Methods ***************************/
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Create the panel that the file chooser will exist in
		JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
		JFileChooser fileChooser = new JFileChooser(); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
		fileChooser.addChoosableFileFilter(filter);
		
		// Proceed to check the file chosen
		int returner = fileChooser.showDialog(panel, "Choose File");
		if(returner == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			
			// Now, find out if a human or computer is playing
			panel = new JPanel();
			panel.add(new JLabel("Who will be playing?"));
			DefaultComboBoxModel<String> options = new DefaultComboBoxModel<String>();
			options.addElement(NumbrixSystem.Player.HUMAN.string());
			options.addElement(NumbrixSystem.Player.COMPUTER.string());
			
			JComboBox comboBox = new JComboBox(options);
			panel.add(comboBox);
			
			// Figure out choice
			String result = "";
			returner = JOptionPane.showConfirmDialog(null, panel, "Player", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(returner == JOptionPane.OK_OPTION)
			{// Get the result
				result = (String) comboBox.getSelectedItem();
				
				// Provide the system the necessary information
				NumbrixSystem.Player player;
				if(result.compareTo(NumbrixSystem.Player.COMPUTER.string()) == 0) player = NumbrixSystem.Player.COMPUTER;
				else player = NumbrixSystem.Player.HUMAN;
				numbrix.system().setup(player, file);
			} /* end if */			
			
		} /* end if */
		
	} /* end actionPerformed method */

} /* end NewActionListener class */
