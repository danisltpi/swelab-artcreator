package artcreator.domain.impl;


/* Factory for creating domain objects */ 

public class DomainImpl {
	private int[] rubiksPalette = {-1, -65536,-23292,-256,-16711936, -16776961};
	private int[] matchStickPalette = {-65536,-403271};

	
	
	public Object mkObject() {
		// TODO Auto-generated method stub
		return null;
	}	
	public int[] getPalette(String type){
		return type == "rubiks" ? rubiksPalette : matchStickPalette;
	}

	public void setPalette(String type, int[] newPalette){
		if(type == "rubiks"){
			this.rubiksPalette = newPalette;
		}else{
			this.matchStickPalette = newPalette;
		}
	}
}
