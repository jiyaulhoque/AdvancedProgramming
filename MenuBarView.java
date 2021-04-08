package view;

import javax.swing.*;

/**
 * The Class MenuBarView. Contains menu
 * File with Exit option.
 */
public class MenuBarView extends JMenuBar {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The menu item exit. */
	private JMenuItem menuItemExit = new JMenuItem("Exit");

	/**
	 * Instantiates a new menu bar view.
	 */
	public MenuBarView() {
		initComponents();
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		JMenu menuFile = new JMenu("File");
		menuFile.add(menuItemExit);
		add(menuFile);
	}

	/**
	 * Gets the menu item exit.
	 *
	 * @return the menu item exit
	 */
	public JMenuItem getMenuItemExit() {
		return menuItemExit;
	}

}
