package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Model;

/**
 * The Class OptionsView. Contains buttons, labels, inputs to load the data and
 * start the algorithm.
 */
public class OptionsView extends JPanel implements Observer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant WIDTH. */
	private static final int WIDTH = 500;

	/** The Constant HEIGHT. */
	private static final int HEIGHT = 200;

	/**
	 * Open file chooser in the current directory (".").
	 */
	private static final JFileChooser FILE_CHOOSER = new JFileChooser(".");

	/**
	 * File extension filter for .gin input files.
	 */
	private static final FileNameExtensionFilter SRC_FILTER = new FileNameExtensionFilter("Binary files", "bin");

	/**
	 * File extension filter for .png test files.
	 */
	private static final FileNameExtensionFilter TEST_FILTER = new FileNameExtensionFilter("PNG files", "png");

	/** The label select K. */
	private JLabel lblSelectK = new JLabel("Select K value");

	/** The label select source. */
	private JLabel lblSelectSrc = new JLabel("Select source (.bin) file(s)");

	/** The label select test. */
	private JLabel lblSelectTest = new JLabel("Select test (.png) image(s)");

	/** The input K. */
	private JSpinner inputK = new JSpinner(new SpinnerNumberModel(Model.K_DEFAULT, 1, 10, 1));

	/** The button source. */
	private JButton btnSrc = new JButton("Browse...");

	/** The button test. */
	private JButton btnTest = new JButton("Browse...");

	/** The button start. */
	private JButton btnStart = new JButton("START");

	/** The button reset. */
	private JButton btnReset = new JButton("RESET");

	/** The label for source files. */
	private JLabel lblSrc = new JLabel();

	/** The label for test files. */
	private JLabel lblTest = new JLabel();

	/**
	 * Instantiates a new options view.
	 */
	public OptionsView() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBorder(BorderFactory.createTitledBorder("Options"));
		setLayout(new GridLayout(4, 3, 10, 25));
		initComponents();
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		btnStart.setEnabled(false); // To make 'START' enable, the user has to select required files.
		btnReset.setEnabled(false); // Reset is enable only after data processing.
		btnSrc.setToolTipText("Only .bin file(s) is allowed!");
		btnTest.setToolTipText("Only .png file(s) is allowed! Also you can select folder with only .png files.");
		add(lblSelectK);
		add(inputK);
		add(Box.createHorizontalBox());
		add(lblSelectSrc);
		add(btnSrc);
		add(lblSrc);
		add(lblSelectTest);
		add(btnTest);
		add(lblTest);
		add(btnStart);
		add(btnReset);
	}

	/**
	 * Gets the source files.
	 *
	 * @return the source files
	 */
	public File[] getSourceFiles() {
		File[] result = null;
		FILE_CHOOSER.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FILE_CHOOSER.setFileFilter(SRC_FILTER);
		FILE_CHOOSER.setMultiSelectionEnabled(true);
		int returnVal = FILE_CHOOSER.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			result = FILE_CHOOSER.getSelectedFiles();
		}
		return result;
	}

	/**
	 * Gets the test files.
	 *
	 * @return the test files
	 */
	public File getTestFiles() {
		File result = null;
		FILE_CHOOSER.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FILE_CHOOSER.setFileFilter(TEST_FILTER);
		FILE_CHOOSER.setMultiSelectionEnabled(false);
		int returnVal = FILE_CHOOSER.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			result = FILE_CHOOSER.getSelectedFile();
		}
		return result;
	}

	/**
	 * Gets the input K.
	 *
	 * @return the input K
	 */
	public JSpinner getInputK() {
		return inputK;
	}

	/**
	 * Gets the button source.
	 *
	 * @return the button source
	 */
	public JButton getBtnSrc() {
		return btnSrc;
	}

	/**
	 * Gets the button test.
	 *
	 * @return the button test
	 */
	public JButton getBtnTest() {
		return btnTest;
	}

	/**
	 * Gets the button start.
	 *
	 * @return the button start
	 */
	public JButton getBtnStart() {
		return btnStart;
	}

	/**
	 * Gets the label source.
	 *
	 * @return the label source
	 */
	public JLabel getLblSrc() {
		return lblSrc;
	}

	/**
	 * Gets the label test.
	 *
	 * @return the label test
	 */
	public JLabel getLblTest() {
		return lblTest;
	}

	/**
	 * Updates the view according to the model state.
	 *
	 * @param o   the observable object (Model)
	 * @param arg the passed argument (not used here)
	 */
	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		update(model);
	}

	/**
	 * Helper method to update this view according to the current state of
	 * {@link Model}.
	 * 
	 * @param model {@link Model}
	 */
	private void update(Model model) {

		btnSrc.setEnabled(!model.isFinished() && !model.isStarted());
		btnStart.setEnabled(!model.isStarted());
		btnTest.setEnabled(!model.isFinished() && !model.isStarted());
		inputK.setEnabled(!model.isFinished() && !model.isStarted());
		btnReset.setEnabled(model.isFinished());
		lblSrc.setText(model.getSourceFilesText());
		lblTest.setText(model.getTestFilesText());
		btnStart.setEnabled(!model.isFinished() && !model.isStarted() && !lblSrc.getText().isEmpty()
				&& !lblTest.getText().isEmpty());
		inputK.setValue(model.getK());
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
