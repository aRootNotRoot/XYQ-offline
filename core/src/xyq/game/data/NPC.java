package xyq.game.data;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import xyq.game.XYQGame;
import xyq.game.stage.NPCActor;
import xyq.game.stage.XYQMapActor;
import xyq.system.LogicSystem;
import xyq.system.TimeEventAdapter;
import xyq.system.ai.MapGraphPath;
import xyq.system.ai.MapNode;
import xyq.system.assets.PPCData;
import xyq.system.bussiness.ShopData;
import xyq.system.utils.RandomUT;

public class NPC implements Comparable<NPC>{
	public XYQGame game;
	public NPCData data;
	NPC npc;

	/**NPC演员*/
	NPCActor actor;
	/**角色点击事件处理器*/
	public NPCClickEventAdapter NCA;
	/**角色AI*/
	public NPCBrain brain;
	
	boolean randomWalk;
	int rwTimeMin;
	int rwTimeMax;
	int rwArea;
	int rwBaseX;
	int rwBaseY;
	
	public NPC(XYQGame game,String NPCName,String shapeName){
		this.game=game;
		npc=this;
		data=new NPCData(this,NPCName,game.shapeManager.getShape(shapeName));
		brain=new NPCBrain(this);
		actor=new NPCActor(this);
		actor.setPosition(1*20+10, 1*20+10);
        //tt.setPosition(0, 0);
		actor.setActorFaceTo(0);
		data.currentAction=ShapeData.STAND;
		actor.switchAction();
		actor.updateNPCName();
		actor.updateNPCTitle();
	}
	public NPC(XYQGame game,String NPCName,String shapeName,PPCData pp){
		this.game=game;
		npc=this;
		data=new NPCData(this,NPCName,game.shapeManager.getShape(shapeName));
		brain=new NPCBrain(this);
		actor=new NPCActor(this,pp);
		actor.setPosition(1*20+10, 1*20+10);
        //tt.setPosition(0, 0);
		actor.setActorFaceTo(0);
		data.currentAction=ShapeData.STAND;
		actor.switchAction();
		actor.updateNPCName();
		actor.updateNPCTitle();
	}
	public XYQGame inGame() {
		return game;
	}
	/**摆摊开卖东西*/
	public void saleON(String name){
		stopMoving();
		data.canMove=false;
		actor.saleON(name);
		data.saling=true;
		
	}
	public void setShopData(ShopData shop){
		this.data.shopData=shop;
		this.data.shopID=shop.shopID;
	}
	public void saleNameChange(String newName){
		actor.saleNameChange(newName);
		data.myZhaoPaiName=newName;
	}
	/**收摊啦*/
	public void saleOFF(){
		data.canMove=true;
		actor.saleOFF();
		data.saling=false;
	}
	public void setBehaviorTree(BehaviorTree<NPC> tree) {
		this.brain.behaviorTree=tree;
	}
	public void setMoveTarget(int logicX, int logicY) {
		data.moveTargetX=logicX;
		data.moveTargetY=logicY;
		
	}
	/**获取标记名称，一般用于事件唯一标识*/
	public String getMarkText(){
		return "["+getInMapID()+"~"+name()+"]";
	}
	public void beginRandomWalk(int timemin,int timemax,int area){
		randomWalk=true;
		this.rwTimeMin=timemin;
		this.rwTimeMax=timemax;
		this.rwArea=area;
		this.rwBaseX=getLogicX();
		this.rwBaseY=getLogicY();
		randomwalk();
	}
	public void stopRandomWalk(int timemin,int timemax,int area){
		randomWalk=false;
		game.ts.delTask(getMarkText()+"随机移动1");
		game.ts.delTask(getMarkText()+"随机移动2");
	}
	void randomwalk(){
		if(game.ls.ifm.SYSTEM_isBattling())
			return;
		int time=RandomUT.getRandom(rwTimeMin, rwTimeMax);
		game.ts.addTask(getMarkText()+"随机移动1", time, new TimeEventAdapter(){
        	@Override
        	public void act(){
        		int[] pos=game.ls.ifm.MAP_getMapRandomPassbleXY(getInMapID(),rwBaseX,rwBaseY,rwArea);
        		game.cs.NPC_MoveTo(npc, pos[0], pos[1]);
        		int time2=RandomUT.getRandom(rwTimeMin, rwTimeMax);
        		game.ts.addTask(getMarkText()+"随机移动2", time2, new TimeEventAdapter(){
                	@Override
                	public void act(){
                		randomwalk();
                	}
                });
        	}
        });
	}
	public String getCurrentStat(){
		return actor.getCurrentAction();
	}
	public void setNPCFaceTo(int direct){
		data.currentFaceTo=direct;
		if(actor!=null)
			actor.setActorFaceTo(direct);
		
	}
	public void addClickEventListener(NPCClickEventAdapter nca){
		this.NCA=nca;
		
	}
	public void clearActions(){
		if(actor!=null)
			actor.clearActions();
	}
	public void addAction(Action action) {
		if(actor!=null)
			actor.addAction(action);
	}
	public boolean canMove(){
		return data.canMove;
	}
	public void moveTo(int logicX,int logicY){
		if(!npc.canMove()){
			if(game.is_Debug)
				Gdx.app.log("[ XYQ ]","[CommandSystem] "+npc.name()+" - 不能移动,当前是不可以移动状态 from ["+npc.getLogicX()+","+npc.getLogicY()+"] to ["+logicX+","+logicY+"]");
			return;
		}
		if(game.senceStage.map.getMapData(logicX, logicY)==1){
			Gdx.app.log("[ XYQ ]","[CommandSystem] "+npc.name()+" - 不能移动 from ["+npc.getLogicX()+","+npc.getLogicY()+"] to ["+logicX+","+logicY+"]");
			Gdx.app.log("[ XYQ ]","目标点是不可到达的点");
			return;
		}
		if(npc.getLogicX()==logicX&&npc.getLogicY()==logicY){
			Gdx.app.log("[ XYQ ]","[CommandSystem] "+npc.name()+" - 不需要移动 from ["+npc.getLogicX()+","+npc.getLogicY()+"] to ["+logicX+","+logicY+"]");
			Gdx.app.log("[ XYQ ]","目标点就是当前点");
			return;
		}
		long startMM=System.currentTimeMillis();
		MapNode start=game.ls.ifm.MAP_getNode(npc.getLogicX(),npc.getLogicY());
		MapNode end=game.ls.ifm.MAP_getNode(logicX,logicY);
		MapGraphPath answer = game.ls.ifm.MAP_pathFind(start, end);
		if(game.is_Debug)
			Gdx.app.log("[ XYQ ]", "[CommandSystem] "+game.ls.player.name()+"准备移动:from ["+npc.getLogicX()+","+npc.getLogicY()+"] to ["+logicX+","+logicY+"]");
					
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
			switchAction(ShapeData.WALK);
			int dir=game.ls.ifm.MAP_getTwoMapPointDiectInt(npc.shape(),answer.get(0),answer.get(1));
			npc.setNPCFaceTo(dir);
			npc.data.movingStep=null;
			npc.data.movingStep=answer;
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
			    	npc.switchAction(ShapeData.STAND);
			    	npc.data.movingStep=null;
			           if(game.is_Debug)
			            	Gdx.app.log("[ XYQ ]", "[ NPC ] ->移动完成--自动设定站立状态");
			            }
			});
			sequenceAction.addAction(stopWalking);
					
			npc.clearActions();
			npc.addAction(sequenceAction);
			
			npc.setMoveTarget(logicX,logicY);
		}
	}
	public void stopMoving(){
		if(data.movingStep==null)
			return;
		actor.clearActions();
		switchAction(ShapeData.STAND);
		data.movingStep=null;
		data.moveTargetX=getLogicX();
		data.moveTargetY=getLogicY();
    	if(game.is_Debug)
    		Gdx.app.log("[ XYQ ]", "[ NPC ] ->停止移动--自动设定站立状态");
	}
	public ShapeData shape(){
		return data.shape;
	}
	public boolean isMovingToTarget(){
		if(data.movingStep!=null&&data.currentAction.equals(ShapeData.WALK))
			if(data.logicX!=data.moveTargetX||data.logicY!=data.moveTargetY)
				return true;
		return false;
	}
	public boolean isMovedToTarget(){
		if(data.movingStep==null&&data.currentAction.equals(ShapeData.STAND))
			if(data.logicX==data.moveTargetX||data.logicY==data.moveTargetY)
				return true;
		return false;
	}
	/**获取NPC目标移动位置，如果没有移动没有目标则返回null*/
	public MapNode getMoveTarget(){
		if(data.movingStep==null)
			return null;
		return data.movingStep.get(data.movingStep.getCount()-1);
	}
	public void setNPCPosition(int x,int y){
		data.logicX=x;
		data.logicY=y;
		actor.setPosition(x*20+10, y*20+10);
	}
	public void setNPCName(String name){
		data.name=name;
		actor.updateNPCName();
	}
	public void setNPCTitle(String title){
		data.title=title;
		actor.updateNPCTitle();
	}
	public void addThisNPC(XYQMapActor inMap){
		inMap.addActor(actor);
		data.inMapID=inMap.mapID;
		inMap.addNPC(this);
	}
	public void useGreenName(boolean b) {
		if(b!=actor.useGreenName){
			actor.useGreenName=b;
			actor.updateNPCName();
		}
		
	}
	public void switchAction(String action){
		data.currentAction=action;
		actor.switchAction();
	}
	public int getLogicX(){
		return data.logicX;
	}
	public int getLogicY(){
		return data.logicY;
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
				Gdx.app.error("[ XYQ ]","[NPC] 设定的坐标是不可到达的点："+x+","+y);
				Gdx.app.error("[ XYQ ]","[NPC] 设定的坐标对应的地图值为："+inMap().getMapData(x,y));
			}
			return;
		}
		
		data.logicX=x;
		data.logicY=y;
		if(data.movingStep==null)
			actor.setPosition(x*20+10, y*20+10);
	}
	private XYQMapActor inMap() {
		return game.maps.getMap(getInMapID());
	}

	public String name() {
		return data.name;
	}
	public void say(String string) {
		actor.say(string);
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]", "[ NPC ] -> "+name()+"说:"+string);
		}
	}
	void updateMovingFaceTo(){
		MapGraphPath movingStep=data.movingStep;
		if(movingStep==null)
			return;
		for(int i=0;i<movingStep.getCount();i++){
			MapNode point=movingStep.get(i);
			if(point.x==getLogicX()&&point.y==getLogicY()){
				if(i<movingStep.getCount()-1){
					int nextIndex=i+1;
					MapNode nextPo=movingStep.get(nextIndex);
					int dir=game.ls.ifm.MAP_getTwoMapPointDiectInt(data.shape,point, nextPo);
					if(dir==actor.getNPCFaceTo())
						return;
					if(game.is_Debug)
						Gdx.app.log("[ XYQ ]","[NPC] -> 移动中，自动设定朝向："+dir+" -> from "+point.x+","+point.y+" to "+nextPo.x+","+nextPo.y);
					setNPCFaceTo(dir);
					return;
				}
			}
		}
	}
	void updateLogicPosByXY(){
		setLogicXY((int)(actor.getX()/20),(int)(actor.getY()/20));
	}
	public void updateXYByLogicXY(){
		actor.setX(getLogicX()*20+10);
		actor.setY(getLogicY()*20+10);
	}
	public int getInMapXByLogic(){
		return  getLogicX()*20+10;
	}
	public int getInMapYByLogic(){
		return  getLogicY()*20+10;
	}
	public int getInMapID(){
		return data.inMapID;
	}
	public void setInMapID(int id){
		data.inMapID=id;
	}
	public void update(float deltaTime) {
		updateLogicPosByXY();
		updateMovingFaceTo();
		if(brain.behaviorTree!=null)
			brain.behaviorTree.step();
	}
	public void drawBubble(Batch batch, float parentAlpha){
		actor.drawBubble(batch, parentAlpha);
	}
	/**按照Y坐标排序（将Y坐标更高的排前面）*/
	@Override
	public int compareTo(NPC arg0) {
		return arg0.getLogicY()-getLogicY();
	}
	
	
	
}

