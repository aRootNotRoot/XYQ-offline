package xyq.system.map;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SeekByteArrayInputStream extends ByteArrayInputStream
{
  public SeekByteArrayInputStream(byte[] buf)
  {
    super(buf);
  }

  public SeekByteArrayInputStream(byte[] buf, int offset, int length) {
    super(buf, offset, length);
  }

  public void seek(int pos) {
    if ((pos < 0) || (pos > this.count)) throw new IndexOutOfBoundsException(pos + ":" + this.count);
    this.pos = pos;
  }

  public long getPosition() {
    return this.pos;
  }

  public void close() {
    this.buf = null;
    this.count = 0;
    System.gc();
  }

  public int readInt() throws IOException {
    int ch1 = read();
    int ch2 = read();
    int ch3 = read();
    int ch4 = read();
    return ch1 + (ch2 << 8) + (ch3 << 16) + (ch4 << 24);
  }
  /**
   * ��ȡ�޷��Ŷ��� 0~65535
   * */
  public short readUnsignedShort() throws IOException {
    int ch1 = read();//��ȡ��һ���ֽ���[��,#01.was ��ȡ14hΪ20]
    int ch2 = read();
    return (short)((ch2 << 8) + ch1);
  }

  public boolean readFully(byte[] buf) throws IOException {
    read(buf);
    return false;
  }
}