package xyq.system.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WasIcon implements Sprite {
	
	
	//protected static final int duration = 100;

	// 方向
	protected int directCount;
	public int currentDirect;
	// 帧
	//protected Frame[] frames;
	protected FrameDatas frames;
	protected String path;
	
	
	protected int cx;
	protected int cy;
	
	protected int w;
	protected int h;
	public WasIcon(){
		
	}

	public WasIcon(ResSystem RS,String pack,String was,PPCData pp) {
		WasData wasData=new WasData(RS.reader.readByName(pack,was));
		if(pp!=null)
			wasData.coloration(pp);
		this.path = wasData.path;
		this.directCount = wasData.direct;
		this.currentDirect = 0;

		this.cx = wasData.x;
		this.cy = wasData.y;
		this.w = wasData.w;
		this.h = wasData.h;
		this.frames = RS.getFrames(pack, was,pp);
		
	}
	public Texture getCurrentTexture(){
		return frames.getFrames(currentDirect)[0].image;
	}
	public void update(int delta) {
	
	}

	public void render(SpriteBatch batch, float x, float y) {
		//frames.getFrames(currentDirect)[0].renderCenter(batch,x,y,cx,h-cy);
		frames.getFrames(currentDirect)[0].render(batch,x,y,w,h);
		//frames[current].render(batch,x,y);
	}
	public void renderD(SpriteBatch batch, float x, float y,float width, float height) {
		frames.getFrames(currentDirect)[0].renderCenter(batch,x,y,cx,h-cy,width,height);
		//frames[current].render(batch,x,y);
	}
	public void renderCenter(SpriteBatch batch, float x, float y) {
		frames.getFrames(currentDirect)[0].renderCenterT(batch,x,y,cx,w-cx);
		//frames[current].render(batch,x,y);
	}
	/*
	public void render(SpriteBatch batch, float x, float y,Color color) {
		frames[current].render((int) x, (int) y, color);
	}
	 */
	public void render(SpriteBatch batch, float dx, float dy, float width, float height) {
		frames.getFrames(currentDirect)[0].render(batch,dx,dy, width, height);
	}

	public int getSpriteDuration() {
		return frames.getFrames(currentDirect).length * 100;
	}


	public Frame getCurrFrame() {
		return frames.getFrames(currentDirect)[0];
	}

	public Frame getFrame(int direct, int current) {
		return frames.getFrames(currentDirect)[current];
	}

	public void render(SpriteBatch batch, int direct, int current, float x, float y) {
		render(batch, x, y);
	}


	@Override
	public int getDirect() {
		return directCount;
	}

	@Override
	public void release() {
		for(int i=0;i<directCount;i++)
		for(Frame frame:frames.getFrames(i)){
			if(frame!=null){
				frame.release();
			}
		}
		System.out.println("释放Sprite资源:[{}]"+path);
	}

	/**获取中心点X*/
	public int getCx() {
		return cx;
	}
	/**获取中心点Y*/
	public int getCy() {
		return cy;
	}
	/**获取宽度*/
	public int getW() {
		return w;
	}
	/**获取高度*/
	public int getH() {
		return h;
	}

	public int getCurrentIndex() {
		return 0;
	}

	public int getCurrDirTextureFrameCount() {
		return frames.frames[currentDirect].length;
	}


	
}
