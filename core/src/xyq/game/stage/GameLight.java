package xyq.game.stage;

import com.badlogic.gdx.graphics.Color;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import xyq.game.XYQGame;
import xyq.system.utils.RandomUT;

public class GameLight extends PointLight{
	int mapID;
	float posX;
	float posY;
	boolean blink;
	float dist;
	int rays;
	String tag="";
	/**创建一个点光源，默认灰白光光源
	 * @param rayHandler 地图的射线处理器
	 * @param blocks 光源大小，是多少个坐标格子
	 * @param x 光源在地图里的哪个位置，像素，地图左下角为基准
	 * @param y 光源在地图里的哪个位置，像素，地图左下角为基准
	 * */
	public GameLight(RayHandler rayHandler, float blocks,float x,float y) {
		super(rayHandler, 50);
		this.setColor(Color.GRAY);
		this.setDistance(blocks/XYQGame.PPM);
		this.posX=x;
		this.posY=y;
		setSoft(true);
		setSoftnessLength(100);
		dist=blocks/XYQGame.PPM;
		rays=50;
	}
	
	public void update(XYQMapActor map){
		float inScreenX=posX+map.getX();
		float inScreenY=posY+map.getY();

		//System.err.println("maps的xy为："+map.getX()+","+map.getY());
		//System.err.println("点光源的xy为："+inScreenX+","+inScreenY);
		
		float halfWidth=(float)XYQGame.V_WIDTH/2f;
		float halfHeight=(float)XYQGame.V_HEIGHT/2f;
		
		
		float x=0;
		if(inScreenX==halfWidth);
		else if(inScreenX>halfWidth)
			x=(inScreenX-halfWidth)/halfWidth;
		else
			x=-1f*(halfWidth-inScreenX)/halfWidth;
		
		float y=0;
		if(inScreenY==halfHeight);
		else if(inScreenY>halfHeight)
			y=(inScreenY-halfHeight)/halfHeight;
		else
			y=-1f*(halfHeight-inScreenY)/halfHeight;
		setPosition(x, y);
		if(blink){
			float s=RandomUT.getRandomF(-0.0025f, 0.0025f);
			s=s/XYQGame.PPM;
			setDistance(s+dist);
			
		}
	}

	/**设定是否开启闪烁*/
	public void setBlink(boolean blink) {
		this.blink=blink;
		
	}
	public boolean isBlink() {
		return blink;
	}
	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public float getDist() {
		return dist;
	}

	public void setDist(float dist) {
		this.dist = dist;
	}

	public int getRays() {
		return rays;
	}

	public void setRays(int rays) {
		this.rays = rays;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	

}
