package xyq.system.assets;

/**
 * pp文件的数据结构(用于精灵染色)
 * @author wpaul
 *
 */
public class PPData {
	
	private ColorationScheme[] colorSchemes;
	
	public PPData(byte[] data,int[] colorations){
		int pos = 0;
		short secLength = (short) (bytesToShort(data,pos)%1000);
		colorSchemes = new ColorationScheme[secLength];
		pos = offset(data,pos);  //偏移一位
		int[] starts = new int[secLength];
		int[] ends = new int[secLength];
		for(int i=0;i<secLength;i++){
			starts[i] = bytesToShort(data,pos);
			pos = offset(data,pos);
			ends[i] = bytesToShort(data,pos);
		}
		

		System.err.println("secLength is "+secLength);
		System.err.println("str is "+new String(data));
		System.err.println("data length is "+data.length);
		
		pos = skipLine(data, pos);//跳过一行
		for(int i=0;i<secLength;i++){
			int schLength = bytesToShort(data,pos);//区段内方案长度
			pos = skipLine(data, pos);  //跳过一行;
			int n = colorations[i];
			pos = skipLine(data,pos,n*5); //跳过n行
			
			short[][] colors = getColors(data,pos);
			
			ColorationScheme scheme = new ColorationScheme(starts[i],ends[i],colors); 
			colorSchemes[i] = scheme;
			pos = skipLine(data,pos,(schLength-n)*5);//跳过剩余n行
		}
	}
	
	public PPData(short[][] colors){
		colorSchemes = new ColorationScheme[1];
		colorSchemes[0] = new ColorationScheme(0,256,colors);
	}
	
	private short[][] getColors(byte[] data,int pos){
		pos = skipLine(data, pos);  //跳过一行;
		short [][] scheme = new short[3][3];
		for(int r=0;r<3;r++){
			for(int c=0;c<3;c++){
				short value = bytesToShort(data, pos);
				pos = offset(data, pos);
				scheme[r][c] = value;
			}
		}
		return scheme;
	}
	
	/**
	 * byte[]转int
	 * @param bytes
	 * @return
	 */
	private short bytesToShort(byte[] bytes,int start) {
		short value = 0;
		while(bytes[start]>=0x30){
			byte b = bytes[start++];
			int tmp = b&0x0f;
			value = (short) ((value*10)+tmp);
		}
		return value;
	}
	//偏移一位
	private static int offset(byte[] bytes,int pos){
		while(true){
			if(bytes[pos++]<0x30)
				return pos;
		}	
	}

	//跳过一行
	private int skipLine(byte[] bytes,int pos){
		while(true){
			if(bytes[pos++]==0x0a)
				return pos;
		}	
	}
	
	/**
	 * 跳过n行
	 * @param bytes
	 * @param pos
	 * @param n
	 * @return
	 */
	private int skipLine(byte[] bytes,int pos,int n){
		int line = 0;
		while(line<n){
			if(bytes[pos++]==0x0a){
				line++;
			}	
		}	
		return pos;
	}

	public ColorationScheme[] getColorSchemes() {
		return colorSchemes;
	}

}
