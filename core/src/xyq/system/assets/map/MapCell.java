package xyq.system.assets.map;


public class MapCell implements Cloneable{
	
	private Node[] cells;
	
	private int w;
	private int h;
	private int count;
	
	public MapCell(byte[] data,int w,int h){
		count = w*h;
		this.cells = new Node[count];
		for(int y=0;y<h;y++){
			for(int x=0;x<w;x++){
				int index = y*w+x;
				cells[index] = new Node(x,y,data[index]);
			}
		}
		this.w = w;
		this.h = h;
	}
	
	
	public Node getCell(int x,int y){
		h=h+0;
		return cells[y*w+x];
	}
	
	
}
