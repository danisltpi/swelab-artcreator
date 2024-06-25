package artcreator.creator.port;

import java.awt.image.BufferedImage;
import java.io.File;

public interface Creator {
	
	void sysop(String str);
	void importImage(File file);
	void chooseTemplateType(String type);
	BufferedImage getTemplateImage();
}
