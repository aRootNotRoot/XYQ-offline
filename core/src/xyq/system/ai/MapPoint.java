package xyq.system.ai;

/**地图点逻辑数据，弃用*/
public class MapPoint implements Comparable<MapPoint>{
	public int x;
	public int y;
	MapPoint parent;
	int F, G, H;
	public MapPoint(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.F = 0;
		this.G = 0;
		this.H = 0;
	}
	@Override
	public boolean equals(Object obj) {
		MapPoint point = (MapPoint) obj;
		if (point.x == this.x && point.y == this.y)
			return true;
		return false;
	}
	public static int getDis(MapPoint p1, MapPoint p2) {
		int dis = Math.abs(p1.x - p2.x) * 10 + Math.abs(p1.y - p2.y) * 10;
		return dis;
	}
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
	@Override
	public int compareTo(MapPoint arg0) {
		return 0;
	}
}
