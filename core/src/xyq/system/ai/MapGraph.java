package xyq.system.ai;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

import xyq.game.stage.XYQMapActor;

/**地图的节点图，记录每个节点数据*/
public class MapGraph implements IndexedGraph<MapNode>{
	/**节点组*/
	Array<MapNode> nodes;
	/**记录地图里逻辑坐标横向总数，x总数*/
	int widthSize;
	/**构建一个地图节点图
	 * @param map 要生成的节点图的地图
	 * */
	public MapGraph(XYQMapActor map){
		//读取map数据，然后生成包含所有节点的，数据通路图
		nodes=new Array<MapNode>();
		for(int y=0;y<map.maxLocY;y++)
			for(int x=0;x<map.maxLocX;x++){
				MapNode node=new MapNode();
				node.x=x;
				node.y=y;
				node.index=y*map.maxLocX+x;
				node.type=map.getMapData(x, y);
				nodes.add(node);
			}
		widthSize=map.maxLocX;
		//之后再生成所有连接，8方向
		//考虑到移动的消耗，要注意，上右方向都有阻挡的时候，不能往右上方直接走
		for(int y=0;y<map.maxLocY;y++)
			for(int x=0;x<map.maxLocX;x++){
				int rightX=x+1;
				int leftX=x-1;
				int upY=y+1;
				int downY=y-1;
				int index=y*map.maxLocX+x;
				MapNode fromNode=nodes.get(index);
				MapNode upNode;
				if(upY>=map.maxLocY){//如果超出边界线
					upNode=null;
				}else{
					index=upY*map.maxLocX+x;
					upNode=nodes.get(index);
				}
				MapNode downNode;
				if(downY<=0){//如果超出边界线
					downNode=null;
				}else{
					index=downY*map.maxLocX+x;
					downNode=nodes.get(index);
				}
				MapNode leftNode;
				if(leftX<=0){//如果超出边界线
					leftNode=null;
				}else{
					index=y*map.maxLocX+leftX;
					leftNode=nodes.get(index);
				}
				MapNode rightNode;
				if(rightX>=map.maxLocX){//如果超出边界线
					rightNode=null;
				}else{
					index=y*map.maxLocX+rightX;
					rightNode=nodes.get(index);
				}
				MapNode rightUPNode;
				if(rightX>=map.maxLocX||upY>=map.maxLocY){//如果超出边界线
					rightUPNode=null;
				}else{
					index=upY*map.maxLocX+rightX;
					rightUPNode=nodes.get(index);
				}
				MapNode rightDownNode;
				if(rightX>=map.maxLocX||downY<=0){//如果超出边界线
					rightDownNode=null;
				}else{
					index=downY*map.maxLocX+rightX;
					rightDownNode=nodes.get(index);
				}
				MapNode leftUpNode;
				if(leftX<=0||upY>=map.maxLocY){//如果超出边界线
					leftUpNode=null;
				}else{
					index=upY*map.maxLocX+leftX;
					leftUpNode=nodes.get(index);
				}
				MapNode leftDownNode;
				if(leftX<=0||downY<=0){//如果超出边界线
					leftDownNode=null;
				}else{
					index=downY*map.maxLocX+leftX;
					leftDownNode=nodes.get(index);
				}
				//检查阻挡块，然后计算消耗
				//0-通行 2-传送 1-阻挡
				if(isPassble(upNode))
					fromNode.addConnection(upNode, 1);
				if(isPassble(downNode))
					fromNode.addConnection(downNode, 1);
				if(isPassble(leftNode))
					fromNode.addConnection(leftNode, 1);
				if(isPassble(rightNode))
					fromNode.addConnection(rightNode, 1);
				
				if(isPassble(rightUPNode))
					if(isPassble(rightNode)||isPassble(upNode))//保证斜着通过的时候，上右两块至少有一个可以通行
						fromNode.addConnection(rightUPNode, 1);
				if(isPassble(rightDownNode))
					if(isPassble(rightNode)||isPassble(downNode))
						fromNode.addConnection(rightDownNode, 1);
				if(isPassble(leftUpNode))
					if(isPassble(leftNode)||isPassble(upNode))
						fromNode.addConnection(leftUpNode, 1);
				if(isPassble(leftDownNode))
					if(isPassble(leftNode)||isPassble(downNode))
						fromNode.addConnection(leftDownNode, 1);
			}
	}
	/**当前节点是否可以通行*/
	public boolean isPassble(MapNode node){
		if(node == null)
			return false;
		//0-通行 2-传送 1-阻挡
		return node.type==0||node.type==2;
	}
	@Override
	public Array<Connection<MapNode>> getConnections(MapNode fromNode) {
		return fromNode.getConnection();
	}

	@Override
	public int getIndex(MapNode node) {
		
		return node.index;
	}
	/**根据逻辑坐标x，y返回节点图里记录对应坐标数据的节点*/
	public MapNode getNode(int x,int y){
		return nodes.get(y*widthSize+x);
	}
	@Override
	public int getNodeCount() {
		
		return nodes.size;
	}

}
