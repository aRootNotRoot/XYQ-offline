package xyq.system;

import java.util.HashMap;

import xyq.game.XYQGame; 
import xyq.system.bussiness.ShopData;
import xyq.system.bussiness.ShopFactory;

public class BussinessSystem {
	XYQGame game;
	/**商店当前ID值*/
	int shopIDs=0;
	/**商店数据*/
	HashMap<Integer,ShopData> shops;
	/**商店工厂*/
	ShopFactory shopFactory;
	
	public BussinessSystem(XYQGame game){
		this.game=game;
		shops=new HashMap<Integer, ShopData>();
		shopFactory=new ShopFactory(game);
	}
	public int getID(){
		return shopIDs++;
	}
	
	/**获取商店数据，如果商店不存在获取null*/
	public ShopData getShop(int ID){
		if(shops.get(ID)==null){
			ShopData shopData=shopFactory.makeShop(ID);
			shops.put(ID,shopData);
			return shopData;
		}
		return shops.get(ID);
	}
}
