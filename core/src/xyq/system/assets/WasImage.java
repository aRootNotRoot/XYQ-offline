package xyq.system.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WasImage implements Sprite {
	
	
	//protected static final int duration = 100;

	// 方向
	protected int directCount;
	public int currentDirect;
	// 帧
	//protected Frame[] frames;
	protected FrameDatas frames;
	protected String path;
	protected int current = 0; // 当前帧
	protected long nextChange = 0; // 更新帧时间点
	protected float speed = 1.0f; // 动画速度
	protected boolean firstUpdate = true;
	protected boolean stopped = false;
	protected int stopAt = -2;
	protected int direction = 1; // 播放的方向，默认正向播放
	protected boolean loop = true; // 循环播放
	protected boolean pingPong = false;
	
	
	protected int cx;
	protected int cy;
	
	protected int w;
	protected int h;
	public WasImage(){
		
	}

	public WasImage(ResSystem RS,String pack,String was,PPCData pp) {
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
		//this.frames = Frame.createFrame(wasData, direct);
		/*
		System.out.println("Anim was文件的centerXY是"+cx+","+cy);
		System.out.println("Anim was文件的WidthHeight是"+w+","+h);
		for(int i=0;i<frames.length;i++){
			System.out.println(i+"号帧的centerXY是"+frames[i].centerX+","+frames[i].centerY);
			System.out.println(i+"号帧的WH是"+frames[i].getWidth()+","+frames[i].getHeight());
		}
		*/
	}
	public Texture getCurrentTexture(){
		return frames.getFrames(currentDirect)[current].image;
	}
	public void update(int delta) {
		if (firstUpdate) {
			delta = 0;
			firstUpdate = false;
		}
		next(delta);
	}

	private void next(long delta) {
		if (stopped) {
			return;
		}

		nextChange -= delta;

		while (nextChange < 0 && (!stopped)) {
			if (current == stopAt) {
				stopped = true;
				break;
			}
			if ((current == frames.getFrames(currentDirect).length - 1) && (!loop) && (!pingPong)) {
				stopped = true;
				break;
			}
			current = (current + direction) % frames.getFrames(currentDirect).length;
			if (pingPong) {
				if (current <= 0) {
					current = 0;
					direction = 1;
					if (!loop) {
						stopped = true;
						break;
					}
				} else if (current >= frames.getFrames(currentDirect).length - 1) {
					current = frames.getFrames(currentDirect).length - 1;
					direction = -1;
				}
			}
			int realDuration = (int) (getCurrFrame().duration / speed);
			nextChange = nextChange + realDuration;
		}
	}

	public void render(SpriteBatch batch, float x, float y) {
		frames.getFrames(currentDirect)[current].renderCenter(batch,x,y,cx,h-cy);
		//frames[current].render(batch,x,y);
	}
	public void renderD(SpriteBatch batch, float x, float y,float width, float height) {
		frames.getFrames(currentDirect)[current].renderCenter(batch,x,y,cx,h-cy,width,height);
		//frames[current].render(batch,x,y);
	}
	public void renderCenter(SpriteBatch batch, float x, float y) {
		frames.getFrames(currentDirect)[current].renderCenterT(batch,x,y,cx,w-cx);
		//frames[current].render(batch,x,y);
	}
	/*
	public void render(SpriteBatch batch, float x, float y,Color color) {
		frames[current].render((int) x, (int) y, color);
	}
	 */
	public void render(SpriteBatch batch, float dx, float dy, float width, float height) {
		frames.getFrames(currentDirect)[current].render(batch,dx,dy, width, height);
	}

	public void setDirect(int direct) {
		this.currentDirect = direct;
		restart();
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public boolean isStop() {
		return stopped;
	}
	
	public void restart() {
		if (frames.getFrames(currentDirect).length == 0) {
			return;
		}
		stopped = false;
		current = 0;
		nextChange = (int) ((frames.getFrames(currentDirect)[0]).duration / speed);
		firstUpdate = true;
	}

	public void setPingPong(boolean pingpong) {
		this.pingPong = pingpong;
	}

	public void stopAt(int frameIndex) {
		this.stopAt = frameIndex;
	}

	public void start() {
		if (!stopped) {
			return;
		}
		stopped = false;
		nextChange = 0;
	}

	public int getSpriteDuration() {
		return frames.getFrames(currentDirect).length * 100;
	}

	public void setSpeed(float spd) {
		if (spd > 0) {
			nextChange = (long) (nextChange * speed / spd);
			speed = spd;
		}
	}

	public Frame getCurrFrame() {
		return frames.getFrames(currentDirect)[current];
	}

	public float getSpeed() {
		return speed;
	}

	public Frame getFrame(int direct, int current) {
		return frames.getFrames(currentDirect)[current];
	}

	public void render(SpriteBatch batch, int direct, int current, float x, float y) {
		this.current = current;
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
		return current;
	}

	public int getCurrDirTextureFrameCount() {
		return frames.frames[currentDirect].length;
	}


	
}
