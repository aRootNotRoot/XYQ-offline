package xyq.system.assets;

public class JNIHelper {
	
	static{
		System.loadLibrary("JNIHelper");
	}
	
	/**
	 * 地图解码Mask数据
	 */
	public static native void decodeMask(byte[] in, byte[] out);
	
	/**
     * String到ID的Hash映射
     */
    public native static long stringToId(String str);
   

}
