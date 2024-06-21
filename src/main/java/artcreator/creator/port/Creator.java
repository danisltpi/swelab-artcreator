package artcreator.creator.port;

import java.awt.image.BufferedImage;

public interface Creator {
	
	void sysop(String str);
	void importImage(String path);
	void chooseTemplateType(String type);
	BufferedImage getTemplateImage();
}
