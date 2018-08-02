package xyq.system.assets.map;

import java.util.List;

public interface PathFinder {
	
	public  List<Node> pathFinding(int srcX,int srcY,int desX,int desY);
	
	//public List<Node> smooth(Node[] nodes);
	public List<Node> optiPath(Node[] path);
	
	public List<Node> pathTrack(List<Node> path);
	
	public  Node getNearestNode(int srcX,int srcY,int desX,int desY);
	
	public List<Node> process(int srcX,int srcY,int desX,int desY);
	
	public byte[] getCell();
	
	//public boolean pass(int x,int y);
	
	public boolean isValid(int x,int y);
	
	public void showData(int x,int y);

}
