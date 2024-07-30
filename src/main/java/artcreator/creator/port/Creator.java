package artcreator.creator.port;

import artcreator.statemachine.port.State;

import java.awt.image.BufferedImage;
import java.io.File;

public interface Creator {
	
	void sysop(String str);
	void importImage(File file);
	void createPreviewWithTemplate(String type);
	void createPreviewOfOriginalPicture();
	BufferedImage getTemplateImage();
	BufferedImage getOriginalImage();
	void saveTemplate(String path);
	public BufferedImage scaleImage(BufferedImage image, int width, int height);
	void setState(State state);
}
