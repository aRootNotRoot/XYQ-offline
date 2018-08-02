package xyq.system.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;


public class MapHeruastic implements Heuristic<MapNode>{
	/**计算两个点的距离当做两个点的估值，多简单啊*/
	@Override
	public float estimate(MapNode node, MapNode endNode) {
		return Math.abs(node.x-endNode.x)+Math.abs(node.y-endNode.y);
	}

}
