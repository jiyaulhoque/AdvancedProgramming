package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 
 * This class is responsible for the load data from the input files.
 *
 */
public class DataLoader {

	/**
	 * Identifies 32x32 pixels per image.
	 */
	public final static int IMG_SIZE = 32;

	/**
	 * First element defines label, next 3072 are values of pixels.
	 */
	private final static int ROW_LENGTH = 3073;

	/**
	 * Accepts single or multiple files and returns list of {@link RowData}.
	 * Depending on what extension the file(s) has, it calls appropriate helper
	 * method.
	 * 
	 * @param files input file(s)
	 * @return list of {@link RowData}
	 * @throws Exception throws if input file is invalid or does not exist
	 */
	public static List<RowData> load(File... files) throws Exception {
		List<RowData> result = null;
		if (files.length == 1) { // Case when only 1 test PNG file or possible directory.
			if (files[0].isDirectory()) { // If user specified folder with PNG images.
				result = loadTestFiles(files[0].listFiles()); // Pass all files inside directory.
			} else if (files[0].getName().toLowerCase().endsWith(".png")) {
				result = loadTestFiles(files);
			} else if (files[0].getName().toLowerCase().endsWith(".bin")) {
				result = loadTrainFiles(files);
			}
		} else { // Case when user loaded 1 or more .bin input files.
			result = loadTrainFiles(files);
		}
		return result;
	}

	/**
	 * Helper method to get list of {@link RowData} from the input test file or
	 * folder with multiple test files. First, it reads the image and convert it to
	 * BufferedImage, the creates d_img 2D array of pixels to get RGB value from
	 * every pixel of BufferedImage and store it to the 2D array.
	 * 
	 * @param files input file/folder
	 * @return list of {@link RowData}
	 * @throws Exception throws if invalid file
	 */
	private static List<RowData> loadTestFiles(File... files) throws Exception {
		List<RowData> result = new ArrayList<>();
		for (File testImg : files) {
			BufferedImage bi = ImageIO.read(testImg);
			int[][] d_img = new int[IMG_SIZE][IMG_SIZE];
			for (int y = 0; y < IMG_SIZE; y++) {
				for (int x = 0; x < IMG_SIZE; x++) {
					d_img[x][y] = bi.getRGB(x, y);
				}
			}

			// Retrieve label from file name and store it as well.
			// Label of test image is required to compare with the label
			// of train image.
			int label = Integer.parseInt(testImg.getName().substring(0, 1));
			result.add(new RowData(d_img, label));
		}
		return result;
	}

	/**
	 * Helper method to get list of {@link RowData} from input file(s). It iterates
	 * over input files (if 1 file, then only 1 iteration), then iterates over all
	 * rows in the file (10000 rows per file as per the project specification), next
	 * it obtains array of bytes for image (3072 bytes), next it creates t_img 2D
	 * array to store RGB values. To obtain RGB, we use 3 values from image array:
	 * R, G and B. According to the project specification, RGB values are stored
	 * using 1024 bytes per value, i.e. red values occupy first 1024 bytes, green
	 * values occupy second 1024 bytes, and red values occupy last third 1024 bytes.
	 * As per the logic above, to get 1 RGB value, we need to combine 3 values from
	 * the different places of array. For example, to get first RGB value, we need
	 * the following: R = array[0], G = array[1024], B = array[2048].
	 * 
	 * @param files input file(s)
	 * @return list of {@link RowData}
	 * @throws Exception throws if input file is invalid
	 */
	private static List<RowData> loadTrainFiles(File... files) throws Exception {
		List<RowData> result = new ArrayList<>();
		for (File binFile : files) {
			byte[] input = Files.readAllBytes(binFile.toPath());
			for (int j = 0; j < input.length; j += ROW_LENGTH) {
				byte[] image = Arrays.copyOfRange(input, j + 1, j + ROW_LENGTH);
				int[][] t_img = new int[IMG_SIZE][IMG_SIZE];
				int i = 0;
				for (int y = 0; y < IMG_SIZE; y++) {
					for (int x = 0; x < IMG_SIZE; x++) {
						int rgb = (image[i] & 0xff) << 16 | (image[i + 1024] & 0xff) << 8 | (image[i + 2048] & 0xff);
						t_img[x][y] = rgb;
						i++;
					}
				}
				result.add(new RowData(t_img, input[j]));
			}
		}
		return result;
	}

}
