package xyq.system.utils;

import java.util.ArrayList;
import java.util.Random;

public class RandomUT {
	static Random ra =new Random();
	/**
	 * 根据设定概率返回命中结果
	 * @param chance 命中几率(比如,chance=50,就意味着命中率为1/50)
	 * */
	public static boolean isGot(int chance)
	{
		if(chance<=0)
			return false;
		int i=ra.nextInt(chance+1)+1;
		if(i==1)
			return true;
		else
			return false;
	}
	static void random(int begin,int end)
	{
		if(end<=0)
			return ;
		for (int i=0;i<30;i++)
		{
			System.out.println(ra.nextInt(end+1)+begin);
		}
	}
	/**获取一个随机数，范围在begin到end之间。需要begin小于等于end
	 * 【支持负数】
	 * */
	public static int getRandom(int begin,int end)
	{/*
		if(begin==end)
			return end;
		end--;
		if(end<0||begin<0||end<begin)
			return 0;
		return ra.nextInt(end+1)+begin;*/
		return (int)Math.round(Math.random() * (end - begin) + begin);
	}
	/**获取随机数，返回float类型（不为小数）*/
	public static float getRandom(float begin,float end)
	{/*
		if(begin==end)
			return end;
		end--;
		if(end<0||begin<0||end<begin)
			return 0;
		return ra.nextInt(end+1)+begin;*/
		return (float)Math.round(Math.random() * (end - begin) + begin);
	}
	/**将某个数随机分解，如，10分3次可能是3,3,4
	 * @param time 分解数量，分解成几个数
	 * @param number 待分解的数
	 * */
	public static int[] getDevide(int time, int number){
		int[] answer=new int[time];
		for(int i=0;i<answer.length;i++){
			answer[i]=0;
		}
		for(int i=0;i<number;i++){
			int idex=getRandom(0, time-1);
			answer[idex]++;
		}
		return answer;
    }
	/**将某个数随机分解，如，10分3次可能是3,3,4
	 * @param time 分解数量，分解成几个数
	 * @param number 待分解的数
	 * @param base 基数，最少为多少
	 * */
	public static int[] getDevide(int time, int number,int base){
		int[] answer=new int[time];
		for(int i=0;i<answer.length;i++){
			answer[i]=base;
		}
		for(int i=0;i<number-base*time;i++){
			int idex=getRandom(0, time-1);
			answer[idex]++;
		}
		return answer;
    }
	/**不好用*/
	public static float getRandomF(float begin,float end)
	{/*
		if(begin==end)
			return end;
		end--;
		if(end<0||begin<0||end<begin)
			return 0;
		return ra.nextInt(end+1)+begin;*/
		float num = begin + (float)(Math.random() * (end-begin+1));
		return num;
	}
	/**根据一个概率表，命中一个索引值返回。
	 * 概率表需要正序，比如25 50 65 80 90 98 104 109 113 116 416
	 * @return int 命中第几个，0为开头
	 * */
	public static int probabilityShot(ArrayList<Integer> chanceTable){
		int chancePoint=getRandom(0, chanceTable.get(chanceTable.size()-1));
		int shot=-1;
		int mi=0;
		for(int i=0;i<chanceTable.size();i++){
			if(i==chanceTable.size()-1){
				return i;
			}
			int ma=chanceTable.get(i);
			if(chancePoint>=mi&&chancePoint<=ma){
				shot=i;
				break;
			}
			mi=ma;
		}
		return shot;
	}
	/**根据一个概率表，命中一个索引值返回。
	 * 概率表需要正序，比如25 50 65 80 90 98 104 109 113 116 416
	 * @param chancePoint 概率数字，用来判断命中第几个索引
	 * @return int 命中第几个，0为开头
	 * */
	public static int probabilityShot(ArrayList<Integer> chanceTable,int chancePoint){
		int shot=-1;
		int mi=0;
		for(int i=0;i<chanceTable.size();i++){
			if(i==chanceTable.size()-1){
				return i;
			}
			int ma=chanceTable.get(i);
			if(chancePoint>=mi&&chancePoint<=ma){
				shot=i;
				break;
			}
			mi=ma;
		}
		return shot;
	}
	public static boolean randomBoolean() {
		int i=getRandom(0, 1);
		if(i==1)
			return true;
		return false;
	}
	public static void main(String args[])
	{
		int chance=5;
		int testCount=20;
		int hit=0;
		boolean a=false;
		for (int i=0;i<testCount;i++)
		{
			a=isGot(chance);
			System.out.println(a);
			if(a)
				hit++;
		}
		System.out.println("---------------------");
		System.out.println("当前概率为1/"+chance);
		System.out.println("测试 ["+testCount+"] 次");
		System.out.println("命中 ["+hit+"] 次");
		System.out.println("实际命中率为:"+(float)hit/(float)testCount);
		
		System.out.println(getRandom(5,30));
		System.out.println("---------------------");
		for(int z=0;z<100;z++)
			System.out.println(getRandom(-4.5f,1.21f));
		System.out.println("---------------------");
		int[] ii=getDevide(3,10);
		for(int z=0;z<ii.length;z++)
			System.out.print(ii[z]+" - ");
		System.out.println("\n");
		System.out.println("---------------------");
		int y=2;
		float zzz=0.2f;
		System.out.println(y*zzz);
	}
	private static String string = "abcdefghijklmnopqrstuvwxyz123456790";   

	/**获取length长度的随机字符串*/
	public static String getRandomStr(int length) {
		StringBuffer sb = new StringBuffer();
	    int len = string.length();
	    for (int i = 0; i < length; i++) {
	        sb.append(string.charAt(getRandom(0,len-1)));
	    }
	    return sb.toString();
	}
}
