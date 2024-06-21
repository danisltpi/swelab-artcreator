package artcreator.domain.impl;


/* Factory for creating domain objects */ 

public class DomainImpl {
	private int[] rubiksPalette;
	private int[] matchStickPalette;

	
	
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
