package xyq.system.assets.map;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * 地图块数据
 * 
 * @author user
 * 
 */
public class MapBlock implements Resource{
	
	public final static short MAPBLOCK_W = 320;	//地图块宽
	public final static short MAPBLOCK_H = 240;	//地图块高

	public static final int STATE_INIT = 0;
	public static final int STATE_LOAD = 1;
	private int xBlock; // x轴block块序号
	private int yBlock; // y轴block块序号

	private int sequence;

	private ByteBuffer data; // 地图块图片数据
	private Texture data_texture;
	private int texId = -1; // 纹理序号

	private int l;
	private int t;

	private MapFile handle;
	
	public volatile int state = STATE_INIT;
	public volatile boolean loaded;
	
	public MapBlock(int bx, int by,MapFile handle) {
		this.xBlock = bx;
		this.yBlock = by;
		this.l = bx * 320;
		this.t = by * 240;

		this.handle = handle;
		state = STATE_INIT;
		loaded = false;
	}

	public MapBlock(int bx, int by, ByteBuffer data) {
		this.xBlock = bx;
		this.yBlock = by;
		this.l = bx * 320;
		this.t = by * 240;
		this.data = data;
		byte[] byte_array=data.array();
		this.data_texture=new Texture(new Pixmap(byte_array,0,byte_array.length));
		state = STATE_INIT;
		loaded = false;
	}


	public MapBlock(int bx, int by) {
		this.xBlock = bx;
		this.yBlock = by;
		this.l = bx * 320;
		this.t = by * 240;
		state = STATE_INIT;
		loaded = false;
	}

	public void load() {
		loadImageTexture();
		data = null;
		loaded = true;
		l=l+0;
		t=t+0;
	}

	// 加载纹理
	public void loadImageTexture() {
		// 加载地图纹理
		data.flip();
		texId = Gdx.gl.glGenTexture(); // Generate texture ID
		Gdx.gl.glBindTexture(GL20.GL_TEXTURE_2D, texId); // Bind texture ID
		Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER,
				GL20.GL_LINEAR);
		Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER,
				GL20.GL_LINEAR);
		Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_RGBA, 320, 240, 0,
				GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, data);
	}

	public void draw(SpriteBatch batch,int offsetX, int offsetY) {
		if (state == STATE_LOAD&&!loaded) {
			load();
		}
		if (loaded) {
			// 数据已经加载完毕
			batch.draw(data_texture, offsetX, offsetY);
			/*
			Gdx.gl.glBindTexture(GL20.GL_TEXTURE_2D, texId);
			Gdx.gl.glBegin(GLCommon.gl_QUADS);
				Gdx.gl.glTexCoord2f(0, 0);
				Gdx.gl.glVertex3f(l - offsetX, t - offsetY, 0);
				Gdx.gl.glTexCoord2f(1, 0);
				Gdx.gl.glVertex3f(l + 320 - offsetX, t - offsetY, 0);
				Gdx.gl.glTexCoord2f(1, 1);
				Gdx.gl.glVertex3f(l + 320 - offsetX, t + 240 - offsetY, 0);
				Gdx.gl.glTexCoord2f(0, 1);
				Gdx.gl.glVertex3f(l - offsetX, t + 240 - offsetY, 0);
			Gdx.gl.glEnd();
			*/
		}
	}

	public void release() {
		if(loaded){
			//Gdx.gl.glDeleteTextures(texId);
			data_texture.dispose();
			state = STATE_INIT;
			loaded = false;
		}
	}

	public void setData(ByteBuffer data) {
		this.data = data;
		byte[] byte_array=data.array();
		if(this.data_texture!=null)
			this.data_texture.dispose();
		this.data_texture=new Texture(new Pixmap(byte_array,0,byte_array.length));
		
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getXBlock() {
		return xBlock;
	}

	public int getYBlock() {
		return yBlock;
	}

	public  void loadMapBlockData() {
		data = BufferUtils.newByteBuffer(MAPBLOCK_W * MAPBLOCK_H * 4);
		handle.loadMapBlock(xBlock, yBlock, data);
		state = STATE_LOAD;
	}

	public void asynLoading() {
		loadMapBlockData();
		//System.out.println ("加载地图:("+xBlock+","+yBlock+")");
	}

	public void asynRelease() {
	}

}