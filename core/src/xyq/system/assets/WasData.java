package xyq.system.assets;


/**
 * Was文件的数据结�?
 * @author david
 *
 */
public class WasData {
	public short size = 0; // 头文件大�?
	public short frame = 0; // 帧数
	public short direct = 0; // 方向�?

	public short w = 0;
	public short h = 0;
	public short x = 0;
	public short y = 0;
	public short[] palette;
	public int[] frameOffsets;
	
	public byte[] data;
	public String path;

	public WasData(byte[] data) {
		// 2字节表示文件类型
		int pos = 0;
		// 读取文件�?
		size = ByteUtil.getShort(data, pos += 2);
		direct = ByteUtil.getShort(data, pos += 2);
		frame = ByteUtil.getShort(data, pos += 2);
		w = ByteUtil.getShort(data, pos += 2);
		h = ByteUtil.getShort(data, pos += 2);
		x = ByteUtil.getShort(data, pos += 2);
		y = ByteUtil.getShort(data, pos += 2);

		palette = new short[256];
		pos = size + 4;
		for (int i = 0; i < 256; i++) {
			palette[i] = ByteUtil.getShort(data, pos);
			pos = pos + 2;
		}

		// 帧偏移量数据
		frameOffsets = new int[direct * frame];
		for (int i = 0; i < direct; i++) {
			for (int n = 0; n < frame; n++) {
				frameOffsets[i * frame + n] = ByteUtil.getInt(data, pos);
				pos = pos + 4;
			}
		}
		this.data = data;
	}

	public short getFrame() {
		return frame;
	}

	public short getDirect() {
		return direct;
	}

	public short getW() {
		return w;
	}

	public short getH() {
		return h;
	}
	
	/**
	 * 修改各个区段的着色
	 * 
	 * @param sectionIndex
	 * @param schemeIndex
	 */
	public void coloration(PPCData pp) {
		
		ColorationScheme[] schemes = pp.getColorSchemes();
		for(ColorationScheme scheme:schemes){
			for (int i = scheme.getStartPos(); i < scheme.getEndPos(); i++) {
				this.palette[i] = scheme.mix(palette[i]);
			}
		}
	}

}
