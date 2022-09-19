package app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**A simple dialog used to display text information.
 * */
public class TextDisplayDialog extends JDialog implements ActionListener{

	JButton close;
	JTextArea text_area;
	
	public TextDisplayDialog(Frame owner) {
		//Passes the parent window to the super class, sets a title. The last argument means that the user input is restricted to this dialog until closed.
		super(owner, "Student records", false);
		
		setLayout(new BorderLayout());
		
		close = new JButton("Close");
		close.setActionCommand("close");
		close.addActionListener(this);
		
		Font f = new Font("Lucida Console", Font.PLAIN,14);
		text_area = new JTextArea(10,3);
		text_area.setEditable(false);
		text_area.setFont(f);
		
		add(new JScrollPane(text_area), BorderLayout.CENTER);
		add(close, BorderLayout.SOUTH);
		
		pack();
		setSize(350, this.getHeight()+100);
		setLocationRelativeTo(owner);
		setResizable(false);
		
		//Upon closing, the dialog will free all resources and only close itself and not the whole program.
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**Appends any string information to the display area.
	 * */
	public void showDialog(String ... strings) {
		text_area.setText("");
		
		for(String s : strings)
			text_area.append(s+"\n\n\n");
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("close"))
			dispose();  //Causes the dialog to close and free any native resources (OS specific objects) taken to display the window.
	}

	
}//End of class
