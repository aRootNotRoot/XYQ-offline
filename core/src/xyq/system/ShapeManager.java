package xyq.system;

import java.util.HashMap;

import xyq.game.XYQGame;
import xyq.game.data.ShapeData;

/**外形管理器*/
public class ShapeManager {
	XYQGame game;
	public HashMap<String, ShapeData> shapes;
	public ShapeManager(XYQGame game){
		this.game=game;
		shapes=new HashMap<String, ShapeData>();
		shapes=game.db.loadAllShapeInfo(shapes);
	}
	/**按照Name获取一个外形数据*/
	public ShapeData getShape(String role) {
		return shapes.get(role);
	}
	
}
