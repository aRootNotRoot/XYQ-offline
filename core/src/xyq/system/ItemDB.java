package xyq.system;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;

import xyq.game.XYQGame;
import xyq.game.data.ItemData;
import xyq.game.data.ItemEquipData;
import xyq.game.data.ItemStackData;

public class ItemDB {
	public final static int ITEM_EQUIP=1;
	public final static int ITEM_BASIC=2;
	public final static int IEQUIP_NORMAL_MADE=1;
	public final static int IEQUIP_ADVANCED_MADE=2;
	//public final static int ITEM_BASIC=3;
	XYQGame game;
	public HashMap<Integer, ItemEquipData> equipItems;
	public HashMap<Integer, ItemData> items;
	//public HashMap<Integer, ItemBasicData> basicItems;
	public ItemDB(XYQGame game){
		this.game=game;
		equipItems=new HashMap<Integer, ItemEquipData>();
		items=new HashMap<Integer, ItemData>();
		//basicItems=new HashMap<Integer, ItemBasicData>();
		
		initEquipData();
		initBasicData();
		//initBasicData();
	}
	/**通过type和id信息，读取道具原始模板信息
	 * @return 返回需要根据具体情况进行强转。type==ITEM_EQUIP则转换为ItemEquipData type==ITEM_BASIC则转换为ItemData
	 * */
	public Object getItem(int type,int id){
		if(type==ITEM_EQUIP){
			return equipItems.get(id);
		}else if(type==ITEM_BASIC){
			return items.get(id);
		}/*
		else if(type==XYQDataBase.ITEM_BASIC){
			return basicItems.get(id);
		}*/
		return null;
	}

	/**通过物品栈信息，读取原始信息*/
	public Object getItem_ISD(ItemStackData nowItem) {
		int type=nowItem.getItemType();
		int id=nowItem.getItemID();
		return getItem(type,id);
	}
	/**快速获取物品名称*/
	public String getItemName(ItemStackData item){
		int type=item.getItemType();
		int id=item.getItemID();
		if(type==ITEM_EQUIP){
			return equipItems.get(id).getName();
		}else if(type==ITEM_BASIC){
			return items.get(id).getName();
		}/*else if(type==XYQDataBase.ITEM_BASIC){
			return basicItems.get(id).getName();
		}*/
		return "";
	}
	/**快速获取物品描述*/
	public String getItemDesc(ItemStackData item){
		int type=item.getItemType();
		int id=item.getItemID();
		if(type==ITEM_EQUIP){
			return equipItems.get(id).getDecription();
		}else if(type==ITEM_BASIC){
			return items.get(id).getDescription();
		}/*else if(type==XYQDataBase.ITEM_BASIC){
			return basicItems.get(id).getDescription();
		}*/
		return "";
	}
	/**快速获取物品名称*/
	public String getItemName(int type,int id){
		/*
		System.err.println("快速获取物品名称");
		System.err.println(type);
		System.err.println(id);
		*/
		if(type==ITEM_EQUIP){
			return equipItems.get(id).getName();
		}else if(type==ITEM_BASIC){
			return items.get(id).getName();
		}/*else if(type==XYQDataBase.ITEM_BASIC){
			return basicItems.get(id).getName();
		}*/
		return "";
	}
	/**快速获取物品描述*/
	public String getItemDesc(int type,int id){
		if(type==ITEM_EQUIP){
			return equipItems.get(id).getDecription();
		}else if(type==ITEM_BASIC){
			return items.get(id).getDescription();
		}/*else if(type==XYQDataBase.ITEM_BASIC){
			return basicItems.get(id).getDescription();
		}*/
		return "";
	}
	public int getMaxCount(ItemStackData item) {
		int type=item.getItemType();
		int id=item.getItemID();
		if(type==ITEM_EQUIP){
			return equipItems.get(id).getItemMaxCount();
		}else if(type==ITEM_BASIC){
			return items.get(id).getItemMaxCount();
		}/*else if(type==XYQDataBase.ITEM_BASIC){
			return basicItems.get(id).getItemMaxCount();
		}*/
		return 1;
	}
	public int getMaxCount(Integer type,int id) {
		if(type==ITEM_EQUIP){
			return equipItems.get(id).getItemMaxCount();
		}else if(type==ITEM_BASIC){
			return items.get(id).getItemMaxCount();
		}/*else if(type==XYQDataBase.ITEM_BASIC){
			return basicItems.get(id).getItemMaxCount();
		}*/
		return 1;
	}
	/*
	public void initBasicData() {
		game.db.loadAllBasicItem(basicItems);
	}
*/
	public void initBasicData() {
		game.db.loadAllItemsData(items);
	}

	public void initEquipData() {
		game.db.loadAllEquip(equipItems);
	}

	/**制造一个默认的Basic道具*/
	public ItemStackData makeBasicItem(int stackIndex,int type,int id,int count) {
		if(count<1||count>99){
			//if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ 道具库 ]-> 生成一个Basic道具失败了，数量不在1-99内："+count);
			//}
			return null;
		}
		boolean overFull=false;
		ItemData itemModel=(ItemData)getItem(type, id);
		int maxCount=itemModel.getItemMaxCount();
		if(count>maxCount)
			overFull=true;
		if(overFull){
			//if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ 道具库 ]-> 生成一个Basic道具失败了，数量"+count+"超过了"+itemModel.getName()+"最大堆叠量："+itemModel.getItemMaxCount());
			//}
			return null;
		}
		ItemStackData item=new ItemStackData(stackIndex, type, id, count);
		item=game.cs.funcDB.funcs.get(itemModel.getFunction_Code()).makeTag_Basic(item, itemModel);
		return item;
	}
	
	/**根据物品type和物品id，创建一个item。首先查找已经有同样物品的位置，随后查找空余位置*/
	public ItemStackData makeSimpleItem(int type,int id,int count){
		ItemStackData item=null;
		int stackIndex=game.ls.ifm.PLAYER_getPlrExistISDIndex(type,id);
		if(stackIndex==-1){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ 道具库 ]-> 快速生成一个简单道具"+getItemName(type, id)+"失败了:背包没空格子了");
			}
			return null;
		}
		/*if(type==XYQDataBase.ITEM_BASIC)
			item=game.itemDB.makeBasicItem(stackIndex,type,id, count);
		else */if(type==ITEM_BASIC)
			item=makeBasicItem(stackIndex,type,id, count);
		return item;
	}
	/**生成一个藏宝图道具
	 * @param mapID 指定藏宝图的地图id，随机生成请传入-1
	 * */
	public ItemStackData makeTreasureMap(int mapID){
		ItemStackData item=null;
		int stackIndex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
		if(stackIndex==-1){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ 道具库 ]-> 快速生成一个藏宝图失败了:背包没空格子了。藏宝图不可以堆叠");
			}
			return null;
		}
		item=new ItemStackData(stackIndex, ITEM_BASIC, 79, 1);
		ItemData model=(ItemData)getItem(ITEM_BASIC, 79);
		item.function_Code=model.getFunction_Code();
		item.function_Tag=model.getFunction_Tag();
		int[] loc=game.ls.ifm.ITEM_generateTreasureMapAttr(mapID);
		item.setAttr1(loc[0]);
		item.setAttr2(loc[1]);
		item.setAttr3(loc[2]);
		item.setTag1(model.getFunctionDescription());
		return item;
	}
	/**生成一个高级藏宝图道具
	 * @param mapID 指定藏宝图的地图id，随机生成请传入-1
	 * */
	public ItemStackData makeAdvancedTreasureMap(int mapID){
		ItemStackData item=null;
		int stackIndex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
		if(stackIndex==-1){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ 道具库 ]-> 快速生成一个高级藏宝图失败了:背包没空格子了。藏宝图不可以堆叠");
			}
			return null;
		}
		item=new ItemStackData(stackIndex, ITEM_BASIC, 80, 1);
		ItemData model=(ItemData)getItem(ITEM_BASIC, 80);
		item.function_Code=model.getFunction_Code();
		item.function_Tag=model.getFunction_Tag();
		int[] loc=game.ls.ifm.ITEM_generateTreasureMapAttr(mapID);
		item.setAttr1(loc[0]);
		item.setAttr2(loc[1]);
		item.setAttr3(loc[2]);
		item.setTag1(model.getFunctionDescription());
		return item;
	}
	/**生成一个未鉴定装备
	 * @param euqipID 装备id
	 * */
	public ItemStackData makeUndefinedEquip(int euqipID,boolean isAdvancedMake){
		ItemStackData item=null;
		int stackIndex=game.ls.ifm.PLAYER_getPlayerFirstEmptyItemStackIndex();
		if(stackIndex==-1){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]", "[ 道具库 ]-> 快速生成一个未鉴定装备失败了:背包没空格子了。藏宝图不可以堆叠");
			}
			return null;
		}
		item=new ItemStackData(stackIndex, ITEM_EQUIP, euqipID, 1);
		item.function_Code=28;
		item.function_Tag="未鉴定装备";

		if(isAdvancedMake)
			item.setAttr1(IEQUIP_ADVANCED_MADE);
		else {
			item.setAttr1(IEQUIP_NORMAL_MADE);
		}
		
		item.setTag1("未鉴定物品");
		setItemMaker(item,"");
		return item;
	}
	/**设定物品制作者信息，覆盖原始信息
	 * @param item 欲设定的物品
	 * @param makerName 制作者名，如果给空则设定为玩家名
	 * */
	public void setItemMaker(ItemStackData item,String makerName){
		if(makerName==null||makerName.equals("")){
			makerName=game.ls.ifm.PLAYER_getName();
		}
		if(item.function_Code==28){
			if(item.getAttr1()==IEQUIP_NORMAL_MADE)
				item.setTag5("制造者 "+makerName);
			else
				item.setTag5(makerName+" 强化打造");
		}else{
			item.setTag5("制造者 "+makerName);
		}
	}
}
