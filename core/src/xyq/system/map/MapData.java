package xyq.system.map;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.io.PrintStream;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

import xyq.game.XYQGame;








/**
 * 游戏地图对象类，含读取解析
 * */
public final class MapData
{
	/**地图宽度*/
	private int width;
	/**地图高度*/
	private int height;
	/**每个地图块的文件位置[行][列]*/
	private int[][] segmentsOffset;
	/**jpeg图像数据[行][列].每块地图像素为320*240*/
	private Object[][] jpegDatas;
	/**地图文件名*/
	private String filename;
	/**随机读取方法对象*/
	private MyRandomAccessFile mapFile;
	/**水平地图块数量*/
	private int horSegmentCount;
  	/**竖直地图块数量*/
	private int verSegmentCount;
	/**地图的材质数组【水平】【竖直】*/
	 Texture[][] mapTextures;
	 /**地图遮罩层大图像*/
	public Texture bigMapShadowTexture;
	/**文件头大小*/
	private int headerSize;
	/**遮罩块数据总数*/
	private int maskCount;
	/**遮罩块数据位置偏移表[第几块]*/
	private int[] masksOffset;
	/**地图的遮罩块对象数组*/
	public MaskUnit[] maskUnits;
	/**暂存地图遮罩数据的*/
	private BufferedImage[] maskBufferImages;
	//子地图Cell参数
    public byte [][] cellData;
    
	private int o_pos,m_pos,i_pos,t;
	private ColorModel cm;
    private Object data;
    private int[] des;
	public MapData(String filename) throws Exception{
		this(new File(filename));
	}

	public MapData(File file) throws Exception {
		this.filename = file.getName();
		this.mapFile = new MyRandomAccessFile(file, "r");
		loadHeader();//读取文件头
		int LocX=(int)Math.ceil(width/20);
		int LocY=(int)Math.ceil(height/20);
	    cellData = new byte[LocX+16][LocY+12];
	    for(int x=0;x<cellData.length;x++)
			for(int y=0;y<cellData[0].length;y++){cellData[x][y]=1;}
	  //读取子地图
	    
        for(int y=0;y<verSegmentCount;y++){
          for(int x=0;x<horSegmentCount;x++){
        	MapUnit mapUnit = ReadUnit(x,y);
        	
        	//子地图合成cell
        	byte[] tempcellData = mapUnit.getCellData();
        	
        	//System.out.println(tempcellData.length);
        	
        	for(int ch=0;ch<12;ch++){
               for(int cw=0;cw<16;cw++){
				this.cellData[x*16+cw][y*12+ch]=tempcellData[ch*16+cw];
				
                }
        	}
          }
        }
	}
	/**读取文件头信息*/
	private void loadHeader()
	{
		if (!isValidMapFile()) throw new IllegalArgumentException("非梦幻地图格式文件!");
		try
		{
			this.width = this.mapFile.readInt2();//读取四个字节.表示宽度
			this.height = this.mapFile.readInt2();//读取高度
			this.horSegmentCount = (int)Math.ceil(this.width / 320.0D); // 4*n字节 地图单元的引索 n=地图的宽度/640*2 * 地图高度/480*2
			this.verSegmentCount = (int)Math.ceil(this.height / 240.0D);// 4*n字节 地图单元的引索 n=地图的宽度/640*2 * 地图高度/480*2
			//或者说,横着有几块,竖着有几块
			this.segmentsOffset = new int[this.horSegmentCount][this.verSegmentCount];
			this.jpegDatas = new Object[this.horSegmentCount][this.verSegmentCount];
			this.mapTextures=new Texture[this.horSegmentCount][this.verSegmentCount];
			for (int v = 0; v < this.verSegmentCount; v++) {
				for (int h = 0; h < this.horSegmentCount; h++)
					this.segmentsOffset[h][v] = this.mapFile.readInt2();//读取地图偏移量数据
			}
			
			//接着读取4字节 文件头大小，包括这4字节，及以上的字节。
			this.headerSize=this.mapFile.readInt2();
			this.maskCount=this.mapFile.readInt2();//读取遮罩块总数(此处待验证)
			this.masksOffset=new int[this.maskCount];//声明保存mask数据的偏移地址数组
			//int tmpdata = 0;
	        for(int i=0;i<maskCount;i++){
	        	this.masksOffset[i]=mapFile.readInt2();

	        } 
	        maskUnits = new MaskUnit[this.maskCount];
	        
	        //TODO
	        //loadAllMaskBigShadow();
	        
	        maskBufferImages=new BufferedImage[this.maskCount];
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("地图解码失败:" + e.getMessage());
		}
	}
	 public int tileswToPixels(int numTiles) {
	        int pixelSize = numTiles * 320 ;
	        return pixelSize;
	    }

	    public int pixelsToTilesw(int pixelCoord) {    	
	        int numTiles = pixelCoord / 320 ;
	        	return numTiles;
	        
	    }
	    
	    public int tileshToPixels(int numTiles){
	    	int pixelSize = numTiles * 240 ;
	        return pixelSize;
	    }
	    
	    public int pixelsToTilesh(int pixelCoord) {
	    	int numTiles = pixelCoord / 240 ;
	    	
	    		return numTiles;
	    	
	        
	    }
	public void createTileMaskImg(int unitnum,int keyx,int keyy,int width,int height,int []mask){
		if(maskBufferImages[unitnum] != null) {
			return;
		}
//		tileImageDes[unitnum] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    	
    	int firstTilex = pixelsToTilesw(keyx);
    	int lastTilex = pixelsToTilesw(keyx + width - 1);
        int firstTiley = pixelsToTilesh(keyy);
        int lastTiley = pixelsToTilesh(keyy + height - 1);
       // lastTileX=Math.min(lastTileX, (int)(rMap.m_SubMapRowNum-1));
       // lastTileY=Math.min(lastTileY, (int)rMap.m_SubMapColNum);
        //System.out.println("fistX,Y is:"+firstTilex+","+firstTiley);
        //System.out.println("lastX,Y is:"+lastTilex+","+lastTiley);
        
      //合并子地图并输出像素值；
        BufferedImage tempMaskImage = new BufferedImage((lastTilex+1-firstTilex)*320,
        	    (lastTiley+1-firstTiley)*240,BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster tempMaskRaster = tempMaskImage.getRaster();
        for (int y = firstTiley; y < lastTiley+1; y++) {
         	
            for (int x = firstTilex; x < lastTilex+1; x++) {
            	
            	try {
            		//读取IMAGE数据
            		InputStream buffin = new ByteArrayInputStream(getJpegData(x, y));
            		BufferedImage Image= ImageIO.read(buffin);
            		Raster raster = Image.getRaster();
            		
            		//合并raster像素矩阵
                	//System.out.println("x,y is:"+320*(x-firstTilex)+","+240*(y-firstTiley));
                	//System.out.println("num is:"+(y*xBlockCount+x));
                	tempMaskRaster.setRect(320*(x-firstTilex), 240*(y-firstTiley), 
                			raster);
                	
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
            
            }
        }
        writerRaster(tempMaskImage,tempMaskRaster,unitnum,keyx,keyy,width,height,mask);
        
    }
	public static InputStream bufferedImageToInputStream(BufferedImage tmpImage){
		  
		    ImageOutputStream imOut;
		    InputStream is = null;
		    ByteArrayOutputStream bs=new ByteArrayOutputStream();
	        tmpImage.flush();
	        try{
	        	imOut=ImageIO.createImageOutputStream(bs);
	        	ImageIO.write(tmpImage, "png", imOut);
	        	is=new ByteArrayInputStream(bs.toByteArray());
	        }catch(IOException e){}
	        
	        return is;
	  }
	
	/**
	 * 获取某个地图遮罩的
	 * */
	public Texture getMaskTexture(MaskUnit mu){
		if(mu.maskTexture!=null)
			return mu.maskTexture;
		//这里舍弃了整数材质，因为GDX可以处理非128那啥的分辨率材质
		//下面的代码是旧Lwjgl项目里用的
		/*
		int biWidth=1024;int biHeight=1024;
		int[] texturePixel={4,4,8,16,32,64,128,256,512,1024,2048,4096};
		//这么做是为了让材质不出现奇怪的线。出现奇怪的线是因为材质的大小不是正好的128整数呗
		for(int tw=0;tw<texturePixel.length;tw++)
			if(mu.getWidth()>texturePixel[tw])
				continue;
			else
				{biWidth=texturePixel[tw];break;}
		for(int th=0;th<texturePixel.length;th++)
			if(mu.getHeight()>texturePixel[th])
				continue;
			else
				{biHeight=texturePixel[th];break;}
		//BufferedImage tempMaskImage = new BufferedImage(biWidth,biHeight, BufferedImage.TYPE_4BYTE_ABGR);
		//WritableRaster raster=tempMaskImage.getRaster();
		 */
		int biWidth=mu.getWidth();int biHeight=mu.getHeight();
		Pixmap masPM=new Pixmap(biWidth,biHeight,Format.RGBA8888);
		int[][][] pixels=getTileMaskPexels((int)mu.getX(),(int)mu.getY(),mu.getWidth(),mu.getHeight(), mu.getData());
		for(int w=0;w<biWidth;w++)
			for(int h=0;h<biHeight;h++)
				if(w>=mu.getWidth()||h>=mu.getHeight()){
					masPM.setColor(0, 0,0,0);
					masPM.drawPixel(w,h);
				}
				else{
					/*
					System.err.println("---");
					for(int i=0;i<4;i++)
						System.err.println(pixels[w][h][i]);
						*/
					if(XYQGame.maskOpcaity)
						masPM.setColor(pixels[w][h][0]/255f, pixels[w][h][1]/255f, pixels[w][h][2]/255f, pixels[w][h][3]/255f/1.5f);
					else{
						if(pixels[w][h][3]==0)
							masPM.setColor(0, 0, 0,0);
						else
							masPM.setColor(pixels[w][h][0]/255f, pixels[w][h][1]/255f, pixels[w][h][2]/255f, 1);
					}
					masPM.drawPixel(w,h);
				}
        mu.maskTexture=new Texture(masPM);
        masPM.dispose();
		return mu.maskTexture;
    }
	
	public int[][][] getTileMaskPexels(int keyx,int keyy,int width,int height,int []mask){
    	
    	int firstTilex = pixelsToTilesw(keyx);
    	int lastTilex = pixelsToTilesw(keyx + width - 1);
        int firstTiley = pixelsToTilesh(keyy);
        int lastTiley = pixelsToTilesh(keyy + height - 1);
        
      //合并子地图并输出像素值；
        BufferedImage tempMaskImage = new BufferedImage((lastTilex+1-firstTilex)*320,
        	    (lastTiley+1-firstTiley)*240,BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster tempMaskRaster = tempMaskImage.getRaster();
        for (int y = firstTiley; y < lastTiley+1; y++) {
         	
            for (int x = firstTilex; x < lastTilex+1; x++) {
            	
            	try {
            		//读取IMAGE数据
            		InputStream buffin = new ByteArrayInputStream(getJpegData(x, y));
            		BufferedImage Image= ImageIO.read(buffin);
            		Raster raster = Image.getRaster();
            		
            		//合并raster像素矩阵
                	//System.out.println("x,y is:"+320*(x-firstTilex)+","+240*(y-firstTiley));
                	//System.out.println("num is:"+(y*xBlockCount+x));
                	tempMaskRaster.setRect(320*(x-firstTilex), 240*(y-firstTiley), 
                			raster);
                	
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
            
            }
        }
        return maskWriterRasterToPixels(tempMaskImage,tempMaskRaster,keyx,keyy,width,height,mask);
        
    }
	private int[][][] maskWriterRasterToPixels(BufferedImage tempMaskImage,WritableRaster tempMaskRaster,int keyx,int keyy,int width,int height,int []mask){

		int [][][] maskPixels=new int[width][height][4];
    	cm = tempMaskImage.getColorModel();
    	
        des = new int [4];
    	for(int h=0;h<height;h++){
    		for(int w=0;w<width;w++){
    			  data = tempMaskRaster.getDataElements(keyx%320+w,keyy%240+h, null);
    			  int rgb = cm.getRGB(data);
    			  int sr,sg,sb;
    			  sr = (rgb & 0xFF0000)>>16;
    	        sg = (rgb & 0xFF00)>>8;
    	        sb = rgb & 0xFF;
    	        des[0]=sr;
    	        des[1]=sg;
    	        des[2]=sb;
    	        if(mask[h*width+w]==3){
    	        	des[3]=140;
    	        }else if(mask[h*width+w] == 1){
    	        	des[0]=0;
    	        	des[1]=0;
    	        	des[2]=0;
    	        	des[3]=0;
    	        }
    	        else{
    	        	des[3]=0;
    	        }
    	        
    	        maskPixels[w][h][0]=des[0];
    	        maskPixels[w][h][1]=des[1];
    	        maskPixels[w][h][2]=des[2];
    	        maskPixels[w][h][3]=des[3];
    		}
    	}
    	return maskPixels;
   	 
   }
	public float[][][] toFloatColor(int[][][] in,int w,int h){
		float[][][] floatArray=new float[w][h][4];
			for(int width=0;width<w;width++)
				for(int height=0;height<h;height++){
					floatArray[width][height][0]=in[width][height][0]*(1f/255f);
					floatArray[width][height][1]=in[width][height][1]*(1f/255f);
					floatArray[width][height][2]=in[width][height][2]*(1f/255f);
					floatArray[width][height][3]=in[width][height][3]*(1f/255f);
				}
		return floatArray;
		
	}
	private void writerRaster(BufferedImage tempMaskImage,WritableRaster tempMaskRaster,int num,int keyx,int keyy,int width,int height,int []mask){
		if(maskBufferImages[num] != null) {
			return;
		}
		maskBufferImages[num] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		
    	cm = tempMaskImage.getColorModel();
    	
       // data = null;
        //int m=0;
        des = new int [4];
       //System.out.println("keyx,keyy is:"+keyx+","+keyy);
       //System.out.println("width,height is:"+width+","+height);
    	for(int h=0;h<height;h++){
    		for(int w=0;w<width;w++){
    			//System.out.println("rgb is:");
    			//data = totalRaster.getDataElements(keyx+w, keyy+h, null);
    			  //int rgb=cm.getRGB(data);
    			//System.out.println("x,y is:"+(keyx+w)+","+(keyy+h));
    			  data = tempMaskRaster.getDataElements(keyx%320+w,keyy%240+h, null);
    			  int rgb = cm.getRGB(data);
    			  int sr,sg,sb;
    			  sr = (rgb & 0xFF0000)>>16;
    	        sg = (rgb & 0xFF00)>>8;
    	        sb = rgb & 0xFF;
    	        des[0]=sr;
    	        des[1]=sg;
    	        des[2]=sb;
    	        if(mask[h*width+w]==3){
    	        	des[3]=110;
    	        }else if(mask[h*width+w] == 1){
    	        	des[0]=0;
    	        	des[1]=0;
    	        	des[2]=0;
    	        	des[3]=0;
    	        }
    	        else{
    	        	des[3]=0;
    	        }
    	        
    	        //rasterDes[num].setPixel(w, h, des);
    	        WritableRaster raster = maskBufferImages[num].getRaster();
    	        raster.setPixel(w, h, des);
    		}
    	}
   	 
   }
	
	
	/**获取供TextureLoader加载的地图块数据流，JPG格式byte数据*/
	public ByteArrayInputStream getMapBlockTexureStream(int h,int v){
		return new ByteArrayInputStream(getJpegData(h,v));
	}
	/**加载整个地图材质数据*/
	public void loadAllMapTexture(){
		int hor=0;int ve=0;
		for(hor=0;hor<this.horSegmentCount;hor++)
			for(ve=0;ve<this.verSegmentCount;ve++)
				mapTextures[hor][ve]=getMapBlockTexture(hor,ve);
			
	}
	/**读取某一块地图的材质，加载用*/
	public Texture getMapBlockTexture(int h,int v){
		if(h>horSegmentCount||v>verSegmentCount){
			System.err.println("欲获取的地图块材质不在范围内["+h+","+v+"]块，当前地图总计"+horSegmentCount+"*"+verSegmentCount+"块");
			return null;
		}
		byte[] JpegData=getJpegData(h,v);
		Pixmap pixmap=new Pixmap(JpegData,0,JpegData.length);
		Texture texture=new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}
	/**从内存清除所有地图材质，释放资源*/
	public void clearAllTexture(){
		this.mapTextures=null;
		System.gc();
		this.mapTextures=new Texture[this.horSegmentCount][this.verSegmentCount];
	}
	/**获取某一块地图的材质*/
	public Texture texture(int h,int v){
		if(h>horSegmentCount||v>verSegmentCount){
			System.err.println("欲获取的地图块材质不在范围内["+h+","+v+"]块，当前地图总计"+horSegmentCount+"*"+verSegmentCount+"块");
			return null;
		}
		if(h<0||v<0){
			System.err.println("欲获取的地图块材质不在范围内["+h+","+v+"]块，索引小于了0");
			return null;
		}
		Texture temp=mapTextures[h][v];
		if(temp==null){
			byte[] JpegData=getJpegData(h,v);
			Pixmap pixmap=new Pixmap(JpegData,0,JpegData.length);
			 temp=new Texture(pixmap);
			mapTextures[h][v]=temp;
			pixmap.dispose();
		}
		return temp;
	}
	/**获取某个地图块的Jpeg数据
	 * @param h 行数,0开始
	 * @param v 列数,0开始（行列相反逻辑）
	 * @return 读取的字节数组
	 * */
	public byte[] getJpegData(int h, int v)
	{
		try{
			byte[] jpegBuf = (byte[])null;//声明变量
			this.mapFile.seek(this.segmentsOffset[h][v]);//定位到之前读取的,某一块地图块的数据位置
			if (isJPEGData()) {//如果JPEG标志没问题(此时文件指针已经定过去了)
				int len = this.mapFile.readInt2();//读取四个字节,"JPEG"标志后四位是图像数据大小
				jpegBuf = new byte[len];//所以声明那么长的数据保存数据
				this.mapFile.readFully(jpegBuf);//读取len长度数据
				this.jpegDatas[h][v] = jpegBuf;//将读完的数据转交 第h行 第v块 的对象
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream(4096);
			boolean isFilled = false;
			bos.reset();
			jpegBuf = (byte[])this.jpegDatas[h][v];
			bos.write(jpegBuf, 0, 2);//从头开始输入两个字节

			isFilled = false;
			int start = 4;
			int p = 4; 
			for (start = 4; p < jpegBuf.length - 2; p++) {
				if ((!isFilled) && (jpegBuf[p] == -1)) { p++; if (jpegBuf[p] == -38) {
					isFilled = true;

					jpegBuf[(p + 2)] = 12;
					bos.write(jpegBuf, start, p + 10 - start);

					bos.write(0);
					bos.write(63);
					bos.write(0);
					start = p + 10;
					p += 9;
        	 } }
				if ((isFilled) && (jpegBuf[p] == -1)) {
					bos.write(jpegBuf, start, p + 1 - start);
					bos.write(0);
					start = p + 1;
				}
			}
			bos.write(jpegBuf, start, jpegBuf.length - start);
			this.jpegDatas[h][v] = bos.toByteArray();
			} catch (Exception e) {
				System.err.println("获取JPEG 数据块失败：" + e.getMessage());
			}
			return (byte[])this.jpegDatas[h][v];//ByteArrayInputStream bais = new ByteArrayInputStream(byte[]jpegDatas);
 	}
	/**获取某个地图遮罩块块的mask数据!!!!!失败!!!
	 * @param index 索引数,0开始
	 * @return 读取的字节数组
	 * */
	/*
	public byte[] getMaskData(int index)
	{
		byte[] MaskDataDec=null;
		try{
			byte[] maskBuf = (byte[])null;//声明变量
			MapMask newMask=new MapMask();
			this.mapFile.seek(this.masksOffset[index]);//定位到之前读取的,某一块地图遮罩块的数据位置
			newMask.startX=this.mapFile.readInt2();
			newMask.startY=this.mapFile.readInt2();
			newMask.width=this.mapFile.readInt2();
			newMask.height=this.mapFile.readInt2();
			newMask.mask_size=this.mapFile.readInt2();
			
			if (newMask.width==0 || newMask.height==0 || newMask.mask_size==0)
				return null;//如果是空数据
			
			newMask.maskData=new byte[newMask.mask_size];
			maskBuf = new byte[newMask.mask_size];//声明那么长的数据保存数据
			this.mapFile.readFully(maskBuf);
			newMask.maskData=maskBuf;
			
			
			
			
		}catch(Exception e){}
		
		return MaskDataDec;
 	}
	*/
	/**获取一个地图遮罩单元对象
	 * @param maskIndex 第几个单元，0开始
	 * */
	public MaskUnit getMaskUnit(int maskIndex) {
		if(maskIndex>this.maskCount){
			Gdx.app.error("[ XYQ ]","[地图] -> MapData.getMaskUnit 传递的mask索引超过了地图最大mask数");return null;
		}
		MaskUnit maskUnit = maskUnits[maskIndex];
		if(maskUnit == null) {
			maskUnit = ReadMask(maskIndex);
			maskUnit.inFotherIndex=maskIndex;
			maskUnits[maskIndex] = maskUnit;
		}
		return maskUnit;
	}
	/**加载所有的遮罩单元*/
	public boolean loadAllMask(){
		MaskUnit maskUnit;
		for(int i=0;i<this.getMaskCount();i++){
			maskUnit = this.maskUnits[i];
			if(maskUnit == null) {
				maskUnit = ReadMask(i);
				maskUnits[i] = maskUnit;
			}
		}
		
		return true;
	}
	
	public MaskUnit ReadMask(int UnitNum){
		
		try {
			byte m_mask[];
			long seek;
			//System.out.println("UnitNum is:"+UnitNum+",maskoffestlist is:"+m_MaskList[UnitNum]);
			seek = this.masksOffset[UnitNum];
			mapFile.seek(seek);
			byte maskhead[] = new byte[20];
			mapFile.read(maskhead, 0, 20);
			float maskX = (float)constructInt(maskhead,0);
			float maskY = (float)constructInt(maskhead,4);
			int maskWidth = constructInt(maskhead,8);
			int maskHeight = constructInt(maskhead,12);
			
			int masksize = constructInt(maskhead,16);
			
			//读mask数据
			//System.out.println("mask x,y,width,height is:"+maskkeyx+","+maskkeyy+","+maskwidth+","+maskheight);
			//System.out.println("masksize is:"+masksize);
			m_mask = new byte[masksize];
			mapFile.read(m_mask,0,masksize);
			
			/*for(int i=0;i<masksize;i++){
				System.out.println(constructMask(m_mask,i));
			}*/
			
			// 解密mask数据
			int bol;
			if(maskWidth%4==0){
				bol=0;
			}else{
				bol=1;
			}
			int align_width = (maskWidth / 4 + bol) * 4;	// 以4对齐的宽度
			byte[] pMaskDataDec = new byte[align_width * maskHeight / 4];		// 1个字节4个像素，故要除以4
			int dec_mask_size = DecompressMask(m_mask, pMaskDataDec);
			//System.out.println("alignwidth is:"+align_width);
			//System.out.println("pmaskdatasize is:"+pMaskDataDec.length);
			
			//System.out.println("dec_mask_size is:"+dec_mask_size);
			
			/*for(int i=0;i<dec_mask_size;i++){
				System.out.println(constructMask(pMaskDataDec,i));
			}*/
			
			//mask数据还原
			int[] maskData = new int[maskWidth*maskHeight];
			int ow=align_width-maskWidth;
			int md=0;
			//System.out.println("align width is:"+align_width);
			//System.out.println("ow is:"+ow);
			
			
			for(int i=0;i<dec_mask_size;i++){
				
				//System.out.println("i is:"+i+",dec_mask is:"+(int)constructMask(pMaskDataDec,i));
				if(((i+1)*4)%align_width==0 ){
					
					for(int j=0;j<4-ow;j++){
						maskData[md++]=constructMaskData(pMaskDataDec[i],j);
						//System.out.println("num is:"+(md-1)+","+maskData[md-1]);
					}
	
			    }else if(maskWidth<4){
			    	for(int j=0;j<maskWidth;j++){
						maskData[md++]=constructMaskData(pMaskDataDec[i],j);
						//System.out.println("num is:"+(md-1)+","+maskData[md-1]);
					}
			    }
				else{
					 for(int j=0;j<4;j++){
						maskData[md++]=constructMaskData(pMaskDataDec[i],j);
						//System.out.println("num is:"+(md-1)+","+maskData[md-1]);
					 }
			    }
	
				//masktemp=constructMask(pMaskDataDec,i);
			   // System.out.println(masktemp);	
			}
			/*for(int i=0;i<maskwidth*maskheight;i++){
				System.out.println(maskData[i]);
			}*/
		
			return new MaskUnit(this,UnitNum,maskX, maskY, maskWidth, maskHeight, maskData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 从流加载mask数据并解码
     * 
     * @param is
     * @throws IOException 
     */    
    public int DecompressMask(byte[] m_mask,byte [] pMaskDataDec){
    	
		o_pos=0;
		m_pos=0;
		i_pos=0 ;
		byte [] op = pMaskDataDec;
		
		byte [] ip= m_mask;
		 
		
		boolean first_literal_run;
		
		//int masktemp;
  	
		
		cont:
		while(true){
			
			first_literal_run=true;
			
			if(i_pos==0){
				if(constructMask(ip,i_pos)>17){
					t = constructMask(ip,i_pos++)-17;
					if(t<4){
						do{
							op[o_pos++]=ip[i_pos++];
						}while(--t>0);
					t=constructMask(ip,i_pos++);
					if(match(op,ip)==1){   //goto match
						break; 
					}else{
						//System.out.println("i_pos is:"+i_pos);
						continue cont;
					}
					
					}else{
						do{
							op[o_pos++]=ip[i_pos++];
						}while(--t>0);
						first_literal_run=false;
					}
				}
			}

			if(first_literal_run){
				//System.out.println("ip is:"+ip[i_pos]+",i_pos is:"+i_pos);
			t=constructMask(ip,i_pos++);
			
			//System.out.println("t is:"+t);
			
			if(t>=16) {
				if(match(op,ip)==1){
					break;
				}else{
					//System.out.println("i_pos is:"+i_pos);
					continue cont;
				}
			}
			if(t==0){
				while(constructMask(ip,i_pos)==0){
					t+=255;
					i_pos++;
				}
				t+=15+constructMask(ip,i_pos++);
			}
			
			//op[o_pos]=ip[i_pos];
            for(int i=0;i<4;i++){
            	op[o_pos++]=ip[i_pos++];    // 获取sizeof(unsigned)个新字符
            	//System.out.println("op is:"+op[o_pos-1]);
            	
            }
            
            //System.out.println("t is:"+t);
			if(--t>0){
				
				if(t>=4){
					do{
						for(int i=0;i<4;i++){
			            	op[o_pos++]=ip[i_pos++];   // 获取sizeof(unsigned)个新字符
			            	
			            	//System.out.println("--t op is:"+op[o_pos-1]);
			            }
						t-=4;
					}while(t>=4);
					if(t>0){
						do{
					       op[o_pos++]=ip[i_pos++];	
					    }while(--t>0);
					}
				}else {
					do{
						op[o_pos++]=ip[i_pos++];
						//System.out.println("op is:"+op[o_pos-1]);
					}while(--t>0);
				}
			}
			}
			
			
			//first_literal_run
			
				t=constructMask(ip,i_pos++);
				
				//System.out.println("t is:"+t+",ipos is:"+i_pos);
			if(t>=16){                             // 是重复字符编码
				if(match(op,ip)==1){
					break;
				}else{
					//System.out.println("i_pos is:"+i_pos);
					continue cont;
				}
				
			}else{
				System.out.println("0x0801");
				m_pos = o_pos - 0x0801;
				m_pos -= t>>2;
	            m_pos -= constructMask(ip,i_pos++)<<2;
	            
	            op[o_pos++] = ip[m_pos++];
	            op[o_pos++] = ip[m_pos++];
	            op[o_pos++] = ip[m_pos++];
	            
	            //goto match_done
	            t=(ip[i_pos]>>6)&3;
				if(t==0){
					
				}else{
					do{
						op[o_pos++]=ip[i_pos++];
					}while(--t>0);
				t=constructMask(ip,i_pos++);
				if(match(op,ip)==1){
					break;
				}else{
					//System.out.println("i_pos is:"+i_pos);
					continue cont;
				}
				}
			}
			

			
		}
		return o_pos;
	}
    /**
     * mask解码
     * 
     * @param is
     * @throws IOException 
     */    
    	
    	public int match(byte[]op,byte[]ip){
    		
    		while(true){
    			
    			
    			boolean copy_match=false;
    			boolean match_done=true;
    			byte []ms=new byte[o_pos];
    			int num=0;
    			
    			//System.out.println("t is:"+t);
    			if(t>=64){

    				for(int i=0;i<o_pos;i++){
    					ms[i]=op[i];
    				}
    				
    				
    				
    				num=1;
    				
    				int po = (t>>2)&7;
    				num+=po;				
    				
    				po = constructMask(ip,i_pos++)<<3;
    				num+=po;
    				
    				
    			       
    			        int mtemp=0;
    			      
    			        
    					/*for(int i=o_pos-1;i>=num;i--){
    				      ms[i]=ms[i-num];

    					}*/
    					
    					for(int j=o_pos-num;j<=o_pos-1;j++){
    						ms[mtemp++]=op[j];
    					}
    				
    				 /*for(int i=0;i<o_pos;i++){
    				   System.out.println("op is:"+op[i]);
    			    }*/
    				
    				t = (t>>5)-1;
    				
    				copy_match=true;
    			}else if(t>=32){

    				t&=31;
    			
    				if(t==0){
    					while(ip[i_pos]==0){
    						t+=255;
    						i_pos++;
    						
    					}
    					
    					t+=31+constructMask(ip,i_pos++);
    					
    				}
    				
    				//m_pos = o_pos -1;
    				//ms=op;
    				
    				for(int i=0;i<o_pos;i++){
    					ms[i]=op[i];
    				}

    				num=1;

    				int po = constructWord(ip,i_pos)>>2;
    				num+=po;
    				
    				int mtemp=0;
    				for(int j=o_pos-num;j<=o_pos-1;j++){
    					ms[mtemp++]=op[j];
    				}

    				/*for(int i=0;i<o_pos;i++){
    				System.out.println("op2 is:"+op[i]);
    			    }
    				*/
    				i_pos +=2;
    				
    			}else if(t>=16){
    				
    				//byte temp;
    				
    				//m_pos = o_pos;
    				for(int i=0;i<o_pos;i++){
    					ms[i]=op[i];
    				}
    				
    				//m_pos -= (t&8)<<11;
    				int po = (t&8)<<11;
    				num=po;

    				t&=7;
    				if(t==0){
    					while(ip[i_pos]==0){
    						t+=255;
    						i_pos++;
    					}
    					
    					t+=7+constructMask(ip,i_pos++);
    				}
    				
    				//m_pos -= constructMask(ip,i_pos)>>2;
    				po = constructWord(ip,i_pos)>>2;
    				num+=po;
    				
    				
    				i_pos +=2;
    				if(ip.length == i_pos){
    					//System.out.println("o_pos is:"+o_pos);
    					return 1;
    				}else{
    					//m_pos -= 0x4000;
    					po = 0x4000;
    					//System.out.println("0x4000 is:"+po);
    				num+=po;
    				//System.out.println("num is:"+num);
    				int mtemp=0;
    				for(int j=o_pos-num;j<=o_pos-1;j++){
    					ms[mtemp++]=op[j];
    				}
    					
    					
    					
    				}
    				
    			}else{
    				
    				System.out.println("t0-15 is:"+t);
    				
    				byte temp;
    				//m_pos = o_pos-1;
    				for(int i=0;i<o_pos;i++){
    					ms[i]=op[i];
    				}
    				
    				temp=ms[o_pos-1];
    				num=1;

    				for(int i=o_pos-1;i>=1;i--){
    			      ms[i]=ms[i-1];
    			      
    			      
    				}
    				ms[0]=temp;
    				
    				
    				
    				//m_pos -= t>>2;
    				int po = (t>>2);
    				num+=po;
    				for(int j=0;j<po;j++){
    					temp=ms[o_pos-1];
    					
    					for(int i=o_pos-1;i>=1;i--){
    				      ms[i]=ms[i-1];

    					}
    					ms[0]=temp;
    				}
    				
    				
    				
    				//m_pos -= constructMask(ip,i_pos++)<<2;
    				po = constructWord(ip,i_pos++)<<2;
    				num+=po;
    				for(int j=0;j<po;j++){
    					temp=ms[o_pos-1];
    					
    					for(int i=o_pos-1;i>=1;i--){
    				      ms[i]=ms[i-1];

    					}
    					ms[0]=temp;
    				}
    				
    				
    				op[o_pos++] = ms[0];
    				op[o_pos++] = ms[1];
    				match_done=false;
    			}
    			
    			if(match_done){

    			if((t>=6 && num>=4)&&copy_match==false){
    				//op[o_pos] = ip[i_pos];
    				m_pos=0;
    				for(int i=0;i<4;i++){
    	            	op[o_pos++]=ms[m_pos++];    // 获取sizeof(unsigned)个新字符
    	            }
    				t-=2;
    				do{
    					
    						for(int i=0;i<4;i++){
    							if(m_pos<num){
    								op[o_pos++]=ms[m_pos++];    // 获取sizeof(unsigned)个新字符
    							}else{
    								m_pos=0;
    								op[o_pos++]=ms[m_pos++];    // 获取sizeof(unsigned)个新字符
    							}
    			            	
    			            }
    					
    					
    					t-=4;
    				}while (t>=4);
    				if(t>0){
    					do{
    						if(m_pos<num){
    							op[o_pos++]=ms[m_pos++];
    						}else{
    							m_pos=0;
    							op[o_pos++]=ms[m_pos++];
    						}
    						
    					}while(--t>0);
    				}
    			}else{
    //copy_match: 
    				m_pos=0;
    	
    				    for(int i=0;i<2;i++){
    				    	if(m_pos<num){
    				    		op[o_pos++]=ms[m_pos++];
    							//System.out.println("ms is:"+ms[m_pos-1]);
    				    	}else{
    				    		m_pos=0;
    				    		op[o_pos++]=ms[m_pos++];
    					        //System.out.println("ms is:"+ms[m_pos-1]);
    				    	}
    				    	
    				    }

    			        do{
    			        	if(m_pos<num){
    			        	op[o_pos++]=ms[m_pos++];
    			        	//System.out.println("ms is:"+ms[m_pos-1]+" m_pos is:"+m_pos);
    			        	}else{
    			        		m_pos=0;
    			        	op[o_pos++]=ms[m_pos++];
    			        	//System.out.println("ms is:"+ms[m_pos-1]+" m_pos is:"+m_pos);
    			        	}
    			        	
    			        }while(--t>0);
    			}
    			}
    	
    				t=(ip[i_pos-2])&3;

    			if(t==0){
    				return 0;
    			}
    			
    			//match_next:
    				do{
    					op[o_pos++]=ip[i_pos++];
    					//System.out.println("op is:"+op[o_pos]);
    				}while(--t>0);
    			t=constructMask(ip,i_pos++);
    			
    		}
    	}
    
    	/**
         * 将BYTE格式转化
         * 
         * @param is
         */
        public static int constructMaskData(byte mask,int offset){
   	    int ret=0;
   	    switch(offset){
   	    case 0:ret = (int)mask & 0x03;break;
   	    case 1:ret = ((int)mask & 0x0c)>>2;break;
   	    case 2:ret = ((int)mask & 0x30)>>4;break;
   	    case 3:ret = ((int)mask & 0xc0)>>6;break;
   	    }
   	    return ret;
        }
        
        /**
         * 将BYTE格式转化
         * 
         * @param is
         */
         public static int constructWord(byte[] mask,int offset){
    	    int ret = ((int) mask[offset + 1] & 0xff);
    	    ret = (ret << 8) | ((int) mask[offset + 0] & 0xff);
    	    return ret;
         }
    	
    /**
     * 将BYTE格式转化
     * 
     * @param is
     */
    public static int constructMask(byte[] mask,int offset){
	    int ret = (int)mask[offset] & 0xff;
	    return ret;
    }

  	public MapUnit ReadUnit(int x,int y){
  		
  		try {
  			int[] masklist = new int[0];
  			byte[] m_cell = new byte[0];
  			long seek;
  			boolean loop=true;
  			seek=segmentsOffset[x][y];
  			
  			
  			mapFile.seek(seek);
  			//System.out.println("seek is:"+seek);
  			
  			byte masknum[]=new byte[4];
  			mapFile.read(masknum, 0, 4);
  			int m_MaskNum = constructInt(masknum,0); //读取mask数量
  			
  			
  			//System.out.println("masknum is:"+m_MaskNum);
  			if(m_MaskNum>0){
  			byte MaskList[]=new byte[4*m_MaskNum];
  			masklist = new int[m_MaskNum];
  			mapFile.read(MaskList, 0, 4*m_MaskNum);
  			    for(int i=0;i<m_MaskNum;i++){
  				    masklist[i]=constructInt(MaskList,4*i);
  			        //System.out.println("masklist "+i+" is:"+masklist[i]+" is:"+m_MaskList[masklist[i]]);
  			    }
  			}
  			
  			while(loop){
  				byte unithead[]=new byte[4*2];
  				mapFile.read(unithead,0,4*2);
  				int m_unithead=constructInt(unithead,0); //读取单元的头数据
  				int m_headSize=constructInt(unithead,4);
  				
  				//System.out.println(0x494D4147+","+0x4A504547+","+0x4D41534B+","+
  						//0x424C4F4B+","+0x43454C4C+","+0x42524947);
  				
  				//System.out.println("head.flag is:"+m_unithead+"  head.size is:"+
  					//	m_headSize);
  				
  				switch (m_unithead){
  				case 0x494D4147: System.out.println("imag"); break;//GAMI
  				case 0x4A504547: //System.out.println("jpeg");
  				
  				
  				
  				//System.out.println("$%$^$%#%$#%#%#%"+size);
  				/*try {
  					
  					mapFile.read(m_jpeg,0,m_headSize);
  					
  					
  				} catch (IOException e) {
  					e.printStackTrace();
  				}*/
  				mapFile.skipBytes(m_headSize);
  				
  				break;//GEPJ
  				
  				case 0x4D41534B: System.out.println("mask");break;//KSAM
  				case 0x424C4F4B: System.out.println("blok");break;//KOLB
  				case 0x43454C4C: //System.out.println("cell");
  				m_cell = new byte[m_headSize];
  				try {
  					mapFile.read(m_cell,0,m_headSize);
  					
  				} catch (IOException e) {
  					e.printStackTrace();
  				}
  				break;//LLEC
  				case 0x42524947: //System.out.println("brig");
  				byte m_brig[]=new byte[m_headSize];
  				
  				try {
  					mapFile.read(m_brig,0,m_headSize);
  				
  				} catch (IOException e) {
  					e.printStackTrace();
  				}
  				loop=false;
  				break;//GIRB
  				}
  			}

  			return new MapUnit(masklist, m_cell);
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  		
  		return null;
  		
  	}
	/**
	 * 判断是否是JPEG数据.其实也没怎么判断,就是判断标识符
	 * @return true JPEG标识符判断正确
	 * */
	private boolean isJPEGData()
	{
		byte[] buf = new byte[4];
		try {
			int len = this.mapFile.read();//这个就是n
			this.mapFile.skipBytes(3 + len * 4);//调用时已经定位了,文件头是四字节,所以4-1=3,所以跳过3+n*4字节
			this.mapFile.read(buf);
			String str = new String(buf);
			return str.equals("GEPJ");
			/*
			 "JPEG数据"在4字节 地图单元引索的开始位置和n*4字节之后. 
			 * */
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	/**判断是否是合法的文件头:0x4D312E30 (M1.0) 大话2新地图 梦幻地图*/
  	private boolean isValidMapFile(){
  		byte[] buf = new byte[4];//声明byte数组,暂存数据.合法的梦幻地图文件头只有四字节
  		try {
  			this.mapFile.read(buf);
  			String str = new String(buf);
  			return str.equals("0.1M");//梦幻西游地图标志为0.1M(M1.0)
  		} catch (IOException ex) {
  			ex.printStackTrace();
  		}
  		return false;
  	}

  public String getFilename() {
    return this.filename;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }
  public int getHeaderSize() {
	return this.headerSize;
  }
  public int getHorSegmentCount()
  {
    return this.horSegmentCount;
  }
  
  public int getVerSegmentCount() {
    return this.verSegmentCount;
  }
  public int getMaskCount(){
	 return this.maskCount; 
	  
  }
  /**
   * 将BYTE格式转化
   * 
   * @param is
   */
  public static int constructInt(byte[] in, int offset) {

      int ret = ((int) in[offset + 3] & 0xff);

      ret = (ret << 8) | ((int) in[offset + 2] & 0xff);

      ret = (ret << 8) | ((int) in[offset + 1] & 0xff);

      ret = (ret << 8) | ((int) in[offset + 0] & 0xff);

      return ret;

  }
  
  /**
   * 自定义随机文件读取类
   * */
  class MyRandomAccessFile extends RandomAccessFile
  {
    public MyRandomAccessFile(String name, String mode)
      throws FileNotFoundException
    {
      super(name,mode);
    }

    public MyRandomAccessFile(File file, String mode) throws FileNotFoundException {
      super(file,mode);
    }

    public int readInt2() throws IOException {
      int ch1 = read();
      int ch2 = read();
      int ch3 = read();
      int ch4 = read();
      if ((ch1 | ch2 | ch3 | ch4) < 0)
        throw new EOFException();
      return (ch1 << 0) + (ch2 << 8) + (ch3 << 16) + (ch4 << 24);
    }
  }
}
/*
 * ======================= MAP HEAD =============================
4字节 0.1M (M1.0)
4字节 地图的宽度
4字节 地图的高度
4*n字节  地图单元的引索 n=地图的宽度/640*2 * 地图高度/480*2
4字节 文件头大小，包括这4字节，及以上的字节。
==============================================================
======================= Unknown ==============================
n字节 未知用途，大小为：第一个单元引索值减去文件头大小。
      注意：这个格式中还没有发现旧格式中的KLOB、GAMI和KSAM。
            有可能和这些单元的用途相同。
==============================================================
======================= Unit Data ============================
4字节 地图单元引索的开始位置。
n*4字节 n为上面的值，[来源：GameRes.com]n为0时不存在。
4字节 GEPJ (JPEG)
4字节 大小
n字节 数据
4字节 LLEC (CELL)
4字节 大小
n字节 数据
4字节 BRIG (GIRB)
4字节 大小
n字节 数据
4字节 结束单元。
==============================================================
0.1M 新地图文件头 
Index 数据块引索
Unknown n字节，未知用途
GEPJ(JPEG) 图象数据
LLEC(CELL) 地图规则，一字节代表一个游戏坐标
GIRB(BRIG) 光亮规则
    :
    :
    :
GEPJ(JPEG) 图象数据
LLEC(CELL) 地图规则，一字节代表一个游戏坐标
GIRB(BRIG) 光亮规则


 * 
 */