package artcreator.creator.impl;

import artcreator.domain.port.Domain;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreatorImpl {

	private StateMachine stateMachine;
	private Domain domain;

	private BufferedImage image;

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
	public void importImage(String path){

        try {
			path = "C:/path/to/image";						// Todo: Debugging tool for visual output (temporary)
			String name = "image_input.png";				// Todo: Debugging tool for visual output (temporary)

			BufferedImage tempImage = processImage(path+name);
			this.image = scaleImage(tempImage, 60, 60);
			saveImage(tempImage, path + "image_output.png");	// Todo: Debugging tool for visual output (temporary)

			stateMachine.setState(State.S.CHOOSE_TEMPLATE_TYPE); // Todo: chooseTemplateType()
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
	private BufferedImage processImage (String path) throws IOException {

		File imageFile = new File(path);

		// Check image existence
		if (!imageFile.exists() || !imageFile.isFile()) {
			throw new IOException("The specified file does not exist or is not a file.");
		}

		// Check image size (< 50 MB)
		long fileSizeInBytes = imageFile.length();
		long maxSizeInBytes = 2 * 1024 * 1024;
		if (fileSizeInBytes > maxSizeInBytes) {
			throw new IOException("The image size exceeds 50 MB");
		}

		BufferedImage image = ImageIO.read(imageFile);

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
		System.out.println("some shit");
	}
}
