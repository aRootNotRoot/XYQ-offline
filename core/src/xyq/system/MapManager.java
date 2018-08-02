package xyq.system;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import xyq.game.XYQGame;
import xyq.game.data.ShapeData;
import xyq.game.stage.XYQMapActor;
import xyq.system.map.MapInfo;

public class MapManager {
	XYQGame game;
	HashMap<Integer, XYQMapActor> maps;
	HashMap<Integer, MapInfo> map_infos;
	
	
	public CameraControl cam;
	public int currentMapID=1501;
	
	boolean mapChanging=false;
	int wantedMapID;
	int wantedDesX;
	int wantedDesY;
	
	public MapManager(XYQGame game) {
		this.game=game;
		maps=new HashMap<Integer, XYQMapActor>();
		map_infos=new HashMap<Integer, MapInfo>();
		this.currentMapID=game.ls.player.getInMapID();
		cam=new CameraControl(game);
		map_infos=game.db.loadAllMapInfo(map_infos);
	}
	public XYQMapActor getMap(int id){
		XYQMapActor temp=maps.get(id);
		if(temp==null){
			Gdx.app.log("[ XYQ ]", "[MapManager] -> 当前"+id+"号地图未加载，开始加载地图");
			temp =new XYQMapActor(game, id);
			//temp.loadBGM();
			maps.put(id, temp);
			temp=maps.get(id);
		}else{

			
		}
		return temp;
	}
	public String getMapName(int mapID) {
		String name=map_infos.get(mapID).mapName;
		if(name==null||name.equals(""))
			return "未知地区";
		return name;
	}
	public String getMapBGM(int mapID) {
		if(map_infos.get(mapID)==null)
			return "";
		return map_infos.get(mapID).BGM;
	}
	public String getMapSmallMapPack(int mapID) {
		if(map_infos.get(mapID)==null)
			return "";
		return map_infos.get(mapID).smallMAP_pack;
	}
	public String getMapSmallMapWas(int mapID) {
		if(map_infos.get(mapID)==null)
			return "";
		return map_infos.get(mapID).smallMAP_was;
	}
	public String getMapTips(int mapID) {
		if(map_infos.get(mapID)==null)
			return "";
		return map_infos.get(mapID).tips;
	}
	public boolean isMapChanging(){
		return mapChanging;
	}
	/**返回当前地图*/
	public XYQMapActor map(){
		XYQMapActor temp=maps.get(currentMapID);
		if(temp==null){
			Gdx.app.log("[ XYQ ]", "[MapManager] -> 当前"+currentMapID+"号地图未加载，开始加载地图");
			temp =new XYQMapActor(game, currentMapID);
			maps.put(currentMapID, temp);
		}
		return temp;
	}
	/**地图切换*/
	public void switchMap(final int id,final int desX,final int dexY){
		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]", "[MapManager] ->开始切换地图");
			Gdx.app.log("[ XYQ ]", "[MapManager] ->目标地图："+id+" 目的坐标为："+desX+","+dexY);
		}
		wantedMapID=id;
		wantedDesX=desX;
		wantedDesY=dexY;
		//game.sm.stopBGM();
		mapChanging=true;
		MoveToAction moveTo2 = Actions.moveTo(0, 0, 0.8F);
		
		game.senceStage.mapChangingMask.clearActions();
		game.senceStage.mapChangingMask.addAction(moveTo2);
	}
	/**完成地图切换*/
	public void doneMapChange(){
		XYQMapActor tempMap=getMap(wantedMapID);
		game.ls.player.actor.remove();
		tempMap.addActor(game.ls.player.actor);
		
		game.senceStage.setMap(tempMap);
		currentMapID=wantedMapID;
		
		
		game.ls.player.movingStep=null;
		game.ls.player.actor.clearActions();
		game.ls.player.actor.switchStat(ShapeData.STAND);
		game.ls.player.setInMapID(wantedMapID);
		game.ls.player.setLogicXY(wantedDesX,wantedDesY);
		game.ls.player.updateXYByLogicXY();
		
		map().lookAtPlayer();
		
		map().smallMapAutoXY();

		if(game.is_Debug){
			Gdx.app.log("[ XYQ ]", "[MapManager] ->输出切换地图调试信息");
			Gdx.app.log("[ XYQ ]", game.ls.player.getInMapID()+"<- InMapID");
			Gdx.app.log("[ XYQ ]", game.ls.player.getLogicX()+"<- logX");
			Gdx.app.log("[ XYQ ]", game.ls.player.getLogicY()+"<- logY");
			Gdx.app.log("[ XYQ ]", game.ls.player.actor.getX()+"<- actorX");
			Gdx.app.log("[ XYQ ]", game.ls.player.actor.getY()+"<- actorY");
			Gdx.app.log("[ XYQ ]", "[MapManager] ->开始通知资源系统改变BGM");
		}
		map().loadBGM();
	}
	public XYQMapActor currentMap(){
		return game.senceStage.map;
	}
	public XYQMapActor playerCurrentMap(){
		return getMap(game.ls.player.getInMapID());
	}
	/**检查地图切换*/
	void checkMapChanging(){
		if(mapChanging){
			float x=game.senceStage.mapChangingMask.getX();
			if(x<=10f){
				game.senceStage.mapChangingMask.setX(0);
				doneMapChange();
				MoveToAction moveTo2 = Actions.moveTo(1281, 0, 0.3F);
				game.senceStage.mapChangingMask.clearActions();
        		game.senceStage.mapChangingMask.addAction(moveTo2);
				mapChanging=false;
			}
		}
	}
	public void update(float dt){
		checkMapChanging();
		cam.update(dt);
	}
	
}
