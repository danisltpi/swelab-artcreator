package artcreator.domain.port;


/* Factory for creating domain objects */ 

public interface Domain {
	
	

	/* Factory methods */ 
	Object mkObject();
	int[] getPalette(String type);
	void setPalette(String type, int[] newPalette);
}
