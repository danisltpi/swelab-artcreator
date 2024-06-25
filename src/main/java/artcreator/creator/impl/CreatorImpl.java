package artcreator.creator.impl;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import artcreator.domain.port.Domain;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;

public class CreatorImpl {

	private StateMachine stateMachine;
	private Domain domain;

	private BufferedImage image;
	private BufferedImage templateImage;

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
		this.domain = domain;
		this.stateMachine = stateMachine;
	}

	public void sysop(String str) {
		System.out.println(str);
	}

	/**
	 * Imports an image from the specified path and scales it as a BufferedImage to 60x60 pixels while preserving the aspect ratio.
	 * If successful, the image can be converted into a template
	 * If failed, an error message will be occurring
	 *
	 * @param path the path to the image file
	 *
	 * 24/06/18 - Jenny
	 */
	public void importImage(File file){

        try {

			BufferedImage tempImage = getImage(file);
			this.image = scaleImage(tempImage, 60, 60);
			String path = "src/main/resources/";	// Todo: Debugging tool for visual output (temporary)
			saveImage(this.image, path + "image_output.png");	// Todo: Debugging tool for visual output (temporary)

			stateMachine.setState(State.S.CHOOSE_TEMPLATE_TYPE); // Todo: chooseTemplateType()
			System.out.println(this.image.toString());
        }

		catch (IOException e) {
			System.err.println("Error processing the image: " + e.getMessage());
			stateMachine.setState(State.S.INVALID_IMPORT); // Todo: new SysOp for error message in GUI?
        }
	}

	/**
	 * Processes an image from the specified path.
	 * Checks if the image file exists, if its size is <= 50 MB, and if its resolution is >= 60px x 60px.
	 * If the image meets these conditions, it is converted to a BufferedImage and returned.
	 *
	 * @param path the path to the image file
	 * @return the processed BufferedImage if valid, otherwise null
	 * @throws IOException if the image file does not exist, its size exceeds 50 MB, or its resolution is less than 60px x 60px
	 *
	 * 24/06/18 - Jenny
	 */
	private BufferedImage getImage (File file) throws IOException {


		// Check image existence
		if (!file.exists() || !file.isFile()) {
			throw new IOException("The specified file does not exist or is not a file.");
		}

		// Check image size (< 50 MB)
		long fileSizeInBytes = file.length();
		long maxSizeInBytes = 50 * 1024 * 1024;
		if (fileSizeInBytes > maxSizeInBytes) {
			throw new IOException("The image size exceeds 50 MB");
		}

		BufferedImage image = ImageIO.read(file);

		// check image resolution (>= 60px x 60px)
		if (image.getWidth() < 60 && image.getHeight() < 60) {
			throw new IOException("The image resolution is less than 60px x 60px");
		}

		// return bufferedImage with valid image input
		return image;
	}

	/**
	 * Scales the given image to fit within the specified target width and height while preserving the aspect ratio.
	 * The resulting image is centered within the target dimensions.
	 *
	 * @param originalImage the original image to be scaled
	 * @param targetWidth the target width
	 * @param targetHeight the target height
	 * @return a new BufferedImage containing the scaled image centered within the target dimensions
	 *
	 * 24/06/18 - Jenny
	 */
	private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {

		// Calculate the new dimensions while preserving the aspect ratio
		int originalWidth = originalImage.getWidth();
		int originalHeight = originalImage.getHeight();
		int newWidth, newHeight;

		if (originalWidth > originalHeight) {
			newWidth = targetWidth;
			newHeight = (originalHeight * targetWidth) / originalWidth;
		} else {
			newHeight = targetHeight;
			newWidth = (originalWidth * targetHeight) / originalHeight;
		}

		// Create a new BufferedImage with the new dimensions
		BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

		// Draw the scaled image
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
		g2d.dispose();

		return scaledImage;
	}

	// Temporary method for visual output (24/06/18 - Jenny)
	private void saveImage(BufferedImage image, String outputPath) throws IOException {
		// Extract the file extension from the output path
		String formatName = outputPath.substring(outputPath.lastIndexOf('.') + 1);

		// Save the image to the specified path
		File outputFile = new File(outputPath);
		if (!ImageIO.write(image, formatName, outputFile)) {
			System.err.println("Error saving the image.");
		}
	}

	public void chooseTemplateType(String type){
		String[] validTypes = {"rubiks", "matchsticks"};
		if(!isTypeValid(type, validTypes)){
			throw new Error("Invalid Type given.");
		}
		int[] colors = domain.getPalette(type);
		BufferedImage templateImage = convertPicture(image, colors);
		this.templateImage = templateImage;
		stateMachine.setState(State.S.TEMPLATE_CREATED);
	}

	private boolean isTypeValid(String type, String[] validTypes){
		boolean returnValue = false;
		for (String checkType: validTypes){
			if (checkType == type){
				returnValue = true;
			}
		}
		return returnValue;
	}

	private BufferedImage convertPicture(BufferedImage image, int[] colors){
		int height = image.getHeight();
		int width = image.getWidth();
		int newValue = 0;
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++){
			for( int y = 0; y < height; y++){
				int currentPixel = image.getRGB(x,y);
				newValue = findNearestColor(colors, currentPixel);
				newImage.setRGB(x,y, newValue);
			}
		}
		return newImage;
	}

	private int findNearestColor(int[] colors, int currentPixel){
		double minDistance = Double.MAX_VALUE;
		double newDistance = 0;
		int bestColor = 0;
		for(int i = 0; i < colors.length; i++){
			newDistance = calculateDistance(currentPixel, colors[i]);
			if (newDistance < minDistance){
				bestColor = colors[i];
			}
		}

		return bestColor;
	}
	private double calculateDistance(int originalColor, int templateColor){
		int[] originSplit = splitColor(originalColor);
		int[] templateSplit = splitColor(templateColor);
		int[] differenceArray = {};
		for(int i = 0; i < originSplit.length; i++){
			differenceArray[i] = originSplit[i] - templateSplit[i];
		}
		return Math.sqrt(Arrays.stream(differenceArray).sum());
	}
	private int[] splitColor(int color){
		int blue = color & 0xff;
		int green = (color & 0xff00) >> 8;
		int red = (color & 0xff0000) >> 16;
		int alpha = (color & 0xff000000) >>> 24;
		return new int[]{blue, green, red, alpha};
	}

	public BufferedImage getTemplateImage(){
		return this.templateImage;
	}
}
