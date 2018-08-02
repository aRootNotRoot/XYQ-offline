package xyq.system.assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UnitSprite implements Sprite{
	
	
	String path;
	int direct;
	Frame frame;
	protected int cx;
	protected int cy;
	
	protected int w;
	protected int h;
	public UnitSprite(){
		
	}
	
	public UnitSprite(WasData desc,int direct){
		this.path = desc.path;
		this.cx = desc.x;
		this.cy = desc.y;
		this.w = desc.w;
		this.h = desc.h;
		this.direct = 0;
		this.frame = Frame.createFrame(desc, direct)[0];
	}

	@Override
	public void update(int delta) {
		
	}

	@Override
	public void render(SpriteBatch batch, float x, float y) {
		frame.render(batch,x, y);
	}

	@Override
	public int getDirect() {
		return direct;
	}

	@Override
	public void render(SpriteBatch batch, int direct, int current, float x, float y) {
		frame.render(batch,x, y);
	}

	@Override
	public Frame getCurrFrame() {
		return frame;
	}

	
	public Sprite copy() {
		UnitSprite sprite = new UnitSprite();
		sprite.path = path;
		sprite.frame = frame;
		sprite.direct = direct;
		return sprite;
	}

	@Override
	public void release() {
		frame.release();
	}

	@Override
	public void render(SpriteBatch batch, float dx, float dy, float width, float height) {
		frame.render(batch,dx,dy, width, height);
	}

	public int getCx() {
		return cx;
	}

	public int getCy() {
		return cy;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	

}
