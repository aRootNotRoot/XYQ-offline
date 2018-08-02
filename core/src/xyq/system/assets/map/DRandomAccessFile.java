package xyq.system.assets.map;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * 参考BufferedInputStream 非线程安全
 * 
 * @author user
 * 
 */
public class DRandomAccessFile extends RandomAccessFile {

	public static final long INT_MAX_VALUE = 0xffffffffL;

	private static int defaultBufferSize = 8192;

	protected volatile byte buf[];

	private int pos;

	private int count;

	public DRandomAccessFile(File file) throws FileNotFoundException {
		this(file, defaultBufferSize);
	}

	public DRandomAccessFile(File file, int size) throws FileNotFoundException {
		super(file, "r");
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		buf = new byte[size];
		pos = 0;
		count = 0;
	}

	public int read() throws IOException {
		if (pos >= count) {
			fill();
			if (pos >= count)
				return -1;
		}
		return buf[pos++] & 0xff;
	}

	@Override
	public int read(byte b[]) throws IOException {
		int len = b.length;
		if (pos >= count) {
			fill();
		}
		if(len>defaultBufferSize){
			int temp = count-pos;
			System.arraycopy(buf,pos,b,0,temp);
			super.read(b,temp,len-count+pos);
			pos=0;
			count=0;
		}else if(len<=count-pos){
			System.arraycopy(buf,pos,b,0,len);
			pos+=len;
		}else{
			int a = count-pos;
			int a2 = len-a;
			System.arraycopy(buf,pos,b,0,a);
			pos+=a;
			fill();
			System.arraycopy(buf,pos,b,a,a2);
			pos+=a2;
		}
		return len;
	}

	private void fill() throws IOException {
		byte[] buffer = buf;
		pos = 0;
		count = pos;
		int n = read(buffer, pos, buffer.length - pos);
		if (n > 0)
			count = n + pos;
	}

	public void seek(long p) throws IOException {
		super.seek(p);
		pos=0;
		count=0;
	}
	
	public int skipBytes(int n) throws IOException {
        int p;
        int newpos;

        if (n <= 0) {
            return 0;
        }
        p = pos;
        newpos = p + n;
        if(newpos<count){
        	pos = newpos;
        	return n;
        }else{
        	seek(newpos-count+getFilePointer());
        }
        

        /* return the actual number of bytes skipped */
        return (int) (newpos - p);
    }

	public final int getByte() throws IOException {
		int ch = this.read();
		if (ch < 0)
			throw new EOFException();
		return ch;
	}

	public short getShort() throws IOException {
		int ch1 = this.read();
		int ch2 = this.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (short) ((ch1) + (ch2 << 8));
	}

	public final int getInt() throws IOException {
		int ch1 = this.read();
		int ch2 = this.read();
		int ch3 = this.read();
		int ch4 = this.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
	}

	public final long getUnsignInt() throws IOException {
		return (long) (getInt() & INT_MAX_VALUE);
	}
	
	public void clear(){
		Arrays.fill(buf,(byte)0);
	}

}
