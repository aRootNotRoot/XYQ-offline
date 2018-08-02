package xyq.system.map;

import com.badlogic.gdx.graphics.Texture;

public final class MaskUnit {

	private float x;
	private float y;
	private int width;
	private int height;
	public int[] data;
	public float[][][] maskImagePixels;
	MapData fother;
	public int inFotherIndex;
	Texture maskTexture;
	
	public MaskUnit(MapData fother,int index,float x, float y, int width, int height, int[] data) {
		super();
		this.fother=fother;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.data = data;
		this.inFotherIndex=index;
		
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getData() {
		return data;
	}
	public float[][][] getMaskImagePixels(){
		if(maskImagePixels==null)
			maskImagePixels=fother.toFloatColor(fother.getTileMaskPexels((int)getX(),(int)getY(),getWidth(),getHeight(),getData()),getWidth(),getHeight());
		return maskImagePixels;
		
	}
	public Texture getTexture(){
		return fother.getMaskTexture(this);
	}
	

}
