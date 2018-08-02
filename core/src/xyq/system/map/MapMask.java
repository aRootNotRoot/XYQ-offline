package xyq.system.map;

/**地图遮罩块类*/
public final class MapMask {
	/**地图遮罩块X坐标*/
	public float startX;
	/**地图遮罩块Y坐标*/
	public float startY;
	/**地图遮罩块宽度*/
	public int width;
	/**地图遮罩块高度*/
	public int height;
	/**地图遮罩块数据大小*/
	public int mask_size;// mask数据大小 
	/**地图遮罩图像数据*/
	public byte[] maskData;
	
	/**
	 * 构造一个地图遮罩对象
	 * @param x 遮罩开始X坐标
	 * @param y 遮罩开始y坐标
	 * @param w 遮罩块宽度
	 * @param h 遮罩块高度
	 * @param size 数据大小
	 * */
	public MapMask(float x,float y,int w,int h,int size){
		this.startX=x;
		this.startY=y;
		this.width=w;
		this.height=h;
		this.mask_size=size;
	}
	public MapMask(){}
	
	
	/**解密一个遮罩层数据
	 * @param output 输出的数组*/
	public byte[] DecompressMask() {
		byte[] out=null;
		/*
		byte[] in=this.maskData;
		
		int t=0;;
		int point=0;
		int point_out=0;
		//DEADEND ......C++完全看不懂啊
		 * */
		return out;
	}
}
