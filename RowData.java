package model;

/**
 * 
 * Useful class to encapsulate 2D array of pixels (image) and corresponding
 * label (value from 0 to 9).
 *
 */
public class RowData {

	/**
	 * 2d array of pixels (image) is required for both of train and test data, so it
	 * is final.
	 */
	private final int[][] image;

	/**
	 * Label is required only for train data, so it is not final.
	 */
	private int label;

	/**
	 * Constructor to initialize both of image and label for train data.
	 * 
	 * @param image 2d array of pixels
	 * @param label 0-9 label to identify image class
	 */
	public RowData(int[][] image, int label) {
		this.image = image;
		this.label = label;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public int[][] getImage() {
		return image;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public int getLabel() {
		return label;
	}

}
