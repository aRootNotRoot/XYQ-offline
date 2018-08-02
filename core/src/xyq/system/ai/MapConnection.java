package xyq.system.ai;

import com.badlogic.gdx.ai.pfa.Connection;

/**新启发式路径搜索，用来表示两个地图节点之间的连接的类*/
public class MapConnection implements Connection<MapNode>{
	MapNode from;
	MapNode to;
	float cost;
	/**构建一个连接
	 * @param from 起始节点
	 * @param to 目标节点
	 * @param cost 代价费用
	 * */
	public MapConnection(MapNode from,MapNode to,float cost){
		this.from=from;
		this.to=to;
		this.cost=cost;
	}
	@Override
	public float getCost() {
		return cost;
	}

	@Override
	public MapNode getFromNode() {
		return from;
	}

	@Override
	public MapNode getToNode() {
		return to;
	}

}
