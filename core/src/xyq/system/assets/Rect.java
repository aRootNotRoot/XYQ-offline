package xyq.system.assets;

public class Rect {
	
	/**左边位置*/
	public int l;
	/**顶部位置*/
	public int t;
	/**宽度*/
	public int w;
	/**高度*/
	public int h;
	
	public Rect(int l,int t,int w,int h){
		this.l = l;
		this.t = t;
		this.w = w;
		this.h = h;
	}
	
	
	public void setW(int w){
		this.w = w;
	}
	
	public void setH(int h ){
		this.h = h;
	}
	
	public Rect(int w,int h){
		this(0,0,w,h);
	}
	

	/**根据偏移坐标，修改矩形位置*/
	public void offset(float offsetX,float offsetY){
		this.l +=offsetX;
		this.t +=offsetY;
	}
	
	public void setPosition(int x,int y){
		this.l = x;
		this.t = y;
	}
	
	
	public boolean isMixed(int al,int at,int ar,int ab){
		int r = l+w-1;
		int b = t+h-1;
		if(this.l>ar||r<al||this.t>ab||b<at){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isMixed(Rect rect){
		int r = l+w-1;
		int b = t+h-1;
		int rr = rect.l+rect.w-1;
		int rb = rect.t+rect.h-1;
		if(this.l>rr||r<rect.l||this.t>rb||b<rect.t){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean inside(float x,float y){
		int r = l+w-1;
		int b = t+h-1;
		if(x<=r&&x>=l&&y<=b&&y>=t){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean over(int x,int y){
		int r = l+w-1;
		int b = t+h-1;
		if(x<=r&&x>=l&&y<=b&&y>=t){
			return true;
		}else{
			return false;
		}
	}

	
}
