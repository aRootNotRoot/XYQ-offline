package xyq.game.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import xyq.game.XYQGame;
import xyq.game.stage.PlayerActor;
import xyq.game.stage.XYQMapActor;
import xyq.system.LogicSystem;
import xyq.system.ai.MapGraphPath;
import xyq.system.ai.MapNode;
import xyq.system.assets.PPCData;
import xyq.game.data.ShapeData;

public class Player{
	XYQGame game;
	Player player;
	/**玩家ID*/
	public String id;
	
	
	/**升级需要的经验*/
	public int levelUpExp=1;
	/**玩家数据*/
	PlayerData  data;
	//-------------------------------------------------------------------------------------------------------------
	
	/**玩家的外形*/
	public ShapeData shape;
	/**玩家演员*/
	public PlayerActor actor;
	/**玩家换色数据*/
	//PPCData pp;
	//-------------------------------------------------------------------------------------------------------------
	/**玩家是否可以移动*/
	public boolean canMove=true;
	/**玩家是否在摆摊*/
	public boolean saling=false;
	/**玩家的移动序列*/
	public MapGraphPath movingStep;
	public String myZhaoPaiName="杂货摊位";
	//public ArrayList<MapPoint> movingStep;
	
	public Player(XYQGame game,String id){
		this.game=game;
		this.id=id;
		player=this;
		
		
		data=game.db.loadDataById(id);
		levelUpExp=game.db.loadLevelUpEXP(data.level);
		//items=game.db.loadRoleItemStackData(id);
		//已经移到playerData了
		//moneys =game.db.loadMoneyData(id);
		shape=game.shapeManager.getShape(data.Role);
		//summons=game.db.loadSummonData(id);
	}
	/**更新玩家的物品数据*/
	public void updataItems(HashMap<Integer,ItemStackData> newItemStack){
		if(newItemStack==null){
			Gdx.app.error("[ XYQ ]","[Player] -> 更新玩家物品数据出错了:因为传递的玩家物品数据空指针");
			return;
		}
		data.items=null;
		data.items=newItemStack;
	}
	public void saveData(){
		
	}
	/**摆摊开卖东西*/
	public void saleON(String name){
		stopMoving();
		canMove=false;
		actor.saleON(name);
		saling=true;
		
	}
	public ArrayList<Summon> getSummons(){
		return data.summons;
	}
	public void saleNameChange(String newName){
		actor.saleNameChange(newName);
		myZhaoPaiName=newName;
	}
	/**收摊啦*/
	public void saleOFF(){
		canMove=true;
		actor.saleOFF();
		saling=false;
	}
	/**获取玩家目标移动位置，如果没有移动没有目标则返回null*/
	public MapNode getMoveTarget(){
		if(movingStep==null)
			return null;
		return movingStep.get(movingStep.getCount()-1);
	}
	/**获取玩家的所有物品
	 * */
	public HashMap<Integer,ItemStackData> getItems() {
		return data.items;
	}
	/**获取玩家的某个物品
	 * @param stackIndex 物品所在的位置【0-5为装备】【6-25为道具】
	 * */
	public ItemStackData getItem(int stackIndex) {
		return data.items.get(stackIndex);
	}
	/**查找并获取玩家背包里的某个物品
	 * @param type 物品type
	 * @param id 物品id
	 * */
	public ItemStackData getItem(int type, int id) {
		for(int i=6;i<26;i++){
			ItemStackData item=getItem(i);
			if(item!=null){
				if(item.getItemType()==type&&item.getItemID()==id)
					return item;
			}
		}
		return null;
	}
	/**设定玩家的某个物品
	 * @param stackIndex 物品所在的位置【0-5为装备】【6-25为道具】
	 * */
	public boolean setItem(int stackIndex,ItemStackData item) {
		if(item.getStackIndex()!=stackIndex){
			Gdx.app.error("[ XYQ ]","[Player] -> 设定玩家物品失败，因为物品数据和位置不匹配");
			return false;
		}
		data.items.put(stackIndex,item);
		return true;
	}
	/**清掉玩家的某个物品
	 * @param stackIndex 物品所在的位置【0-5为装备】【6-25为道具】
	 * */
	public boolean clearItem(int stackIndex) {
		data.items.remove(stackIndex);
		return true;
	}
	/**移动玩家的某个物品
	 * @param nowItem 欲移动的物品
	 * @param toStackIndex 欲移动到的位置
	 * */
	public void moveItem(ItemStackData nowItem, int toStackIndex) {
		if(data.items.get(toStackIndex)!=null){
			Gdx.app.error("[ XYQ ]","[Player] -> 移动玩家物品失败，因为目标位置有物品了。如果想交换物品位置，请更换方法switchItem");
			return;
		}
		int stackIndex=nowItem.getStackIndex();
		nowItem.setStackIndex(toStackIndex);
		removeItem(stackIndex);
		setItem(toStackIndex,nowItem);
	}
	/**移动玩家的某个物品,如果目标位置有了物品则交换
	 * @param nowItem 欲移动的物品
	 * @param toStackIndex 欲移动到的位置
	 * */
	public void moveOrSwitchItem(ItemStackData nowItem, int toStackIndex) {
		if(data.items.get(toStackIndex)!=null){
			switchItem(nowItem.getStackIndex(), toStackIndex);
			return;
		}
		int stackIndex=nowItem.getStackIndex();
		nowItem.setStackIndex(toStackIndex);
		removeItem(stackIndex);
		setItem(toStackIndex,nowItem);
	}
	/**减少某个物品的数量
	 * @param nowItem 欲减少的物品
	 * @param count 减少的数量
	 * */
	public void minusItem(ItemStackData nowItem,int count){
		int nowCount=nowItem.getItemCount();
		if(nowCount<count){
			Gdx.app.error("[ XYQ ]","[Player] -> 减少玩家物品失败，因为想减少的数量超过了拥有的数量："+count+"当前拥有："+nowCount);
			return;
		}
		nowCount-=count;
		if(nowCount<=0){
			removeItem(nowItem.getStackIndex());
		}else{
			getItem(nowItem.getStackIndex()).setItemCount(nowCount);
		}
	}
	/**移动玩家的某个物品
	 * @param fromStackIndex 欲移动的位置
	 * @param toStackIndex 欲移动到的位置
	 * */
	public void moveItem(int fromStackIndex, int toStackIndex) {
		ItemStackData nowItem=data.items.get(fromStackIndex);
		if(nowItem==null){
			Gdx.app.error("[ XYQ ]","[Player] -> 移动玩家物品失败，因为原始位置的没有物品");
			return;
		}
		if(data.items.get(toStackIndex)!=null){
			Gdx.app.error("[ XYQ ]","[Player] -> 移动玩家物品失败，因为目标位置有物品了。如果想交换物品位置，请更换方法switchItem");
			return;
		}
		int stackIndex=nowItem.getStackIndex();
		nowItem.setStackIndex(toStackIndex);
		removeItem(stackIndex);
		setItem(toStackIndex,nowItem);
	}
	/**交换玩家的两个某个物品
	 * @param oneStackIndex 欲交换的第一个物品索引
	 * @param twoStackIndex 欲交换的第二个物品索引
	 * */
	public void switchItem(int oneStackIndex, int twoStackIndex) {
		if(oneStackIndex<0||twoStackIndex<0){
			Gdx.app.error("[ XYQ ]","[Player] -> 交换玩家物品失败，索引小于0了");
			return;
		}
		if(oneStackIndex==twoStackIndex){
			Gdx.app.error("[ XYQ ]","[Player] -> 交换玩家物品失败，目标位置和原始位置一样，不需要交换");
			return;
		}
		ItemStackData item1=data.items.get(oneStackIndex);
		ItemStackData item2=data.items.get(twoStackIndex);
		
		if(item1==null){
			Gdx.app.error("[ XYQ ]","[Player] -> 交换玩家物品失败，因为索引一位置的物品是空的："+oneStackIndex);
			return;
		}
		if(item2==null){
			Gdx.app.error("[ XYQ ]","[Player] -> 交换玩家物品失败，因为索引二位置的物品是空的："+twoStackIndex);
			return;
		}
		item2.setStackIndex(oneStackIndex);
		item1.setStackIndex(twoStackIndex);
		
		data.items.remove(oneStackIndex);
		data.items.remove(twoStackIndex);
		
		data.items.put(oneStackIndex,item2);
		data.items.put(twoStackIndex,item1);

	}
	/**删除玩家的某个物品
	 * @param stackIndex 物品所在的位置【0-5为装备】【6-25为道具】
	 * */
	public boolean removeItem(int stackIndex) {
		data.items.remove(stackIndex);
		return true;
	}
	
	/**装备玩家的某个装备
	 * @param nowItem 欲装备的物品
	 * */
	public void equipItem(ItemStackData nowItem) {
		int typeOfEquip=((ItemEquipData)game.itemDB.getItem_ISD(nowItem)).getRES_no();
		if(typeOfEquip<0||typeOfEquip>5){
			Gdx.app.error("[ XYQ ]","[Player] -> 准备装备装备，但是读取的装备类型不在规定的范围0-5内    [@]"+Calendar.getInstance().getTime());
			return;
		}
		moveOrSwitchItem(nowItem,typeOfEquip);
		//装备好以后重绘提示图标
		game.senceStage.dialogUIHud.reloadItemInfoPanel();
	}
	/**获取玩家背包里第一个空的位置(6-25)*/
	public int PLAYER_getPlayerFirstEmptyItemStackIndex() {
		for(int i=6;i<26;i++){
			if(game.ls.player.getItems().get(i)==null)
				return i;
		}
		return -1;
	}
	/**卸下玩家的某个装备
	 * @param nowItem 欲卸的物品
	 * */
	public void deEquipItem(ItemStackData nowItem) {
		int nowStackIndex=nowItem.getStackIndex();
		if(nowStackIndex<0||nowStackIndex>5){
			Gdx.app.error("[ XYQ ]","[Player] -> 无法卸下装备,欲操作的物品不是装备");
			return;
		}
		//TODO 把操作全部移动走，这里只留数据
		int toStackIndex=PLAYER_getPlayerFirstEmptyItemStackIndex();
		if(toStackIndex==-1){
			game.cs.UI_showSystemMessage("背包满了，无法卸下装备");
			return;
		}
		moveItem(nowItem, toStackIndex);
		if(game.is_Debug){
			String itemName=((ItemEquipData)game.itemDB.getItem_ISD(nowItem)).getName();
			Gdx.app.log("[ XYQ ]","[Player] -> 卸下了 "+itemName+"    [@]"+Calendar.getInstance().getTime());
		}
		
	}
	
	public PlayerData playerData(){
		return data;
	}
	public void reloadLevelUpExp(){
		levelUpExp=game.db.loadLevelUpEXP(data.level);
	}
	/**为界面更新游戏数据,加载玩家角色*/
	public void makeActor(){
		PPCData pp=game.rs.getPPData(shape.getColoration_pack(), shape.getColoration_was(),data.colorations);
		actor=new PlayerActor(game,shape,pp);
		actor.setPosition(data.location[0]*20+10, data.location[1]*20+10);
        //tt.setPosition(0, 0);
		actor.setPlayerFaceTo(0);
		actor.switchStat(ShapeData.STAND);
		actor.setPlayerName(data.name);
		actor.setPlayerTitle(data.Title);
		
		game.senceStage.map.addActor(actor);
		int[] xy=game.senceStage.map.getXYByLookPos(getInMapXByLogic(),getInMapYByLogic());
		game.senceStage.map.setX(xy[0]);
		game.senceStage.map.setY(xy[1]);
		
		game.senceStage.infoHud.setHeadImg("wzife.wdf", "方头像\\"+data.Role);
	}
	public void showAddonAnimation(String pack,String was,float xOff,float yOff){
		if(actor!=null)
			actor.showAddonAnimation(pack, was,xOff,yOff);
	}
	
	public void say(String words){
		if(actor!=null){
			actor.say(words);
			if(game.is_Debug){
				Gdx.app.log("[ XYQ ]", "[ Player ] -> "+name()+"说:"+words);
			}	
		}
	}
	public void drawBubble(Batch batch, float parentAlpha){
		actor.drawBubble(batch, parentAlpha);
	}
	public int getLogicX(){
		return data.location[0];
	}
	public int getLogicY(){
		return data.location[1];
	}
	public void setLogicXY(int x,int y){
		if(x>inMap().maxLocX)
			x=inMap().maxLocX-1;
		if(y>inMap().maxLocY)
			y=inMap().maxLocY-1;
		if(x<0)
			x=0;
		if(y<0)
			y=0;
		if(inMap().getMapData(x,y)==1){
			if(game.is_Debug){
				Gdx.app.error("[ XYQ ]","[Player] 设定的坐标是不可到达的点："+x+","+y);
				Gdx.app.error("[ XYQ ]","[Player] 设定的坐标对应的地图值为："+inMap().getMapData(x,y));
			}
			return;
		}
		else if(inMap().getMapData(x,y)==2){
			int trans[]=game.db.loadMapTransConfig(getInMapID(),x,y);
			if(trans[0]==-1||trans[1]==-1||trans[2]==-1){
				if(game.is_Debug)
					Gdx.app.error("[ XYQ ]","[Player] 传送失败：索引不对或数据库里面找不到传送数据："+trans[1]+","+ trans[2]+"@"+trans[0]+"号地图");
			}
			else{
				game.cs.MAP_SwitchMap(trans[0], trans[1], trans[2]);
				/*
				if(!game.maps.isMapChanging())
					game.maps.switchMap(trans[0], trans[1], trans[2]);
					*/
			}
		}
		data.location[0]=x;
		data.location[1]=y;
		actor.setLogicXY(data.location[0],data.location[1]);
	}
	private XYQMapActor inMap() {
		return game.maps.getMap(getInMapID());
	}
	public void setPlayerFaceTo(int i){
		if(i>=shape.getMaxDirection()){
			Gdx.app.error("[ XYQ ]", "[ Player ] -> 设定的朝向"+i+"超过了外形数据的最大方向数"+shape.getMaxDirection());
		}
		actor.setPlayerFaceTo(i);
	}
	public String name() {
		return data.name;
	}
	public void stopMoving(){
		if(movingStep==null)
			return;
		actor.clearActions();
		actor.switchStat(ShapeData.STAND);
    	movingStep=null;
    	if(game.is_Debug)
    		Gdx.app.log("[ XYQ ]", "[ Player ] ->停止移动--自动设定站立状态");
	}
	void updateMovingFaceTo(){
		if(movingStep==null)
			return;
		for(int i=0;i<movingStep.getCount();i++){
			MapNode point=movingStep.get(i);
			if(point.x==data.location[0]&&point.y==data.location[1]){
				if(i<movingStep.getCount()-1){
					int nextIndex=i+1;
					MapNode nextPo=movingStep.get(nextIndex);
					int dir=game.ls.ifm.MAP_getTwoMapPointDiectInt(shape,point, nextPo);
					if(dir==actor.getPlayerFaceTo())
						return;
					if(game.is_Debug)
						Gdx.app.log("[ XYQ ]","[Player] -> 移动中，自动设定朝向："+dir+" -> from "+point.x+","+point.y+" to "+nextPo.x+","+nextPo.y);
					actor.setPlayerFaceTo(dir);
					return;
				}
			}
		}
	}
	void updateLogicPosByXY(){
		setLogicXY((int)(actor.getX()/20),(int)(actor.getY()/20));
	}
	/**立即根据当前的逻辑坐标，更新玩家绘制XY坐标*/
	public void updateXYByLogicXY(){
		actor.setX(getLogicX()*20+10);
		actor.setY(getLogicY()*20+10);
	}
	void updateInfoHudXY(){
		game.senceStage.infoHud.setPlayerCoor(data.location);
		game.senceStage.infoHud.setPlayerMapName(game.maps.getMap(data.inMapID).mapName);
		game.senceStage.infoHud.setBarPercent(this);
	}
	void updateInfoDlg(){
		game.senceStage.dialogUIHud.updataPlrBasicInfo(data);
	}
	/**获取当前玩家在地图内的X坐标*/
	public int getInMapXByLogic(){
		return  getLogicX()*20+10;
	}
	/**获取当前玩家在地图内的Y坐标*/
	public int getInMapYByLogic(){
		return  getLogicY()*20+10;
	}
	/**获取当前玩家在地图的ID*/
	public int getInMapID(){
		return data.inMapID;
	}
	/**设定当前玩家所在地图id*/
	public void setInMapID(int id){
		data.inMapID=id;
	}
	public void moveTo (int logicX,int logicY){
		
		if(!player.canMove){
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]","[CommandSystem] "+player.name()+" - 不能移动,当前是不可以移动状态 from ["+player.getLogicX()+","+player.getLogicY()+"] to ["+logicX+","+logicY+"]");
			return;
		}
		if(game.senceStage.map.getMapData(logicX, logicY)==1){
			Gdx.app.log("[ XYQ ]","[CommandSystem] "+player.name()+" - 不能移动 from ["+player.getLogicX()+","+player.getLogicY()+"] to ["+logicX+","+logicY+"]");
			Gdx.app.log("[ XYQ ]","目标点是不可到达的点");
			return;
		}
		if(player.getLogicX()==logicX&&player.getLogicY()==logicY){
			Gdx.app.log("[ XYQ ]","[CommandSystem] "+player.name()+" - 不需要移动 from ["+player.getLogicX()+","+player.getLogicY()+"] to ["+logicX+","+logicY+"]");
			Gdx.app.log("[ XYQ ]","目标点就是当前点");
			return;
		}
		long startMM=System.currentTimeMillis();
		MapNode start=game.ls.ifm.MAP_getNode(player.getLogicX(),player.getLogicY());
		MapNode end=game.ls.ifm.MAP_getNode(logicX,logicY);
		MapGraphPath answer = game.ls.ifm.MAP_pathFind(start, end);
		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[CommandSystem] "+game.ls.player.name()+"准备移动:from ["+player.getLogicX()+","+player.getLogicY()+"] to ["+logicX+","+logicY+"]");
					
		if(answer==null||answer.getCount()<=0) {
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]", "[CommandSystem] 目标不可达："+logicX+","+logicX);
		}else {
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]",answer.toString());
			
			long endMM=System.currentTimeMillis()-startMM;
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]","[CommandSystem]A*查找路线耗时"+endMM+"ms");
					 
			
			//开始移动
			if(answer.getCount()==1){
				return;
			}
			SequenceAction sequenceAction = Actions.sequence();
			MapNode lastPoint=start;
			float moveTime=0;
			player.actor.switchStat(ShapeData.WALK);
			int dir=game.ls.ifm.MAP_getTwoMapPointDiectInt(player.shape,answer.get(0),answer.get(1));
			player.setPlayerFaceTo(dir);
			player.movingStep=null;
			player.movingStep=answer;
			for(int i=1;i<answer.getCount();i++){
				moveTime=game.ls.ifm.MAP_getTwoMapPointDest(lastPoint,answer.get(i))/LogicSystem.plrMoveSpeed;
				lastPoint=answer.get(i);
				MoveToAction moveTo = Actions.moveTo(answer.get(i).x*20+10, answer.get(i).y*20+10, moveTime);
			    sequenceAction.addAction(moveTo);
			    /*
			    RunnableAction runnable = Actions.run(new Runnable() {
			        @Override
			        public void run() {
			            	
			        }
			    });
			    sequenceAction.addAction(runnable);
			    */
			}
			RunnableAction stopWalking = Actions.run(new Runnable() {
			    @Override
			     public void run() {
			    	player.actor.switchStat(ShapeData.STAND);
			        player.movingStep=null;
			           if(game.is_Debug)
			            	Gdx.app.log("[ XYQ ]", "[ Player ] ->移动完成--自动设定站立状态");
			            }
			});
			sequenceAction.addAction(stopWalking);
					
			player.actor.clearActions();
			player.actor.addAction(sequenceAction);
			
			
		}
	}
	public void update(float deltaTime) {
		

	}
	public void updateInfoHud(){
		updateLogicPosByXY();
		updateMovingFaceTo();
		updateInfoHudXY();
		updateInfoDlg();
	}
	
	public class Shape {
		/**东南朝向0*/
		public final static int SouthEast=0;
		/**西南朝向1*/
		public final static int SouthWest=1;
		/**西北朝向2*/
		public final static int NorthWest=2;
		/**东北朝向3*/
		public final static int NorthEast=3;
		/**南朝向4*/
		public final static int South=4;
		/**西朝向5*/
		public final static int West=5;
		/**北朝向6*/
		public final static int North=6;
		/**东朝向7*/
		public final static int East=7;
		
		/**静立外形0*/
		public final static int STAND=0;
		/**行走外形1*/
		public final static int WALK=1;
	}


	/**获取可以放下对应物品的位置索引*/
	public ArrayList<Integer> getCouldPutItemIndex(int type,int id){
		ArrayList<Integer> indexs=new ArrayList<Integer>();
		HashMap<Integer, ItemStackData> items=getItems();
		for(int i=6;i<26;i++){
			ItemStackData aItem=items.get(i);
			if(aItem==null)
				indexs.add(i);
			else if(aItem.getItemType()==type&&aItem.getItemID()==id){
				if(game.itemDB.getMaxCount(aItem)>aItem.getItemMaxCount())
					indexs.add(i);
			}
		}
	
		return indexs;
	}
	
}

