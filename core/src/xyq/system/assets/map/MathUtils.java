package xyq.system.assets.map;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 数学公式
 * @author user
 *
 */
public class MathUtils {

	//正弦函数
	public static double getSinV(Random random){
		return Math.sin(random.nextInt());
	}
	
	//Float转Int
	public static int getInt(float in){
		BigDecimal b = new BigDecimal(in);
		return b.setScale(0,BigDecimal.ROUND_HALF_UP).intValue(); 
	}
	
	//得到0至(max-1)的随机数
	public static int getRandom(int max,Random random){
		if(max<=0){
			return 0;
		}
		return random.nextInt(max);
	}
	
	//得到min至(max-1)的随机数
	public static int getRandom(int min, int max,Random random) {
		int num = max - min;
		if(num<=0){
			return min;
		}
		num = random.nextInt(num);
		return num + min;
	}
	
	//百分比随机数千位
	public static float percent(int min,int max,Random random){
		int value = getRandom(min,max,random);
		return value/1000.f;
	}
	
	//计算几率
	public static boolean getRandomRate(int rate,Random random){
		if(rate<0){ 
			return false; 
		}
		if(rate>=100){
			return true;
		}
		
		int num = random.nextInt(100);
		if(num<rate){
			return true;
		}else{
			return false;
		}
	}
	
	public static int getDirect(float x,float y,float tx,float ty){
		if(tx-x<0){
			return ty-y<0?2:1;
		}
		else{
			return ty-y<0?3:0;
		}
	}
	
	public static int getIndexByArrayRegion(int[] array,int value){
		if(array==null||array.length==0)
			return 0;
		
		for(int i=0;i<array.length;i++){
			if(value<=array[i]){
				return i;
			}
		}
		return 0;
	}
}
