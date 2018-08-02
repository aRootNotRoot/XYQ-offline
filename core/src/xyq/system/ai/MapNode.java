package xyq.system.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

/**地图节点数据*/
public class MapNode {
	/**与其他的连接数据*/
	Array<Connection<MapNode>> connections;
	/**节点的逻辑x坐标*/
	public int x;
	/**节点的逻辑y坐标*/
	public int y;
	/**节点在节点图里的索引*/
	public int index;
	/**节点的类型 0-通行 1-阻挡 2-传送*/
	public int type;
	
	public MapNode(){
		connections=new Array<Connection<MapNode>>();
	}
	
	public Array<Connection<MapNode>> getConnection() {
		return connections;
	}
	public void addConnection(MapNode toNode,float cost){
		if(toNode!=null)
			connections.add(new MapConnection(this, toNode, cost));
	}
	public String toString(){
		return "[x="+x+";y="+y+";type="+type+";index="+index+"]";
	}
}
