package xyq.system.assets;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Frame {
	
	public Texture image;//gdx 的图像
	public int duration;//帧间隔

	public int x = -1;

	public int y = -1;

	public int centerX;
	public int centerY;

	public Rect rect;
	
	public Pixmap pixels;
	
	boolean loaded;//是否加载完                                                                    

	public Frame(int centerx, int centery, int w, int h, int duration) {
		this.duration = duration;
		this.centerX = centerx;
		this.centerY = centery;
		this.rect = new Rect(w, h);
		this.loaded = false;
		this.image = null;
	}

	public Frame(int duration, int x, int y) {
		this.duration = duration;
		this.x = x;
		this.y = y;
	}

	public void render(SpriteBatch batch,float x, float y) {
		if(image==null)
			return;
		//image.draw(x - centerX, y - centerY);
		batch.draw(image, x - centerX, y - centerY);
	}
	/*
	public void render(SpriteBatch batch,float x, float y,float scale,Color filter) {
		if(image==null)
			return;
		batch.draw(image, x - centerX, y - centerY);
		image.draw(x - centerX, y - centerY, scale , filter);
	}
	*/
	public void render(SpriteBatch batch,float x, float y, float scale) {
		if(image==null)
			return;
		batch.draw(image, x - centerX, y - centerY,scale*image.getWidth(),scale*image.getHeight());
	}
	/*
	public void render(float x, float y, Color filter) {
		if(image==null)
			return;
		image.draw(x - centerX, y - centerY, filter);
	}*/

	public void render(SpriteBatch batch,float x, float y, float width, float height) {
		if(image==null)
			return;
		batch.draw(image, x - centerX, y - centerY,width,height);
			
		//image.draw(x - centerX, y - centerY, width, height);
	}	
	public void renderCenter(SpriteBatch batch,float x, float y, float PrecenterX,float YOffset) {
		if(image==null)
			return;
		float rX=PrecenterX-centerX;
		float rY=getHeight()-centerY;
		float dX=x+rX;
		float dY=y-rY+YOffset;
		batch.draw(image,dX, dY);
		//System.out.println("dang qian zhen");
		//System.out.println(dX+","+dY);
		//image.draw(x - centerX, y - centerY, width, height);
	}
	public void renderCenter(SpriteBatch batch,float x, float y, float PrecenterX,float YOffset,float width,float height) {
		if(image==null)
			return;
		float rX=PrecenterX-centerX;
		float rY=getHeight()-centerY;
		float dX=x+rX;
		float dY=y-rY+YOffset;
		batch.draw(image,dX, dY,width,height);
		//System.out.println("dang qian zhen");
		//System.out.println(dX+","+dY);
		//image.draw(x - centerX, y - centerY, width, height);
	}
	public void renderCenterT(SpriteBatch batch,float x, float y, float PrecenterX,float XOffset) {
		if(image==null)
			return;
		float rX=PrecenterX-centerX-XOffset;
		float rY=getHeight()-centerY;
		float dX=x+rX;
		float dY=y-rY;
		batch.draw(image,dX, dY);
		//System.out.println("dang qian zhen");
		//System.out.println(dX+","+dY);
		//image.draw(x - centerX, y - centerY, width, height);
	}
	public boolean isLoaded(){
		return loaded;
	}

	public Rect getRect(int x, int y) {
		rect.setPosition(x - centerX, y - centerY);
		return rect;
	}

	public void release() {
		if (image != null){
			image.dispose();
			image = null;
			loaded = false;
		}
	}
	/*
	public boolean isContain(int mouseX, int mouseY, int posX, int posY) {
		if(image==null)
			return false;
		Rect rect = getRect(posX, posY);
		if (rect.inside(mouseX, mouseY)) {
			int dx = mouseX - ( posX - centerX);
			int dy = mouseY - ( posY - centerY);

			byte[] buffer = image.getTexture().getTextureData();
			int w = image.getTexture().getTextureWidth();
			if (buffer[(dy * w + dx) * 4 + 3] == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	 */
	public int getWidth() {
		return rect.w;
	}

	public int getHeight() {
		return rect.h;
	}
	
	protected final static int DEFAULT_DURATION = 100;
	
	public static Frame createFrame(WasData info,int direct,int i){
		int fx, fy, fw, fh;
		int duration = DEFAULT_DURATION;
		int offset = info.frameOffsets[direct * info.frame + i];// 帧像素偏移量
		if (offset == 0){
			return new Frame(0, 0, 0, 0, duration);
		}
		offset = offset + info.size + 4;
		int pos = offset;
		fx = ByteUtil.getInt(info.data, pos);// x轴坐标
		pos += 4;
		fy = ByteUtil.getInt(info.data, pos);// y轴坐标
		pos += 4;
		fw = ByteUtil.getInt(info.data, pos);// 帧宽度
		pos += 4;
		fh = ByteUtil.getInt(info.data, pos);// 帧高度
		pos += 4;
		// 创建ImageBuffer
		//ImageBuffer pixels = new ImageBuffer(fw, fh);
		Pixmap pixels = new Pixmap( fw, fh, Format.RGBA8888 );
		pixels.setBlending(Blending.None);
		// 行像素数据偏移
		int[] lineOffsets = new int[fh];// 行像素偏移量
		for (int l = 0; l < fh; l++) {
			lineOffsets[l] = ByteUtil.getInt(info.data, pos);
			pos += 4;
		}
		// 创建帧对象
		boolean bline = true;
		int tempx = 0;
		for (int y = 0; y < fh; y++) {
			int x = 0, count = 0, index = 0;
			short color;
			pos = offset + lineOffsets[y];
			while (x < fw) {
				int t = ByteUtil.getByte(info.data, pos);
				pos++;
				if (t == 0) {
					while (x < fw) {
						setPixel(pixels, x, y, 0);
						//pixels.drawPixel(x, y, 0);
						x++;
					}
					continue;
				}

				// int t = type & 0xc0;
				switch (t & 0xc0) {
				case 0x0:
					if (bline && (y == 1)) {
						bline = false;
					}
					if ((t & 0x20) > 0) {
						index = ByteUtil.getByte(info.data, pos);
						pos++;
						color = info.palette[index];
						int ic = color + ((t & 0x1F) << 16);
						setPixel(pixels, x, y, ic);
						//pixels.drawPixel(x, y, ic);
						x++;
					} else if (t != 0) {
						count = t & 0x1f;// count
						t = ByteUtil.getByte(info.data, pos);
						pos++;// alpha
						index = ByteUtil.getByte(info.data, pos);
						pos++;
						color = info.palette[index];
						int ic = color + ((t & 0x1F) << 16);
						tempx = x;
						x += count;
						for (int p = 0; p < count; p++) {
							setPixel(pixels, tempx, y, ic);
							//pixels.drawPixel(tempx, y, ic);
							tempx++;
						}
					}
					break;
				case 0x40:// 01：表示象素组，剩下的6比特(0~63)为数据段的长度。
					if (bline && (y == 1)) {
						bline = false;
					}
					count = t & 0x3f;
					tempx = x;
					x += count;
					int alpha = (0x1F << 16);
					for (int p = 0; p < count; p++) {
						index = ByteUtil.getByte(info.data, pos);
						pos++;
						color = info.palette[index];
						int ic = color + alpha;
						setPixel(pixels, tempx, y, ic);
						//pixels.drawPixel(tempx, y, ic);
						tempx++;
					}
					break;
				case 0x80:// 10：表示重复象素 n 次，n 为剩下的6比特(0~63)表示
					if (bline && (y == 1)) {
						bline = false;
					}
					count = t & 0x3f;
					index = ByteUtil.getByte(info.data, pos);
					pos++;
					color = info.palette[index];
					int ic = color + (0x1F << 16);
					tempx = x;
					x += count;
					for (int p = 0; p < count; p++) {
						try{
							setPixel(pixels, tempx, y, ic);
							//pixels.drawPixel(tempx, y, ic);
						}catch(Exception e){
							e.printStackTrace();
						}
						tempx++;
					}
					break;
				case 0xc0:// 11：表示跳过象素 n 个，n 为剩下的6比特(0~63)表示
					count = t & 0x3f;
					int z = 0;
					while (x < fw && z < count) {
						setPixel(pixels, x, y, 0);
						//pixels.drawPixel(x, y, 0);
						x++;
						z++;
					}
					break;
				default:
					break;
				}
			}
		}
		
		if (bline) {
			System.out.println("[ DEV ]:[ Frame ] -> 有重复行Frames绘制，待完善");
			/*
			//byte[] tempdata = pixels.getRGBA();
			//byte[] tempdata = pixels.getPixels().array();
			byte[] tempdata = new byte[pixels.getPixels().remaining()];
			//System.out.println("tempdata length is"+tempdata.length);
			int bmpstart = 0;
			int bmppitch = 4 * pixels.getWidth();
			for (int y = 0; y < fh; y += 2) {
				//System.arraycopy(源,          源起始位置, 目标,             目标起始位置,         长度);
				//加载addon的动画效果，有时，会因为有空行，便如此代码块
				System.arraycopy(tempdata, bmpstart, tempdata, bmpstart+ bmppitch, bmppitch);
				bmpstart += bmppitch << 1;
			}
			*/
		}
		Frame frame = new Frame(fx, fy, fw, fh, duration);
		//frame.image = pixels.getImage(Image.FILTER_LINEAR);
		frame.image = new Texture(pixels, Format.RGBA8888, true);
		return frame;
	}

	//创建帧
	public static Frame[] createFrame(WasData info,int direct){
		Frame[] frames = new Frame[info.frame];
		
		for(int i=0;i<info.frame;i++){
			frames[i] = createFrame(info, direct, i);
		}
		return frames;
	}
	
	private static void setPixel(Pixmap pixels, int x, int y, int color) {
		// red
		int r = ((color >>> 11) & 0x1F) << 3;
		// green
		int g = ((color >>> 5) & 0x3f) << 2;
		// blue
		int b = (color & 0x1F) << 3;
		// alpha
		int a = ((color >>> 16) & 0x1f) << 3;
		//System.out.println("old RGB is:"+r+","+g+","+b+","+a);
		float R=r/255f;
		float G=g/255f;
		float B=b/255f;
		if(a>230)a=255;//这句是过滤一点点透明的图像，让它不透明
		float A=a/255f;
		pixels.setColor(R,G,B,A);

		//System.out.println("RGB is:"+R+","+G+","+B+","+A);
		pixels.drawPixel(x, y);
		//pixels.drawPixel(x, y);
		//image.setRGBA(x, y, r, g, b, a);
	}
	 
}
