package xyq.system.bussiness;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;

/**商品信息*/
public class GoodsData {
	XYQGame game;
	/**商品对应的物品*/
	ItemStackData item;
	/**商品售价*/
	int price;
	/**自动上架,也就是永远卖不完，自动补货*/
	boolean infinite;
	
	public GoodsData(XYQGame game){
		this.game=game;
		infinite=false;
	}
	public ItemStackData getItem() {
		return item;
	}

	public void setItem(ItemStackData item) {
		this.item = item;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}
	
	public GoodsData copy(){
		GoodsData newone=new GoodsData(game);
		newone.infinite=this.infinite;
		newone.price=this.price;
		ItemStackData itemStackData=this.item.copy();
		newone.item=itemStackData;
		return newone;
	}
}
