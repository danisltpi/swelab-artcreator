package artcreator.creator.port;

public interface Creator {
	
	void sysop(String str);
	void importImage(String path);
	void chooseTemplateType(String type);
}
