package xyq.system;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;

import xyq.game.XYQGame;
import xyq.game.data.ItemStackData;
import xyq.game.data.NPC;
import xyq.game.data.ShapeData;
import xyq.game.data.Summon;
import xyq.game.data.SummonData;
import xyq.game.stage.XYQMapActor;
import xyq.system.ai.MapGraphPath;
import xyq.system.ai.MapNode;
import xyq.system.ai.MapPoint;
import xyq.system.bussiness.GoodsData;
//import xyq.system.bussiness.ShopManager;
import xyq.system.utils.RandomUT;

/**信息获取器*/
public class Information {
	XYQGame game;
	/**检测是否在附近的尺度范围*/
	public static int NERBY_SIZE=5;
	/**生成的藏宝图可能的地图id池*/
	public final int[] treasureMapIDPool={
			1092,1103,1146,1193,1208,1218,1110,1091,1506 ,1140,1131,1142  ,1174
		//傲来,水帘,五庄,江南,朱紫,墨家,国境,郊外,东海,普陀,狮驼,女儿,北俱
	};
	
	//public ShopManager shopManager;
	
	public Information(XYQGame game) {
		this.game=game;
		//shopManager=new ShopManager(game);
	}
	/**根据物品类型，id查找一个存在的物品栏。如果背包里查找不到，也就是为空，则查找第一个空着的位置
	 * @return 如果满了，找不到，就返回-1
	 * */
	public int PLAYER_getPlrExistISDIndex(int type,int id){
		HashMap<Integer, ItemStackData> items=game.ls.player.getItems();
		for(int i=6;i<26;i++){
			ItemStackData aItem=items.get(i);
			if(aItem==null)
				continue;
			if(aItem.getItemType()==type&&aItem.getItemID()==id){
				return i;
			}
		}
		int stackIndex=PLAYER_getPlayerFirstEmptyItemStackIndex();
		if(stackIndex==-1){
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[ Player ] -> 背包满了，找不到空的位置索引");
			}
			return -1;
		}
		return stackIndex;
	}
	/**获取玩家背包里第一个空的位置(6-25)
	 * @return 如果找不到则返回-1
	 * */
	public int PLAYER_getPlayerFirstEmptyItemStackIndex() {
		for(int i=6;i<26;i++){
			if(game.ls.player.getItems().get(i)==null)
				return i;
		}
		return -1;
	}
	/**获取玩家背包里某个道具的索引(6-25)
	 * @param makeANew 如果背包里找不到这个道具，是否找一个空位
	 * @return 如果找不到则返回-1
	 * */
	public int PLAYER_getPlayerOneItemStackIndex(ItemStackData item,boolean makeANew) {
		for(int i=6;i<26;i++){
			ItemStackData waitToComp=game.ls.player.getItems().get(i);
			if(waitToComp==null)
				continue;
			if(item.getItemType()==waitToComp.getItemType()&&item.getItemID()==waitToComp.getItemID()){
				return i;
			}
		}
		if(makeANew){
			return PLAYER_getPlayerFirstEmptyItemStackIndex();
		}
		return -1;
	}
	/**获取玩家在当前地图里的逻辑X坐标*/
	public int PLAYER_getLogicXPos() {
		return game.ls.player.getLogicX();
	}
	/**获取玩家在当前地图里的逻辑Y坐标*/
	public int PLAYER_getLogicYPos() {
		return game.ls.player.getLogicY();
	}
	/**获取当前玩家的名字*/
	public String PLAYER_getName() {
		return game.ls.player.name();
	}
	/**获取当前玩家的名字*/
	public int PLAYER_getID() {
		return Integer.valueOf(game.ls.player.id);
	}
	/**获取玩家当前现金银两*/
	public int PLAYER_getMoney() {
		return game.ls.player.playerData().moneys[0];
	}
	/**获取玩家当前等级*/
	public int PLAYER_getLevel() {
		return game.ls.player.playerData().level;
	}
	/**获取玩家当前存银*/
	public int PLAYER_getStoredMoney() {
		return game.ls.player.playerData().moneys[1];
	}
	/**获取玩家某个技能学会的等级*/
	public int PLAYER_getLearnedSkillPoint(String skillName) {
		int id=game.db.loadSkillMainIDByName(skillName);
		int learnedPoint=game.db.loadLearnedSkillPoint(id);
		return learnedPoint;
	}
	/**获取当前玩家的所有召唤兽*/
	public ArrayList<Summon> PLAYER_getSummons() {
		ArrayList<Summon> summons=game.ls.player.getSummons();
		return summons;
	}
	/**获取当前玩家的道具*/
	public ItemStackData PLAYER_getItem(int index) {
		ItemStackData itemStackData=game.ls.player.getItem(index);
		return itemStackData;
	}
	/**获取当前玩家的所有携带和参战召唤兽*/
	public ArrayList<Summon> PLAYER_getPickSummons() {
		ArrayList<Summon> summons=PLAYER_getSummons();
		ArrayList<Summon> summon=new ArrayList<Summon>();
		for(int i=0;i<summons.size();i++){
			if(summons.get(i).getSet_index().equals("携带"))
				summon.add(summons.get(i));
			else if(summons.get(i).getSet_index().equals("参战"))
				summon.add(summons.get(i));
		}
		return summon;
	}
	/**获取当前玩家携带的召唤兽总数*/
	public int PLAYER_getPickSummonCount() {
		ArrayList<Summon> summons=PLAYER_getPickSummons();
		return summons.size();
	}
	/**获取当前玩家的种族，比如龙太子是仙族
	 * @return 人-魔-仙
	 * */
	public String PLAYER_getRoleNation(){
		String Role=game.ls.player.playerData().Role;
		if(Role.equals("龙太子")||Role.equals("玄彩娥")||Role.equals("舞天姬")||Role.equals("神天兵")||Role.equals("羽灵神"))
			return "仙";
		else if(Role.equals("狐美人")||Role.equals("骨精灵")||Role.equals("巨魔王")||Role.equals("虎头怪")||Role.equals("杀破狼"))
			return "魔";
		else if(Role.equals("飞燕女")||Role.equals("英女侠")||Role.equals("逍遥生")||Role.equals("剑侠客")||Role.equals("巫蛮儿"))
			return "人";
		return null;
	}
	/**判断一个npc是否离玩家近*/
	public boolean NPC_isNearByPlr(NPC npc) {
		if(npc==null||game.ls.player==null)
			return false;
		int[] me=new int[2];
		me[0]=npc.getLogicX();
		me[1]=npc.getLogicY();
		int[] target=new int[2];
		target[0]=game.ls.player.getLogicX();
		target[1]=game.ls.player.getLogicY();
		return MAP_isNearBy(target, me);
		
	}
	/**获取当前地图的NPC
	 * @param npcName 准备查找的NPC的名字
	 * @return 如果找不到返回null
	 * */
	public NPC NPC_getCurrentMapNPC(String npcName){
		return game.maps.currentMap().getNPC(npcName);
	}
	/**根据两个坐标来判断是否两个位置相近*/
	public boolean MAP_isNearBy(int[] target,int[] me){
		if(target[0]-me[0]>-NERBY_SIZE&&target[0]-me[0]<NERBY_SIZE&&target[1]-me[1]>-NERBY_SIZE&&target[1]-me[1]<NERBY_SIZE)
			return true;
		return false;
	}
	/**根据两个坐标来判断是否两个位置相近*/
	public boolean MAP_isNearBy(MapNode target,MapNode me){
		int[] him=new int[2];him[0]=target.x;him[1]=target.y;
		int[] mine=new int[2];mine[0]=me.x;mine[1]=me.y;
		return MAP_isNearBy(him,mine);

	}
	/**获取两个点之间的直线距离
	 * */
	public float MAP_getTwoMapPointDest(MapPoint start,MapPoint end){
		return (float)Math.sqrt(Math.pow(start.x - end.x,2)+Math.pow(start.y - end.y,2));
	}
	/**获取两个点之间的直线距离
	 * */
	public float MAP_getTwoMapPointDest(MapNode start,MapNode end){
		return (float)Math.sqrt(Math.pow(start.x - end.x,2)+Math.pow(start.y - end.y,2));
	}
	/**获取两个点之间的直线距离
	 * */
	public float MAP_getTwoMapPointDest(float x1,float y1,float x2,float y2){
		return (float)Math.sqrt(Math.pow(x1 - x2,2)+Math.pow(y1 - y2,2));
	}
	/**获取两个点之间的向量方向【左下角为坐标原点】
	 * @param start 起点
	 * @param end 终点
	 * @return 起点->终点的方向
	 * */
	public String MAP_getTwoMapPointDiect(MapPoint start,MapPoint end){
		if(end.x>start.x&&end.y>start.y)//往右上方走
			return "右上";
		else if(end.x>start.x&&end.y<start.y)//往右下方走
			return "右下";
		else if(end.x<start.x&&end.y<start.y)//往左下方走
			return "左下";
		else if(end.x<start.x&&end.y>start.y)//往左上方走
			return "左上";
		else if(end.x<start.x&&end.y==start.y)//往左走
			return "左";
		else if(end.x<start.x&&end.y==start.y)//往右走
			return "右";
		else if(end.x==start.x&&end.y<start.y)//往下走
			return "下";
		else//往上走
			return "上";
	}
	/**获取两个点之间的向量方向【左下角为坐标原点】
	 * @param start 起点
	 * @param end 终点
	 * @return 起点->终点的方向代码 0:右下 1:左下 2:左上 3:右上 4:下 5:左 6:上 7:右
	 * */
	public int MAP_getTwoMapPointDiectInt(MapPoint start,MapPoint end){
		//if(game.is_Debug)
			//Gdx.app.log("[ XYQ ]", "[LogicSystem] -> 开始计算两个点的朝向 -> from "+start.x+","+start.y+" to "+end.x+","+end.y);
		if(end.x>start.x&&end.y>start.y)//往右上方走
			return 3;
		else if(end.x>start.x&&end.y<start.y)//往右下方走
			return 0;
		else if(end.x<start.x&&end.y<start.y)//往左下方走
			return 1;
		else if(end.x<start.x&&end.y>start.y)//往左上方走
			return 2;
		else if(end.x<start.x&&end.y==start.y)//往左走
			return 5;
		else if(end.x>start.x&&end.y==start.y)//往右走
			return 7;
		else if(end.x==start.x&&end.y<start.y)//往下走
			return 4;
		else//往上走
			return 6;
	}
	/**根据地图两个Node节点和外形的最大方向数，获取朝向*/
	public int MAP_getTwoMapPointDiectInt(ShapeData shape, MapNode start, MapNode end) {
		if(shape.getMaxDirection()==8){
			if(end.x>start.x&&end.y>start.y)//往右上方走
				return 3;
			else if(end.x>start.x&&end.y<start.y)//往右下方走
				return 0;
			else if(end.x<start.x&&end.y<start.y)//往左下方走
				return 1;
			else if(end.x<start.x&&end.y>start.y)//往左上方走
				return 2;
			else if(end.x<start.x&&end.y==start.y)//往左走
				return 5;
			else if(end.x>start.x&&end.y==start.y)//往右走
				return 7;
			else if(end.x==start.x&&end.y<start.y)//往下走
				return 4;
			else//往上走
				return 6;
		}else if(shape.getMaxDirection()==4){
			if(end.x>start.x&&end.y>start.y)//往右上方走
				return 3;
			else if(end.x>start.x&&end.y<start.y)//往右下方走
				return 0;
			else if(end.x<start.x&&end.y<start.y)//往左下方走
				return 1;
			else if(end.x<start.x&&end.y>start.y)//往左上方走
				return 2;
			else if(end.x<start.x&&end.y==start.y)//往左走
				return 2;
			else if(end.x>start.x&&end.y==start.y)//往右走
				return 3;
			else if(end.x==start.x&&end.y<start.y)//往下走
				return 1;
			else//往上走
				return 3;
		}
		return 0;
	}

	/**获取两个点之间的向量四向方向【左下角为坐标原点】
	 * @param start 起点
	 * @param end 终点
	 * @return 起点->终点的方向代码 0:SouthEast 1:SouthWest 2:NorthWest 3:NorthEast
	 * */
	public int MAP_getTwoMapPointDiectInt_4(MapPoint start,MapPoint end){
		//if(game.is_Debug)
			//Gdx.app.log("[ XYQ ]", "[LogicSystem] -> 开始计算两个点的朝向 -> from "+start.x+","+start.y+" to "+end.x+","+end.y);
		if(end.x>start.x&&end.y>start.y)//往右上方走
			return 3;
		else if(end.x>start.x&&end.y<start.y)//往右下方走
			return 0;
		else if(end.x<start.x&&end.y<start.y)//往左下方走
			return 1;
		else if(end.x<start.x&&end.y>start.y)//往左上方走
			return 2;
		else if(end.x<start.x&&end.y==start.y)//往左走
			return 2;
		else if(end.x>start.x&&end.y==start.y)//往右走
			return 3;
		else if(end.x==start.x&&end.y<start.y)//往下走
			return 1;
		else//往上走
			return 3;
	}
	/**判断是否在朝着4方向【左右上下】*/
	public boolean MAP_isMovingTo4(MapPoint start,MapPoint end){
		if(end.x>start.x&&end.y>start.y)//往右上方走
			return false;
		else if(end.x>start.x&&end.y<start.y)//往右下方走
			return false;
		else if(end.x<start.x&&end.y<start.y)//往左下方走
			return false;
		else if(end.x<start.x&&end.y>start.y)//往左上方走
			return false;
		else if(end.x<start.x&&end.y==start.y)//往左走
			return true;
		else if(end.x>start.x&&end.y==start.y)//往右走
			return true;
		else if(end.x==start.x&&end.y<start.y)//往下走
			return true;
		else//往上走
			return true;
	}
	/**获取当前玩家在的地图ID*/
	public int MAP_getCurrentMapID() {
		return game.maps.currentMapID;
	}
	/**获取当前玩家在的地图*/
	public XYQMapActor MAP_getCurrentMap() {
		return game.maps.map();
	}
	/**获取当前玩家在的地图BGM*/
	public String MAP_getCurrentMapBGM() {
		return game.maps.map().mapBGM;
	}
	/**获取某个地图上可以通行的随机坐标*/
	public int[] MAP_getMapRandomPassbleXY(int mapID) {
		int[] pos=new int[2];
		pos=game.maps.getMap(mapID).getRandomXY();
		return pos;
	}
	/**获取某个地图上以某个坐标为基准某范围内的可以通行的随机坐标,*/
	public int[] MAP_getMapRandomPassbleXY(int mapID,int logicX,int logicY,int area) {
		//System.err.println("需要重新制作MAP_getMapRandomPassbleXY");
		//game.cs.UI_showSystemMessage("需要重新制作MAP_getMapRandomPassbleXY方法");
		int[] pos=new int[2];
		pos=game.maps.getMap(mapID).getRandomXY(logicX,logicY,area);
		return pos;
	}
	/**获取某个地图的地图数据  地图数据：1-阻挡 0-通行 2-传送*/
	public int MAP_getMapData(int mapID,int logicX,int logicY) {
		return game.maps.getMap(mapID).getMapData(logicX, logicY);
	}
	/**在当前地图内，启发式搜索，查找到路径*/
	public MapGraphPath MAP_pathFind(int logicStartX,int logicStartY,int logicEndX,int logicEndY){
		return MAP_getCurrentMap().pathFound(logicStartX,logicStartY,logicEndX,logicEndY);
	}
	/**在当前地图内，启发式搜索，查找到路径*/
	public MapGraphPath MAP_pathFind(MapNode startNode,MapNode endNode){
		return MAP_getCurrentMap().pathFound(startNode.x,startNode.y,endNode.x,endNode.y);
	}
	/**获取某个地图的地图名字*/
	public String MAP_getMapName(int mapID) {
		return game.maps.getMapName(mapID);
	}
	/**获取当前地图内某逻辑坐标对应的地图节点*/
	public MapNode MAP_getNode(int logicX, int logicY) {
		return MAP_getCurrentMap().getMapNode(logicX, logicY);
	}
	/**简单对百分比数据进行处理，使其范围为0-1f*/
	public float MATH_getPercent(float cdone){
		if(cdone>1f)
			return 1f;
		if(cdone<0f)
			return 0f;
		return cdone;
	}
	/**将八向方向转换为四向方向*/
	public int MATH_eightToFourDir(int currentFaceTo) {
		// 5746 2313
		if(currentFaceTo==5)
			currentFaceTo=2;
		else if(currentFaceTo==7)
			currentFaceTo=3;
			else if(currentFaceTo==4)
				currentFaceTo=1;
				else if(currentFaceTo==6)
					currentFaceTo=3;
		return currentFaceTo;
	}
	/**生成一个藏宝图的坐标数据
	 * @param mapID 指定某个地图ID，也可以传入-1来表示不指定，随机地图
	 * @return {地图id，x坐标，y坐标}
	 * */
	public int[] ITEM_generateTreasureMapAttr(int mapID){
		int[] pos=new int[3];
		if(mapID==-1){
			int idx=RandomUT.getRandom(0,treasureMapIDPool.length-1);
			pos[0]=treasureMapIDPool[idx];
			int[] poss=MAP_getMapRandomPassbleXY(pos[0]);
			pos[1]=poss[0];
			pos[2]=poss[1];
		}else{
			pos[0]=mapID;
			int[] poss=MAP_getMapRandomPassbleXY(pos[0]);
			pos[1]=poss[0];
			pos[2]=poss[1];
		}
		return pos;
	}
	/**判断是否是相同类型的道具
	 * */
	public boolean ITEM_isTheSameItem(ItemStackData item1,ItemStackData item2){
		if(item1.getItemID()==item2.getItemID()&&item1.getItemType()==item2.getItemType())
			return true;
		return false;
	}
	/**将一个道具数据转换为一个商品
	 * @param item 道具
	 * @param price 售价
	 * @param infinite 是否是无限供货的
	 * @return 商品
	 * */
	public GoodsData ITEM_generateGoods(ItemStackData item,int price,boolean infinite){
		GoodsData goods;
		goods=new GoodsData(game);
		goods.setItem(item);
		goods.setPrice(price);
		goods.setInfinite(infinite);
		return goods;
	}
	/**获取物品名称*/
	public String ITEM_getItemName(ItemStackData item){
		return game.itemDB.getItemName(item);
	}
	/**获取物品名称
	 * @param item_type ItemDB.ITEM_BASIC等物品类型
	 * @param item_id 物品ID
	 * */
	public String ITEM_getItemName(int item_type,int item_id){
		return game.itemDB.getItemName(item_type,item_id);
	}
	/**获取一个召唤兽的类型名称，比如昵称是小龟的大海龟，类型名是大海龟
	 * @param Summon 召唤兽
	 * @return 名字
	 * */
	public String SUMMON_getTypeName(Summon summon){
		return SUMMON_getTypeName(summon.data());
	}
	
	/**获取一个召唤兽的类型名称，比如昵称是小龟的大海龟，类型名是大海龟
	 * @param SummonData 召唤兽数据
	 * @return 名字
	 * */
	public String SUMMON_getTypeName(SummonData data){
		String name="";
		name=game.summonDB.getModel(data.getType_id()).getName();
		return name;
	}
	
	/**获取一个召唤兽的参战等级
	 * @param Summon 召唤兽
	 * @return 等级
	 * */
	public int SUMMON_getInBattleLevel(Summon summon){
		return SUMMON_getInBattleLevel(summon.data());
	}
	
	/**获取一个召唤兽的参战等级
	 * @param SummonData 召唤兽数据
	 * @return 等级
	 * */
	public int SUMMON_getInBattleLevel(SummonData data){
		int level=0;
		level=game.summonDB.getModel(data.getType_id()).getLevel();
		return level;
	}
	/**当前是否在战斗中
	 * @return 是否在战斗中
	 * */
	public boolean SYSTEM_isBattling(){
		if(game==null)
			return false;
		if(game.senceStage==null)
			return false;
		if(game.senceStage.battleHud==null)
			return false;
		return game.senceStage.battleHud.isVisible();
	}
	
	
	
	
}
