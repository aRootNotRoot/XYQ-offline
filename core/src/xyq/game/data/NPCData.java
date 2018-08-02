package xyq.game.data;

import xyq.system.ai.MapGraphPath;
import xyq.system.bussiness.ShopData;

/**记录NPC数据的类*/
public class NPCData {
	NPC me;
	/**NPC所在的X坐标*/
	public int logicX;
	/**NPC所在的Y坐标*/
	public int logicY;
	/**NPC所在的地图ID*/
	public int inMapID;
	
	/**外形数据*/
	public ShapeData shape;
	
	/**移动序列*/
	public MapGraphPath movingStep;
	/**移动的目标终点X*/
	public int moveTargetX;
	/**移动的目标终点Y*/
	public int moveTargetY;
	 
	/**NPC名字*/
	public String name="";
	/**NPC称谓*/
	public String title;
	
	/**当前NPC动作状态*/
	public String currentAction=ShapeData.STAND;
	/**当前NPC动作朝向*/
	public int currentFaceTo=0;

	/**玩家是否在摆摊*/
	public boolean saling=false;
	public String myZhaoPaiName="杂货摊位";
	/**是否可以移动*/
	public boolean canMove=true;
	/**NPC商店数据*/
	public ShopData shopData;
	/**NPC商店ID数据*/
	public int shopID;
	
	public NPCData(NPC me){
		this.me=me;
	}
	public NPCData(NPC me,String name,ShapeData shape){
		this.me=me;
		this.name=name;
		this.shape=shape;
	}
}
