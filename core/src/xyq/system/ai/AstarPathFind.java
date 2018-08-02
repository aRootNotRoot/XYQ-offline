package xyq.system.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import xyq.game.stage.XYQMapActor;

/**A*寻路工具类【静态，现在未使用】*/
public class AstarPathFind {
	// 八向寻找 - 前四个是上下左右，后四个是斜角
	public final static int[] dx = { 0, -1, 0, 1, -1, -1, 1, 1 };
	public final static int[] dy = { -1, 0, 1, 0, 1, -1, -1, 1 };
	// 四向寻找
	//public final static int[] dx = { 0, -1, 0, 1 };
	//public final static int[] dy = { -1, 0, 1, 0 };
	/**是否限制斜向移动的开关*/
	static boolean LIMITX=true;
	// 最外圈都是1表示不可通过
	//测试用地图数据
	final static public int[][] map = {
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		 { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		  { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
	public static void main(String[] args) {
		 MapPoint start = new MapPoint(1, 1);
		 MapPoint end = new MapPoint(10, 13);
		 /*
		 	第一个问题：起点FGH需要初始化吗？
		 	看参考资料的图片发现不需要
		 */
		 long startMM=System.currentTimeMillis();
		 Stack<MapPoint> stack = printPath(start, end);
		 if(null==stack) {
			 System.out.println("不可达");
		 }else {
			 while(!stack.isEmpty()) {
				//输出(1,2)这样的形势需要重写toString
				 System.out.print(stack.pop()+" -> ");
			 }
			 System.out.println();
		 }                      
		 long endMM=System.currentTimeMillis()-startMM;
		 System.out.println("A*查找路线耗时"+endMM);
	}
	/**测试用寻路方法*/
	public static Stack<MapPoint> printPath(MapPoint start, MapPoint end) {
		ArrayList<MapPoint> openTable = new ArrayList<MapPoint>();
		 ArrayList<MapPoint> closeTable = new ArrayList<MapPoint>();
		 openTable .clear();
		 closeTable.clear();
		 Stack<MapPoint> pathStack = new Stack<MapPoint>();
		 start.parent = null;
		//该点起到转换作用，就是当前扩展点
		 MapPoint currentPoint = new MapPoint(start.x, start.y);
		//closeTable.add(currentPoint);
		 boolean flag = true;
		 while(flag) {
			 for (int i = 0; i < dx.length; i++) {
				 int fx = currentPoint.x + dx[i];
				 int fy = currentPoint.y + dy[i];
				 MapPoint tempPoint = new MapPoint(fx,fy);
				 if (map[fx][fy] == 1) {
					 // 由于边界都是1中间障碍物也是1，，这样不必考虑越界和障碍点扩展问题
					//如果不设置边界那么fx >=map.length &&fy>=map[0].length判断越界问题
					 continue;
				 } else {
					 if(end.equals(tempPoint)) {
						 flag = false;
						//不是tempPoint，他俩都一样了此时
						 end.parent = currentPoint;
						 break;
					 }
					 if(i<4) {
						 tempPoint.G = currentPoint.G + 10;
					 }else {
						 tempPoint.G = currentPoint.G + 14;
					 }
					 tempPoint.H = MapPoint.getDis(tempPoint,end);
					 tempPoint.F = tempPoint.G + tempPoint.H;
					//因为重写了equals方法，所以这里包含只是按equals相等包含
					//这一点是使用java封装好类的关键
					 if(openTable.contains(tempPoint)) {
						 int pos = openTable.indexOf(tempPoint );
						 MapPoint temp = openTable.get(pos);
						 if(temp.F > tempPoint.F) {
							 openTable.remove(pos);
							 openTable.add(tempPoint);
							 tempPoint.parent = currentPoint;
						 }
						 }else if(closeTable.contains(tempPoint)){
							 int pos = closeTable.indexOf(tempPoint );
							 MapPoint temp = closeTable.get(pos);
							 if(temp.F > tempPoint.F) {
								 closeTable.remove(pos);
								 openTable.add(tempPoint);
								 tempPoint.parent = currentPoint;
							 }
						}else {
							openTable.add(tempPoint);
							tempPoint.parent = currentPoint;
						}
				 }
			 }//end for
			 if(openTable.isEmpty()) {
				 return null;
			 }//无路径
			 if(false==flag) {
				 break;
			 }//找到路径
			 openTable.remove(currentPoint);
			 closeTable.add(currentPoint);
			 Collections.sort(openTable);
			 currentPoint = openTable.get(0);
		 }//end while
		 MapPoint node = end;
		 while(node.parent!=null) {
			 pathStack.push(node);
			 node = node.parent;
		 }
		 return pathStack;
	 }
	/**用于限制斜向移动，但不限制斜向寻路的*/
	static boolean cantMoveX(XYQMapActor inMap,int nowx,int nowy,int dx,int dy){
		if(!LIMITX)
			return false;
		//System.out.println(nowx+","+nowy+","+dx+","+dy);
		
		/*
		if(nowx>lastx&&nowy>lasty)//右上方
		{
			if(inMap.getMapData(lastx, lasty+1)==1&&inMap.getMapData(lastx+1, lasty)==1)
				return true;
		}
		if(nowx>lastx&&nowy<lasty)//右下方
		{
			if(inMap.getMapData(lastx, lasty-1)==1&&inMap.getMapData(lastx+1, lasty)==1)
				return true;
		}
		if(nowx<lastx&&nowy>lasty)//左上方
		{
			if(inMap.getMapData(lastx-1, lasty)==1&&inMap.getMapData(lastx, lasty+1)==1)
				return true;
		}
		if(nowx<lastx&&nowy<lasty)//左下方
		{
			if(inMap.getMapData(lastx, lasty-1)==1&&inMap.getMapData(lastx-1, lasty)==1)
				return true;
		}
		*/
		if(dx>0&&dy>0){//右上方
			if(inMap.getMapData(nowx+1,nowy)==1&&inMap.getMapData(nowx, nowy+1)==1)
				return true;
		}
		if(dx>0&&dy<0){//右下方
			if(inMap.getMapData(nowx+1,nowy)==1&&inMap.getMapData(nowx, nowy-1)==1)
				return true;
		}
		if(dx<0&&dy>0){//左上方
			if(inMap.getMapData(nowx-1,nowy)==1&&inMap.getMapData(nowx, nowy+1)==1)
				return true;
		}
		if(dx<0&&dy<0){//左下方
			if(inMap.getMapData(nowx-1,nowy)==1&&inMap.getMapData(nowx, nowy-1)==1)
				return true;
		}
		
		//System.out.println("++++++");
		
		return false;
	}
	/**寻路，返回寻路结果栈
	 * @param inMap 准备计算寻路的地图
	 * @param start 起始点数据
	 * @param end 目标点数据
	 * */
	public static Stack<MapPoint> printPath(XYQMapActor inMap,MapPoint start, MapPoint end) {
		/*
			初步测试，从建邺最左上角寻路到最右下角，用时3.5-4s。应该考虑多线程运算
			特别当逻辑系统控制n个角色同时移动的时候
		 */
		ArrayList<MapPoint> openTable = new ArrayList<MapPoint>();
		 ArrayList<MapPoint> closeTable = new ArrayList<MapPoint>();
		 openTable .clear();
		 closeTable.clear();
		 Stack<MapPoint> pathStack = new Stack<MapPoint>();
		 start.parent = null;
		 MapPoint currentPoint = new MapPoint(start.x, start.y);
		 boolean flag = true;
		 int fx=start.x;int fy=start.y;
		 while(flag) {
			 for (int i = 0; i < dx.length; i++) {
				 fx = currentPoint.x + dx[i];
				 fy = currentPoint.y + dy[i];
				 MapPoint tempPoint = new MapPoint(fx,fy);
				 if ((inMap.getMapData(fx, fy) == 1)||cantMoveX(inMap,currentPoint.x,currentPoint.y,dx[i],dy[i])) {
					 continue;
				 } else {
					 if(end.equals(tempPoint)) {
						 flag = false;
						 end.parent = currentPoint;
						 //TODO 这里有一个bug，当简单移动一格的时候，会直接break
						 //TODO 需要优化算法，在长安这种大地图，跨界寻路的时候，由于路程长，地图大，查找耗时太多了
						 break;
					 }
					 if(i<4) {
						 tempPoint.G = currentPoint.G + 10;
					 }else {
						 tempPoint.G = currentPoint.G + 14;
					 }
					 tempPoint.H = MapPoint.getDis(tempPoint,end);
					 tempPoint.F = tempPoint.G + tempPoint.H;
					 if(openTable.contains(tempPoint)) {
						 int pos = openTable.indexOf(tempPoint );
						 MapPoint temp = openTable.get(pos);
						 if(temp.F > tempPoint.F) {
							 openTable.remove(pos);
							 openTable.add(tempPoint);
							 tempPoint.parent = currentPoint;
						 }
						 }else if(closeTable.contains(tempPoint)){
							 int pos = closeTable.indexOf(tempPoint );
							 MapPoint temp = closeTable.get(pos);
							 if(temp.F > tempPoint.F) {
								 closeTable.remove(pos);
								 openTable.add(tempPoint);
								 tempPoint.parent = currentPoint;
							 }
						}else {
							openTable.add(tempPoint);
							tempPoint.parent = currentPoint;
						}
				 }
			 }
			 if(openTable.isEmpty()) {
				 return null;
			 }
			 if(false==flag) {
				 break;
			 }
			 openTable.remove(currentPoint);
			 closeTable.add(currentPoint);
			 Collections.sort(openTable);
			 if(openTable.size()>0)
				 currentPoint = openTable.get(0);
		 }
		 MapPoint node = end;
		 while(node.parent!=null) {
			 pathStack.push(node);
			 node = node.parent;
		 }
		 return pathStack;
	 }
//感谢 张朋飞
	
}