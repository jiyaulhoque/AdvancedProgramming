package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * The Class KNN represents KNN algorithm to find the k-nearest neighbor. It is
 * also a part of Observer pattern along with {@link Model}:
 * {@link KNN#progress} variable calculates the % of progress of knn algorithm
 * and this value is needed to be displayed in ProgressView window panel, so
 * every time it is changed, the Model is notified and changes accordingly.
 */
public class KNN extends Observable {

	/** The progress value in %. */
	private int progress;

	/**
	 * True if run the algorithm is allowed, otherwise false (if user clicks on
	 * 'Stop' button).
	 */
	private boolean allowRun;

	/** The estimated label. */
	private int estimated_label;

	/** The confidence. */
	private double confidence;

	/**
	 * For multiple test images: num_correct_classifications/num_test_images (%).
	 */
	private int accuracy;

	/**
	 * Instantiates a new KNN. Run is allowed by default.
	 */
	public KNN() {
		allowRun = true;
	}

	/**
	 * Runs the algorithm.
	 *
	 * @param k             the k value
	 * @param train_dataset the train dataset
	 * @param test_dataset  the test dataset
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void run(int k, List<RowData> train_dataset, List<RowData> test_dataset) throws IOException {

		List<DistLblData> distance_lbl_array = new ArrayList<>();
		int numData = 0;
		int num_correct_classifications = 0;
		int num_test_images = test_dataset.size();
		int totalData = train_dataset.size() * num_test_images;
		outer: for (RowData d_img : test_dataset) { // Iterate over all test files.
			for (int l = 0; l < train_dataset.size(); l++) { // Iterate over all train files.
				if (!allowRun) { // Break the outer loop once 'stop' was clicked.
					break outer;
				}
				int[][] t_img = train_dataset.get(l).getImage(); // Store current train image.
				int t_lbl = train_dataset.get(l).getLabel(); // Store current train label.
				double eD = 0; // Stores final Euclidean distance for all pixels.

				for (int i = 0; i < DataLoader.IMG_SIZE; i++) { // Loop through the row-pixels.
					for (int j = 0; j < DataLoader.IMG_SIZE; j++) { // Loop through the column-pixels.
						eD += Math.pow(t_img[i][j] - d_img.getImage()[i][j], 2);
					}
				}

				eD = Math.sqrt(eD); // Update Euclidean distance.
				distance_lbl_array.add(new DistLblData(eD, t_lbl)); // Add to the list along witht the label.
				numData++;
				progress = numData * 100 / totalData; // Update progress value.

				// Notify Model that progress value has changed.
				setChanged();
				notifyObservers();
			}

			// Sort by distances to have shortest distance at the first place of list.
			Collections.sort(distance_lbl_array, (e1, e2) -> Double.compare(e1.getDistance(), e2.getDistance()));

			// Apply K value.
			List<DistLblData> closest_k_label_array = distance_lbl_array.subList(0, k);

			// Sort by the frequencies of labels to have the label with the most frequencies
			// at the first
			// place of the list.
			Collections.sort(closest_k_label_array,
					(e1, e2) -> Integer.compare(Collections.frequency(closest_k_label_array, e2),
							Collections.frequency(closest_k_label_array, e1)));

			// Update estimated label.
			estimated_label = (int) closest_k_label_array.get(0).getLabel();

			// Update confidence.
			confidence = (double) Collections.frequency(closest_k_label_array, closest_k_label_array.get(0))
					/ closest_k_label_array.size();

			if (estimated_label == d_img.getLabel()) {
				num_correct_classifications++;
			}

			distance_lbl_array.clear();
		}

		// Update accuracy.
		accuracy = num_correct_classifications * 100 / num_test_images;
	}

	/**
	 * Gets the progress.
	 *
	 * @return the progress
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * Sets the allow run.
	 *
	 * @param allowRun the new allow run
	 */
	public void setAllowRun(boolean allowRun) {
		this.allowRun = allowRun;
	}

	/**
	 * Gets the estimated label.
	 *
	 * @return the estimated label
	 */
	public int getEstimated_label() {
		return estimated_label;
	}

	/**
	 * Gets the confidence.
	 *
	 * @return the confidence
	 */
	public double getConfidence() {
		return confidence;
	}

	/**
	 * Gets the accuracy.
	 *
	 * @return the accuracy
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * Sets the progress.
	 *
	 * @param progress the new progress
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}

}
