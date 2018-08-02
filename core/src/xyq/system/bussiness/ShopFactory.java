package xyq.system.bussiness;

import java.util.HashMap;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.system.ItemDB;
import xyq.system.utils.RandomUT;

public class ShopFactory {
	public static final int TANWEI_SHOP=0;
	public static final int SYSTEM_SHOP=1;
	
	
	XYQGame game;
	/**储存预定义商店的生成器*/
	HashMap<Integer, ShopMaker> makers;
	public ShopFactory(final XYQGame game){
		this.game=game;
		makers=new HashMap<Integer, ShopMaker>();
		
		//-----------------------------------------------------------------------------------
		//预定义商店生成。
		makers.put(0, new ShopMaker(){
			@Override
			public ShopData make() {
				ShopData shop=makeEmptyShop(0,"新手杂货","※欧阳子默△",970791,TANWEI_SHOP);
				
				//ID 53 包子
				ItemStackData itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 53, 18);
				GoodsData goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 188, false);
				shop.addGoods(goods);
				
				//ID 83 宠物口粮
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 83, 75);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 99, false);
				shop.addGoods(goods);
				
				return shop;
			}
		});
		//-----------------------------------------------------------------------------------
		makers.put(1, new ShopMaker(){
			@Override
			public ShopData make() {
				ShopData shop=makeEmptyShop(1,"新手杂货","※欧阳子默△",970791,TANWEI_SHOP);
				
				//ID 94 飞行符
				ItemStackData itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 94, 1);
				GoodsData goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 498, false);
				shop.addGoods(goods);
				
				//ID 87 避水珠
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 87, 1);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 49998, false);
				shop.addGoods(goods);
				
				//ID 53 包子
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 53, 97);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 298, false);
				shop.addGoods(goods);
				
				//加一堆飞行符
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 94, 1);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 498, false);
				shop.addGoods(goods);
				
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 94, 1);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 498, false);
				shop.addGoods(goods);
				
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 94, 1);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 498, false);
				shop.addGoods(goods);
				
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 94, 1);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 498, false);
				shop.addGoods(goods);
				
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 83, 19);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 150, false);
				shop.addGoods(goods);
				
				itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 97, 2);
				goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 500, false);
				shop.addGoods(goods);
				
				int id=RandomUT.getRandom(2, 50);
				SummonData data2=game.summonDB.makeSummonData(0,4,false,true);
				SummonData data3=game.summonDB.makeSummonData(98,2,true,false);
				id=RandomUT.getRandom(2, 50);
				SummonData data4=game.summonDB.makeSummonData(id,4,false,false);
				id=RandomUT.getRandom(2, 50);
				SummonData data5=game.summonDB.makeSummonData(id,2,true,false);
				
				Summon newone=game.summonDB.makeSummon(data2);
				newone.setCode_tag("199999");
				shop.getSummons().add(newone);
				newone=game.summonDB.makeSummon(data3);
				newone.setCode_tag("99999999");
				shop.getSummons().add(newone);
				newone=game.summonDB.makeSummon(data4);
				newone.setCode_tag("32500");
				shop.getSummons().add(newone);
				newone=game.summonDB.makeSummon(data5);
				newone.setCode_tag("199999");
				shop.getSummons().add(newone);
				
				return shop;
			}
		});
		//-----------------------------------------------------------------------------------
		//商店2。系统商店
		makers.put(2, new ShopMaker(){
			@Override
			public ShopData make() {
				ShopData shop=makeEmptySystemShop(2);
						
				//ID 53 包子
				ItemStackData itemStackData=game.itemDB.makeBasicItem(-1, ItemDB.ITEM_BASIC, 53, 99);
				GoodsData goods=game.ls.ifm.ITEM_generateGoods(itemStackData, 150, true);
				shop.addGoods(goods);
			
						
				return shop;
			}
		});
		
	}

	/**制造一个玩家拥有的空的商店数据*/
	public ShopData makeEmptyShop(int shopID,String Name,String ownerName,int ownerID,int shopType){
		ShopData shop=new ShopData(game);
		shop.setShopID(shopID);
		shop.setName(Name);
		shop.setOwner(ownerName);
		shop.setOwnerID(ownerID);
		shop.setShopType(shopType);
		//shops.put(shop.getShopID(),shop);
		return shop;
	}
	/**制造一个玩家拥有的空的商店数据*/
	public ShopData makeEmptyShop(int shopID){
		ShopData shop=new ShopData(game);
		shop.setShopID(shopID);
		shop.setName("杂货摊位");
		shop.setOwner(game.ls.ifm.PLAYER_getName());
		shop.setOwnerID(game.ls.ifm.PLAYER_getID());
		shop.setShopType(TANWEI_SHOP);
		//shops.put(shop.getShopID(),shop);
		return shop;
	}
	/**制造一个空的系统商店数据*/
	public ShopData makeEmptySystemShop(int shopID){
		ShopData shop=new ShopData(game);
		shop.setShopID(shopID);
		shop.setName("系统商店");
		shop.setOwner("系统");
		shop.setOwnerID(100000);
		shop.setShopType(SYSTEM_SHOP);
		//shops.put(shop.getShopID(),shop);
		return shop;
	}
	/**制造一个空的商店数据
	 * @param name 店铺名字
	 * @param plrName 玩家名
	 * @param plrID 玩家ID
	 * @param shopType 店铺类型  0-摊位 1-系统商店 2-店面
	 * */
	public ShopData makeEmptyShop(int shopID,String name,String plrName,String plrID,int shopType){
		ShopData shop=new ShopData(game);
		shop.setShopID(shopID);
		shop.setName(name);
		shop.setOwner(plrName);
		shop.setOwnerID(Integer.valueOf(plrID));
		shop.setShopType(shopType);
		//shops.put(shop.getShopID(),shop);
		return shop;
	}

	/**根据ID生成一个商店，先从商店预定义里找，如果找不到则返回空*/
	public ShopData makeShop(int ID) {
		ShopMaker maker=makers.get(ID);
		//如果商店制作器没有预定义，则返回空的商店
		if(maker==null){
			ShopData shop=makeEmptyShop(ID);
			return shop;
		}else{
			ShopData shop=maker.make();
			return shop;
		}
	}
}
