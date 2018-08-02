package xyq.system.assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Sprite extends Resource{
	
	//更新
	public void update(int delta);
	//渲染
	public void render(SpriteBatch batch,float x,float y);
	
	//public void render(SpriteBatch batch,float x,float y,Color color);

	public void render(SpriteBatch batch, int direct,int current,float x,float y);
	
	public void render(SpriteBatch batch, float dx, float dy, float width, float height);
	//获取方向
	public int getDirect();
	
	public Frame getCurrFrame();
	
	public int getW();

	public int getH();
	
	public int getCx();

	public int getCy();

	
}
