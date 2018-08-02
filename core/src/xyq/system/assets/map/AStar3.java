package xyq.system.assets.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A×算法第三版,采用LinkedList作为OpenList
 * 
 * @author user
 * 
 */
public class AStar3 implements PathFinder {

	public final static byte OPEN_FLAG = 3;
	public final static byte CLOSE_FLAG = 4;

	private final static int COST_STRAIGHT = 10;
	private final static int COST_DIAGONAL = 14;
	
	private final static int UNIT = 20;

	private byte[] data;
	private int w, h;

	private Node[] neighbours;

	public AStar3(byte[] data, int w, int h) {
		this.data = data;
		this.w = w;
		this.h = h;
		neighbours = new Node[8];
	}

	public List<Node> process(int srcX,int srcY,int desX,int desY) {
		if(!walkAvailable(desX,desY)){
			Node des = getNearestNode(srcX,srcY,desX,desY);
			if(des!=null){
				desX = des.x;
				desY = des.y;
			}
		}
		List<Node> nodes = pathFinding(srcX,srcY,desX,desY);
		//List<Node> path = smooth(nodes);
		//return path;
		return nodes;
	}

	//得到最近的可用点
	public Node getNearestNode(int srcX,int srcY,int desX,int desY) {
		double sin=0,cos=0,angle=0;
		if(desX!=srcX){
			 angle = Math.atan(Math.abs(((double)(srcY-desY))/((double)(desX-srcX))));
			 if(desY<srcY){
				 sin = -Math.sin(angle);
			 }else{
				 sin = Math.sin(angle);
			 }
			 if(desX>srcX){
				 cos = Math.abs(Math.cos(angle));
			 }else{
				 cos = -Math.abs(Math.cos(angle));
			 }
		}else{
			if(desY>srcY){
				sin=1;
			}else if(desY<srcY){
				sin=-1;
			}else{
				sin=0;
			}
			cos = 0;
		}
		double dist = Math.sqrt(Math.pow((desX-srcX)*UNIT,2)+Math.pow((desY-srcY)*UNIT,2));
		int d = 1;
		while(dist>d){
			double y = (desY)*UNIT+UNIT/2-sin*d;
			double x = (desX)*UNIT+UNIT/2-cos*d;
			
			int a = (int)Math.floor(x/UNIT);
			int b = (int)Math.floor(y/UNIT);
			
			if(data[b*w+a]!=1){
				return new Node(a,b);
			}else{
				d++;
			}
		}
		return null;
	}

	public List<Node> pathFinding(int srcX, int srcY, int desX, int desY) {
		List<Node> list = new ArrayList<Node>();
		MapCell cells = new MapCell(data, w, h);

		List<Node> open = new ArrayList<Node>();
		Node start = cells.getCell(srcX, srcY);

		Node result = null;
		// 加入开放列表
		insertOpenList(open, start);
		Node current = null;
		while (open.size() > 0) {
			// 取出Open表中最小节点，即为第一个节点。
			current = open.remove(0);
			//System.out.println("(" + current.x + "," + current.y + ")f:"
			//		+ current.f + ",g:" + current.g + ",h:" + current.h);
			// 如果当前点为目标节点
			if (current.x == desX && current.y == desY) {
				result = current;
				break;
			}
			// 标识为关闭状态
			current.state = CLOSE_FLAG;

			int count = 0;
			int sx = Math.max(0, current.x - 1);
			int ex = Math.min(w - 1, current.x + 1);
			int sy = Math.max(0, current.y - 1);
			int ey = Math.min(h - 1, current.y + 1);
			Node node = null;
			for (int y = sy; y <= ey; y++) {
				for (int x = sx; x <= ex; x++) {
					node = cells.getCell(x, y);
					if (node.state != MapFile.UNABLE_FLAG && node.state != CLOSE_FLAG) {
						int g = calculateG(current, node);
						if (node.state == OPEN_FLAG) {
							// 有更小的G
							if (g < node.g) {
								node.parent = current;
								node.g = g;
								node.f = g + node.h;
								// 该节点在Open表中重新排序
								resort(open, node);

							}
						} else {
							neighbours[count++] = node;
							node.g = g;
						}
					}
				}
			}
			// 得到该点的所有相邻点
			for (int i = 0; i < count; i++) {
				neighbours[i].h = calculateH(neighbours[i], desX, desY);
				neighbours[i].f = neighbours[i].h + neighbours[i].g;
				neighbours[i].parent = current;
				insertOpenList(open, neighbours[i]);
			}

		}
		// 有结果
		while (true) {
			if (result == null) {
				break;
			} else {
				list.add(result);
				result = result.parent;
			}
		}

		// 反转结果
		Collections.reverse(list);
		//Node[] array = new Node[list.size()];
		//array = list.toArray(array);
		//reverse(array);
		//return array;
		return list;
	}

	//倒转数组
	public void reverse(Node[] nodes) {
		int size = nodes.length;
		int csize = size / 2;
		Node temp = null;
		for (int i = 0; i < csize; i++) {
			temp = nodes[size - i - 1];
			nodes[size - i - 1] = nodes[i];
			nodes[i] = temp;
		}
	}

	// 插入开放列表，并把状态设置为OPEN
	private void insertOpenList(List<Node> open, Node node) {
		// 按F值插入
//		boolean insert = false;
//		int size = open.size();
//		for (int i = 0; i < size; i++) {
//			if (open.get(i).f >= node.f) {
//				open.add(i, node);
//				insert = true;
//				break;
//			}
//		}
//
//		if (!insert) {
//			open.add(node);
//		}
		int index = binarySearch(open,open.size(),node.f);
		open.add(index,node);
		node.state = OPEN_FLAG;
	}

	private void resort(List<Node> open, Node node) {
//		open.remove(node);
//		int size = open.size();
//		for (int i = 0; i <size; i++) {
//			if (open.get(i).f >= node.f) {
//				open.add(i, node);
//				return;
//			}
//		}
		int length = open.indexOf(node);
		int index = binarySearch(open,length,node.f);
		open.add(index, node);
	}
	
	//二分查找插入,length，为插入的范围
	private static int binarySearch(List<Node> open,int length,int f){
		int min = 0;
		int max = length-1;
		int index = length;
		while(min <= max){
			int mid = (min + max)/2;
			Node node = open.get(mid);
			if(node.f>=f){
				max = mid - 1;
				index = mid;
			}else{
				min = mid + 1;
			}
		}
		return index;
	}


	private int calculateG(Node parent, Node node) {
		if (node.x == parent.x || node.y == parent.y) {
			return parent.g + COST_STRAIGHT;
		} else {
			return parent.g + COST_DIAGONAL;
		}
	}

	private int calculateH(Node node, int desX, int desY) {
		return Math.abs(desX - node.x) * COST_STRAIGHT
				+ Math.abs(desY - node.y) * COST_STRAIGHT;
	}
	
	private boolean walkAvailable(int x,int y){
		if(data[y*w+x]==MapFile.UNABLE_FLAG)
			return false;
		else
			return true;
	}
	
	/**
	 * 优化自动寻路算法
	 */
	/*@Override
	public List<Node> optiPath(List <Node> path){
		if(path.isEmpty())return path;
		List <Node> optipath = new ArrayList<Node>();
		int start =0;
		optipath.add((Node)path.get(start));
		for(int i=1;i<path.size();i++){
			Node source = path.get(start);
			Node target = path.get(i);
			List<Node> linepath = SearchUtils.getLinePath(source.x,source.y,target.x,target.y);
			if(!passLinePath(linepath)){
				i--;
				start = i;
				optipath.add(path.get(i));
			}
		}
		optipath.add((Node)path.get(path.size()-1));
		return optipath;
	} */
	
	/*public boolean passLinePath(List <Node>linepath){
		boolean passed = true;
		for (int i = 0; i < linepath.size(); i++) {
			Node p = linepath.get(i);
			//if (!pass(p.x, p.y)) {
			if(pass(p.x,p.y)){
			    passed = false;
				break;
			}
		}
		return passed;
	}*/
	
	/**
	 * 
	 * @param nodes
	 * @return
	 */
	public List<Node> optiPath(Node[] nodes){
		List<Node> list = new LinkedList<Node>();
		int min = 0;
		int size = nodes.length;
		int max = size-1;
		while(true){
			if(min>=size-1){
				break;
			}
			if(walkable(nodes[min],nodes[max])){
				//logger.info("可行走点：{},{}",nodes[max].x,nodes[max].y);
				list.add(nodes[max]);
				min = max;
				max = size-1;
			}else{
				//logger.info("===阻挡点：{},{}",nodes[max].x,nodes[max].y);
				max--;
				if(max==min){
					min++;
					max = size-1;
					list.add(nodes[min]);
				}
			}
		}
		return list;
	}
	
	private boolean walkable(Node s,Node t){
		double sin=0,cos=0,angle=0;
		if(t.x!=s.x){
			 angle = Math.atan(Math.abs(((double)(s.y-t.y))/((double)(t.x-s.x))));
			 if(t.y<s.y){
				 sin = -Math.sin(angle);
			 }else{
				 sin = Math.sin(angle);
			 }
			 if(t.x>s.x){
				 cos = Math.abs(Math.cos(angle));
			 }else{
				 cos = -Math.abs(Math.cos(angle));
			 }
		}else{
			if(t.y>s.y){
				sin=1;
			}else if(t.y<s.y){
				sin=-1;
			}else{
				sin=0;
			}
			cos = 0;
		}
		double dist = Math.sqrt(Math.pow((t.x-s.x)*UNIT,2)+Math.pow((t.y-s.y)*UNIT,2));
		int d = 1;
		while(dist>d){
			double y = (s.y)*UNIT+UNIT/2+sin*d;
			double x = (s.x)*UNIT+UNIT/2+cos*d;
			
			int a = (int)Math.floor(x/UNIT);
			int b = (int)Math.floor(y/UNIT);
			
			if(data[b*w+a]==1){
				return false;
			}else{
				d++;
			}
		}
		return true;
	}
	public static void main(String[] args) {
		Node n1 = new Node(0,0,(byte)0);
		n1.f = 1;
		Node n2 = new Node(1,1,(byte)1);
		n2.f = 3;
		Node n3 = new Node(2,2,(byte)2);
		n3.f = 5;
		Node n4 = new Node(3,3,(byte)3);
		n4.f = 7;
		
		Node p = new Node(100,100,(byte)4);
		p.f = 2;
		List<Node> a = new ArrayList<Node>();
		a.add(n1);
		a.add(n2);
		a.add(n3);
		System.out.println(binarySearch(a,2,0));
	}

	public byte[] getCell() {
		return data;
	}

	@Override
	public List<Node> pathTrack(List<Node> path) {
		if(path.isEmpty())return path;
		List <Node> track = new ArrayList<Node>();
		int start =0;
		track.add((Node)path.get(start));
		for(int i=1;i<path.size();i++){
			List<Node> subpath = path.subList(start, i);
			Node source = subpath.get(0);
			Node target = subpath.get(subpath.size()-1);
			int temp = (target.x-source.x)*(target.x-source.x)+(target.y-source.y)*(target.y-source.y);
			int  fdistance = (int)Math.sqrt(temp*100);
			if(fdistance/100 == 1){
				start =i;
				track.add((Node)path.get(start));
			}
		}
		track.add((Node)path.get(path.size()-1));
		return track;
	}

//	@Override
//	public boolean pass(int x, int y) {
//		if(data[x+y*w]==1)
//			return false;
//		return true;
//	}
	
	@Override
	public boolean isValid(int x,int y) {
		return (x>=0&&x<w)?(y>=0&&y<h):false;
	}
	
	/******测试用*******/
	public void showData(int x,int y){
		System.err.println(data[x+y*w]);
	}
}
