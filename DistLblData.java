package model;

/**
 * 
 * This class is responsible to store label (value from 0 to 9) along with the
 * distance. This is required in
 * {@link KNN#run(int, java.util.List, java.util.List)} algorithm in order to
 * sort closest_k_label_array by frequency of label. Here I mean the following
 * from the project specification: # Find the label class with maximum number of
 * label counts
 *
 */
public class DistLblData {

	/**
	 * Euclidean distance between pixels.
	 */
	private final double distance;

	/**
	 * Label value from 0 to 9.
	 */
	private final int label;

	/**
	 * Constructor to initialize DistLblData with the distance and label.
	 * 
	 * @param distance {@link DistLblData#distance}
	 * @param label    {@link DistLblData#label}
	 */
	public DistLblData(double distance, int label) {
		this.distance = distance;
		this.label = label;
	}

	/**
	 * Getter to get the distance.
	 * 
	 * @return {@link DistLblData#distance}
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Getter to get the label.
	 * 
	 * @return {@link DistLblData#label}
	 */
	public int getLabel() {
		return label;
	}

	/**
	 * Useful method for debugging purpose. It simply returns the string
	 * representation of this object.
	 */
	@Override
	public String toString() {
		return distance + " -> " + label;
	}

	/**
	 * For hash code (used in equals) we use only label to allow sorting only by
	 * label.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + label;
		return result;
	}

	/**
	 * Override equals as well to compare objects of this class only by label.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DistLblData other = (DistLblData) obj;
		if (label != other.label)
			return false;
		return true;
	}

}
