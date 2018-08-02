package xyq.system.ai;

import java.util.Iterator;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.utils.Array;

/**节点图路径，用于保存寻路后找到的路径*/
public class MapGraphPath implements GraphPath<MapNode>{
	Array<MapNode> nodes;
	public MapGraphPath(){
		nodes=new Array<MapNode>();
	}
	@Override
	public Iterator<MapNode> iterator() {
		return nodes.iterator();
	}

	@Override
	public int getCount() {
		return nodes.size;
	}

	@Override
	public MapNode get(int index) {
		return nodes.get(index);
	}

	@Override
	public void add(MapNode node) {
		nodes.add(node);
	}

	@Override
	public void clear() {
		nodes.clear();
	}

	@Override
	public void reverse() {
		nodes.reverse();
	}
	public String toString(){
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append("[MapGraphPath]\n");
		for(int i=0;i<getCount();i++)
			sBuilder.append(get(i).toString()+"\n");
		sBuilder.append("---------------------\n");
		return sBuilder.toString();
	}
}
