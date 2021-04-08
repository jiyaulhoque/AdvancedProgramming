package view;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

import controller.Controller;
import model.Model;

/**
 * The Class GUI that represents the main view in MVC pattern. It contains all
 * GUI components (panels, menu bar).
 */
public class GUI extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The menu bar view. */
	private MenuBarView menuBarView = new MenuBarView();

	/** The options view. */
	private OptionsView optionsView = new OptionsView();

	/** The progress view. */
	private ProgressView progressView = new ProgressView();

	/** The result view. */
	private ResultView resultView = new ResultView(this);

	/**
	 * Instantiates a new gui.
	 *
	 * @param title the title
	 */
	public GUI(String title) {
		super(title);

		initComponents();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		setJMenuBar(menuBarView);
		add(optionsView, BorderLayout.NORTH);
		add(progressView, BorderLayout.CENTER);
		add(resultView, BorderLayout.SOUTH);
	}

	/**
	 * Gets the menu view.
	 *
	 * @return the menu view
	 */
	public MenuBarView getMenuView() {
		return menuBarView;
	}

	/**
	 * Gets the options view.
	 *
	 * @return the options view
	 */
	public OptionsView getOptionsView() {
		return optionsView;
	}

	/**
	 * Exits the program.
	 */
	public void exit() {
		setVisible(false);
		dispose();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		Model model = new Model();
		GUI view = new GUI("Image Classification");
		Controller controller = new Controller(model, view);
		controller.init();
		view.setVisible(true);
	}

	/**
	 * Gets the progress view.
	 *
	 * @return the progress view
	 */
	public ProgressView getProgressView() {
		return progressView;
	}

	/**
	 * Gets the result view.
	 *
	 * @return the result view
	 */
	public ResultView getResultView() {
		return resultView;
	}

}
