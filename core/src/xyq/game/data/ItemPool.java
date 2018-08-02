package xyq.game.data;

public class ItemPool {
	public int type;
	public int id;
	
	int chanceValue;

	
	public ItemPool(int type,int id,int chance){
		this.id=id;
		this.type=type;
		
		this.chanceValue=chance;
	}

	
}
