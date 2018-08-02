package xyq.system.assets;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 字节操作帮助类
 * @author eyu
 *
 */
public class ByteUtil {

	public static final long INT_MAX_VALUE = 0xffffffffL;

	public static int getByte(byte[] b, int start) {
		return b[start++] & 0xff;
	}

	public static int getInt(byte[] b, int start) {
		int ch1 = b[start++] & 0xff;
		int ch2 = b[start++] & 0xff;
		int ch3 = b[start++] & 0xff;
		int ch4 = b[start++] & 0xff;
		return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
	}

	public static short getShort(byte[] b, int start) {
		int ch1 = b[start++] & 0xff;
		int ch2 = b[start++] & 0xff;
		return (short) ((ch1) + (ch2 << 8));
	}

	public static long getUnsignInt(byte[] b, int start) throws IOException {
		return (long) (getInt(b, start) & INT_MAX_VALUE);
	}

	public static byte[] intToByte(int i) {
        byte[] abyte0 = new byte[4];
        abyte0[0] = (byte) (0xff & i);
        abyte0[1] = (byte) ((0xff00 & i) >> 8);
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);

        return abyte0;

    }

    public  static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 0xFF;
        addr |= ((bytes[1] << 8) & 0xFF00);
        addr |= ((bytes[2] << 16) & 0xFF0000);
        addr |= ((bytes[3] << 24) & 0xFF000000);
        return addr;

    }
    
    public static void setInt(ByteBuffer buf,int pos,int val){
		buf.position(pos);
		buf.put(ByteUtil.intToByte(val));
    }
    
    public static int getInt(ByteBuffer buf,int pos){
		buf.position(pos);
		byte[] b = new byte[4];
		buf.get(b);
		return ByteUtil.bytesToInt(b);
    }
}
