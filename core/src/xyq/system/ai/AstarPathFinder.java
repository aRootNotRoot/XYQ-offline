package xyq.system.ai;

import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;

/**libgdx 启发式A*路径搜索引擎*/
public class AstarPathFinder {
	/**A*路径搜索器*/
	IndexedAStarPathFinder<MapNode> pathFinder;
	/**构建一个搜索器，传入当前地图的节点图*/
	public AstarPathFinder(MapGraph graph){
		pathFinder=new IndexedAStarPathFinder<MapNode>(graph,false);
	}
	/**指定一个起始节点，指定一个目标节点，传入启发式引擎，计算路径
	 * @param foundPath 查找到的路径，如果目标不可达，则为空长度
	 * */
	public void pathFind(MapNode startNode,MapNode endNode, MapGraphPath foundPath){
		pathFinder.searchNodePath(startNode, endNode,new MapHeruastic(), foundPath);
	}
		
		
	
}
