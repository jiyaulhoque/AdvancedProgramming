package view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.Model;

/**
 * The Class ResultView.
 * It contains labels to display a result of the KNN algorithm.
 * As per the project specification, there are 2 variants: when 
 * single test image is used, and when multiply tests are used.
 */
public class ResultView extends JPanel implements Observer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant FONT. */
	private final static Font FONT = new Font("Arial", Font.BOLD, 20);

	/** The Constant WIDTH. */
	private final static int WIDTH = 500;

	/** The Constant HEIGHT. */
	private final static int HEIGHT = 100;

	/** The label correct. */
	private JLabel lblCorrect = new JLabel();

	/** The label test image. */
	private JLabel lblTestImg = new JLabel();

	/** The label accuracy. */
	private JLabel lblAccuracy = new JLabel();

	/** The label confidence. */
	private JLabel lblConfidence = new JLabel();

	/** The gui. */
	private GUI gui;

	/**
	 * Instantiates a new result view.
	 *
	 * @param gui the gui
	 */
	public ResultView(GUI gui) {
		this.gui = gui;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBorder(BorderFactory.createTitledBorder("Result"));
		setVisible(false);
		initComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		lblCorrect.setFont(FONT);
		lblAccuracy.setFont(FONT);
		lblConfidence.setFont(FONT);
		add(lblCorrect);
		add(lblConfidence);
		add(lblTestImg);
		add(lblAccuracy);
	}

	/**
	 * Updates the view.
	 *
	 * @param o   the observable object (Model)
	 * @param arg the passed argument (not used here)
	 */
	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		setVisible(model.isFinished());
		if (model.isFinished() || model.isReseted()) {
			lblCorrect.setVisible(model.isSingleTest());
			lblConfidence.setVisible(model.isSingleTest());
			lblTestImg.setVisible(model.isSingleTest());
			lblAccuracy.setVisible(!model.isSingleTest());
			lblCorrect.setText("Correct label: " + model.getEstimatedLabel());
			lblConfidence.setText("Confidence: " + String.format("%.1f", model.getConfidence()));
			lblTestImg.setIcon(new ImageIcon(model.getTestImgFilename()));
			lblAccuracy.setText("Accuracy for K = " + model.getK() + " is " + model.getAccuracy() + "%");
			gui.pack();
		}
	}

}
