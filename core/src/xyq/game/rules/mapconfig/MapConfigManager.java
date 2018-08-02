package xyq.game.rules.mapconfig;

import xyq.game.XYQGame;
import xyq.game.stage.XYQMapActor;

public class MapConfigManager {
	XYQGame game;
	
	public MapConfigManager(XYQGame game) {
		this.game=game;
	}

	public void loadConfig(int mapID,XYQMapActor map) {
		switch(mapID){
			case 1501:JianYeChengMapConfig.config(game,map);break;
		}
		
	}
}
