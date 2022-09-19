package gui;

import java.awt.Container;
import java.awt.GridBagConstraints;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**A single instance object (singleton) that can be accessed from anywhere in the program. The GridBagComponentHelper was implemented
 * to help with the tedious and repetitive task of adding components to an interface that uses the GridBag layout. This layout makes use
 * of GridBagConstraints that have to be set adequately in order to obtain the desired result. This class simplifies that by providing
 * a few easy to use functions.
 * */
public class GridBagComponentHelper {

	private static GridBagComponentHelper instance;
	private GridBagConstraints gbc;
	private Container parent;
	
	public GridBagComponentHelper() {
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		//Components will have 5 pixels of spacing in between them, on all 4 sides.
		gbc.insets.set(5, 5, 5, 5);
		
		resetPosition();
	}
	
	public static GridBagComponentHelper getInstance() {
		if(instance == null)
			instance = new GridBagComponentHelper();
		
		return instance;
	}
	
	/**The components are added from left to right, and from top to bottom ('reading' order). This function will
	 * reset the starting position from where the components will be added from.
	 * */
	private void resetPosition() {
		gbc.gridx = 0;
		gbc.gridy = 0;
	}
	
	/**This is used to tell the GridBagComponentHelper where to add components later on.
	 * */
	public void begin(Container parent) {
		this.parent = parent;
	}
	
	/**Adds a component with no span that fills all its cell space within the GridBag layout.
	 * */
	public void addComponent(JComponent child) {
		addComponent(child, 1,1, 1f, 1f);
	}
	
	/**Adds a component with no span that fills a specific amount of its cell space within the GridBag layout.
	 * */
	public void addComponent(JComponent child, double weightX, double weightY){
		addComponent(child, 1,1, weightX, weightY);
	}
	
	/**Adds a component with specified span that fills all its cell space within the GridBag layout.
	 * */
	public void addComponent(JComponent child, int spanWidth, int spanHeight){
		addComponent(child, spanWidth, spanHeight, 1f, 1f);
	}
	
	/**Adds a component with specified span that fills a specific amont of its cell space within the GridBag layout.
	 * */
	public void addComponent(JComponent child, int spanWidth, int spanHeight, double weightX, double weightY) {
		if(parent == null)
			return;
		
		gbc.gridwidth = spanWidth;
		gbc.gridheight = spanHeight;
		gbc.weightx = weightX;
		gbc.weighty = weightY;
		
		parent.add(child,gbc);
		
		//This will cause the next object to be placed to the right of the previous one, unless newRow() is called
		gbc.gridx++;
	}
	
	/**Changes the constraints the next object will be added on a new row and reseting the horizontal position.
	 * */
	public void newRow() {
		gbc.gridx = 0;
		gbc.gridy++;
	}
	
	/**Releasing the current object that components can be added to.
	 * */
	public void end() {
		parent = null;
		resetPosition();
	}
	
}//end of class
