package xyq.system.assets;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;


/**
 * 客户端资源读取类
 * 
 * @author david
 *
 */
public class AssetReader {

	public static final int TYPE_WDF = 1; // WDF压缩文件
	public static final int TYPE_NML = 2; // 普�?�文�?

	/** WDF索引缓存 */
	private Map<String, Map<Long, WdfIndex>> files = null;

	

	public AssetReader() {
		files = new HashMap<String, Map<Long, WdfIndex>>();

		System.setProperty("org.newdawn.slick.pngloader", "false");
	}

	public void init(String base) {
		initVolume("shape", base);
		initVolume("smap", base);
		initVolume("addon", base);
		initVolume("magic", base);
		initVolume("waddon", base);
		initVolume("misc", base);
		initVolume("wzife", base);
		initVolume("mapani", base);
		initVolume("item", base);
	}

	// 重置
	public void reset() {
		files.clear();
	}

	/*
	 * 建立WDF文件索引
	 */
	public void initVolume(final String name, final String dir) {
		Map<Long, WdfIndex> data = new HashMap<Long, WdfIndex>();
		// 指定目录，进行搜�?
		File file = new File(dir);
		String[] datas = file.list();

		// 遍历�?有文件，添加
		for (int i = 0; i < datas.length; i++) {
			if (datas[i] != null && datas[i].startsWith(name + ".wd") && datas[i].length() == (name.length() + 4)) {
				try {
					indexVolume(data, dir + datas[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		files.put(name, data);
	}

	/*
	 * 文件加载
	 */
	public byte[] read(final String path) {
		return readWdfData(path);
	}

	/*
	 * 根据关键路径，加载WDF文件数据
	 */
	private byte[] readWdfData(final String path) {
		int index = path.indexOf('/');
		String prefix = path.substring(0, index); // 截取文件名字
		String suffix = path.substring(index + 1); // 截取文件路径
		
		Map<Long, WdfIndex> file = files.get(prefix);
		if(file==null)
			System.err.println("file is null");
		try {
			byte[] data = load(file, suffix);
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 加载分卷文件、建立索�?
	 */
	private void indexVolume(Map<Long, WdfIndex> aCache, String path) throws IOException {
		RandomAccessFile handle = new RandomAccessFile(path, "r");
		byte[] data = null;
		data = new byte[12];
		handle.read(data);
		int count = ByteUtil.getInt(data, 4);// 文件标识
		int offset = ByteUtil.getInt(data, 8);// 索引文件位置
		handle.seek(offset);
		data = new byte[count * 16];
		handle.read(data);
		int pos = 0;
		for (int i = 0; i < count; i++) {
			long aUid = ByteUtil.getUnsignInt(data, pos);
			long aOffset = ByteUtil.getUnsignInt(data, pos += 4);
			long aSize = ByteUtil.getUnsignInt(data, pos += 4);
			long aSpace = ByteUtil.getUnsignInt(data, pos += 4);
			pos += 4;
			aCache.put(Long.valueOf(aUid), new WdfIndex(aUid, aOffset, aSize, aSpace, handle, path));
		}
	}

	/*
	 * 根据WDF文件索引，加载数�?
	 */
	private byte[] load(Map<Long, WdfIndex> data, String path) throws IOException {
		// 如果带有后缀�?
		long key = 0;
		int aPointIndex = path.indexOf('.');
		if (aPointIndex == -1) {
			// 直接加载
			key = Long.valueOf(Long.valueOf(path, 16));
		} else {
			// 转换路径
			path = path.replaceAll("/", "\\\\");
			key = JNIHelper.stringToId(path);
		}
		WdfIndex index = data.get(key);

		if (index == null) {
			return null;
		}
		// 获取字节�?
		return index.getData();
	}

	public static class WdfIndex {
		long id, offset, size, space, key;
		RandomAccessFile handle;
		String filename;

		WdfIndex(long id, long offset, long size, long space, RandomAccessFile handle, String filename) {
			this.id = id;
			this.size = size;
			this.space = space;
			this.offset = offset;
			this.handle = handle;
			this.filename = filename;
		}

		byte[] getData() throws IOException {
			byte[] b = new byte[(int) size];
			// 加锁，保证指针的�?致�??
			synchronized (handle) {
				handle.seek(offset);
				handle.read(b);
			}
			return b;
		}
	}

}
