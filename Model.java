package model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

/**
 * As a part of MVC pattern, this class contains all business logic of
 * application: validate inputs, update shared values, invoke KNN algorithm and
 * so on. It also uses Observer pattern to be observable for views and observe
 * KNN to change the state if {@link KNN#getProgress()} value is changed.
 */
public class Model extends Observable implements Observer {

	/** The Constant K_DEFAULT value (it can be any value from 1 to 10). */
	public static final int K_DEFAULT = 5;

	/** The knn reference. */
	private KNN knn;

	/** The source files. */
	private File[] sourceFiles;

	/** The test files. */
	private File testFiles;

	/** The k value. */
	private int k;

	/** True if algorithm is being performed, otherwise false. */
	private boolean started;

	/** True if algorithm has finished, otherwise false. */
	private boolean finished;

	/** The reference to the thread where knn is run. */
	private Thread knnThread;

	/** True if it is single test mode, otherwise false (multiple test files). */
	private boolean singleTest;

	/** True if reseted (back to initial state), otherwise false. */
	private boolean reseted;

	/**
	 * Instantiates a new model.
	 */
	public Model() {
		knn = new KNN();
		k = K_DEFAULT; // Default k value.
	}

	/**
	 * Sets the source files. Also checks whether the files are valid and if so,
	 * notifies views (observers).
	 *
	 * @param sourceFiles the new source files
	 */
	public void setSourceFiles(File[] sourceFiles) {
		if (isSrcValid(sourceFiles)) {
			this.sourceFiles = sourceFiles;
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Sets the test files. Also checks whether the files are valid and if so,
	 * notifies views (observers).
	 *
	 * @param testFiles the new test files
	 */
	public void setTestFiles(File testFiles) {
		if (isTestValid(testFiles)) {
			this.testFiles = testFiles;
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Checks if source files are valid.
	 *
	 * @param sourceFiles the source files
	 * @return true, if valid
	 */
	private boolean isSrcValid(File[] sourceFiles) {
		boolean result = true;
		if (sourceFiles != null) {
			for (File src : sourceFiles) {
				if (!src.getName().toLowerCase().endsWith(".bin")) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Checks if test files are valid.
	 *
	 * @param testFiles the test files
	 * @return true, if is test valid
	 */
	private boolean isTestValid(File testFiles) {
		boolean result = true;
		if (testFiles == null) {
			result = false;
		} else {
			if (testFiles.isDirectory()) {
				for (File test : testFiles.listFiles()) {
					if (!test.getName().toLowerCase().endsWith(".png")) {
						result = false;
						break;
					}
				}
			} else {
				if (!testFiles.getName().endsWith(".png")) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Gets the source files text.
	 *
	 * @return the source files text
	 */
	public String getSourceFilesText() {
		String output = "";
		if (sourceFiles != null) {
			output = sourceFiles[0].getName();
			if (sourceFiles.length > 1) {
				output += ", ...";
			}
		}
		return output;
	}

	/**
	 * Starts KNN algorithm. It runs KNN in a separate thread to avoid freezing the
	 * GUI.
	 */
	public void start() {
		started = true;
		reseted = false;
		finished = false;

		// Notify views.
		setChanged();
		notifyObservers();

		try {
			List<RowData> test_files = DataLoader.load(testFiles);
			singleTest = test_files.size() == 1;
			List<RowData> src_files = DataLoader.load(sourceFiles);
			knnThread = new Thread(() -> {
				try {
					knn.run(k, src_files, test_files);
					stop();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			knnThread.start();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid input file", "Error occurred", 
					JOptionPane.ERROR_MESSAGE);
			reset();
		}
	}

	/**
	 * Stops algorithm (user clicks on button 'stop').
	 */
	public void stop() {
		started = false;
		finished = true;
		knn.setAllowRun(false);
		setChanged();
		notifyObservers();
	}

	/**
	 * Resets the model to the initial state, clears result.
	 */
	public void reset() {
		started = false;
		reseted = true;
		finished = false;
		knn.setAllowRun(true);
		sourceFiles = null;
		testFiles = null;
		k = K_DEFAULT;
		knn.setProgress(0);
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets the test img filename.
	 *
	 * @return the test img filename
	 */
	public String getTestImgFilename() {
		return testFiles != null ? testFiles.toString() : "";
	}

	/**
	 * Gets the test files text.
	 *
	 * @return the test files text
	 */
	public String getTestFilesText() {
		return testFiles != null ? testFiles.getName() : "";
	}

	/**
	 * Gets the source files.
	 *
	 * @return the source files
	 */
	public File[] getSourceFiles() {
		return sourceFiles;
	}

	/**
	 * Gets the test files.
	 *
	 * @return the test files
	 */
	public File getTestFiles() {
		return testFiles;
	}

	/**
	 * Gets the k.
	 *
	 * @return the k
	 */
	public int getK() {
		return k;
	}

	/**
	 * Sets the k.
	 *
	 * @param k the new k
	 */
	public void setK(int k) {
		this.k = k;
	}

	/**
	 * Checks if is started.
	 *
	 * @return true, if is started
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * Gets the progress.
	 *
	 * @return the progress
	 */
	public int getProgress() {
		return knn.getProgress();
	}

	/**
	 * Gets the estimated label.
	 *
	 * @return the estimated label
	 */
	public int getEstimatedLabel() {
		return knn.getEstimated_label();
	}

	/**
	 * Gets the confidence.
	 *
	 * @return the confidence
	 */
	public double getConfidence() {
		return knn.getConfidence();
	}

	/**
	 * Gets the accuracy.
	 *
	 * @return the accuracy
	 */
	public int getAccuracy() {
		return knn.getAccuracy();
	}

	/**
	 * Updates model. It also notifies all views as well.
	 *
	 * @param o   the observable object (KNN)
	 * @param arg the argument passed (not used here)
	 */
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

	/**
	 * Checks if is finished.
	 *
	 * @return true, if is finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Checks if is single test.
	 *
	 * @return true, if is single test
	 */
	public boolean isSingleTest() {
		return singleTest;
	}

	/**
	 * Checks if is reseted.
	 *
	 * @return true, if is reseted
	 */
	public boolean isReseted() {
		return reseted;
	}

	/**
	 * Gets the knn.
	 *
	 * @return the knn
	 */
	public KNN getKnn() {
		return knn;
	}

}
