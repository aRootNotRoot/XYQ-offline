package xyq.system.bussiness;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Group;

import xyq.game.XYQGame;
import xyq.game.data.Summon;

/**商店信息*/
public class ShopData {
	XYQGame game;
	/**商店的id*/
	public int shopID;
	
	/**商店的店名*/
	String name;
	/**商店的店主名字*/
	String owner;
	/**商店的店主ID*/
	int ownerID;
	/**商店的开店时间*/
	String openTime;
	/**商店的商业指数*/
	int bussinessPoint;
	/**商店里的商品，索引代表几号商品*/
	HashMap<Integer,GoodsData> goods;
	/**商店里卖的召唤兽*/
	ArrayList<Summon> summons;
	
	/**是否是店面类型商店而不是随便的摊位
	 * 0-摊位 1-系统商店 2-店面
	 * */
	int shopType;
	
	Group shopUI;
	
	int goodsIndex;
	public ShopData(XYQGame game){
		this.game=game;
		goods=new HashMap<Integer,GoodsData>();
		summons=new ArrayList<Summon>();
	}
	
	public ArrayList<Summon> getSummons() {
		return summons;
	}

	public void setSummons(ArrayList<Summon> summons) {
		this.summons = summons;
	}

	public void setGoodsIndex(int goodsIndex) {
		this.goodsIndex = goodsIndex;
	}

	int getGoodsIndex(){
		return goodsIndex++;
	}
	/**获取这个商店里所有商品总数*/
	public int getGoodsCount(){
		return goodsIndex;
	}
	public int getShopID() {
		return shopID;
	}
	public void setShopID(int shopID) {
		this.shopID = shopID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public int getBussinessPoint() {
		return bussinessPoint;
	}
	public void setBussinessPoint(int bussinessPoint) {
		this.bussinessPoint = bussinessPoint;
	}
	public HashMap<Integer,GoodsData> getGoods() {
		return goods;
	}
	public void setGoods(HashMap<Integer,GoodsData> goods) {
		this.goods = goods;
	}
	public int getShopType() {
		return shopType;
	}
	/**店面类型
	 * 0-摊位 1-系统商店 2-店面
	 * */
	public void setShopType(int shopType) {
		this.shopType = shopType;
	}

	/**增加一个商品到商店*/
	public void addGoods(GoodsData goods2) {
		goods.put(getGoodsIndex(), goods2);
		
	}
	
}
