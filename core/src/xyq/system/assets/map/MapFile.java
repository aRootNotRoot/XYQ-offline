package xyq.system.assets.map;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Random;

import javax.imageio.ImageIO;

import xyq.system.assets.ByteUtil;


/**
 * 
 * @modify 2016-2-19 wpaul
 *
 */
public class MapFile {
	
	public final static byte UNABLE_FLAG = 1;

	public String basepath;

	private DRandomAccessFile f; // 文件句柄

	int id; // 地图编号

	public int w = 0;			//地图宽
	public int h = 0;			//地图高
	public int xBlock;			//地图横向X轴块数
	public int yBlock;			//地图纵向Y轴块数
	
	
	public int[] maskOffsets;	//遮罩层偏移
	public int[] blokOffsets;	//地图块偏移
	
	public MapMask[] masks;

//	public MapFile(int id) {
//		reset(id);
//	}
	
	public MapFile(String path){
		basepath = path+"/scene/";
	}
	
	public MapFile(File file){
		try {
			// 释放资源
			if (f != null) {
				f.close();
			}
			w = 0;
			h = 0;
			xBlock = 0;
			yBlock = 0;
			maskOffsets = null;
			blokOffsets = null;
			
			if (masks != null) {
				for (int i = 0; i < masks.length; i++) {
					masks[i] = null;
				}
			}
			
			// 重新加载
			f = new DRandomAccessFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
	}

	public void reset(int id) {
		try {
			// 释放资源
			if (f != null) {
				f.close();
			}
			w = 0;
			h = 0;
			xBlock = 0;
			yBlock = 0;
			maskOffsets = null;
			blokOffsets = null;
			
			if (masks != null) {
				for (int i = 0; i < masks.length; i++) {
					masks[i] = null;
				}
			}
			
			// 重新加载
			this.id = id;
			f = new DRandomAccessFile(new File(basepath + id + ".map"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
	}

	// 初始化地图文件
	public void init() {
		// 加载地图基础信息
		int index = 0;
		byte[] buf = new byte[12];
		try {
			f.read(buf);
			byte[] mark = new byte[4];
			System.arraycopy(buf, 0, mark, 0, mark.length);

			if (!"0.1M".equals(new String(mark))) {
				// logger.error("===文件检验失败：{}", new String(temp));
				return;
			}
			index += 4;
			w = ByteUtil.getInt(buf, index);
			index += 4;
			h = ByteUtil.getInt(buf, index);
			// logger.debug("===w:{},h:{}", w, h);
			xBlock = (int) Math.ceil(w / 320.0f);
			yBlock = (int) Math.ceil(h / 240.0f);

			int blockCount = xBlock * yBlock;
			buf = new byte[blockCount * 4];
			f.read(buf);
			blokOffsets = new int[blockCount];
			index = 0;
			for (int i = 0; i < blockCount; i++) {
				blokOffsets[i] = ByteUtil.getInt(buf, index);
				index = index + 4;
			}
			int maskSize = f.getInt();
			maskSize=maskSize+0;
			int maskCount = f.getInt();
			masks = new MapMask[maskCount];
			maskOffsets = new int[maskCount];
			buf = new byte[maskCount * 4];
			index = 0;
			f.read(buf);
			for (int i = 0; i < maskCount; i++) {
				maskOffsets[i] = ByteUtil.getInt(buf, index);
				index += 4;
			}
			// 保存所有Mask数据进内存
			for (int i = 0; i < maskCount; i++) {
				f.seek(maskOffsets[i]);
				buf = new byte[20];
				index = 0;
				f.read(buf);
				int l = ByteUtil.getInt(buf, index);
				index += 4;
				int t = ByteUtil.getInt(buf, index);
				index += 4;
				int w = ByteUtil.getInt(buf, index);
				index += 4;
				int h = ByteUtil.getInt(buf, index);
				index += 4;
				int size = ByteUtil.getInt(buf, index);
				index += 4;
				buf = new byte[size];
				f.read(buf);
				int align_width = (int) Math.ceil(w / 4.0f) * 4;
				byte[] out = new byte[align_width * h / 4];
				//JNIHelper.decodeMask(buf, out);
				masks[i] = new MapMask(l, t, w, h, out);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void loadMapBlock(int bx, int by, ByteBuffer data) {
		byte[] array = new byte[data.limit()];
		int[] masks = null;
		try {
			long iOffset=0;
			try{
			 iOffset = blokOffsets[bx + by * xBlock];
			}catch(Exception e){
				e.printStackTrace();
			}

			f.seek(iOffset);
			// 读取Mask数据
			int maskcount = f.getInt();
			if (maskcount > 0) {
				masks = new int[maskcount];
				for (int i = 0; i < maskcount; i++) {
					int index = f.getInt();
					masks[i] = index;
				}
			}
			if (isJpeg()) {
				int len = f.getInt();
				byte[] jpegBuf = new byte[len];
				f.read(jpegBuf);
				// modify jpeg data
				ByteArrayOutputStream bos = new ByteArrayOutputStream(4096);
				boolean isFilled = false;// 是否0xFF->0xFF 0x00
				bos.reset();
				bos.write(jpegBuf, 0, 2);
				// skip 2 bytes: FF A0
				int p, start;
				isFilled = false;
				for (p = 4, start = 4; p < jpegBuf.length - 2; p++) {
					if (!isFilled && jpegBuf[p] == (byte) 0xFF
							&& jpegBuf[++p] == (byte) 0xDA) {
						isFilled = true;
						jpegBuf[p + 2] = 12;
						bos.write(jpegBuf, start, p + 10 - start);
						bos.write(0);
						bos.write(0x3F);
						bos.write(0);
						start = p + 10;
						p += 9;
					}
					if (isFilled && jpegBuf[p] == (byte) 0xFF) {
						bos.write(jpegBuf, start, p + 1 - start);
						bos.write(0);
						start = p + 1;
					}
				}
				bos.write(jpegBuf, start, jpegBuf.length - start);
				InputStream in = new ByteArrayInputStream(bos.toByteArray());
				//JPEGImageDecoder decode = JPEGCodec.createJPEGDecoder(in);
				//BufferedImage tag = decode.decodeAsBufferedImage();
				BufferedImage tag = ImageIO.read(in);
				int[] pixels = new int[tag.getWidth() * tag.getHeight()];
				tag.getRGB(0, 0, tag.getWidth(), tag.getHeight(), pixels, 0,
						tag.getWidth());
				for (int ty = 0; ty < tag.getHeight(); ty++) {
					for (int tx = 0; tx < tag.getWidth(); tx++) {
						int pixel = pixels[ty * tag.getWidth() + tx];
						int index = (ty * 320 + tx) * 4;
						if (index >= array.length)
							break;
						array[index] = (byte) ((pixel >> 16) & 0xFF);
						array[index+1]= (byte) ((pixel >> 8) & 0xFF); 
						array[index+2]= (byte) (pixel & 0xFF); 
						array[index+3]= (byte) ((pixel >> 24) & 0xFF); 
					}
				}
			}
			// logger.debug("读取CELL时，指针位置为:{}", f.getFilePointer());
			// if (isCell()) {
			//
			// }
			// logger.debug("读取BRIG时，指针位置为:{}", f.getFilePointer());
			// if (isBrig()) {
			//
			// }
			// logger.debug("读取BRIG时，指针位置为:{}", f.getFilePointer());
		} catch (IOException e) {
			e.printStackTrace();
		}
		data.put(array);
		return ;
	}

	private boolean isJpeg() {
		byte[] buf = new byte[4];
		try {
			f.read(buf);
			String str = new String(buf);
			return str.equals("GEPJ");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	// 地图可行走区域数据
	public byte[] getCell() throws IOException {
		int vw = MapBlock.MAPBLOCK_W / 20;
		int vh = MapBlock.MAPBLOCK_H / 20;
		int h = yBlock * vh;
		int w = xBlock * vw;
		byte[] cells = new byte[h * w];
		for (int by = 0; by < yBlock; by++) {
			for (int bx = 0; bx < xBlock; bx++) {
				f.seek(blokOffsets[bx + by * xBlock]);
				int len = f.readUnsignedByte();
				f.skipBytes(3 + len * 4 + 4);
				len = f.getInt();
				f.skipBytes(len + 8);
				for (int j = 0; j < 12; j++) {
					for (int i = 0; i < 16; i++) {
						byte s = (byte) f.getByte();
						cells[w * (by * vh + j) + bx * vw + i] = s;
					}
				}
			}
		}
		return cells;
	}
	
	public PathFinder getPathSearcher(){
		int xCellCount = xBlock * 16;
		int yCellCount = yBlock * 12;
		
		try {
			PathFinder searcher = AStarFactory.createAstar(getCell(), xCellCount, yCellCount, 3);
			return searcher;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得随机坐标
	 * @throws IOException 
	 */
	public int[] getCoord(Random random){
		int[] coord = new int[2];
		try {
			byte[] cell = getCell();
			int x = 0;
			int y = 0;
			int width = w/20;
			int height = h/20;
			do{
				x = MathUtils.getRandom(width, random);
				y = MathUtils.getRandom(height, random);
			}while(cell[x+width*y]==UNABLE_FLAG);
			coord[0] = x;               //X坐标
			coord[1] = getCellH()-y-1;  //Y坐标
			System.out.println(" : x,y is:"+coord[0]+" "+coord[1]);
			return coord;
		} catch (IOException e) {
			throw new NullPointerException("map["+id+"] cell is null");
		}
	}
	/***************************
	public int[] getCoord(Random random){
		int[] coord = new int[2];
		try {
			byte[] cell = getCell();
			int idx = 0;
			do{
				idx = MathUtils.getRandom(cell.length, random);
			}while(cell[idx]==UNABLE_FLAG);
			int y = idx/getCellW();
			int x = idx-y*getCellW();
			coord[0] = x;               //X坐标
			coord[1] = getCellH()-y-1;  //Y坐标
			System.out.print("idx is:"+idx);
			System.out.println(" : x,y is:"+x+" "+y);
			return coord;
		} catch (IOException e) {
			throw new NullPointerException("map["+id+"] cell is null");
		}
	}
	********************************/

	public int getCellW(){
		int vw = MapBlock.MAPBLOCK_W / 20;
		return xBlock * vw;
	}
	
	public int getCellH(){
		int vh = MapBlock.MAPBLOCK_H / 20;
		return yBlock * vh;
	}


	/*public PathFinder getPathFinder(){
		int xCellCount = xBlock * 16;
		int yCellCount = yBlock * 12;
		
		try {
			PathFinder finder = AStarFactory.createAstar(getCell(), xCellCount, yCellCount, 3);
			return finder;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/

	
	public static void main(String[] args) throws IOException{
			/************************
Random random = new Random(System.currentTimeMillis());
		MapFile file = new MapFile("C:/XYQDev/xyq/");
		file.reset(1092);
		int w = file.getCellW();
		int h = file.getCellH();
		byte [] cells = file.getCell();
	
		for(int r=0;r<h;r++){
			for(int c=0;c<w;c++){
				System.out.print(cells[c+r*w]);
			}
			System.out.println();
		}
		
		for(int i=0;i<100;i++)
			file.getCoord(random);
			*********************/
	}
}
