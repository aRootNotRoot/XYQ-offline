package xyq.system.assets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


/**
 * 客户端资源读取类
 * 感谢大胃王xc_wangwang
 * 感谢梦想的奴隶
 *
 */
public class WdfAssetsReader {

	public static final int TYPE_WDF = 1; // WDF压缩文件
	public static final int TYPE_NML = 2; //普通文件

	/** WDF索引缓存 */
	private Map<String, Map<Long, WdfIndex>> files = null;
	/** WDF中文注释索引缓存 */
	public Map<String, Map<String, String>> files_ini_index = null;

	public WdfAssetsReader() {
		files = new HashMap<String, Map<Long, WdfIndex>>();
		files_ini_index= new HashMap<String, Map<String, String>>();
	}

	public void init(String base) {
		initPack("shape", base);
		initPack("smap", base);
		initPack("chat", base);
		initPack("addon", base);
		initPack("magic", base);
		initPack("waddon", base);
		initPack("misc", base);
		initPack("wzife", base);
		initPack("mapani", base);
		initPack("item", base);
		initPack("goods", base);
	}

	// 重置
	public void reset() {
		files.clear();
	}

	/*
	 * 建立WDF文件索引
	 */
	public void initPack(final String name, final String dir) {
		Gdx.app.log("[ XYQ ]","[WdfAssetsReader] -> 准备读取Wdf压缩包索引");
		Map<Long, WdfIndex> data = new HashMap<Long, WdfIndex>();
		// 指定目录，进行搜索
		FileHandle[] files_yrl = Gdx.files.local("resWDF/").list();
		ArrayList<String> files_url = new ArrayList<String>();
		//System.out.println("当前目录为"+Gdx.files.internal("resWDF/").file().getAbsolutePath());
		for(FileHandle file:files_yrl){
			files_url.add(file.file().getName());
		}
		for (int i = 0; i < files_url.size(); i++) {
			if (files_url.get(i) != null && files_url.get(i).startsWith(name + ".wd")) {
				try {
					
					if(!files_url.get(i).endsWith("ini")){//剔除ini文件
						files_ini_index.put(files_url.get(i), new HashMap<String, String>());
						indexVolume(data, dir + files_url.get(i));
					}
					else
						ini_indexVolume(Gdx.files.internal("resWDF/").file().getAbsolutePath()+"\\"+files_url.get(i),files_url.get(i));
					Gdx.app.log("[ XYQ ]","索引文件:"+files_url.get(i));
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
	public byte[] readByName(final String wdfName,final String WasName) {
		String wasID=getWasIDByName(wdfName,WasName);
		if(wasID==null){
			System.err.println("wasID获取失败，请检查代码，比如多一点空格");
		}
		String path=wdfName.substring(0, wdfName.length()-4)+"/"+wasID;
		return readWdfData(path);
	}

	/*
	 * 根据关键路径，加载WDF文件数据
	 */
	private byte[] readWdfData(final String path) {
		int index = path.indexOf('/');
		String prefix = path.substring(0, index); // 截取文件名字
		String suffix = path.substring(index + 1); // 截取文件路径
		//System.err.println("prefix  is :"+prefix);
		//System.err.println("suffix  is :"+suffix);
		//System.err.println("-------------------");
		Map<Long, WdfIndex> file = files.get(prefix);
		if(file==null){
			Gdx.app.error("[ XYQ ]","[WdfAssetsReader]readWdfData错误！file is null");
		}
		try {
			byte[] data = load(file, suffix);
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 加载对应文件的Ini配置信息
	 * @throws FileNotFoundException 
	 * */
	void ini_indexVolume(String iniFileAbPath,String wdfName) throws FileNotFoundException{
		Gdx.app.log("[ XYQ ]","加载ini："+iniFileAbPath);
		//FileInputStream in = new FileInputStream(iniFileAbPath,"UTF-8");
		try {
			InputStreamReader reader=new InputStreamReader(new FileInputStream(iniFileAbPath), "GB2312");
		
			BufferedReader br = new BufferedReader(reader);
	           
            String str = null;
           
            while((str = br.readLine()) != null) {
                 if("[Resource]".equals(str));
                	 //System.out.println(str);
                 else{
                	 String[] oneLine=str.split("=");
                	 String wdffile=wdfName.substring(0,wdfName.length()-4);
                	 files_ini_index.get(wdffile).put(oneLine[1], oneLine[0]);
                 }
            }
           
            br.close();
            
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String getWasIDByName(String Wdf,String name){
		String loadedID=files_ini_index.get(Wdf).get(name);
		if(files_ini_index.get(Wdf)==null){
			Gdx.app.error("[ XYQ ]","WdfAssetsReader > getWasIDByName >[ "+Wdf+" ]查找的索引表为空");
		}
		if(loadedID==null||loadedID.equals("")){
			Gdx.app.error("[ XYQ ]","WdfAssetsReader > getWasIDByName >无法根据[ "+Wdf+" ]和[ "+name+" ]查找到ID");
		}
		return loadedID;
	}
	/*
	 * 加载分卷文件、建立索引
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
	 * 根据WDF文件索引，加载数据
	 */
	private byte[] load(Map<Long, WdfIndex> data, String path) throws IOException {
		// 如果带有后缀
		long key = 0;
		int aPointIndex = path.indexOf('.');
		//System.out.println("load wdf jni");
		//System.out.println(path);
		if (aPointIndex == -1) {
			// 直接加载
			key = Long.valueOf(Long.valueOf(path, 16));
			//System.out.println("16 key is:"+key);
		} else {
			// 转换路径
			path = path.replaceAll("/", "\\\\");
			key = JNIHelper.stringToId(path);
			//System.out.println("key is:"+key);
		}
		WdfIndex index = data.get(key);

		if (index == null) {
			return null;
		}
		// 获取字节数据
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
			// 加锁，保证指针的一致
			synchronized (handle) {
				handle.seek(offset);
				handle.read(b);
			}
			return b;
		}
	}

}
