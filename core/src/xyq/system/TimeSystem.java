package xyq.system;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import xyq.system.utils.TextUtil;


public class TimeSystem {
	
	final String[] timeName={
			"子时","丑时","寅时","卯时","辰时","巳时",
			"午时","未时","申时","酉时","戌时","亥时"
	};
	final float[] lightDist={
			0.5f,0.5f,0.5f,0.7f,0.9f,1f,
			1f,1f,1f,0.9f,0.7f,0.5f
	};
	
	long TimeCount=0l;
	long lastActTime=0l;
	LinkedList<TSTask> taskList;
	int i=0;
	long temp=0l;
	
	
	Calendar c;
	Date d;
	public TimeSystem()
	{
		taskList=new LinkedList<TSTask>();
		refreshTimeData();
		d = new Date();
	}
	public void refreshTimeData(){
		c = Calendar.getInstance();
	}
	public int getYear(){
		refreshTimeData();
		return c.get(Calendar.YEAR); 
	}
	public int getMonth(){
		refreshTimeData();
		return c.get(Calendar.MONTH)+1; 
	}
	public int getDay(){
		refreshTimeData();
		return c.get(Calendar.DATE); 
	}
	public int getHour(){
		refreshTimeData();
		return c.get(Calendar.HOUR_OF_DAY); 
	}
	public int getMinute(){
		refreshTimeData();
		return c.get(Calendar.MINUTE); 
	}
	public int getSecond(){
		refreshTimeData();
		return c.get(Calendar.SECOND); 
	}
	public String getWeek(){
		d = new Date();
		return TextUtil.getWeek(d);
	}
	/**获取游戏内时间
	 * @return 子丑寅卯
	 * */
	public String getGameTime(){
		int min=getMinute();
		int[] mintime={5,10,15,20,25,30,35,40,45,50,55};
		int time=11;
		for(int i=0;i<mintime.length;i++){
			if(min<mintime[i]){
				time=i;
				break;
			}
		}
		return timeName[time];
	}
	/**现在是否是游戏里的晚上*/
	public boolean isGameNight(){
		int min=getMinute();
		int[] mintime={5,10,15,20,25,30,35,40,45,50,55};
		int time=11;
		for(int i=0;i<mintime.length;i++){
			if(min<mintime[i]){
				time=i;
				break;
			}
		}
		if(time>=3&&time<=8)
			return false;
		else
			return true;
	}
	/**根据游戏时间返回游戏环境光大小*/
	public float getALight(){
		int min=getMinute();
		int[] mintime={5,10,15,20,25,30,35,40,45,50,55};
		int time=11;
		for(int i=0;i<mintime.length;i++){
			if(min<mintime[i]){
				time=i;
				break;
			}
		}
		return lightDist[time];
	}
	public void add(long timedelta) {
		TimeCount+=timedelta;
		//Gdx.app.log("timecount+", timedelta+"");
		if(TimeCount>=Long.MAX_VALUE-1000l)
			TimeCount=0;
		check();
		//System.out.println(TimeCount);
	}
	/**添加一个时间任务
	 * @param atime 多少毫秒以后执行
	 * @param TE 要执行的代码
	 * */
	public void addTask(long atime,TimeEventAdapter TE)
	{
		TSTask tt=new TSTask(TimeCount,atime,TE);
		taskList.add(tt);
		tt=null;
	}
	/**添加一个带名字的时间任务
	 * @param name 时间任务的名称
	 * @param atime 多少毫秒以后执行
	 * @param TE 要执行的代码
	 * */
	public void addTask(String name,long atime,TimeEventAdapter TE)
	{
		TSTask tt=new TSTask(name,TimeCount,atime,TE);
		taskList.add(tt);
		tt=null;
	}
	/**添加一个带名字的循环时间任务
	 * @param name 时间任务的名称
	 * @param atime 多少毫秒间隔执行
	 * @param TE 要执行的代码
	 * */
	public void addCycleTask(String name,long atime,TimeEventAdapter TE)
	{
		TSTask tt=new TSTask(name,TimeCount,atime,true,TE);
		taskList.add(tt);
		tt=null;
	}
	/**删除一个带名字的时间任务
	 * @param name 时间任务的名称
	 * @param 注意  只会删除第一个
	 * */
	public void delTask(String name)
	{
		if(taskList.size()==0)
			return;
		for(i=0;i<taskList.size();i++)
		{
			if(taskList.get(i).taskName.equals(name))
			{
				taskList.remove(i);
				return;
			}
		}
	}
	public void check()
	{
		if(taskList.size()==0)
			return;
		for(i=0;i<taskList.size();i++)
		{
			if(lastActTime>TimeCount)//如果循环了时间表，时间变成了0
			{
				temp=(Long.MAX_VALUE-1000l)+TimeCount;//真实时间间隔
				if(temp>=taskList.get(i).atferTime)
				{
					taskList.get(i).TEA.act();
					lastActTime=TimeCount;
					if(taskList.get(i).cycle){//如果是循环的任务类型
						taskList.get(i).beginTime=TimeCount;
					}else{
						taskList.remove(i);
					}
				}
			}
			else
			{
				if(TimeCount-taskList.get(i).beginTime>=taskList.get(i).atferTime)
				{
					taskList.get(i).TEA.act();
					lastActTime=TimeCount;
					if(taskList.get(i).cycle){//如果是循环的任务类型
						taskList.get(i).beginTime=TimeCount;
					}else{
						taskList.remove(i);
					}
				}
			}
		}
	}
	
	public class TSTask{
		long beginTime;
		long atferTime;
		String taskName="none";
		TimeEventAdapter TEA;
		
		boolean cycle=false;
		public TSTask(long btime,long atime,TimeEventAdapter TE)
		{
			this.beginTime=btime;
			this.atferTime=atime;
			this.TEA=TE;
		}
		public TSTask(String name,long btime,long atime,TimeEventAdapter TE)
		{
			this.taskName=name;
			this.beginTime=btime;
			this.atferTime=atime;
			this.TEA=TE;
		}
		public TSTask(String name,long btime,long atime,boolean cycle,TimeEventAdapter TE)
		{
			this.taskName=name;
			this.beginTime=btime;
			this.atferTime=atime;
			this.TEA=TE;
			this.cycle=cycle;
		}
	}
	
	
}
