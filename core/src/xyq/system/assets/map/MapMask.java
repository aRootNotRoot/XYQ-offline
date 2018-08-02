package xyq.system.assets.map;


/**
 * 地图遮挡数据
 * @author David
 *
 */
public class MapMask {
	
	public int l;
	public int t;
	public int w;
	public int h;
	
	public byte[] data;
	
	public byte[] image;
	
	
	public MapMask(int l, int t, int w, int h, byte[] data) {
		this.l = l;
		this.t = t;
		this.w = w;
		this.h = h;
		this.data = data;
	}

}
