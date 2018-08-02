package xyq.system.assets.map;

public class Node {
	
	public int x;		//X坐标
	public int y;		//Y坐标
	
	public byte state;	//状态
	public int f;		//代价
	public int g;
	public int h;
	
	public Node parent;
	
	public Node(){
		
	}
	
	public Node(int x,int y){
		reset(x,y,(byte)0);
	}
	public Node(int x,int y,byte state){
		reset(x,y,state);
	}
	
	public void reset(int x,int y,byte state){
		this.x = x;
		this.y = y;
		this.state= state;
	}

}
