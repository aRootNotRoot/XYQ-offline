package xyq.system.assets;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

/**
 * pp文件的数据结构(用于精灵染色)
 * @author wpaul
 *
 */
public class PPCData {
	
	private ColorationScheme[] colorSchemes;
	/**调色板数据
	 * @param data 从was中读取的byte数据
	 * @param colorations 数组，保存的换色方案，第i块用几号方案
	 * */
	public PPCData(byte[] data,int[] colorations){
		int pos = 0;
		//short secLength = (short) (bytesToShort(data,pos)%1000);notify();
		
		String dataStr=new String(data);
		String[] lineStr=dataStr.split("\n");
		ArrayList<Short> ppData=new ArrayList<Short>(); 
		
		for(int line=0;line<lineStr.length;line++){
			String[] temp=lineStr[line].split(" ");
			for(int i=0;i<temp.length;i++)
				ppData.add(Short.valueOf(temp[i]));
		}
		/*
		System.out.print("--------------ppdata----------- ");
		for(int j=0;j<ppData.size();j++){
			System.out.print(ppData.get(j)+" ");
		}
		*/
		short secLength=ppData.get(0);
		if(colorations.length!=secLength){
			Gdx.app.error("[XYQ]","[PPCData 警告] 传递的换色方案控制数组长度"+colorations.length+"与读取的换色数据分块长度不一致"+secLength);
		}

		colorSchemes = new ColorationScheme[secLength];
		
		/*
		pos = offset(data,pos);  //偏移一位
		int[] starts = new int[secLength];
		int[] ends = new int[secLength];
		for(int i=0;i<secLength;i++){
			starts[i] = bytesToShort(data,pos);
			pos = offset(data,pos);
			ends[i] = bytesToShort(data,pos);
		}
		*/
		int[] starts = new int[secLength];
		int[] ends = new int[secLength];
		pos=1;
		for(int i=0;i<secLength;i++){
			starts[i]=ppData.get(pos);
			ends[i]=ppData.get(pos+1);
			pos++;
		}
		/*
		System.out.println("--------------start----------- ");
		for(int j=0;j<starts.length;j++){
			System.out.print(starts[j]+" ");
		}
		System.out.println("--------------ends----------- ");
		for(int j=0;j<ends.length;j++){
			System.out.print(ends[j]+" ");
		}
		
		System.err.println("secLength is "+secLength);
		System.err.println("str is "+new String(data));
		System.err.println("data length is "+data.length);
		 */
		
		//TODO 根据传进来的换色控制数组，读取对应位置的颜色信息
		//当前pos指向换色分块最后一位
		pos++;
		////当前pos指向第一换色方案分块长度
		for(int i=0;i<secLength;i++){
			int schLength = ppData.get(pos);//区段内方案长度
			int wantColorSC=colorations[i];//欲选择的换色方案
			int schTotalLength=schLength*9;//当前换色分块总计长度
			pos++;//pos指向换色矩阵第一位
			int schEndPos=pos+schTotalLength;
			pos+=wantColorSC*9;//指向当前欲换色矩阵第一位
			short[][] colors=new short[3][3];
			for(int r=0;r<3;r++){
				for(int c=0;c<3;c++){
					colors[r][c]=ppData.get(pos);
					pos++;
				}
			}
			ColorationScheme scheme = new ColorationScheme(starts[i],ends[i],colors); 
			colorSchemes[i] = scheme;
			//跳过余下数据
			pos=schEndPos;
		}
		
		/*
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
		*/
	}
	
	public PPCData(short[][] colors){
		colorSchemes = new ColorationScheme[1];
		colorSchemes[0] = new ColorationScheme(0,256,colors);
	}
	

	
	
	public ColorationScheme[] getColorSchemes() {
		return colorSchemes;
	}

}
