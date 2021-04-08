package view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.Model;

/**
 * The Class ProgressView. It contains gui components to stop/reset and progress
 * bar.
 */
public class ProgressView extends JPanel implements Observer {

	/** The Constant WIDTH. */
	private final static int WIDTH = 500;

	/** The Constant HEIGHT. */
	private final static int HEIGHT = 100;

	/** The progress bar. */
	private JProgressBar progressBar = new JProgressBar(1, 100);

	/** The button stop. */
	private JButton btnStop = new JButton("STOP");

	/** The button reset. */
	private JButton btnReset = new JButton("RESET");

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new progress view.
	 */
	public ProgressView() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBorder(BorderFactory.createTitledBorder("Progress"));
		initComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		progressBar.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int) (HEIGHT * 0.4)));
		progressBar.setStringPainted(true);
		btnStop.setEnabled(false);
		add(progressBar);
		add(btnStop);
	}

	/**
	 * Updates the view.
	 *
	 * @param o   the the observable object (Model)
	 * @param arg the passed argument (not used here)
	 */
	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		progressBar.setValue(model.getProgress());
		btnStop.setEnabled(model.isStarted());
	}

	/**
	 * Gets the button stop.
	 *
	 * @return the button stop
	 */
	public JButton getBtnStop() {
		return btnStop;
	}

	/**
	 * Gets the button reset.
	 *
	 * @return the button reset
	 */
	public JButton getBtnReset() {
		return btnReset;
	}

}
