package xyq.system.ingame.fishing;

public class FishPool {
	public int type;
	public int id;
	
	public int power=0;
	public int angle=0;
	public int way=0;
	
	int chanceValue;
	
	public String[] catchTags;
	
	public FishPool(int type,int id,int power,int angle,int way,int chance,String[] tags){
		this.id=id;
		this.type=type;
		
		this.power=power;
		this.angle=angle;
		this.way=way;
		
		this.chanceValue=chance;
		this.catchTags=tags;
	}

	public String getWay() {
		StringBuilder sBuilder=new StringBuilder();
		sBuilder.append("【");
		if(power==FishingGame.xiaoli){
			sBuilder.append("小力");sBuilder.append(",");
		}else if(power==FishingGame.zhongli){
			sBuilder.append("中力");sBuilder.append(",");
		}else if(power==FishingGame.dali){
			sBuilder.append("大力");sBuilder.append(",");
		}
		
		if(angle==FishingGame.zhigan){
			sBuilder.append("直竿");sBuilder.append(",");
		}else if(angle==FishingGame.xiegan){
			sBuilder.append("斜竿");sBuilder.append(",");
		}
		
		if(way==FishingGame.kuaisu){
			sBuilder.append("快速");
		}else if(way==FishingGame.yuhui){
			sBuilder.append("迂回");
		}
		
		sBuilder.append("】");
		return sBuilder.toString();
	}
}
